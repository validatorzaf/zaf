package cz.zaf.validator;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationStatus;

public abstract class TestHelper {

    private static final Logger log = LoggerFactory.getLogger(TestHelper.class);

    static {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
        root.setLevel(Level.ALL);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(Level.ALL);
        }
    }

    public static Path getPath(String relativePath) throws UnsupportedEncodingException {
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        URL url = classLoader.getResource(relativePath);
        if (url == null) {
            fail("Failed to locate sip, path: " + relativePath);
        }

        String filePath = java.net.URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
        return new File(filePath).toPath();
    }

    static public void checkTestResult(String path,
                                       ValidationStatus stavKontroly, ValidationLayerResult vysledek,
                                       String[] pravidlaOk, String[] pravidlaChybna) {
        if (stavKontroly != null) {
            if (!vysledek.getValidationStatus().equals(stavKontroly)) {
                // detail selhanych kontrol
                if (vysledek.getValidationStatus() == ValidationStatus.ERROR) {
                    // doslo k neocekavanemu selhani -> vypis selhanych
                    boolean errorLogged = false;
                    for (String pravidloOk : pravidlaOk) {
                        RuleValidationError prav = vysledek.getPravidlo(pravidloOk);
                        if (prav != null) {
                            log.error("Chybujici pravidlo: {}, vypisChyby: {}, mistoChyby: {}", pravidloOk,
                                      prav.getVypisChyby(), prav.getMistoChyby(), prav.getMistoChyby());
                            errorLogged = true;
                        }
                    }
                    if(!errorLogged) {
                        // vypis vsech chybujicich kontrol
                        for(RuleValidationError prav: vysledek.getPravidla()) {
                            log.error("Chybujici pravidlo: {}, vypisChyby: {}, mistoChyby: {}",
                                      prav.getId(),
                                      prav.getVypisChyby(), prav.getMistoChyby(), prav.getMistoChyby());
                            
                        }
                    }
                }
    
                fail(() -> "SIP: " + path + ", Očekávaný stav: "
                    + stavKontroly + ", výsledný stav: " + vysledek.getValidationStatus());
            }
        }
    
        // overeni pravidel OK
        if (pravidlaOk != null) {
            for (int i = 0; i < pravidlaOk.length; i++) {
                String kodPravidla = pravidlaOk[i];
                RuleValidationError pravidlo = vysledek.getPravidlo(kodPravidla);
                if (pravidlo != null) {
                    fail(() -> "SIP: " + path + ", Pravidlo: " + kodPravidla
                        + ", ocekavano OK, ale selhalo, misto chyby: " + pravidlo.getMistoChyby()
                        + ", popis chyby: " + pravidlo.getVypisChyby());
                }
            }
        }
    
        // overeni pravidel s chybou
        if (pravidlaChybna != null) {
            for (int i = 0; i < pravidlaChybna.length; i++) {
                String kodPravidla = pravidlaChybna[i];
                RuleValidationError pravidlo = vysledek.getPravidlo(kodPravidla);
                if (pravidlo == null) {
                    fail(() -> "SIP: " + path + ", Pravidlo: " + kodPravidla
                            + ", ocekavana Chyba, ale bylo OK");
                }
            }
        }
    }

}
