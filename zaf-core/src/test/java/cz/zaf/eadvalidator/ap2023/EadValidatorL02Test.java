package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.wf.wf00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL02Test extends EadValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/AP2023";

    @Test
    void testWF_01_OK01() {
    	testWf("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }

    @Test
    void testWF_01_chyba01() {
    	testWf("02-KONTROLA SPRAVNOSTI XML/01_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    private void testWf(String path,
                         ValidationStatus status,
                         String[] oks,
                         String[] fails) {
        testEad(PATH_TESTDATA + "/" + path,
                AP2023Profile.ARCH_DESC,
                ValidationLayers.WELL_FORMED,
                status,
                oks, fails);

    }

}
