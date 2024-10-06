package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.wf.wf00_09.Rule01;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class AipValidatorWfTest extends AipValidatorTestBase {
	
    static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testWf_01_OK01() {
    	testWf("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }


    @Test
    void testWf_01_CHYBA01() {
    	testWf("03-KONTROLA SPRAVNOSTI XML/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    private void testWf(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.WELL_FORMED,
				status, oks, fails);
	}
    
}
