package cz.zaf.premis;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule03;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule04;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule05;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule06;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule07;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule08;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule09;
import cz.zaf.premisvalidator.layers.obs.obs10_19.Rule10;
import cz.zaf.premisvalidator.layers.obs.obs10_19.Rule11;
import cz.zaf.premisvalidator.layers.obs.obs10_19.Rule12;
import cz.zaf.premisvalidator.layers.obs.obs10_19.Rule13;
import cz.zaf.premisvalidator.layers.obs.obs10_19.Rule14;
import cz.zaf.premisvalidator.profile.PremisProfile;

public class PremisCntTest extends PremisValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/PREMIS";
    
    @Test
    void testCnt_01_OK01() {
    	testPkgInfo("sdilene-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
						Rule10.CODE, Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE },
                new String[] {});
    }

    @Test
    void testCnt_01_OK02() {
    	testPkgInfo("sdilene-ok3.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
						Rule10.CODE, Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE },
                new String[] {});
    }

    @Test
    void testCnt_02_OK01() {
    	testMetadata("sdilene-ok2.xml",
                ValidationStatus.OK,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
						Rule10.CODE, Rule11.CODE, Rule13.CODE, Rule14.CODE },
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
    
    @Test
    void testCnt_07_Chyba01() {
    	testMetadata("05-KONTROLA OBSAHU/07-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
                new String[] { Rule07.CODE });
    }

    @Test
    void testCnt_07_Chyba02() {
    	testMetadata("05-KONTROLA OBSAHU/07-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
                new String[] { Rule07.CODE });
    }
    
    @Test
    void testCnt_08_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/08-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE  },
                new String[] { Rule08.CODE });
    }

    @Test
    void testCnt_08_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/08-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE  },
                new String[] { Rule08.CODE });
    }

    @Test
    void testCnt_09_Ok01() {
    	testMetadata("05-KONTROLA OBSAHU/09-ok1.xml",
                ValidationStatus.OK,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] {  });
    }

    @Test
    void testCnt_09_Chyba01() {
    	testMetadata("05-KONTROLA OBSAHU/09-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }

    @Test
    void testCnt_09_Chyba02() {
    	testMetadata("05-KONTROLA OBSAHU/09-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }

    @Test
    void testCnt_08_Chyba03() {
    	testPkgInfo("05-KONTROLA OBSAHU/08-chyba3.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE  },
                new String[] { Rule08.CODE });
    }

    @Test
    void testCnt_10_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/10-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testCnt_10_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/10-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testCnt_10_Chyba03() {
    	testPkgInfo("05-KONTROLA OBSAHU/10-chyba3.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }
    @Test
    void testCnt_10_Chyba04() {
    	testPkgInfo("05-KONTROLA OBSAHU/10-chyba4.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }
    @Test
    void testCnt_10_Chyba05() {
    	testPkgInfo("05-KONTROLA OBSAHU/10-chyba5.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule10.CODE });
    }

    @Test
    void testCnt_11_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/11-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE },
                new String[] { Rule11.CODE });
    }
    @Test
    void testCnt_11_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/11-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE },
                new String[] { Rule11.CODE });
    }
    @Test
    void testCnt_11_Chyba03() {
    	testPkgInfo("05-KONTROLA OBSAHU/11-chyba3.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE },
                new String[] { Rule11.CODE });
    }
    
    @Test
    void testCnt_12_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/12-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE, Rule11.CODE },
                new String[] { Rule06.CODE, Rule12.CODE });
    }
    
    @Test
    void testCnt_12_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/12-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE, Rule11.CODE },
                new String[] { Rule06.CODE, Rule12.CODE });
    }

    @Test
    void testCnt_12_Chyba03() {
    	testPkgInfo("05-KONTROLA OBSAHU/12-chyba3.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE, Rule11.CODE },
                new String[] { Rule12.CODE });
    }

    @Test
    void testCnt_13_Chyba01() {
    	testPkgInfo("05-KONTROLA OBSAHU/13-chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE, Rule11.CODE, Rule12.CODE },
                new String[] { Rule13.CODE });
    }

    @Test
    void testCnt_13_Chyba02() {
    	testPkgInfo("05-KONTROLA OBSAHU/13-chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule10.CODE, Rule11.CODE, Rule12.CODE },
                new String[] { Rule13.CODE });
    }

    private void testPkgInfo(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
    	Function<String, RepresentationInfo> repReader = repId -> {
    		if("uuid-a13c7fb3-caad-4505-8a3e-144fc6dca744".equals(repId)) {
	    		return new RepresentationInfo("uuid-a13c7fb3-caad-4505-8a3e-144fc6dca744",
	    				EarkCz.REPRESENTATION_SUBMISSION, null);
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

