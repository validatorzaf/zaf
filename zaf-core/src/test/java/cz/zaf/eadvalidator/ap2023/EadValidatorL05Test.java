package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL05Test extends EadValidatorTestBase {
	static public final String PATH_TESTDATA = "testdata/AP2023";
	
    @Test
    void testObs_01_OK01() {
    	testPopis("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE },
                new String[] {});
    }
    
    @Test
    void testObs_01_chyba01() {
    	testPopis("05-KONTROLA OBSAHU/001_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }
    
    private void testPopis(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
    	testObs(path, AP2023Profile.ARCH_DESC, status, oks, fails);
    }    

    private void testPomucka(String path,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
    	testObs(path, AP2023Profile.FINDING_AID, status, oks, fails);
    }    

    private void testObs(String path,
    		AP2023Profile profile,
            ValidationStatus status,
            String[] oks,
            String[] fails) {
    	testEad(PATH_TESTDATA + "/" + path,
    			profile,
    			ValidationLayers.OBSAH,
    			status,
    			oks, fails);
    }    
}
