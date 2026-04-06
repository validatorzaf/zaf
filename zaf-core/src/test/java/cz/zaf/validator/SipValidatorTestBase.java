package cz.zaf.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationStatus;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.SipLoader;

/**
 * Spolecna zakladni impl. testu pro NSESSS2017 i NSESSS2024
 */
public abstract class SipValidatorTestBase {

    private static final Logger log = LoggerFactory.getLogger(SipValidatorTestBase.class);

    protected static Path workDirPath;

    /**
     * Nacte SIP z testovacich dat
     *
     * @param sipPath
     * @param expLoadType
     *            Ocekavany typ nahrani
     * @return
     */
    protected SipLoader loadSip(String sipPath, LoadType expLoadType) {
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

    /**
     * Overi vysledek validace pro danou uroven kontroly.
     * Pokud vysledek chybi, vypise chyby z posledni urovne a selze.
     */
    protected void checkValidationResult(String path,
                                         SipInfo sipInfo,
                                         ValidationLayerType typUrovneKontroly,
                                         ValidationStatus stavKontroly,
                                         String[] pravidlaOk,
                                         String[] pravidlaChybna) {
        ValidationLayerResult result = sipInfo.getUrovenKontroly(typUrovneKontroly);
        if (result == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Missing result for SIP: ").append(path)
              .append(", urovenKontroly: ").append(typUrovneKontroly)
              .append("\n");
            var cnt = sipInfo.getValidationLayerResults().size();
            if (cnt > 0) {
                // try to read last validation
                result = sipInfo.getKontrola(cnt - 1);
                var ruleResults = result.getPravidla();
                for (var ruleResult : ruleResults) {
                    sb.append("Failed rule in previous layer: ").append(ruleResult.getId()).append(", ")
                      .append(ruleResult.getVypisChyby())
                      .append(", misto: ")
                      .append(ruleResult.getMistoChyby())
                      .append("\n");
                }
            }
            fail(sb.toString());
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
                    .forEach(File::delete);
        } catch (IOException e) {
            fail(() -> "Failed to remove working directory: " + workDirPath);
        }
    }
}
