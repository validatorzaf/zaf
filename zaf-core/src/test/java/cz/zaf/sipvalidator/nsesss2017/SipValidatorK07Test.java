package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_01;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_02;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_03;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09.Pravidlo7_04;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

public class SipValidatorK07Test extends SipValidatorTestBase {
    static public final String PATH_DATA_K07 = "testdata/07 KONTROLA KOMPONENT";

    void testPackage(String path,
                     LoadType loadType,
                     ValidationStatus stavKontroly,
                     ProfilValidace profilValidace,
                     String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K07 + "/" + path, loadType,
                    profilValidace,
                    TypUrovenKontroly.KOMPONENT,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    void testPackage(String path,
                     ValidationStatus stavKontroly,
                     ProfilValidace profilValidace,
                     String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(path, LoadType.LT_DIR,
                    stavKontroly,
                    profilValidace,
                    pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK07_01_01() {
        testPackage("01-chyba1", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_01.KOD });
    }

    @Test
    void testK07_01_02() {
        testPackage("01-chyba2", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_01.KOD });
    }

    @Test
    void testK07_01_OK01() {
        testPackage("01-OK", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_01.KOD },
                    new String[] {});
    }

    @Test
    void testK07_02_01() {
        testPackage("02-chyba1", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_02.KOD });
    }

    @Test
    void testK07_02_02() {
        testPackage("02-chyba2", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_02.KOD });
    }

    @Test
    void testK07_02_OK01() {
        testPackage("02-OK1", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_02.KOD },
                    new String[] {});
    }

    @Test
    void testK07_02_OK02() {
        testPackage("02-OK2", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_02.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_01() {
        testPackage("03-chyba1", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_02() {
        testPackage("03-chyba2", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_03() {
        testPackage("03-chyba3", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_05() {
        testPackage("03-chyba5", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_06() {
        testPackage("03-chyba6", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_07() {
        testPackage("03-chyba7", ValidationStatus.ERROR,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_03.KOD });
    }

    @Test
    void testK07_03_OK01() {
        testPackage("03-OK1", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK02() {
        testPackage("03-OK2", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK03() {
        testPackage("03-OK3", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK04() {
        testPackage("03-OK4", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK05() {
        testPackage("03-OK5", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK06() {
        testPackage("03-OK6", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK07() {
        testPackage("03-OK7", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK08() {
        testPackage("03-OK8", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK09() {
        testPackage("03-OK9", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK10() {
        testPackage("03-OK10", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_03_OK11() {
        testPackage("03-OK11", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_03.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK01() {
        testPackage("04-OK1", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK02() {
        testPackage("04-OK2", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK03() {
        testPackage("04-OK3", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK04() {
        testPackage("04-OK4", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK05() {
        testPackage("04-OK5", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK06() {
        testPackage("04-OK6", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_OK07() {
        testPackage("04-OK7", null,
                    ZakladniProfilValidace.PREJIMKA,
                    new String[] { Pravidlo7_04.KOD },
                    new String[] {});
    }

    @Test
    void testK07_04_01() {
        testPackage("04-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_04.KOD });
    }

    @Test
    void testK07_04_02() {
        testPackage("04-chyba2", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                    new String[] {},
                    new String[] { Pravidlo7_04.KOD });
    }
}
