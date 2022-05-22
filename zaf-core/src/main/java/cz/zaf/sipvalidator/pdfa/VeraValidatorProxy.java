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

    // 5 minutes
    public static int inactivityDelay = 10;
    public static TimeUnit inactivityTimeunit = TimeUnit.MINUTES;

    final static Logger log = LoggerFactory.getLogger(VeraValidatorProxy.class);

    static VeraValidatorProxy instance = null;

    private Path veraAppPath;

    private String javaBin;

    final private ExecutorService executor = Executors.newSingleThreadExecutor();

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
                               final String javaBin,
                               final boolean useServerMode) {
        this.veraAppPath = veraAppPath;
        this.javaBin = javaBin;
        this.useServerMode = useServerMode;

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
            } finally {
                instance = null;
            }
        }
    }

    synchronized static public VeraValidatorProxy init() throws IOException {
        if (instance != null) {
            return instance;
        }

        log.debug("Starting VeraValidatorProxy");
        InputStream veraAppStream = VeraValidatorProxy.class.getResourceAsStream("/verapdf/greenfield-apps.jar");
        if (veraAppStream == null) {
            log.error("Failed to load verapdf from resources");
            throw new RuntimeException("Failed to load verapdf from resources");
        }

        // extract to temp directory
        File greenFieldAppJar = File.createTempFile("zaf-greenfield-apps", ".jar");
        greenFieldAppJar.deleteOnExit();

        Path veraAppPath = greenFieldAppJar.toPath();

        Files.copy(veraAppStream, veraAppPath, StandardCopyOption.REPLACE_EXISTING);

        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";

        String serverMode = System.getProperty(ZAF_VERA_SERVERMODE);

        boolean useServerMode;
        if (serverMode != null && (serverMode == "0" ||
                serverMode == "false" || serverMode == "disabled")) {
            log.debug("VeraValidatorProxy will not use serverMode");
            useServerMode = false;
        } else {
            log.debug("VeraValidatorProxy will use serverMode");
            useServerMode = true;

        }

        instance = new VeraValidatorProxy(veraAppPath, javaBin, useServerMode);

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
        pb.redirectErrorStream(true);
        return pb.start();
    }

    private ValidationResult validateOnServer(Path pdfPath) {
        Validate.notNull(inactivityExecutor);

        prepareServerValidation();

        ValidationResult vr = new ValidationResult();
        try {

            String pdfAbsPath = pdfPath.toAbsolutePath().toString();
            if (activeProcess == null || !activeProcess.isAlive()) {
                activeProcess = createProcess(pdfAbsPath, true);
            } else {
                OutputStream paramStream = activeProcess.getOutputStream();
                try {
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
                    String errorMsg = readServerOutput(activeProcess.getInputStream());
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
        } catch (IOException | InterruptedException e) {
            log.debug("Failed to run validator.", e);
            vr.setError("Výjimka při ověření: " + e.toString());
        } finally {
            finishServerValidation();
        }
        return vr;
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
     * @param inputStream
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private String readServerOutput(InputStream inputStream) throws IOException, ParserConfigurationException,
            SAXException {
        InputStreamReader sr = new InputStreamReader(inputStream, "utf-8");
        BufferedReader reader = new BufferedReader(sr);
        while (true) {
            String line = reader.readLine();
            log.debug("VeraPDF output: " + line);
            if (line.endsWith(".xml")) {
                Path outputPath = Paths.get(line);
                if (Files.isReadable(outputPath)) {
                    try (InputStream xmlInputStream = Files.newInputStream(outputPath)) {
                        return readOutput(xmlInputStream);
                    }
                } else {
                    return "Failed to read file: " + outputPath;
                }

            }
        }
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
                if (qName.equals("validationResult")) {
                    String isCompliantValue = attributes.getValue("isCompliant");
                    if (isCompliantValue != null) {
                        if (isCompliantValue.equals("true")) {
                            isCompliant.set(true);
                        } else {
                            sb.append("Neodpovídá standardu: ");
                            String flavourValue = attributes.getValue("flavour");
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

        return isCompliant.get() ? null : sb.length() > 0 ? sb.toString() : "Nerozpoznaná chyba";
    }

}
