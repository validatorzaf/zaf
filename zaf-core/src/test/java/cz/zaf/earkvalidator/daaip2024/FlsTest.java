package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule01;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule02;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule03;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule04;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule05;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class FlsTest extends AipValidatorTestBase  {
	static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void testFls_01_OK01() {
    	testFls("OK1/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule05.CODE },
                new String[] {});
    }	

    @Test
    void testFls_01_OK02() {
    	testFls("OK2/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule05.CODE },
                new String[] {});
    }	

    @Test
    void testFls_01_OK03() {
    	testFlsSipChange("OK3/04ccc520-c5a9-4c9f-a83f-28d91fd37aa7",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule04.CODE },
                new String[] {});
    }	

    @Test
    void testFls_01_CHYBA01() {
    	testFls("07-KONTROLA SOUBORU/01-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE },
                new String[] { Rule01.CODE });
    }	

    @Test
    void testFls_02_CHYBA01() {
    	testFls("07-KONTROLA SOUBORU/02-CHYBA01/8b58672e-7893-45c3-ab37-2b133389329d",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE },
                new String[] { Rule02.CODE });
    }

    private void testFls(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP,
				ValidationLayers.FILES,
				status, oks, fails);
	}
    
    private void testFlsSipChange(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testAip(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.SIP_CHANGE,
				ValidationLayers.FILES,
				status, oks, fails);
	}    
}
