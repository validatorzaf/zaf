package cz.zaf.sipvalidator.nsesss2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs100_109.Pravidlo102;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs100_109.Pravidlo103;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs100_109.Pravidlo105;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs100_109.Pravidlo106;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs30_39.Pravidlo39;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs50_59.Pravidlo54;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs70_79.Pravidlo77;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89.Pravidlo85;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89.Pravidlo88;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99.Pravidlo95;
import cz.zaf.sipvalidator.nsesss2024.pravidla07.kom00_09.Pravidlo7_01;
import cz.zaf.sipvalidator.nsesss2024.pravidla07.kom00_09.Pravidlo7_02;
import cz.zaf.sipvalidator.nsesss2024.pravidla07.kom00_09.Pravidlo7_03;
import cz.zaf.sipvalidator.nsesss2024.pravidla07.kom00_09.Pravidlo7_04;
import cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

public class SipValidatorK07Test extends SipValidatorTestBase {
    static public final String PATH_DATA_K07 = "testdata/NSESSS2024/07 KONTROLA KOMPONENT";

    void testPackage(String path,
                     LoadType loadType,
                     ValidationStatus stavKontroly,
                     ProfilValidace profilValidace,
                     String[] pravidlaOk, String[] pravidlaChybna, String[] exclChecks) {
        testPackage(PATH_DATA_K07 + "/" + path, loadType,
                    profilValidace,
                    TypUrovenKontroly.KOMPONENT,
                    stavKontroly, pravidlaOk, pravidlaChybna, exclChecks);
    }

    void testPackage(String path,
                     ValidationStatus stavKontroly,
                     ProfilValidace profilValidace,
                     String[] pravidlaOk, String[] pravidlaChybna, String[] exclChecks) {
        testPackage(path, LoadType.LT_DIR,
                    stavKontroly,
                    profilValidace,
                    pravidlaOk, pravidlaChybna, exclChecks);
    }

    void testPackage(String path,
                     ValidationStatus stavKontroly,
                     ProfilValidace profilValidace,
                     String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(path, LoadType.LT_DIR,
                    stavKontroly,
                    profilValidace,
                    pravidlaOk, pravidlaChybna, null);
    }

    @Test
    void testK07_01_01() {
        testPackage("kom1-chyba1", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_01.KOD });
    }

    @Test
    void testK07_01_02() {
        testPackage("kom1-chyba2", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_01.KOD });
    }

    @Test
    void testK07_01_OK01() {
        testPackage("kom1-OK", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_01.KOD },
                    new String[] {});
    }

    @Test
    void testK07_02_01() {
        testPackage("kom2-chyba1", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_01.KOD },
                    new String[] { Pravidlo7_02.KOD });
    }

    @Test
    void testK07_02_02() {
        testPackage("kom2-chyba2", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_01.KOD },
                    new String[] { Pravidlo7_02.KOD });
    }

    @Test
    void testK07_02_OK01() {
        testPackage("kom2-OK1", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_01.KOD, Pravidlo7_02.KOD },
                    new String[] {});
    }

    @Test
    void testK07_02_OK02() {
        testPackage("kom2-OK2", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_02.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_01() {
        testPackage("kom3-chyba1", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_02() {
        testPackage("kom3-chyba2", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_03() {
        testPackage("kom3-chyba3", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_05() {
        testPackage("kom3-chyba5", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_06() {
        testPackage("kom3-chyba6", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    /*@Test
    void testK07_03_07() {
        testPackage("kom3-chyba7", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }*/

    @Test
    void testK07_03_OK01() {
        testPackage("kom3-OK1", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK02() {
        testPackage("kom3-OK2", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK03() {
        testPackage("kom3-OK3", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK04() {
        testPackage("kom3-OK4", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK05() {
        testPackage("kom3-OK5", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {},
                    new String[] { Pravidlo105.OBS105 });
    }

    /*@Test
    void testK07_03_OK06() {
        testPackage("kom3-OK6", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {},
                    new String[] { Pravidlo103.OBS103 });
    }*/

    /*
    @Test
    void testK07_03_OK07() {
        testPackage("kom3-OK7", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {},
                    new String[] { Pravidlo102.OBS102, Pravidlo103.OBS103 });
    }*/

    /*
    @Test
    void testK07_03_OK08() {
        testPackage("kom3-OK8", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {},
                    new String[] { Pravidlo102.OBS102, Pravidlo103.OBS103 });
    }*/

    /*
    @Test
    void testK07_03_OK09() {
        testPackage("kom3-OK9", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {},
                    new String[] { Pravidlo94.OBS94, Pravidlo95.OBS95, Pravidlo102.OBS102, Pravidlo103.OBS103,
                            Pravidlo105.OBS105 });
    }*/

    @Test
    void testK07_03_OK10() {
        testPackage("kom3-OK10", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {},
                    new String[] { Pravidlo39.OBS39, Pravidlo54.OBS54, Pravidlo77.OBS77, Pravidlo95.OBS95,
                            Pravidlo102.OBS102, Pravidlo103.OBS103 });
    }

    @Test
    void testK07_03_OK11() {
        testPackage("kom3-OK11", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {},
                    new String[] { Pravidlo102.OBS102, Pravidlo103.OBS103, Pravidlo105.OBS105 });
    }

    @Test
    void testK07_04_OK01() {
        testPackage("kom4-OK1", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK02() {
        testPackage("kom4-OK2", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {},
                    new String[] { Pravidlo106.OBS106 });
    }

    @Test
    void testK07_04_OK03() {
        testPackage("kom4-OK3", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {},
                    new String[] { Pravidlo85.OBS85, Pravidlo88.OBS88 });
    }

    @Test
    void testK07_04_OK04() {
        testPackage("kom4-OK4", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK05() {
        testPackage("kom4-OK5", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {},
                    new String[] { Pravidlo77.OBS77 });
    }

    @Test
    void testK07_04_OK06() {
        testPackage("kom4-OK6", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK07() {
        testPackage("kom4-OK7", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_01() {
        testPackage("kom4-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_04.KOD });
    }

    @Test
    void testK07_04_02() {
        testPackage("kom4-chyba2", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_04.KOD });
    }
}
