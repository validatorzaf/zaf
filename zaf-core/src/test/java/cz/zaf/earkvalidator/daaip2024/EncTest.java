package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.enc.enc00_09.Rule01;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class EncTest extends AipValidatorTestBase {
	
    static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testEnc_01_OK01() {
    	testEnc("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }
    
    @Test
    void testEnc_01_CHYBA01() {
    	testEnc("02-KONTROLA ZNAKOVE SADY/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    @Test
    void testEnc_01_CHYBA02() {
    	testEnc("02-KONTROLA ZNAKOVE SADY/01-CHYBA02/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    @Test
    void testEnc_01_CHYBA03() {
    	testEnc("02-KONTROLA ZNAKOVE SADY/01-CHYBA03/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    @Test
    void testEnc_01_CHYBA04() {
    	testEnc("02-KONTROLA ZNAKOVE SADY/01-CHYBA04/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    private void testEnc(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.ENCODING,
				status, oks, fails);
	}
}
