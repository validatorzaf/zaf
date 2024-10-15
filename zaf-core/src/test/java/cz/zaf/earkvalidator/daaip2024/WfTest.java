package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.Properties;
import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.wf.wf00_09.Rule01;
import cz.zaf.earkvalidator.layers.wf.wf00_09.Rule02;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class WfTest extends AipValidatorTestBase {
	
    static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testWf_01_OK01() {
    	testWf("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] {});
    }

    @Test
    void testWf_01_CHYBA01() {
    	testWf("03-KONTROLA SPRAVNOSTI XML/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }


    @Test
    void testWf_02_CHYBA01() {
    	String maxSizeOrig = System.getProperty(Properties.ZAF_METS_MAX_SIZE);
		System.setProperty(Properties.ZAF_METS_MAX_SIZE, "1KB");
    	
    	testWf("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE },
                new String[] { Rule02.CODE });
    	if(maxSizeOrig!=null) {
    		System.setProperty(Properties.ZAF_METS_MAX_SIZE, maxSizeOrig);
    	} else {
			System.clearProperty(Properties.ZAF_METS_MAX_SIZE);
    	}    	
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
