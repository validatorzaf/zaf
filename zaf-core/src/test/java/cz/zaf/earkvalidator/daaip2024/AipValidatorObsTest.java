package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule03;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule04;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule05;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule06;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule07;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule08;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule09;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule10;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule11;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule12;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule13;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule14;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule15;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule16;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule17;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule18;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule19;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule20;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule21;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule22;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule23;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule24;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule25;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule26;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule27;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule28;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule29;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class AipValidatorObsTest extends AipValidatorTestBase {

	static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testObs_01_OK01() {
    	testObs("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
						Rule10.CODE, Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
						Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE },
                new String[] {});
    }	
	
    @Test
    void testObs_01_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule01.CODE });
    }	

    @Test
    void testObs_01_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/01-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule01.CODE });
    }	

	
    @Test
    void testObs_02_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/02-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule02.CODE });
    }	

    @Test
    void testObs_03_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/03-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testObs_03_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/03-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule03.CODE });
    }	

    @Test
    void testObs_04_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/04-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule04.CODE });
    }	

    @Test
    void testObs_04_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/04-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule04.CODE });
    }


    @Test
    void testObs_05_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/05-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule05.CODE });
    }	

    @Test
    void testObs_05_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/05-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule05.CODE });
    }

    @Test
    void testObs_06_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/06-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE },
                new String[] { Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE });
    }	

    @Test
    void testObs_07_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/07-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule07.CODE });
    }	

    @Test
    void testObs_08_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/08-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule09.CODE },
                new String[] { Rule08.CODE });
    }	

    @Test
    void testObs_09_CHYBA01() {
    	testObs("06-KONTROLA OBSAHU/09-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }	

    @Test
    void testObs_09_CHYBA02() {
    	testObs("06-KONTROLA OBSAHU/09-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,	
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
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
