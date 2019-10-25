package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilyValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

class SipValidatorK01Test
        extends SipValidatorTestBase {

    static public final String PATH_DATA_K01 = "testdata/01 KONTROLA DATA";


    void testPackageK01(String path, LoadType expLoadType,
                        StavKontroly stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K01 + "/" + path, expLoadType,
                    ProfilyValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.DATOVE_STRUKTURY,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK01_01() {
        testPackageK01("data1-OK", LoadType.LT_DIR, StavKontroly.CHYBA, 
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2 },
                       new String[] { K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_02() {
        testPackageK01("data1-chyba1.xml", LoadType.LT_XML, StavKontroly.CHYBA,
                       null,
                       new String[] { K01_DatoveStruktury.DATA1 });
    }

    @Test
    void testK01_03() {
        testPackageK01("data1-chyba2.zip", LoadType.LT_ZIP, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1 },
                       new String[] { K01_DatoveStruktury.DATA2 });
    }

    @Test
    void testK01_04() {
        testPackageK01("data1-chyba3.pdf", LoadType.LT_UNKNOWN, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA2 },
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_05() {
        testPackageK01("data1-chyba4.zip", LoadType.LT_ZIP, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1 },
                       new String[] { K01_DatoveStruktury.DATA2, K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_06() {
        testPackageK01("data2-chyba1.zip", LoadType.LT_ZIP, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1 },
                       new String[] { K01_DatoveStruktury.DATA2, K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_07() {
        testPackageK01("data2-chyba2.zip", LoadType.LT_ZIP, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1 },
                       new String[] { K01_DatoveStruktury.DATA2, K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_08() {
        testPackageK01("data2-chyba3.zip", LoadType.LT_ZIP, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1 },
                       new String[] { K01_DatoveStruktury.DATA2, K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_09() {
        testPackageK01("data2-OK1.zip", LoadType.LT_ZIP, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2 },
                       new String[] { K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_10() {
        testPackageK01("2019-05-13-MUJESS00046030-00040003.zip", LoadType.LT_ZIP, StavKontroly.OK,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2, K01_DatoveStruktury.DATA3 },
                       new String[] {});
    }

    @Test
    void testK01_11() {
        testPackageK01("data3-chyba1", LoadType.LT_DIR, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2 },
                       new String[] { K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_12() {
        testPackageK01("data3-chyba2", LoadType.LT_DIR, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2 },
                       new String[] { K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_13() {
        testPackageK01("data3-chyba3", LoadType.LT_DIR, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2 },
                       new String[] { K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_14() {
        testPackageK01("data3-chyba4", LoadType.LT_DIR, StavKontroly.CHYBA,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2 },
                       new String[] { K01_DatoveStruktury.DATA3 });
    }

    @Test
    void testK01_15() {
        testPackageK01("data3-OK1", LoadType.LT_DIR, StavKontroly.OK,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2, K01_DatoveStruktury.DATA3 },
                       null);
    }

    @Test
    void testK01_16() {
        testPackageK01("data3-OK2", LoadType.LT_DIR, StavKontroly.OK,
                       new String[] { K01_DatoveStruktury.DATA1, K01_DatoveStruktury.DATA2, K01_DatoveStruktury.DATA3 },
                       null);
    }
}
