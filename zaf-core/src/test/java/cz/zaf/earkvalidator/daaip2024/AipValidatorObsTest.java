package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule03;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule04;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class AipValidatorObsTest extends AipValidatorTestBase {

	static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testObs_01_OK01() {
    	testObs("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] {});
    }	
	
    @Test
    void testObs_01_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule01.CODE });
    }	

    @Test
    void testObs_01_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/01-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule01.CODE });
    }	

	
    @Test
    void testObs_02_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/02-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule02.CODE });
    }	

    @Test
    void testObs_03_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/03-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testObs_03_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/03-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testObs_04_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/04-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }	

    @Test
    void testObs_04_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/04-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }	

    private void testObs(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.OBSAH,
				status, oks, fails);
	}
}
