package cz.zaf.premis;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.layers.enc.enc00_09.Rule01;

public class PremisEncTest extends PremisValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/PREMIS";
    @Test
    void testEnc_01_OK01() {
    	testEnc("sdilene-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }

    private void testEnc(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testPremis(PATH_TESTDATA + "/" + path,
				ValidationLayers.ENCODING,
				status, oks, fails);
	}	
}
