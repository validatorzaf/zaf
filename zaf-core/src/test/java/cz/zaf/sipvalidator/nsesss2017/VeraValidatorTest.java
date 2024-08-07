package cz.zaf.sipvalidator.nsesss2017;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import cz.zaf.sipvalidator.pdfa.ValidationResult;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
import cz.zaf.validator.TestHelper;

class VeraValidatorTest extends SipValidatorTestBase {


    static Path workDirPath;

    @Test
    void testAutoCleanup() throws InterruptedException {
        try {
            // Set short Vera life time
            VeraValidatorProxy.setInactivityDelay(100, TimeUnit.MILLISECONDS);

            VeraValidatorProxy vvp = VeraValidatorProxy.init();
            assertNotNull(vvp);
            
            // test invalid PDF
            Path invalidSrcPath = TestHelper.getPath(SipValidatorK07Test.PATH_DATA_K07
                    + "/04-chyba1/komponenty/soubor.pdf");
            ValidationResult vr1 = VeraValidatorProxy.validate(invalidSrcPath);
            assertFalse(vr1.isCompliant());
            assertTrue(StringUtils.isNotBlank(vr1.getErrorMessage()));

            // test valid PDF
            Path validSrcPath = TestHelper.getPath(SipValidatorK07Test.PATH_DATA_K07
                    + "/04-OK1/komponenty/soubor.pdf");
            ValidationResult vr2 = VeraValidatorProxy.validate(validSrcPath);
            assertTrue(vr2.isCompliant());
            assertNull(vr2.getErrorMessage());

            Thread.sleep(200);
            assertFalse(VeraValidatorProxy.isActiveServer());

            // test opetovneho startu
            Path invalidSrcPath2 = TestHelper.getPath(SipValidatorK07Test.PATH_DATA_K07
                    + "/04-OK1/komponenty/soubor.txt");
            ValidationResult vr3 = VeraValidatorProxy.validate(invalidSrcPath2);
            assertFalse(vr3.isCompliant());
            assertTrue(StringUtils.isNotBlank(vr3.getErrorMessage()));

            VeraValidatorProxy.destroy();


        } catch (IOException e) {
            fail("Fail to initialize proxy", e);
        } finally {
            // Set back default Vera life time
            VeraValidatorProxy.setInactivityDelay(10, TimeUnit.MINUTES);
        }
    }

}
