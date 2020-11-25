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

    static public VeraValidatorProxy init() throws IOException {
        if (instance != null) {
            return instance;
        }

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

        return new VeraValidatorProxy(veraAppPath, javaBin);
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
                    log.error("Failed to read validation result.", e);
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
            log.error("Failed to run validator.", e);
            vr.setError("Výjimka při ověření: " + e.toString());
        }
        return vr;
    }

    private String readOutput(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException {
        SAXParser parser = factory.newSAXParser();
        AtomicBoolean isCompliant = new AtomicBoolean();
        final StringBuilder sb = new StringBuilder();

        parser.parse(inputStream, new DefaultHandler() {
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
        });

        return isCompliant.get() ? null : sb.length() > 0 ? sb.toString() : "Nerozpoznaná chyba";
    }

}
