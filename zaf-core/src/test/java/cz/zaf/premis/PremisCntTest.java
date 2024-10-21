package cz.zaf.premis;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.premisvalidator.profile.PremisProfile;

public class PremisCntTest extends PremisValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/PREMIS";
    
    @Test
    void testCnt_01_OK01() {
    	testPkgInfo("sdilene-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }

    @Test
    void testCnt_02_OK01() {
    	testMetadata("sdilene-ok2.xml",
                ValidationStatus.OK,
                new String[] { Rule02.CODE },
                new String[] {});
    }

    @Test
    void testCnt_01_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/01-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule01.CODE });
    }

    @Test
    void testCnt_02_Chyba01() {
    	testMetadata("05-KONTROLA OBSAHU/02-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {  },
                new String[] { Rule02.CODE });
    }

    private void testPkgInfo(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testPremis(PATH_TESTDATA + "/" + path,
				ValidationLayers.OBSAH,
				status, 
				PremisProfile.PACKAGE_INFO, oks, fails);
	}	

    private void testMetadata(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
		testPremis(PATH_TESTDATA + "/" + path,
				ValidationLayers.OBSAH,
				status, 
				PremisProfile.METADATA, oks, fails);
	}	
}

