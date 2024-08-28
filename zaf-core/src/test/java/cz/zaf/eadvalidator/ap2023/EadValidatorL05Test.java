package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule03;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule04;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule05;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL05Test extends EadValidatorTestBase {
	static public final String PATH_TESTDATA = "testdata/AP2023";
	
    @Test
    void testObs_OK01() {
    	testPomucka("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE },
                new String[] {});
    }

    @Test
    void testObs_OK02() {
    	testPopis("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE },
                new String[] {});
    }
    
    @Test
    void testObs_01_chyba01() {
    	testPopis("05-KONTROLA OBSAHU/001_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] { },
                new String[] {Rule01.CODE });
    }
    
    @Test
    void testObs_02_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/002_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE },
                new String[] {Rule02.CODE });
    }

    @Test
    void testObs_02_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/002_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE },
                new String[] {Rule02.CODE });
    }

    @Test
    void testObs_02_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/002_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE },
                new String[] {Rule02.CODE });
    }

    @Test
    void testObs_02_chyba04() {
    	testPomucka("05-KONTROLA OBSAHU/002_chyba4.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE },
                new String[] {Rule02.CODE });
    }

    @Test
    void testObs_02_chyba05() {
    	testPomucka("05-KONTROLA OBSAHU/002_chyba5.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE },
                new String[] {Rule02.CODE });
    }

    @Test
    void testObs_03_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/003_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE },
                new String[] { Rule03.CODE });
    }

    @Test
    void testObs_03_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/003_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE },
                new String[] { Rule03.CODE });
    }

    @Test
    void testObs_04_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/004_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }

    @Test
    void testObs_04_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/004_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04.CODE });
    }

    @Test
    void testObs_05_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/005_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE  },
                new String[] { Rule05.CODE });
    }

    @Test
    void testObs_05_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/005_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE  },
                new String[] { Rule05.CODE });
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
