package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule01;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule02;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule03;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule04;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class NSTest extends AipValidatorTestBase {

	static public final String PATH_TESTDATA = "testdata/DAAIP2024";
	
    @Test
    void testNs_01_OK01() {
    	testNs("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] {});
    }	
	
    // spolecny test pro Rule02 - chybi NS
    @Test
    void testNs_01_CHYBA01() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule03.CODE, Rule04.CODE },
                new String[] { Rule01.CODE, Rule02.CODE });
    }	

    @Test
    void testNs_01_CHYBA02() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/01-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE });
    }	

    @Test
    void testNs_02_CHYBA01() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/02-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule02.CODE });
    }	

    @Test
    void testNs_03_CHYBA01() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/03-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] { Rule03.CODE, Rule04.CODE });
    }	

    @Test
    void testNs_03_CHYBA02() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/03-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testNs_03_CHYBA03() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/03-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testNs_04_CHYBA01() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/04-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }	

    @Test
    void testNs_04_CHYBA02() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/04-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }
    
    @Test
    void testNs_04_CHYBA03() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/04-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }	
    
    @Test
    void testNs_04_CHYBA04() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/04-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }	

    @Test
    void testNs_04_CHYBA05() {
    	testNs("04-KONTROLA JMENNYCH PROSTORU XML/04-CHYBA05/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }	
    
    private void testNs(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.NAMESPACE,
				status, oks, fails);
	}
	
}
