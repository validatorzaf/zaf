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
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule25;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule27;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule31;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule35;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule37;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule43;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule49;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule60;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule61;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule62;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule63;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule64;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule65;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule66;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule67;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule68;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule69;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule70;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule71;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule72;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule73;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule75;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule84;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule85;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule86;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule87;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule93;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule94;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule95;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule97;

import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL05Test extends EadValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/AP2023";

    @Test
    void testObs_OK01() {
        testPomucka("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[]{
                    Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE,
                    Rule43.CODE, Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE, Rule87.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule97.CODE
                },
                new String[]{});
    }

    @Test
    void testObs_OK02() {
        testPopis("sdilene_OK2.xml",
                ValidationStatus.OK,
                new String[]{
                    Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE,
                    Rule43.CODE, Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE, Rule87.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule97.CODE
                },
                new String[]{});
    }

    @Test
    void testObs_01_chyba01() {
        testPopis("05-KONTROLA OBSAHU/001_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule01.CODE});
    }

    @Test
    void testObs_02_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/002_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule02.CODE});
    }

    @Test
    void testObs_02_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/002_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule02.CODE});
    }

    @Test
    void testObs_02_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/002_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule02.CODE});
    }

    @Test
    void testObs_02_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/002_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule02.CODE});
    }

    @Test
    void testObs_02_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/002_chyba5.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule02.CODE});
    }

    @Test
    void testObs_03_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/003_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule03.CODE});
    }

    @Test
    void testObs_03_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/003_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule03.CODE});
    }

    @Test
    void testObs_03_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/003_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule03.CODE});
    }

    @Test
    void testObs_04_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/004_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule04.CODE});
    }

    @Test
    void testObs_04_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/004_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule04.CODE});
    }

    @Test
    void testObs_04a_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/004a_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule04a.CODE});
    }

    @Test
    void testObs_04a_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/004a_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule04a.CODE});
    }

    @Test
    void testObs_05_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/005_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule05.CODE});
    }

    @Test
    void testObs_05_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/005_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule05.CODE});
    }

    @Test
    void testObs_06_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/006_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule06.CODE});
    }

    @Test
    void testObs_07_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/007_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule07.CODE});
    }

    @Test
    void testObs_07_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/007_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule07.CODE});
    }

    @Test
    void testObs_07_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/007_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule07.CODE});
    }

    @Test
    void testObs_07_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/007_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule07.CODE});
    }

    @Test
    void testObs_08_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/008_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule08.CODE});
    }

    @Test
    void testObs_08_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/008_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule08.CODE});
    }

    @Test
    void testObs_08_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/008_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule08.CODE});
    }

    @Test
    void testObs_08_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/008_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule08.CODE});
    }

    @Test
    void testObs_09_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/009_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule09.CODE});
    }

    @Test
    void testObs_09_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/009_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule09.CODE});
    }

    @Test
    void testObs_09_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/009_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule09.CODE});
    }

    @Test
    void testObs_09_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/009_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule09.CODE});
    }

    @Test
    void testObs_11_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/011_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule11.CODE});
    }

    @Test
    void testObs_11_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/011_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule11.CODE});
    }

    @Test
    void testObs_11_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/011_chyba5.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule11.CODE});
    }

    @Test
    void testObs_12_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/012_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule11.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule12.CODE});
    }

    @Test
    void testObs_12_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/012_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule11.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule12.CODE});
    }

    @Test
    void testObs_12_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/012_chyba5.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE, Rule11.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule12.CODE});
    }

    @Test
    void testObs_15_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/015_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule15.CODE});
    }

    @Test
    void testObs_15_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/015_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule15.CODE});
    }

    @Test
    void testObs_19_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/019_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule19.CODE});
    }

    @Test
    void testObs_19_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/019_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule19.CODE});
    }

    @Test
    void testObs_20_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/020_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule20.CODE});
    }

    @Test
    void testObs_20_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/020_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule20.CODE});
    }

    @Test
    void testObs_22_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/022_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule22.CODE});
    }

    @Test
    void testObs_23_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba5.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_25_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_27_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/027_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule27.CODE});
    }

    @Test
    void testObs_27_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/027_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule27.CODE});
    }

    @Test
    void testObs_27_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/027_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule27.CODE});
    }

    @Test
    void testObs_27_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/027_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule27.CODE});
    }

    @Test
    void testObs_27_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/027_chyba5.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule27.CODE});
    }

    @Test
    void testObs_27_chyba06() {
        testPomucka("05-KONTROLA OBSAHU/027_chyba6.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule27.CODE});
    }

    @Test
    void testObs_27_chyba07() {
        testPomucka("05-KONTROLA OBSAHU/027_chyba7.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule27.CODE});
    }

    @Test
    void testObs_31_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_31_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_31_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_31_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_35_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/035_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule35.CODE});
    }

    @Test
    void testObs_36_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/036_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule35.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule36.CODE});
    }

    @Test
    void testObs_36_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/036_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule31.CODE, Rule35.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule36.CODE});
    }

    @Test
    void testObs_36_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/036_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule31.CODE, Rule35.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule36.CODE});
    }

    @Test
    void testObs_37_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/037_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule35.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule37.CODE});
    }

    @Test
    void testObs_43_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/043_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,},
                new String[]{Rule43.CODE});
    }

    @Test
    void testObs_49_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/049_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,},
                new String[]{Rule49.CODE});
    }

    @Test
    void testObs_60_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/060_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE},
                new String[]{Rule60.CODE});
    }

    @Test
    void testObs_60_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/060_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE},
                new String[]{Rule60.CODE});
    }

    @Test
    void testObs_61_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/061_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE},
                new String[]{Rule61.CODE});
    }

    @Test
    void testObs_61_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/061_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE},
                new String[]{Rule61.CODE});
    }

    @Test
    void testObs_62_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/062_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE},
                new String[]{Rule62.CODE});
    }

    @Test
    void testObs_62_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/062_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE},
                new String[]{Rule62.CODE});
    }

    @Test
    void testObs_62_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/062_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE},
                new String[]{Rule62.CODE});
    }

    @Test
    void testObs_63_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/063_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE},
                new String[]{Rule63.CODE});
    }

    @Test
    void testObs_63_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/063_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE},
                new String[]{Rule63.CODE});
    }

    @Test
    void testObs_64_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/064_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE},
                new String[]{Rule64.CODE});
    }

    @Test
    void testObs_64_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/064_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE},
                new String[]{Rule64.CODE});
    }

    @Test
    void testObs_65_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/065_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE},
                new String[]{Rule65.CODE});
    }

    @Test
    void testObs_65_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/065_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE},
                new String[]{Rule65.CODE});
    }

    @Test
    void testObs_66_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/066_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE},
                new String[]{Rule66.CODE});
    }

    @Test
    void testObs_66_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/066_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE},
                new String[]{Rule66.CODE});
    }

    @Test
    void testObs_67_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/067_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE},
                new String[]{Rule67.CODE});
    }

    @Test
    void testObs_67_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/067_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE},
                new String[]{Rule67.CODE});
    }

    @Test
    void testObs_68_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/068_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE},
                new String[]{Rule68.CODE});
    }

    @Test
    void testObs_68_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/068_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE},
                new String[]{Rule68.CODE});
    }

    @Test
    void testObs_69_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/069_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE},
                new String[]{Rule69.CODE});
    }

    @Test
    void testObs_69_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/069_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE},
                new String[]{Rule69.CODE});
    }

    @Test
    void testObs_70_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/070_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE},
                new String[]{Rule70.CODE});
    }

    @Test
    void testObs_71_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/071_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE
                },
                new String[]{Rule71.CODE});
    }

    @Test
    void testObs_72_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/072_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE
                },
                new String[]{Rule72.CODE});
    }

    @Test
    void testObs_72_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/072_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE
                },
                new String[]{Rule72.CODE});
    }

    @Test
    void testObs_73_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/073_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE
                },
                new String[]{Rule73.CODE});
    }

    @Test
    void testObs_75_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/075_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE
                },
                new String[]{Rule75.CODE});
    }

    @Test
    void testObs_75_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/075_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE
                },
                new String[]{Rule75.CODE});
    }

    @Test
    void testObs_84_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/084_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE
                },
                new String[]{Rule84.CODE});
    }

    @Test
    void testObs_84_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/084_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE
                },
                new String[]{Rule84.CODE});
    }

    @Test
    void testObs_85_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/085_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE
                },
                new String[]{Rule85.CODE});
    }

    @Test
    void testObs_86_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/086_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE,},
                new String[]{Rule86.CODE});
    }

    @Test
    void testObs_87_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/087_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE,},
                new String[]{Rule87.CODE});
    }

    @Test
    void testObs_93_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/093_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE, Rule87.CODE, Rule87.CODE
                },
                new String[]{Rule93.CODE});
    }

    @Test
    void testObs_94_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/094_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE, Rule87.CODE, Rule87.CODE,
                    Rule93.CODE
                },
                new String[]{Rule94.CODE});
    }

    @Test
    void testObs_95_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/095_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE, Rule87.CODE, Rule87.CODE,
                    Rule93.CODE, Rule94.CODE
                },
                new String[]{Rule95.CODE});
    }

    @Test
    void testObs_97_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/097_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE, Rule87.CODE, Rule87.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE
                },
                new String[]{Rule97.CODE});
    }

    @Test
    void testObs_97_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/097_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule23.CODE, Rule25.CODE, Rule27.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule85.CODE, Rule86.CODE, Rule87.CODE, Rule87.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE
                },
                new String[]{Rule97.CODE});
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
