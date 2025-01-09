package cz.zaf.eadvalidator.ap2023;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule03;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule04;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule04a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule05;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule06;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule07;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule08;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule09;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule11;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule12;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule15;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule19;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule20;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule22; 
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule23; 
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule24; 
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule25; 
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule26; 
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule27; 
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule28; 
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule29;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL05Test extends EadValidatorTestBase {
	static public final String PATH_TESTDATA = "testdata/AP2023";
	
    @Test
    void testObs_OK01() {
    	testPomucka("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                		Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                                Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule36.CODE},
                new String[] {});
    }

    @Test
    void testObs_OK02() {
    	testPopis("sdilene_OK2.xml",
                ValidationStatus.OK,
                new String[] { Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                		Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                                Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE, Rule26.CODE, Rule27.CODE, Rule28.CODE, Rule29.CODE,
                		Rule36.CODE },
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
    void testObs_03_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/003_chyba3.xml",
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
    void testObs_04a_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/004a_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04a.CODE });
    }

    @Test
    void testObs_04a_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/004a_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] { Rule01.CODE, Rule02.CODE, Rule03.CODE },
                new String[] { Rule04a.CODE });
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

    @Test
    void testObs_06_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/006_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE },
                new String[] { Rule06.CODE });
    }

    @Test
    void testObs_07_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/007_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
                new String[] { Rule07.CODE });
    }

    @Test
    void testObs_07_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/007_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
                new String[] { Rule07.CODE });
    }

    @Test
    void testObs_07_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/007_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
                new String[] { Rule07.CODE });
    }

    @Test
    void testObs_07_chyba04() {
    	testPomucka("05-KONTROLA OBSAHU/007_chyba4.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE },
                new String[] { Rule07.CODE });
    }

    @Test
    void testObs_08_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/008_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE  },
                new String[] { Rule08.CODE });
    }

    @Test
    void testObs_08_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/008_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE },
                new String[] { Rule08.CODE });
    }

    @Test
    void testObs_08_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/008_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE },
                new String[] { Rule08.CODE });
    }

    @Test
    void testObs_08_chyba04() {
    	testPomucka("05-KONTROLA OBSAHU/008_chyba4.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE },
                new String[] { Rule08.CODE });
    }

    @Test
    void testObs_09_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/009_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }

    @Test
    void testObs_09_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/009_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }

    @Test
    void testObs_09_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/009_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }
    
    @Test
    void testObs_09_chyba04() {
    	testPomucka("05-KONTROLA OBSAHU/009_chyba4.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE },
                new String[] { Rule09.CODE });
    }
    
    @Test
    void testObs_11_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/011_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule11.CODE });
    }

    @Test
    void testObs_11_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/011_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule11.CODE });
    }
    
    @Test
    void testObs_11_chyba05() {
    	testPomucka("05-KONTROLA OBSAHU/011_chyba5.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE },
                new String[] { Rule11.CODE });
    }

    @Test
    void testObs_12_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/012_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule11.CODE },
                new String[] { Rule12.CODE });
    }

    @Test
    void testObs_12_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/012_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule11.CODE },
                new String[] { Rule12.CODE });
    }
    
    @Test
    void testObs_15_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/015_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule15.CODE });
    }
    
    @Test
    void testObs_15_chyba04() {
    	testPomucka("05-KONTROLA OBSAHU/015_chyba4.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule15.CODE });
    }
    
    @Test
    void testObs_19_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/019_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule19.CODE });
    }
    
    @Test
    void testObs_19_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/019_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule19.CODE });
    }
    
    @Test
    void testObs_22_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/022_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule22.CODE });
    }
    
    @Test
    void testObs_23_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/023_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule23.CODE });
    }
    
    @Test
    void testObs_23_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/023_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule23.CODE });
    }
    
    @Test
    void testObs_23_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/023_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule23.CODE });
    }
    
     @Test
    void testObs_24_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/024_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule24.CODE });
    }
    
    @Test
    void testObs_24_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/024_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule24.CODE });
    }
    
    @Test
    void testObs_24_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/024_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule24.CODE });
    }  
    
     @Test      
    void testObs_25_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/025_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule25.CODE });
    }
    
    @Test
    void testObs_25_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/025_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule25.CODE });
    }
    
    @Test
    void testObs_25_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/025_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule25.CODE });
    }
    
    @Test      
    void testObs_26_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/026_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule26.CODE });
    }
    
    @Test
    void testObs_26_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/026_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule26.CODE });
    }
    
    @Test
    void testObs_26_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/026_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule26.CODE });
    }
    
    @Test      
    void testObs_26_chyba04() {
    	testPomucka("05-KONTROLA OBSAHU/026_chyba4.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule26.CODE });
    }
    
    @Test
    void testObs_26_chyba05() {
    	testPomucka("05-KONTROLA OBSAHU/026_chyba5.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule26.CODE });
    }
    
    @Test
    void testObs_26_chyba06() {
    	testPomucka("05-KONTROLA OBSAHU/026_chyba6.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule26.CODE });
    }
    
         @Test      
    void testObs_26_chyba07() {
    	testPomucka("05-KONTROLA OBSAHU/026_chyba7.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule26.CODE });
    }
    
    @Test
    void testObs_27_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/027_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule27.CODE });
    }
    
    @Test
    void testObs_27_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/027_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule27.CODE });
    }
    
    @Test      
    void testObs_28_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/028_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule28.CODE });
    }
    
    @Test
    void testObs_28_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/028_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule28.CODE });
    }
    
    @Test
    void testObs_28_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/028_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule28.CODE });
    }
    
    @Test      
    void testObs_29_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/029_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule29.CODE });
    }
    
    @Test
    void testObs_29_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/029_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {},
                new String[] { Rule29.CODE });
    }    
    
    @Test
    void testObs_36_chyba01() {
    	testPomucka("05-KONTROLA OBSAHU/036_chyba1.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule11.CODE, Rule12.CODE },
                new String[] { Rule36.CODE });
    }

    @Test
    void testObs_36_chyba02() {
    	testPomucka("05-KONTROLA OBSAHU/036_chyba2.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule11.CODE, Rule12.CODE },
                new String[] { Rule36.CODE });
    }

    @Test
    void testObs_36_chyba03() {
    	testPomucka("05-KONTROLA OBSAHU/036_chyba3.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, 
                		Rule11.CODE, Rule12.CODE },
                new String[] { Rule36.CODE });
    }

    @Test
    void testObs_12_chyba05() {
    	testPomucka("05-KONTROLA OBSAHU/012_chyba5.xml",
                ValidationStatus.ERROR,
                new String[] {Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule11.CODE },
                new String[] { Rule12.CODE });
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
