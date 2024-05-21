package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.fvl01.fvl00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL01Test extends EadValidatorTestBase {

    static public final String PATH_DATA_K01 = "testdata/AP2023";

    @Test
    void testFVL01_01_OK01() {
        testVfl("OK1-MINIMAL/EAD.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }

    private void testVfl(String path,
                         ValidationStatus status,
                         String[] oks,
                         String[] fails) {
        testEad(PATH_DATA_K01 + "/" + path,
                AP2023Profile.ARCH_DESC,
                ValidationLayers.BASIC_FILE_FORMAT,
                status,
                oks, fails);

    }

}
