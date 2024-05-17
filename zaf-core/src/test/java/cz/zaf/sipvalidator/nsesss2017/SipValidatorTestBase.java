package cz.zaf.sipvalidator.nsesss2017;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.SipLoader;

/**
 * Zakladni impl. testu
 *
 */
public abstract class SipValidatorTestBase {

    static final Logger log = LoggerFactory.getLogger(SipValidatorTestBase.class);

    static Path workDirPath;

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

    // set fixed date for all tests
    static {
        System.setProperty(KontrolaNsess2017Context.ZAF_VALIDATION_DATE, "2022-06-01");
    }

    Path getPath(String relativePath) throws UnsupportedEncodingException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(relativePath);
        if (url == null) {
            fail("Failed to locate sip, path: " + relativePath);
        }

        String filePath = java.net.URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
        return new File(filePath).toPath();
    }

    /**
     * 
     * @param sipPath
     * @param expLoadType
     *            Ocekavany typ nahrani
     * @return
     */
    SipLoader loadSip(String sipPath, LoadType expLoadType) {
        try {
            Path sourceDirPath = getPath(sipPath);
            Path absPath = sourceDirPath.toAbsolutePath();

            SipLoader sipLoader = new SipLoader(absPath, workDirPath.normalize().toString(), false);

            // kontrola zpusobu nahrani
            if (expLoadType != null) {
                assertEquals(sipLoader.getSip().getLoadType(), expLoadType,
                             () -> {
                                 return "SIP: " + sipPath + ", Očekávaný typ nahrání: " + expLoadType
                                         + ", skutečný typ: " + sipLoader.getSip().getLoadType();
                             });
            }
            return sipLoader;
        } catch (UnsupportedEncodingException e) {
            fail("Failed to decode path: " + e);
            return null;
        }

    }

    void testPackage(String path, LoadType expLoadType,
                     ProfilValidace profilValidace,
                     TypUrovenKontroly typUrovneKontroly,
                     ValidationStatus stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        log.debug("Loading SIP: {}, loadType: {}, urovenKontroly: {}", path, expLoadType, typUrovneKontroly);

        SipLoader sipLoader = loadSip(path, expLoadType);
        SipValidator sipValidator = new SipValidator(profilValidace, Collections.emptyList());
        sipValidator.validate(sipLoader);
        SipInfo sipInfo = sipLoader.getSip();

        ValidationLayerResult vysledek = sipInfo.getUrovenKontroly(typUrovneKontroly);

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

    @BeforeAll
    static void pripravaTestu() {
        workDirPath = Paths.get("rozbaleno-testy");
        if (Files.exists(workDirPath)) {
            // delete old
            uklidTestu();
        }
        try {
            Files.createDirectories(workDirPath);
        } catch (IOException e) {
            fail(() -> "Failed to create working directory: " + workDirPath);
        }
    }

    @AfterAll
    static void uklidTestu() {
        try {
            Files.walk(workDirPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    //.peek(System.out::println)
                    .forEach(File::delete);
        } catch (IOException e) {
            fail(() -> "Failed to remove working directory: " + workDirPath);
        }

    }

}
