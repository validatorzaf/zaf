package cz.zaf.sipvalidator.pdfa;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class VeraValidatorProxy {

    /**
     * Name of system property allows to use vera in server mode
     */
    public static final String ZAF_VERA_SERVERMODE = "zaf.vera.servermode";

    /**
     * Name of system property allows to use Vera installed locally
     */
    public static final String ZAF_VERA_PATH = "zaf.vera.path";

    // 10 minutes
    public static int inactivityDelay = 10;
    public static TimeUnit inactivityTimeunit = TimeUnit.MINUTES;

    final static Logger log = LoggerFactory.getLogger(VeraValidatorProxy.class);

    static VeraValidatorProxy instance = null;

    private Path veraAppPath;

    private String javaBin;

    final private ExecutorService executor = Executors.newSingleThreadExecutor();

    private List<Path> deleteFiles;

    static private BufferedReader activeProcessReader;

    private final static SAXParserFactory factory = SAXParserFactory.newInstance();

    static private boolean useServerMode;

    /**
     * Active VeraPDF process
     */
    static private Process activeProcess;

    /**
     * Inactivity timer
     * 
     * Timer is used to cleanup inactive VeraValidator
     */
    static private ScheduledThreadPoolExecutor inactivityExecutor;

    static private Runnable scheduledTask;

    static private int parallelRunCounter = 0;

    private VeraValidatorProxy(final Path veraAppPath,
                               final String javaBin) {
        this.veraAppPath = veraAppPath;
        this.javaBin = javaBin;

    }

    public static int getInactivityDelay() {
        return inactivityDelay;
    }

    public static void setInactivityDelay(final int inactivityDelay,
                                          final TimeUnit inactivityTimeunit) {
        VeraValidatorProxy.inactivityDelay = inactivityDelay;
        VeraValidatorProxy.inactivityTimeunit = inactivityTimeunit;
    }

    public static TimeUnit getInactivityTimeunit() {
        return inactivityTimeunit;
    }

    public static synchronized boolean isActiveServer() {
        return activeProcess != null;
    }

    synchronized static public void destroyActiveProcess() {
        if (activeProcess != null) {
            activeProcess.destroy();
            activeProcess = null;
            activeProcessReader = null;
        }
    }

    synchronized static public void destroy() {
        if (inactivityExecutor != null) {
            try {
                inactivityExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
                inactivityExecutor.shutdown();
            } finally {
                inactivityExecutor = null;
            }
        }

        if (instance != null) {
            log.debug("Terminating VeraValidatorProxy");

            try {
                instance.executor.shutdownNow();
                // clean up has to be called after shutdown
                // when all handles are freed
                instance.cleanUp();
            } catch (Exception e) {
                log.error("Filed to clean up VeraValidatorProxy", e);
            }
            instance = null;
        }
    }

    synchronized static public VeraValidatorProxy init() throws IOException {
        if (instance != null) {
            return instance;
        }

        log.debug("Starting VeraValidatorProxy");

        Path veraAppPath;
        String veraPathParam = System.getProperty(ZAF_VERA_PATH);
        if (StringUtils.isNotBlank(veraPathParam)) {
            // Use external validator
            veraAppPath = Paths.get(veraPathParam);
            if (!Files.isRegularFile(veraAppPath)) {
                String errorMsg = new StringBuilder()
                        .append("Incorrect value of parameter '")
                        .append(ZAF_VERA_PATH)
                        .append("': ")
                        .append(veraPathParam)
                        .append(". Valid path to the .jar file is expected.").toString();
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        } else {
            // Use internal validator
            InputStream veraAppStream = VeraValidatorProxy.class.getResourceAsStream("/verapdf/greenfield-apps.jar");
            if (veraAppStream == null) {
                log.error("Failed to load verapdf from resources");
                throw new RuntimeException("Failed to load verapdf from resources");
            }
            // extract to temp directory
            File greenFieldAppJar = File.createTempFile("zaf-greenfield-apps", ".jar");
            greenFieldAppJar.deleteOnExit();
            veraAppPath = greenFieldAppJar.toPath();

            Files.copy(veraAppStream, veraAppPath, StandardCopyOption.REPLACE_EXISTING);
        }
        log.debug("Using VeraPDF: {}", veraAppPath);


        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";

        String serverMode = System.getProperty(ZAF_VERA_SERVERMODE);

        if (serverMode != null && (serverMode.equals("0") ||
                serverMode.equalsIgnoreCase("false") || serverMode.equalsIgnoreCase("disabled"))) {
            log.debug("VeraValidatorProxy will not use serverMode");
            useServerMode = false;
        } else {
            log.debug("VeraValidatorProxy will use serverMode");
            useServerMode = true;

        }

        instance = new VeraValidatorProxy(veraAppPath, javaBin);

        Validate.isTrue(inactivityExecutor == null);
        
        inactivityExecutor = new ScheduledThreadPoolExecutor(1);

        return instance;
    }


    static synchronized public ValidationResult validate(Path pdfPath) {
        
        Validate.notNull(instance);

        if (useServerMode) {
            ValidationResult ret = instance.validateOnServer(pdfPath);
            if (ret != null) {
                return ret;
            }
        }

        // try to execute directly
        return instance.singleRun(pdfPath);
    }

    private Process createProcess(String pdfPath, boolean serverMode) throws IOException {
        List<String> params = new ArrayList<>(10);
        params.addAll(Arrays.asList(javaBin, "-classpath",
                                    this.veraAppPath.toAbsolutePath().toString(),
                                    "org.verapdf.apps.GreenfieldCliWrapper",
                                    "--format", "xml"));
        if (serverMode) {
            params.add("--servermode");
        }
        params.add(pdfPath);

        // Run vera
        ProcessBuilder pb = new ProcessBuilder(params);
        // Error is merged with standard output
        pb.redirectErrorStream(true);
        return pb.start();
    }

    synchronized private ValidationResult validateOnServer(Path pdfPath) {
        Validate.notNull(inactivityExecutor);

        prepareServerValidation();

        ValidationResult vr = new ValidationResult();
        try {

            String pdfAbsPath = pdfPath.toAbsolutePath().toString();
            if (activeProcess == null || !activeProcess.isAlive()) {
                activeProcess = createProcess(pdfAbsPath, true);
                InputStreamReader sr = new InputStreamReader(activeProcess.getInputStream(), "utf-8");
                activeProcessReader = new BufferedReader(sr);
            } else {
                OutputStream paramStream = activeProcess.getOutputStream();
                try {
                    log.debug("Request to process file {}", pdfAbsPath);

                    OutputStreamWriter sw = new OutputStreamWriter(paramStream, "UTF-8");
                    sw.append(pdfAbsPath);
                    sw.append("\n");
                    sw.flush();
                    paramStream.flush();
                } catch (IOException e) {
                    log.error("Failed to write parameters to VeraPDF in server mode");
                    return null;
                }

            }

            final CountDownLatch cdt = new CountDownLatch(1);
            executor.execute(() -> {
                try {
                    String errorMsg = readServerOutput(activeProcessReader);
                    log.debug("Server output: {}", errorMsg);
                    if (errorMsg == null) {
                        vr.setCompliant(true);
                    } else {
                        vr.setError(errorMsg);
                    }
                } catch (Exception e) {
                    log.debug("Failed to read validation result.", e);
                    vr.setError("Výjimka při čtení výsledku ověření: " + e);
                } finally {
                    cdt.countDown();
                }
            });

            // wait to read results
            if (!cdt.await(5, TimeUnit.MINUTES)) {
                log.error("Failed to read results, timeout");
            }

            // try to delete temp files
            cleanUp();

        } catch (IOException | InterruptedException e) {
            log.debug("Failed to run validator.", e);
            vr.setError("Výjimka při ověření: " + e.toString());
        } finally {
            finishServerValidation();
        }
        return vr;
    }

    private void cleanUp() {
        // delete temp files
        if (deleteFiles != null) {
            List<Path> undeletedFiles = null;
            for (Path deleteFile : deleteFiles) {
                try {
                    Files.delete(deleteFile);
                } catch (Exception e) {
                    if (undeletedFiles == null) {
                        undeletedFiles = new ArrayList<>(deleteFiles.size());
                    }
                    undeletedFiles.add(deleteFile);
                }
            }
            deleteFiles = undeletedFiles;
        }
    }

    static synchronized private void prepareServerValidation() {
        if (scheduledTask != null) {
            Validate.isTrue(parallelRunCounter >= 0);

            if (parallelRunCounter == 0) {
                inactivityExecutor.remove(scheduledTask);
            }
        } else {
            Validate.isTrue(parallelRunCounter == 0);
            // initialize for first time
            scheduledTask = new Runnable() {

                @Override
                public void run() {
                    destroyActiveProcess();
                }
            };
        }
        parallelRunCounter++;
    }

    static synchronized private void finishServerValidation() {
        log.debug("Enable scheduled task");

        Validate.isTrue(parallelRunCounter > 0);
        Validate.notNull(inactivityExecutor);

        parallelRunCounter--;
        if (parallelRunCounter == 0) {
            inactivityExecutor.schedule(scheduledTask,
                                        inactivityDelay,
                                        inactivityTimeunit);
        }
    }

    /**
     * Read output from server mode
     * 
     * @param processReader
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private String readServerOutput(BufferedReader processReader) throws IOException, ParserConfigurationException,
            SAXException {
        if (processReader == null) {
            throw new RuntimeException("Process reader cannot be null");
        }
        while (true) {
            // read next line from VeraPDF
            String line = processReader.readLine();
            log.debug("VeraPDF output: " + line);
            if (line.endsWith(".xml")) {
                Path outputPath = Paths.get(line);
                if (Files.isReadable(outputPath)) {
                    try (InputStream xmlInputStream = Files.newInputStream(outputPath)) {
                        return readOutput(xmlInputStream);
                    } finally {
                        addDeleteFile(outputPath);
                    }
                } else {
                    return "Failed to read file: " + outputPath;
                }

            }
        }
    }

    /**
     * Add file for later removal
     * 
     * Method is called from tread reading results
     * 
     * @param fileToDelete
     */
    private void addDeleteFile(Path fileToDelete) {
        if (this.deleteFiles == null) {
            this.deleteFiles = new ArrayList<Path>(1);
        }
        this.deleteFiles.add(fileToDelete);
    }

    private ValidationResult singleRun(Path pdfPath) {
        
        ProcessBuilder pb = new ProcessBuilder(javaBin, "-classpath",
                this.veraAppPath.toAbsolutePath().toString(),
                "org.verapdf.apps.GreenfieldCliWrapper",
                "--format", "xml",
                pdfPath.toAbsolutePath().toString());

        pb.redirectErrorStream(true);

        ValidationResult vr = new ValidationResult();
        try {
            final Process p = createProcess(pdfPath.toAbsolutePath().toString(), false);

            final CountDownLatch cdt = new CountDownLatch(1);
            executor.execute(() -> {
                try {
                    String errorMsg = readOutput(p.getInputStream());
                    if (errorMsg == null) {
                        vr.setCompliant(true);
                    } else {
                        vr.setError(errorMsg);
                    }
                } catch (Exception e) {
                    log.debug("Failed to read validation result.", e);
                    vr.setError("Výjimka při čtení výsledku ověření: " + e);
                } finally {
                    cdt.countDown();
                }
            });

            if (!p.waitFor(5, TimeUnit.MINUTES)) {
                p.destroy();
                return vr;
            }
            // wait to read results
            if (!cdt.await(5, TimeUnit.MINUTES)) {
                log.error("Failed to read results, timeout");
            }
        } catch (IOException | InterruptedException e) {
            log.debug("Failed to run validator.", e);
            vr.setError("Výjimka při ověření: " + e.toString());
        }
        return vr;

    }

    private String readOutput(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException {
        SAXParser parser = factory.newSAXParser();
        AtomicBoolean isCompliant = new AtomicBoolean();
        final StringBuilder sb = new StringBuilder();

        DefaultHandler dh = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException {
                if (qName.equals("validationReport")) {
                    String isCompliantValue = attributes.getValue("isCompliant");
                    if (isCompliantValue != null) {
                        if (isCompliantValue.equals("true")) {
                            isCompliant.set(true);
                        } else {
                            sb.append("Neodpovídá standardu: ");
                            String flavourValue = attributes.getValue("profileName");
                            if (flavourValue != null) {
                                sb.append(flavourValue);
                            } else {
                                sb.append("neznámý");
                            }
                        }
                    }
                }
                super.startElement(uri, localName, qName, attributes);
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXException {
                throw new SAXException("Failed to pare", e);
            }
        };

        parser.parse(inputStream, dh);

        String result;
        if (isCompliant.get()) {
            result = null;
        } else {
            if (sb.length() > 0) {
                result = sb.toString();
            } else {
                result = "Nerozpoznaná chyba";
            }
        }

        return result;
    }

}
