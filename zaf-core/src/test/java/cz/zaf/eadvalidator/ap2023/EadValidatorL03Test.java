package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule03;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL03Test extends EadValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/AP2023";
    
    @Test
    void testNS_01_OK01() {
    	testNs("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] {});
    }

    @Test
    void testNS_01_chyba01() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/01_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }

    @Test
    void testNS_01_chyba02() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/01_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }
    
    @Test
    void testNS_02_chyba01() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/02_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule02.CODE });
    }

    @Test
    void testNS_03_chyba01() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/03_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] { Rule03.CODE });
    }

    @Test
    void testNS_03_chyba02() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/03_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] { Rule03.CODE });
    }

    private void testNs(String path,
                         ValidationStatus status,
                         String[] oks,
                         String[] fails) {
        testEad(PATH_TESTDATA + "/" + path,
                AP2023Profile.ARCH_DESC,
                ValidationLayers.NAMESPACE,
                status,
                oks, fails);

    }

}
