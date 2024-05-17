package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.pravidla02.kod00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

public class SipValidatorK02Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K02 = "testdata/02 KONTROLA ZNAKOVE SADY";

    void testPackageK02(String path,
                        ValidationStatus stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K02 + "/" + path, LoadType.LT_DIR,
                    ZakladniProfilValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.ZNAKOVE_SADY,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK02_01() {
        testPackageK02("kod1-chyba1", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_02() {
        testPackageK02("kod1-chyba2", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_03() {
        testPackageK02("kod1-chyba3", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_04() {
        testPackageK02("kod1-chyba4", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_05() {
        testPackageK02("kod1-chyba5", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_06() {
        testPackageK02("kod1-chyba6", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_07() {
        testPackageK02("kod1-chyba7", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_08() {
        testPackageK02("kod1-chyba8", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_09() {
        testPackageK02("kod1-chyba9", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_10() {
        testPackageK02("kod1-chyba10", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_11() {
        testPackageK02("kod1-chyba11", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK02_OK_1() {
        testPackageK02("kod1-OK", ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD },
                       new String[] {});
    }
}
