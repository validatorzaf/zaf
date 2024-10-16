package cz.zaf.premis;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.layers.wf.wf00_09.Rule01;
import cz.zaf.premisvalidator.layers.wf.wf00_09.Rule02;

public class PremisWfTest extends PremisValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/PREMIS";
    
    @Test
    void testWf_01_OK01() {
    	testWf("sdilene-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] {});
    }

    @Test
    void testWf_01_Chyba01() {
    	testWf("02-KONTROLA SPRAVNOSTI XML/01-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE },
                new String[] { Rule01.CODE });
    }

    private void testWf(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testPremis(PATH_TESTDATA + "/" + path,
				ValidationLayers.WELL_FORMED,
				status, oks, fails);
	}	

}
