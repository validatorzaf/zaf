package cz.zaf.sipvalidator.nsesss2017;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.validator.TestHelper;

/**
 * Zakladni impl. testu
 *
 */
public abstract class SipValidatorTestBase {

    private static final Logger log = LoggerFactory.getLogger(SipValidatorTestBase.class);

    static Path workDirPath;

    // set fixed date for all tests
    static {
        System.setProperty(KontrolaNsessContext.ZAF_VALIDATION_DATE, "2022-06-01");
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
            Path sourceDirPath = TestHelper.getPath(sipPath);
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
                     ValidationStatus stavKontroly,
                     String[] pravidlaOk,
                     String[] pravidlaChybna) {
        testPackage(path, expLoadType, profilValidace, typUrovneKontroly, stavKontroly,
                    pravidlaOk, pravidlaChybna, null);
    }

    void testPackage(String path, LoadType expLoadType,
                     ProfilValidace profilValidace,
                     TypUrovenKontroly typUrovneKontroly,
                     ValidationStatus stavKontroly,
                     String[] pravidlaOk,
                     String[] pravidlaChybna,
                     String[] exclChecks) {
        log.debug("Loading SIP: {}, loadType: {}, urovenKontroly: {}", path, expLoadType, typUrovneKontroly);

        List<String> exclCheckList = exclChecks == null ? Collections.emptyList() : Arrays.asList(exclChecks);

        SipLoader sipLoader = loadSip(path, expLoadType);
        SipValidator sipValidator = new SipValidator(profilValidace, exclCheckList);
        sipValidator.validate(sipLoader);
        SipInfo sipInfo = sipLoader.getSip();

        ValidationLayerResult result = sipInfo.getUrovenKontroly(typUrovneKontroly);
        if (result == null) {
            log.error("Missing result for SIP: {}, urovenKontroly: {}", path, typUrovneKontroly);
            var cnt = sipInfo.getValidationLayerResults().size();
            if (cnt > 0) {
                // try to read last validation
                result = sipInfo.getKontrola(cnt - 1);
                var ruleResults = result.getPravidla();
                for (var ruleResult : ruleResults) {
                    log.error("Failed rule in previouse layer: {}, {}, misto: {}", ruleResult.getId(),
                              ruleResult.getVypisChyby(), ruleResult.getMistoChyby());
                }
            }
            fail("Result is null, path: " + path);
        }

        TestHelper.checkTestResult(path, stavKontroly, result, pravidlaOk, pravidlaChybna);
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
