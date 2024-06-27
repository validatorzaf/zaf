package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo3;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

class SipValidatorK01Test
        extends SipValidatorTestBase {

    static public final String PATH_DATA_K01 = "testdata/NSESSS2017/01 KONTROLA DATA";

    void testPackageK01(String path, LoadType expLoadType,
                        ValidationStatus stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K01 + "/" + path, expLoadType,
                    ZakladniProfilValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.DATOVE_STRUKTURY,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK01_01_OK01() {
        testPackageK01("data1-OK", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_01_01() {
        testPackageK01("data1-chyba1.xml", LoadType.LT_XML, ValidationStatus.ERROR,
                       null,
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK01_01_02() {
        testPackageK01("data1-chyba2.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] {
                               Pravidlo1.KOD
                       },
                       new String[] {
                               Pravidlo2.KOD,
                               Pravidlo3.KOD
                       });
    }

    @Test
    void testK01_01_03() {
        testPackageK01("data1-chyba3.pdf", LoadType.LT_UNKNOWN, ValidationStatus.ERROR,
                       new String[] { Pravidlo2.KOD },
                       new String[] { Pravidlo1.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_01_04() {
        testPackageK01("data1-chyba4.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       null,
                       new String[] { Pravidlo1.KOD,
                               Pravidlo2.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_02_01() {
        testPackageK01("data2-chyba1.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_02_02() {
        testPackageK01("data2-chyba2.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_02_03() {
        testPackageK01("data2-chyba3.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_02_OK01() {
        testPackageK01("data2-OK1.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_11() {
        testPackageK01("data3-chyba1", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_12() {
        testPackageK01("data3-chyba2", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_13() {
        testPackageK01("data3-chyba3", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_14() {
        testPackageK01("data3-chyba4", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_15() {
        testPackageK01("data3-OK1", LoadType.LT_DIR, ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD, Pravidlo3.KOD },
                       null);
    }

    @Test
    void testK01_16() {
        testPackageK01("data3-OK2", LoadType.LT_DIR, ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD, Pravidlo2.KOD, Pravidlo3.KOD },
                       null);
    }
}
