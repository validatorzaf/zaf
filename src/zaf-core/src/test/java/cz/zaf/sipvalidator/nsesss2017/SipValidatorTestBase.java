package cz.zaf.sipvalidator.nsesss2017;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;

/**
 * Zakladni impl. testu
 *
 */
public abstract class SipValidatorTestBase {
    static Path workDirPath;

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
        SipLoader sipLoader = loadSip(path, expLoadType);
        SipValidator sipValidator = new SipValidator(profilValidace);
        sipValidator.validate(sipLoader);
        SipInfo sipInfo = sipLoader.getSip();

        VysledekKontroly vysledek = sipInfo.getUrovenKontroly(typUrovneKontroly);

        if (stavKontroly != null) {
            assertEquals(vysledek.getStavKontroly(), stavKontroly, () -> "SIP: " + path + ", Očekávaný stav: "
                    + stavKontroly + ", výsledný stav: " + vysledek.getStavKontroly());
        }

        // overeni pravidel OK
        if (pravidlaOk != null) {
            for (int i = 0; i < pravidlaOk.length; i++) {
                String kodPravidla = pravidlaOk[i];
                PravidloKontroly pravidlo = vysledek.getPravidlo(kodPravidla);
                assertNotNull(pravidlo, () -> "SIP: " + path + ", Chybí pravidlo: " + kodPravidla);
                assertEquals(pravidlo.getStav(), true, () -> "SIP: " + path + ", Pravidlo: " + kodPravidla
                        + ", ocekavano OK, ale selhalo");
            }
        }

        // overeni pravidel s chybou
        if (pravidlaChybna != null) {
            for (int i = 0; i < pravidlaChybna.length; i++) {
                String kodPravidla = pravidlaChybna[i];
                PravidloKontroly pravidlo = vysledek.getPravidlo(kodPravidla);
                assertNotNull(pravidlo, () -> "SIP: " + path + ", Chybí pravidlo: " + kodPravidla);
                assertEquals(pravidlo.getStav(), false, () -> "SIP: " + path + ", Pravidlo: " + kodPravidla
                        + ", ocekavana Chyba, ale bylo OK");
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