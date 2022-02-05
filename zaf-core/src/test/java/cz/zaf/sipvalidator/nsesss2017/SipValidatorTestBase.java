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

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;
import cz.zaf.sipvalidator.sip.VysledekPravidla;

/**
 * Zakladni impl. testu
 *
 */
public abstract class SipValidatorTestBase {

    Logger log = LoggerFactory.getLogger(SipValidatorTestBase.class);

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

    /**
     * 
     * @param sipPath
     * @param expLoadType
     *            Ocekavany typ nahrani
     * @return
     */
    SipLoader loadSip(String sipPath, LoadType expLoadType) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(sipPath);
        if (url == null) {
            fail("Failed to locate sip, path: " + sipPath);
        }
        String filePath;
        try {
            filePath = java.net.URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
            File file = new File(filePath);
            String sourceDir = file.getAbsolutePath();
            //String sourceDir = "testdata/01 KONTROLA DATA";

            SipLoader sipLoader = new SipLoader(sourceDir, workDirPath.normalize().toString());

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
                     StavKontroly stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        log.debug("Loading SIP: {}, loadType: {}, urovenKontroly: {}", path, expLoadType, typUrovneKontroly);

        SipLoader sipLoader = loadSip(path, expLoadType);
        SipValidator sipValidator = new SipValidator(profilValidace, Collections.emptyList());
        sipValidator.validate(sipLoader);
        SipInfo sipInfo = sipLoader.getSip();

        VysledekKontroly vysledek = sipInfo.getUrovenKontroly(typUrovneKontroly);

        if (stavKontroly != null) {
            if (!vysledek.getStavKontroly().equals(stavKontroly)) {
                // detail selhanych kontrol
                if (vysledek.getStavKontroly() == StavKontroly.CHYBA) {
                    // doslo k neocekavanemu selhani -> vypis selhanych
                    boolean errorLogged = false;
                    for (String pravidloOk : pravidlaOk) {
                        VysledekPravidla prav = vysledek.getPravidlo(pravidloOk);
                        if (prav != null) {
                            log.error("Chybujici pravidlo: {}, vypisChyby: {}, mistoChyby: {}", pravidloOk,
                                      prav.getVypis_chyby(), prav.getMisto_chyby(), prav.getMisto_chyby());
                            errorLogged = true;
                        }
                    }
                    if(!errorLogged) {
                        // vypis vsech chybujicich kontrol
                        for(VysledekPravidla prav: vysledek.getPravidla()) {
                            log.error("Chybujici pravidlo: {}, vypisChyby: {}, mistoChyby: {}",
                                      prav.getId(),
                                      prav.getVypis_chyby(), prav.getMisto_chyby(), prav.getMisto_chyby());
                            
                        }
                    }
                }

                fail(() -> "SIP: " + path + ", Očekávaný stav: "
                    + stavKontroly + ", výsledný stav: " + vysledek.getStavKontroly());
            }
        }

        // overeni pravidel OK
        if (pravidlaOk != null) {
            for (int i = 0; i < pravidlaOk.length; i++) {
                String kodPravidla = pravidlaOk[i];
                VysledekPravidla pravidlo = vysledek.getPravidlo(kodPravidla);
                if (pravidlo != null) {
                    fail(() -> "SIP: " + path + ", Pravidlo: " + kodPravidla
                        + ", ocekavano OK, ale selhalo, misto chyby: " + pravidlo.getMisto_chyby()
                        + ", popis chyby: " + pravidlo.getVypis_chyby());
                }
            }
        }

        // overeni pravidel s chybou
        if (pravidlaChybna != null) {
            for (int i = 0; i < pravidlaChybna.length; i++) {
                String kodPravidla = pravidlaChybna[i];
                VysledekPravidla pravidlo = vysledek.getPravidlo(kodPravidla);
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
