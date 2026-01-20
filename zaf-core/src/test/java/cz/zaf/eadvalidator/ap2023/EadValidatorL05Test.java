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
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule101;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule103;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule104;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule104a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule105;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule11;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule12;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule13;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule14;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule15;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule16;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule17;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule18;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule19;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule20;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule21;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule22;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule23;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule24;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule25;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule31;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule35;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36b;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule37;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule37a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule42;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule43;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule44;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule49;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule50;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule52;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule53;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule54;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule55;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule56;
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
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74b;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74c;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74d;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule75;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule77;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule78;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule79;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule80;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule81;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule82;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule83;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule84;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule89;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule93;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule94;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule95;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule96;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule97;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule99;

import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class EadValidatorL05Test extends EadValidatorTestBase {

    static public final String PATH_TESTDATA = "testdata/AP2023";

    @Test
    void testObs_OK01() {
        testPomucka("sdilene_OK1.xml",
                ValidationStatus.OK,
                new String[]{
                    Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule36b.CODE, Rule37.CODE, Rule37a.CODE,
                    Rule42.CODE, Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE, Rule55.CODE, Rule56.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE, Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule96.CODE, Rule97.CODE, Rule99.CODE,
                    Rule101.CODE, Rule103.CODE, Rule104.CODE, Rule104a.CODE, Rule105.CODE
                },
                new String[]{});
    }

    @Test
    void testObs_OK02() {
        testPopis("sdilene_OK2.xml",
                ValidationStatus.OK,
                new String[]{
                    Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE, Rule37a.CODE,
                    Rule42.CODE, Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE, Rule55.CODE, Rule56.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE, Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule96.CODE, Rule97.CODE, Rule99.CODE,
                    Rule101.CODE, Rule103.CODE, Rule104.CODE, Rule104a.CODE, Rule105.CODE
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
    void testObs_13_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/013_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule13.CODE});
    }

    @Test
    void testObs_14_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/014_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule14.CODE});
    }

    @Test
    void testObs_15_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/015_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule15.CODE});
    }

    @Test
    void testObs_15_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/015_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule15.CODE});
    }

    @Test
    void testObs_16_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/016_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule16.CODE});
    }

    @Test
    void testObs_17_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/017_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule17.CODE});
    }

    @Test
    void testObs_18_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/018_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule18.CODE});
    }

    @Test
    void testObs_19_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/019_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule19.CODE});
    }

    @Test
    void testObs_19_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/019_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule19.CODE});
    }

    @Test
    void testObs_20_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/020_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule20.CODE});
    }

    @Test
    void testObs_20_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/020_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule20.CODE});
    }

    @Test
    void testObs_21_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/021_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE, Rule20.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule21.CODE});
    }

    @Test
    void testObs_22_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/022_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule22.CODE});
    }

    @Test
    void testObs_23_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_23_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/023_chyba5.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule23.CODE});
    }

    @Test
    void testObs_24_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/024_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule24.CODE});
    }

    @Test
    void testObs_25_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_25_chyba05() {
        testPomucka("05-KONTROLA OBSAHU/025_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule25.CODE});
    }

    @Test
    void testObs_31_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_31_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_31_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_31_chyba04() {
        testPomucka("05-KONTROLA OBSAHU/031_chyba4.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule31.CODE});
    }

    @Test
    void testObs_35_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/035_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule35.CODE});
    }

    @Test
    void testObs_36_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/036_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule36.CODE});
    }

    @Test
    void testObs_36_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/036_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule36.CODE});
    }

    @Test
    void testObs_36_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/036_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule36.CODE});
    }

    @Test
    void testObs_36a_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/036a_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule36a.CODE});
    }

    @Test
    void testObs_37_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/037_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule37.CODE});
    }

    @Test
    void testObs_37a_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/037a_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule37a.CODE});
    }

    @Test
    void testObs_42_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/042_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule42.CODE});
    }

    @Test
    void testObs_43_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/043_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule43.CODE});
    }

    @Test
    void testObs_44_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/044_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule44.CODE});
    }

    @Test
    void testObs_49_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/049_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule49.CODE});
    }

    @Test
    void testObs_50_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/050_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule50.CODE});
    }

    @Test
    void testObs_52_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/052_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule52.CODE});
    }

    @Test
    void testObs_53_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/053_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule53.CODE});
    }

    @Test
    void testObs_54_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/054_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule54.CODE});
    }

    @Test
    void testObs_55_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/055_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
                new String[]{Rule55.CODE});
    }

