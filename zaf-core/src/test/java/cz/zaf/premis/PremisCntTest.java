package cz.zaf.premis;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule03;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule04;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule05;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule06;
import cz.zaf.premisvalidator.profile.PremisProfile;

public class PremisCntTest extends PremisValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/PREMIS";
    
    @Test
    void testCnt_01_OK01() {
    	testPkgInfo("sdilene-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
                new String[] {});
    }

    @Test
    void testCnt_02_OK01() {
    	testMetadata("sdilene-ok2.xml",
                ValidationStatus.OK,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
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

    @Test
    void testCnt_03_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/03-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE },
                new String[] { Rule03.CODE });
    }

    @Test
    void testCnt_03_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/03-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE },
                new String[] { Rule03.CODE });
    }

    @Test
    void testCnt_04_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/04-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }

    @Test
    void testCnt_04_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/04-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }

    @Test
    void testCnt_04_Chyba03() {
    	testPkgInfo("05-KONTROLA OBSAHU/04-chyba3.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }

    @Test
    void testCnt_05_Chyba01() {
    	testMetadata("05-KONTROLA OBSAHU/05-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule05.CODE });
    }

    @Test
    void testCnt_05_Chyba02() {
    	testMetadata("05-KONTROLA OBSAHU/05-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule05.CODE });
    }

    @Test
    void testCnt_05_Chyba03() {
    	testMetadata("05-KONTROLA OBSAHU/05-chyba3.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE },
                new String[] { Rule05.CODE });
    }


    @Test
    void testCnt_06_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/06-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE  },
                new String[] { Rule06.CODE });
    }
    
    @Test
    void testCnt_06_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/06-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE  },
                new String[] { Rule06.CODE });
    }

    private void testPkgInfo(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
    	Function<String, RepresentationInfo> repReader = repId -> {
    		if("uuid-a13c7fb3-caad-4505-8a3e-144fc6dca744".equals(repId)) {
	    		return new RepresentationInfo("uuid-a13c7fb3-caad-4505-8a3e-144fc6dca744",
	    				"submission", null);
    		}
    		return null;
    	};
    	
		testPremis(PATH_TESTDATA + "/" + path,
				ValidationLayers.OBSAH,
				status, 
				PremisProfile.PACKAGE_INFO, oks, fails, repReader);
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

