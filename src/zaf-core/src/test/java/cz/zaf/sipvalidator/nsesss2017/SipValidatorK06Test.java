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
}