//    @Test
//    void testObs_56_chyba01() {
//        testPomucka("05-KONTROLA OBSAHU/056_chyba1.xml",
//                ValidationStatus.ERROR,
//                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
//                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
//                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
//                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
//                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
//                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE, Rule55.CODE,
//                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE, Rule72.CODE},
//                new String[]{Rule56.CODE});
//    }
    @Test
    void testObs_60_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/060_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE},
                new String[]{Rule60.CODE});
    }

    @Test
    void testObs_60_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/060_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE},
                new String[]{Rule60.CODE});
    }

    @Test
    void testObs_61_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/061_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE},
                new String[]{Rule61.CODE});
    }

    @Test
    void testObs_61_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/061_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE},
                new String[]{Rule61.CODE});
    }

    @Test
    void testObs_62_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/062_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE},
                new String[]{Rule62.CODE});
    }

    @Test
    void testObs_62_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/062_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE},
                new String[]{Rule62.CODE});
    }

    @Test
    void testObs_62_chyba03() {
        testPomucka("05-KONTROLA OBSAHU/062_chyba3.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE},
                new String[]{Rule62.CODE});
    }

    @Test
    void testObs_63_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/063_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE},
                new String[]{Rule63.CODE});
    }

    @Test
    void testObs_63_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/063_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE},
                new String[]{Rule63.CODE});
    }

    @Test
    void testObs_64_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/064_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE},
                new String[]{Rule64.CODE});
    }

    @Test
    void testObs_64_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/064_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE},
                new String[]{Rule64.CODE});
    }

    @Test
    void testObs_65_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/065_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE},
                new String[]{Rule65.CODE});
    }

    @Test
    void testObs_65_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/065_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE},
                new String[]{Rule65.CODE});
    }

    @Test
    void testObs_66_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/066_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE},
                new String[]{Rule66.CODE});
    }

    @Test
    void testObs_66_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/066_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE},
                new String[]{Rule66.CODE});
    }

    @Test
    void testObs_67_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/067_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE},
                new String[]{Rule67.CODE});
    }

    @Test
    void testObs_67_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/067_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE},
                new String[]{Rule67.CODE});
    }

    @Test
    void testObs_68_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/068_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE},
                new String[]{Rule68.CODE});
    }

    @Test
    void testObs_68_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/068_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE},
                new String[]{Rule68.CODE});
    }

    @Test
    void testObs_69_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/069_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE},
                new String[]{Rule69.CODE});
    }

    @Test
    void testObs_69_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/069_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE},
                new String[]{Rule69.CODE});
    }

    @Test
    void testObs_70_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/070_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE},
                new String[]{Rule70.CODE});
    }

    @Test
    void testObs_71_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/071_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE},
                new String[]{Rule71.CODE});
    }

    @Test
    void testObs_72_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/072_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE},
                new String[]{Rule72.CODE});
    }

    @Test
    void testObs_72_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/072_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE},
                new String[]{Rule72.CODE});
    }

    @Test
    void testObs_73_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/073_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE},
                new String[]{Rule73.CODE});
    }

    @Test
    void testObs_74_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/074_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE},
                new String[]{Rule74.CODE});
    }

    @Test
    void testObs_74a_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/074a_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE},
                new String[]{Rule74a.CODE});
    }

    @Test
    void testObs_74b_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/074b_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE},
                new String[]{Rule74b.CODE});
    }

    @Test
    void testObs_74c_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/074c_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE},
                new String[]{Rule74c.CODE});
    }

    @Test
    void testObs_74d_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/074d_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE},
                new String[]{Rule74d.CODE});
    }

    @Test
    void testObs_75_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/075_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE},
                new String[]{Rule75.CODE});
    }

    @Test
    void testObs_75_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/075_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE},
                new String[]{Rule75.CODE});
    }

    @Test
    void testObs_77_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/077_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE},
                new String[]{Rule77.CODE});
    }

    @Test
    void testObs_78_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/078_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE},
                new String[]{Rule78.CODE});
    }

