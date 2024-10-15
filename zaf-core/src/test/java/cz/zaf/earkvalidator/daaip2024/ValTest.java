package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.val.val00_09.Rule01;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class ValTest extends AipValidatorTestBase {

	static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testVal_01_OK01() {
    	testVal("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }	
	
    @Test
    void testVal_01_CHYBA01() {
    	testVal("05-KONTROLA PROTI SCHEMATU/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }	
    
    @Test
    void testVal_01_CHYBA02() {
    	testVal("05-KONTROLA PROTI SCHEMATU/01-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    private void testVal(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.VALIDATION,
				status, oks, fails);
	}
}
