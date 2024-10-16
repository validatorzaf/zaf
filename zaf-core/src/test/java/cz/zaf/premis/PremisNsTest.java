package cz.zaf.premis;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule01;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule02;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule03;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule04;

public class PremisNsTest extends PremisValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/PREMIS";
    
    @Test
    void testNs_01_OK01() {
    	testNs("sdilene-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] {});
    }

    @Test
    void testNs_01_Chyba01() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/01-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule03.CODE, Rule04.CODE },
                new String[] { Rule01.CODE, Rule02.CODE });
    }

    @Test
    void testNs_01_Chyba02() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/01-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE });
    }

    @Test
    void testNs_02_Chyba01() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/02-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule02.CODE });
    }

    @Test
    void testNs_03_Chyba01() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/03-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] { Rule03.CODE, Rule04.CODE });
    }

    @Test
    void testNs_04_Chyba01() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/04-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE  });
    }

    @Test
    void testNs_04_Chyba02() {
    	testNs("03-KONTROLA JMENNYCH PROSTORU XML/04-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE  });
    }

    private void testNs(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testPremis(PATH_TESTDATA + "/" + path,
				ValidationLayers.NAMESPACE,
				status, oks, fails);
	}	

}