//    @Test
//    void testObs_79_chyba01() {
//        testPomucka("05-KONTROLA OBSAHU/079_chyba1.xml",
//                ValidationStatus.ERROR,
//                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
//                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
//                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
//                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
//                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
//                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
//                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
//                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE},
//                new String[]{Rule79.CODE});
//    }
    @Test
    void testObs_80_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/080_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE},
                new String[]{Rule80.CODE});
    }

    @Test
    void testObs_81_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/081_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE},
                new String[]{Rule81.CODE});
    }

    @Test
    void testObs_82_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/082_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE},
                new String[]{Rule82.CODE});
    }

    @Test
    void testObs_83_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/083_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE},
                new String[]{Rule83.CODE});
    }

    @Test
    void testObs_84_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/084_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE},
                new String[]{Rule84.CODE});
    }

    @Test
    void testObs_84_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/084_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE},
                new String[]{Rule84.CODE});
    }

    @Test
    void testObs_89_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/089_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE, Rule84.CODE},
                new String[]{Rule89.CODE});
    }

    @Test
    void testObs_89_chyba02() {
        testPomucka("05-KONTROLA OBSAHU/089_chyba2.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE, Rule84.CODE},
                new String[]{Rule89.CODE});
    }

    @Test
    void testObs_93_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/093_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE
                },
                new String[]{Rule93.CODE});
    }

    @Test
    void testObs_94_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/094_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
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
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE
                },
                new String[]{Rule95.CODE});
    }

    @Test
    void testObs_96_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/096_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE
                },
                new String[]{Rule96.CODE});
    }

    @Test
    void testObs_97_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/097_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
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
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE
                },
                new String[]{Rule97.CODE});
    }

    @Test
    void testObs_99_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/099_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule97.CODE
                },
                new String[]{Rule99.CODE});
    }

    @Test
    void testObs_101_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/101_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE, Rule84.CODE, Rule89.CODE},
                new String[]{Rule101.CODE});
    }

    @Test
    void testObs_103_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/103_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{
                    Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule97.CODE
                },
                new String[]{Rule103.CODE});
    }

    @Test
    void testObs_104_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/104_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{
                    Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule97.CODE,
                    Rule103.CODE
                },
                new String[]{Rule104.CODE});
    }
    
        @Test
    void testObs_104a_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/104a_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule04a.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule13.CODE, Rule14.CODE, Rule15.CODE, Rule16.CODE, Rule17.CODE, Rule18.CODE, Rule19.CODE,
                    Rule20.CODE, Rule21.CODE, Rule22.CODE, Rule23.CODE, Rule24.CODE, Rule25.CODE,
                    Rule31.CODE, Rule35.CODE, Rule36.CODE, Rule36a.CODE, Rule36b.CODE, Rule37.CODE,
                    Rule43.CODE, Rule44.CODE, Rule49.CODE,
                    Rule50.CODE, Rule52.CODE, Rule53.CODE, Rule54.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule74.CODE, Rule74a.CODE, Rule74b.CODE, Rule74c.CODE, Rule74d.CODE, Rule74d.CODE, Rule75.CODE, Rule77.CODE, Rule78.CODE, Rule79.CODE,
                    Rule80.CODE, Rule81.CODE, Rule82.CODE, Rule83.CODE, Rule84.CODE, Rule89.CODE},
                new String[]{Rule104a.CODE});
    }

    @Test
    void testObs_105_chyba01() {
        testPomucka("05-KONTROLA OBSAHU/105_chyba1.xml",
                ValidationStatus.ERROR,
                new String[]{
                    Rule01.CODE, Rule02.CODE, Rule03.CODE, Rule04.CODE, Rule05.CODE, Rule06.CODE, Rule07.CODE, Rule08.CODE, Rule09.CODE,
                    Rule11.CODE, Rule12.CODE, Rule15.CODE, Rule19.CODE,
                    Rule20.CODE, Rule22.CODE, Rule25.CODE,
                    Rule31.CODE, Rule36.CODE,
                    Rule49.CODE,
                    Rule60.CODE, Rule61.CODE, Rule62.CODE, Rule63.CODE, Rule64.CODE, Rule65.CODE, Rule66.CODE, Rule67.CODE, Rule68.CODE, Rule69.CODE,
                    Rule70.CODE, Rule71.CODE, Rule72.CODE, Rule73.CODE, Rule75.CODE,
                    Rule84.CODE, Rule89.CODE,
                    Rule93.CODE, Rule94.CODE, Rule95.CODE, Rule97.CODE,
                    Rule103.CODE, Rule104.CODE
                },
                new String[]{Rule105.CODE});
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
