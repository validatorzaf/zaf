package cz.zaf.sipvalidator.pdfa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class VeraValidatorProxy {

    final static Logger log = LoggerFactory.getLogger(VeraValidatorProxy.class);

    static VeraValidatorProxy instance = null;

    private Path veraAppPath;

    private String javaBin;

    final private ExecutorService executor = Executors.newSingleThreadExecutor();

    final SAXParserFactory factory = SAXParserFactory.newInstance();

    private VeraValidatorProxy(final Path veraAppPath,
                               final String javaBin) {
        this.veraAppPath = veraAppPath;
        this.javaBin = javaBin;

    }

    synchronized static public void destroy() {
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

        instance = new VeraValidatorProxy(veraAppPath, javaBin);
        return instance;
    }


    public ValidationResult validate(Path pdfPath) {
        
        
        ProcessBuilder pb = new ProcessBuilder(javaBin, "-classpath",
                this.veraAppPath.toAbsolutePath().toString(),
                "org.verapdf.apps.GreenfieldCliWrapper",
                "--format", "xml",
                pdfPath.toAbsolutePath().toString());

        pb.redirectErrorStream(true);

        ValidationResult vr = new ValidationResult();
        try {
            final Process p = pb.start();

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
