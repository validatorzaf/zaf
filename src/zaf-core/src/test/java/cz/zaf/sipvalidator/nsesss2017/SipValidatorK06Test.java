package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilyValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

public class SipValidatorK06Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K06 = "testdata/06 KONTROLA OBSAHU";

    void testPackageK06(String path,
                        StavKontroly stavKontroly,
                        ProfilValidace profilValidace,
                        String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K06 + "/" + path, LoadType.LT_DIR,
                    profilValidace,
                    TypUrovenKontroly.OBSAHOVA,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK06_01() {
        testPackageK06("01-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS1 });
    }

    @Test
    void testK06_OK01() {
        testPackageK06("01-OK", StavKontroly.OK,
                       ProfilyValidace.SKARTACE_METADATA,
                       new String[] { K06_Obsahova.OBS1 },
                       new String[] {});
    }

    @Test
    void testK06_02_01() {
        testPackageK06("02-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS2 });
    }

    @Test
    void testK06_02_02() {
        testPackageK06("02-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS2 });
    }

    @Test
    void testK06_02_03() {
        testPackageK06("02-chyba3", StavKontroly.CHYBA,
                       ProfilyValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS2 });
    }

    @Test
    void testK06_02_OK01() {
        testPackageK06("02-OK1", StavKontroly.OK,
                       ProfilyValidace.SKARTACE_METADATA,
                       new String[] { K06_Obsahova.OBS2 },
                       new String[] {});
    }

    @Test
    void testK06_02_OK02() {
        testPackageK06("02-OK2", null,
                       ProfilyValidace.SKARTACE_METADATA,
                       new String[] { K06_Obsahova.OBS2 },
                       new String[] {});
    }

    @Test
    void testK06_03_01() {
        testPackageK06("03-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS3 });
    }

    @Test
    void testK06_03_02() {
        testPackageK06("03-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS3 });
    }

    @Test
    void testK06_03_03() {
        testPackageK06("03-chyba3", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS3 });
    }

    @Test
    void testK06_03_OK01() {
        testPackageK06("03-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS3 },
                       new String[] {});
    }

    @Test
    void testK06_04_01() {
        testPackageK06("04-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS4 });
    }

    @Test
    void testK06_04_OK01() {
        testPackageK06("04-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS4 },
                       new String[] {});
    }

    @Test
    void testK06_09_01() {
        testPackageK06("09-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS9 });
    }

    @Test
    void testK06_09_02() {
        testPackageK06("09-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS9 });
    }

    @Test
    void testK06_09_OK01() {
        testPackageK06("09-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS9 },
                       new String[] {});
    }

    @Test
    void testK06_10_01() {
        testPackageK06("10-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS10 });
    }

    @Test
    void testK06_10_OK01() {
        testPackageK06("10-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS10 },
                       new String[] {});
    }

    @Test
    void testK06_11_01() {
        testPackageK06("11-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS11 });
    }

    @Test
    void testK06_11_02() {
        testPackageK06("11-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS11 });
    }

    @Test
    void testK06_11_OK01() {
        testPackageK06("11-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS11 },
                       new String[] {});
    }

    @Test
    void testK06_12_01() {
        testPackageK06("12-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS12 });
    }

    @Test
    void testK06_12_OK01() {
        testPackageK06("12-OK1", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS12 },
                       new String[] {});
    }

    @Test
    void testK06_12_OK02() {
        testPackageK06("12-OK2", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS12 },
                       new String[] {});
    }

    @Test
    void testK06_13_01() {
        testPackageK06("13-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS13 });
    }

    @Test
    void testK06_13_OK01() {
        testPackageK06("13-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS13 },
                       new String[] {});
    }

    @Test
    void testK06_14_01() {
        testPackageK06("14-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS14 });
    }

    @Test
    void testK06_14_OK01() {
        testPackageK06("14-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS14 },
                       new String[] {});
    }

    @Test
    void testK06_15_01() {
        testPackageK06("15-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS15 });
    }

    @Test
    void testK06_15_OK01() {
        testPackageK06("15-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS15 },
                       new String[] {});
    }

    @Test
    void testK06_16_01() {
        testPackageK06("16-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS16 });
    }

    @Test
    void testK06_16_02() {
        testPackageK06("16-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS16 });
    }

    @Test
    void testK06_16_OK01() {
        testPackageK06("16-OK1", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS16 },
                       new String[] {});
    }

    @Test
    void testK06_16_OK02() {
        testPackageK06("16-OK2", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS16 },
                       new String[] {});
    }

    @Test
    void testK06_17_01() {
        testPackageK06("17-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS17 });
    }

    @Test
    void testK06_17_OK01() {
        testPackageK06("17-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS17 },
                       new String[] {});
    }

    @Test
    void testK06_18_01() {
        testPackageK06("18-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS18 });
    }

    @Test
    void testK06_18_02() {
        testPackageK06("18-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS18 });
    }

    @Test
    void testK06_18_OK01() {
        testPackageK06("18-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS18 },
                       new String[] {});
    }

    @Test
    void testK06_19_01() {
        testPackageK06("19-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS19 });
    }

    @Test
    void testK06_19_02() {
        testPackageK06("19-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS19 });
    }

    @Test
    void testK06_19_OK01() {
        testPackageK06("19-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS19 },
                       new String[] {});
    }

    @Test
    void testK06_20_01() {
        testPackageK06("20-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS20 });
    }

    @Test
    void testK06_20_OK01() {
        testPackageK06("20-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS20 },
                       new String[] {});
    }

    @Test
    void testK06_22_01() {
        testPackageK06("22-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS22 });
    }

    @Test
    void testK06_22_OK01() {
        testPackageK06("22-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS22 },
                       new String[] {});
    }

    @Test
    void testK06_23_01() {
        testPackageK06("23-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS23 });
    }

    @Test
    void testK06_23_02() {
        testPackageK06("23-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS23 });
    }

    @Test
    void testK06_23_OK01() {
        testPackageK06("23-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS23 },
                       new String[] {});
    }

    @Test
    void testK06_24_01() {
        testPackageK06("24-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS24 });
    }

    @Test
    void testK06_24_OK01() {
        testPackageK06("24-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS24 },
                       new String[] {});
    }

    @Test
    void testK06_25_01() {
        testPackageK06("25-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS25 });
    }

    @Test
    void testK06_25_OK01() {
        testPackageK06("25-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS25 },
                       new String[] {});
    }

    @Test
    void testK06_26_01() {
        testPackageK06("26-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS26 });
    }

    @Test
    void testK06_26_02() {
        testPackageK06("26-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS26 });
    }

    @Test
    void testK06_26_OK01() {
        testPackageK06("26-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS26 },
                       new String[] {});
    }

    @Test
    void testK06_27_01() {
        testPackageK06("27-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS27 });
    }

    @Test
    void testK06_27_OK01() {
        testPackageK06("27-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS27 },
                       new String[] {});
    }

    @Test
    void testK06_28_01() {
        testPackageK06("28-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS28 });
    }

    @Test
    void testK06_28_02() {
        testPackageK06("28-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS28 });
    }

    @Test
    void testK06_28_OK01() {
        testPackageK06("28-OK1", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK02() {
        testPackageK06("28-OK2", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK03() {
        testPackageK06("28-OK3", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK04() {
        testPackageK06("28-OK4", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK05() {
        testPackageK06("28-OK5", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK06() {
        testPackageK06("28-OK6", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_29_01() {
        testPackageK06("29-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS29 });
    }

    @Test
    void testK06_29_02() {
        testPackageK06("29-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS29 });
    }

    @Test
    void testK06_29_03() {
        testPackageK06("29-chyba3", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS29 });
    }

    @Test
    void testK06_29_OK01() {
        testPackageK06("29-OK1", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS29 },
                       new String[] {});
    }

    @Test
    void testK06_29_OK02() {
        testPackageK06("29-OK2", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS29 },
                       new String[] {});
    }

    @Test
    void testK06_30_01() {
        testPackageK06("30-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS30 });
    }

    @Test
    void testK06_30_02() {
        testPackageK06("30-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS30 });
    }

    @Test
    void testK06_30_OK01() {
        testPackageK06("30-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS30 },
                       new String[] {});
    }

    @Test
    void testK06_31_01() {
        testPackageK06("31-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS31 });
    }

    @Test
    void testK06_31_02() {
        testPackageK06("31-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS31 });
    }

    @Test
    void testK06_31_03() {
        testPackageK06("31-chyba3", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS31 });
    }

    @Test
    void testK06_31_OK01() {
        testPackageK06("31-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS31 },
                       new String[] {});
    }

    @Test
    void testK06_33_01() {
        testPackageK06("33-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS33 });
    }

    @Test
    void testK06_33_02() {
        testPackageK06("33-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS33 });
    }

    @Test
    void testK06_33_OK01() {
        testPackageK06("33-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS33 },
                       new String[] {});
    }

    @Test
    void testK06_34_01() {
        testPackageK06("34-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS34 });
    }

    @Test
    void testK06_34_02() {
        testPackageK06("34-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS34 });
    }

    @Test
    void testK06_34_OK01() {
        testPackageK06("34-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS34 },
                       new String[] {});
    }

    @Test
    void testK06_35_01() {
        testPackageK06("35-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS35 });
    }

    @Test
    void testK06_35_02() {
        testPackageK06("35-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS35 });
    }

    @Test
    void testK06_35_03() {
        testPackageK06("35-chyba3", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS35 });
    }

    @Test
    void testK06_35_OK01() {
        testPackageK06("35-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS35 },
                       new String[] {});
    }

    @Test
    void testK06_36_01() {
        testPackageK06("36-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS36 });
    }

    @Test
    void testK06_36_OK01() {
        testPackageK06("36-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS36 },
                       new String[] {});
    }

    @Test
    void testK06_37_01() {
        testPackageK06("37-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS37 });
    }

    @Test
    void testK06_37_02() {
        testPackageK06("37-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS37 });
    }

    @Test
    void testK06_37_OK01() {
        testPackageK06("37-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS37 },
                       new String[] {});
    }

    @Test
    void testK06_38_01() {
        testPackageK06("38-chyba", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS38 });
    }

    @Test
    void testK06_38_OK01() {
        testPackageK06("38-OK1", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS38 },
                       new String[] {});
    }

    @Test
    void testK06_38_OK02() {
        testPackageK06("38-OK2", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS38 },
                       new String[] {});
    }

    @Test
    void testK06_39_01() {
        testPackageK06("39-chyba1", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS39 });
    }

    @Test
    void testK06_39_02() {
        testPackageK06("39-chyba2", StavKontroly.CHYBA,
                       ProfilyValidace.PREJIMKA,
                       new String[] {},
                       new String[] { K06_Obsahova.OBS39 });
    }

    @Test
    void testK06_39_OK01() {
        testPackageK06("39-OK", null,
                       ProfilyValidace.PREJIMKA,
                       new String[] { K06_Obsahova.OBS39 },
                       new String[] {});
    }
}
