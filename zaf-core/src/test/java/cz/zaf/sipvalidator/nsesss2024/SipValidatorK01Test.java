package cz.zaf.sipvalidator.nsesss2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo1a;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo3;
import cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

class SipValidatorK01Test
        extends SipValidatorTestBase {

    static public final String PATH_DATA_K01 = "testdata/NSESSS2024/01 KONTROLA DATA";

    void testPackageK01(String path, LoadType expLoadType,
                        ValidationStatus stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K01 + "/" + path, expLoadType,
                    ZakladniProfilValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.DATOVE_STRUKTURY,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK01_01_OK01() {
        testPackageK01("dat1-OK", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_01_OK02() {
        testPackageK01("dat1-OK1.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_01_01() {
        testPackageK01("dat1-chyba1.xml", LoadType.LT_XML, ValidationStatus.ERROR,
                       null,
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK01_01_02() {
        testPackageK01("dat1-chyba2.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] {
                               Pravidlo1.KOD
                       },
                       new String[] {
                    		   Pravidlo1a.KOD,
                               Pravidlo2.KOD,
                               Pravidlo3.KOD
                       });
    }    

    @Test
    void testK01_01_03() {
        testPackageK01("dat1-chyba3.pdf", LoadType.LT_UNKNOWN, ValidationStatus.ERROR,
                       new String[] { Pravidlo2.KOD },
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_01_04() {
        testPackageK01("dat1-chyba4.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       null,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, 
                               Pravidlo2.KOD, Pravidlo3.KOD });
    }
    
    @Test
    void testK01_01a_OK03() {
        testPackageK01("dat1a-abcdefghijklmnopqrstu123456789ABCDEFGHIJKLMNO-OK3", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_01a_01() {
        testPackageK01("dat1a-#-chyba1", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo1a.KOD });
    }

    @Test
    void testK01_01a_02() {
        testPackageK01("dat1a-abcdefghijklmnopqrstu123456789ABCDEFGHIJKLMNOPQRSTUV-chyba2", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo1a.KOD });
    }

    @Test
    void testK01_01a_03() {
        testPackageK01("dat1a-š-chyba3", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo1a.KOD });
    }

    @Test
    void testK01_02_01() {
        testPackageK01("dat2-chyba1.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo1a.KOD, Pravidlo2.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_02_02() {
        testPackageK01("dat2-chyba2.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo1a.KOD, Pravidlo2.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_02_03() {
        testPackageK01("dat2-chyba3.zip", LoadType.LT_ZIP, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo1a.KOD, Pravidlo2.KOD, Pravidlo3.KOD });
    }

    @Test
    void testK01_03_OK01() {
        testPackageK01("dat3-OK1", LoadType.LT_DIR, ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD, Pravidlo3.KOD },
                       new String[] { });
    }

    @Test
    void testK01_03_01() {
        testPackageK01("dat3-chyba1", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_03_02() {
        testPackageK01("dat3-chyba2", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_03_03() {
        testPackageK01("dat3-chyba3", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }

    @Test
    void testK01_03_04() {
        testPackageK01("dat3-chyba4", LoadType.LT_DIR, ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD, Pravidlo1a.KOD, Pravidlo2.KOD },
                       new String[] { Pravidlo3.KOD });
    }
}
