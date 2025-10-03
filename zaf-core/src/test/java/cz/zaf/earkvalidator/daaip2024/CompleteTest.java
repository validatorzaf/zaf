package cz.zaf.earkvalidator.daaip2024;

import org.junit.jupiter.api.Test;

import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class CompleteTest extends AipValidatorTestBase {
	
    static public final String PATH_TESTDATA = "testdata/DAAIP2024";

    @Test
    void test_01_OK01() {
    	testCompleteAip("OK1/8b58672e-7893-45c3-ab37-2b133389329d", true);
    }
    
    @Test
    void test_01_OK02() {
    	testCompleteAip("OK2/8b58672e-7893-45c3-ab37-2b133389329d", true);
    }

    private void testCompleteAip(String path,
    		boolean expectedSuccess) {
		testFull(PATH_TESTDATA + "/" + path,
				DAAIP2024Profile.AIP, 
				expectedSuccess);
	}
}
