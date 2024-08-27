package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.val.val00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL04Test extends EadValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/AP2023";

    @Test
    void testVAL_01_OK01() {
    	testVal("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }
    
    @Test
    void testVal_01_chyba01() {
    	testVal("04-KONTROLA PROTI SCHEMATU/01_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    @Test
    void testVal_01_chyba02() {
    	testVal("04-KONTROLA PROTI SCHEMATU/01_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    private void testVal(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
    	testEad(PATH_TESTDATA + "/" + path,
    			AP2023Profile.ARCH_DESC,
    			ValidationLayers.VALIDATION,
    			status,
    			oks, fails);

}

}
