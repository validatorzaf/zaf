package cz.zaf.premis;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.layers.val.val00_09.Rule01;
import cz.zaf.premisvalidator.profile.PremisProfile;

public class PremisValTest extends PremisValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/PREMIS";
    
    @Test
    void testVal_01_OK01() {
    	testVal("sdilene-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }

    @Test
    void testVal_01_Chyba01() {
    	testVal("04-KONTROLA PROTI SCHEMATU/01-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    private void testVal(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testPremis(PATH_TESTDATA + "/" + path,
				ValidationLayers.VALIDATION,
				status, 
				PremisProfile.METADATA, oks, fails);
	}	

}
