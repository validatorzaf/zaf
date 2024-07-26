package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.enc.enc00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL01Test extends EadValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/AP2023";

    @Test
    void testKOD_01_OK01() {
    	testEnc("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }

    @Test
    void testKOD_01_CHYBA01() {
    	testEnc("01-KONTROLA ZNAKOVE SADY/01_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    @Test
    void testKOD_01_CHYBA02() {
    	testEnc("01-KONTROLA ZNAKOVE SADY/01_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    @Test
    void testKOD_01_CHYBA03() {
    	testEnc("01-KONTROLA ZNAKOVE SADY/01_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    @Test
    void testKOD_01_CHYBA04() {
    	testEnc("01-KONTROLA ZNAKOVE SADY/01_chyba4.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    private void testEnc(String path,
                         ValidationStatus status,
                         String[] oks,
                         String[] fails) {
        testEad(PATH_TESTDATA + "/" + path,
                AP2023Profile.ARCH_DESC,
                ValidationLayers.ENCODING,
                status,
                oks, fails);

    }

}
