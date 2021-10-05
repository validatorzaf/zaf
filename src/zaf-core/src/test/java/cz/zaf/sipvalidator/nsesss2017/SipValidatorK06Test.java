package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo3;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo4;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo9;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo10;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo11;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo12;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo13;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo14;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo15;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo16;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo17;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo18;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo19;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo20;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo22;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo23;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo24;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo25;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo26;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo27;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo28;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo29;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo30;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo31;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo33;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo34;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo35;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo36;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo37;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo38;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo39;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo40;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo44;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo45;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo46;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo47;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo48;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo49;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo50;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo51;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo52;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo53;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo54;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo54a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo55;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo56;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo57;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo58;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo59;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo60;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo61;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo61a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo62;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo63;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo64;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo65;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo66;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo67;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo68;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo69;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo70;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo71;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo72;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo73;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo74;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo75;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo76;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo77;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo78;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo79;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo80;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo81;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo82;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo83;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo84;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo85;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo86;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo87;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo88;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo89;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo90;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo91;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo92;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo94;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo95;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo96;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo97;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo98;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo99;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
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
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { Pravidlo1.OBS1 });
    }

    @Test
    void testK06_OK01() {
        testPackageK06("01-OK", StavKontroly.OK,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo1.OBS1 },
                       new String[] {});
    }

    @Test
    void testK06_02_01() {
        testPackageK06("02-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { Pravidlo2.OBS2 });
    }

    @Test
    void testK06_02_02() {
        testPackageK06("02-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { Pravidlo2.OBS2 });
    }

    @Test
    void testK06_02_03() {
        testPackageK06("02-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { Pravidlo2.OBS2 });
    }

    @Test
    void testK06_02_OK01() {
        testPackageK06("02-OK1", StavKontroly.OK,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo2.OBS2 },
                       new String[] {});
    }

    @Test
    void testK06_02_OK02() {
        testPackageK06("02-OK2", null,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo2.OBS2 },
                       new String[] {});
    }

    @Test
    void testK06_03_01() {
        testPackageK06("03-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo3.OBS3 });
    }

    @Test
    void testK06_03_02() {
        testPackageK06("03-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo3.OBS3 });
    }

    @Test
    void testK06_03_03() {
        testPackageK06("03-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo3.OBS3 });
    }

    @Test
    void testK06_03_OK01() {
        testPackageK06("03-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo3.OBS3 },
                       new String[] {});
    }

    @Test
    void testK06_04_01() {
        testPackageK06("04-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo4.OBS4 });
    }

    @Test
    void testK06_04_OK01() {
        testPackageK06("04-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo4.OBS4 },
                       new String[] {});
    }

    @Test
    void testK06_09_01() {
        testPackageK06("09-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo9.OBS9 });
    }

    @Test
    void testK06_09_02() {
        testPackageK06("09-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo9.OBS9 });
    }

    @Test
    void testK06_09_OK01() {
        testPackageK06("09-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo9.OBS9 },
                       new String[] {});
    }

    @Test
    void testK06_10_01() {
        testPackageK06("10-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo10.OBS10 });
    }

    @Test
    void testK06_10_OK01() {
        testPackageK06("10-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo10.OBS10 },
                       new String[] {});
    }

    @Test
    void testK06_11_01() {
        testPackageK06("11-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo11.OBS11 });
    }

    @Test
    void testK06_11_02() {
        testPackageK06("11-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo11.OBS11 });
    }

    @Test
    void testK06_11_OK01() {
        testPackageK06("11-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo11.OBS11 },
                       new String[] {});
    }

    @Test
    void testK06_12_01() {
        testPackageK06("12-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo12.OBS12 });
    }

    @Test
    void testK06_12_OK01() {
        testPackageK06("12-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo12.OBS12 },
                       new String[] {});
    }

    @Test
    void testK06_12_OK02() {
        testPackageK06("12-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo12.OBS12 },
                       new String[] {});
    }

    @Test
    void testK06_13_01() {
        testPackageK06("13-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo13.OBS13 });
    }

    @Test
    void testK06_13_OK01() {
        testPackageK06("13-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo13.OBS13 },
                       new String[] {});
    }

    @Test
    void testK06_14_01() {
        testPackageK06("14-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo14.OBS14 });
    }

    @Test
    void testK06_14_OK01() {
        testPackageK06("14-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo14.OBS14 },
                       new String[] {});
    }

    @Test
    void testK06_15_01() {
        testPackageK06("15-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo15.OBS15 });
    }

    @Test
    void testK06_15_OK01() {
        testPackageK06("15-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo15.OBS15 },
                       new String[] {});
    }

    @Test
    void testK06_16_01() {
        testPackageK06("16-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo16.OBS16 });
    }

    @Test
    void testK06_16_02() {
        testPackageK06("16-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo16.OBS16 });
    }

    @Test
    void testK06_16_OK01() {
        testPackageK06("16-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo16.OBS16 },
                       new String[] {});
    }

    @Test
    void testK06_16_OK02() {
        testPackageK06("16-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo16.OBS16 },
                       new String[] {});
    }

    @Test
    void testK06_17_01() {
        testPackageK06("17-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo17.OBS17 });
    }

    @Test
    void testK06_17_OK01() {
        testPackageK06("17-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo17.OBS17 },
                       new String[] {});
    }

    @Test
    void testK06_18_01() {
        testPackageK06("18-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo18.OBS18 });
    }

    @Test
    void testK06_18_02() {
        testPackageK06("18-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo18.OBS18 });
    }

    @Test
    void testK06_18_OK01() {
        testPackageK06("18-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo18.OBS18 },
                       new String[] {});
    }

    @Test
    void testK06_19_01() {
        testPackageK06("19-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo19.OBS19 });
    }

    @Test
    void testK06_19_02() {
        testPackageK06("19-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo19.OBS19 });
    }

    @Test
    void testK06_19_OK01() {
        testPackageK06("19-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo19.OBS19 },
                       new String[] {});
    }

    @Test
    void testK06_20_01() {
        testPackageK06("20-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo20.OBS20 });
    }

    @Test
    void testK06_20_OK01() {
        testPackageK06("20-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo20.OBS20 },
                       new String[] {});
    }

    @Test
    void testK06_22_01() {
        testPackageK06("22-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo22.OBS22 });
    }

    @Test
    void testK06_22_OK01() {
        testPackageK06("22-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo22.OBS22 },
                       new String[] {});
    }

    @Test
    void testK06_23_01() {
        testPackageK06("23-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo23.OBS23 });
    }

    @Test
    void testK06_23_02() {
        testPackageK06("23-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo23.OBS23 });
    }

    @Test
    void testK06_23_OK01() {
        testPackageK06("23-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo23.OBS23 },
                       new String[] {});
    }

    @Test
    void testK06_24_01() {
        testPackageK06("24-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo24.OBS24 });
    }

    @Test
    void testK06_24_OK01() {
        testPackageK06("24-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo24.OBS24 },
                       new String[] {});
    }

    @Test
    void testK06_25_01() {
        testPackageK06("25-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo25.OBS25 });
    }

    @Test
    void testK06_25_OK01() {
        testPackageK06("25-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo25.OBS25 },
                       new String[] {});
    }

    @Test
    void testK06_26_01() {
        testPackageK06("26-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo26.OBS26 });
    }

    @Test
    void testK06_26_02() {
        testPackageK06("26-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo26.OBS26 });
    }

    @Test
    void testK06_26_OK01() {
        testPackageK06("26-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo26.OBS26 },
                       new String[] {});
    }

    @Test
    void testK06_27_01() {
        testPackageK06("27-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo27.OBS27 });
    }

    @Test
    void testK06_27_OK01() {
        testPackageK06("27-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo27.OBS27 },
                       new String[] {});
    }

    @Test
    void testK06_28_01() {
        testPackageK06("28-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo28.OBS28 });
    }

    @Test
    void testK06_28_02() {
        testPackageK06("28-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo28.OBS28 });
    }

    @Test
    void testK06_28_OK01() {
        testPackageK06("28-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo28.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK02() {
        testPackageK06("28-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo28.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK03() {
        testPackageK06("28-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo28.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK04() {
        testPackageK06("28-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo28.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK05() {
        testPackageK06("28-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo28.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_28_OK06() {
        testPackageK06("28-OK6", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo28.OBS28 },
                       new String[] {});
    }

    @Test
    void testK06_29_01() {
        testPackageK06("29-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo29.OBS29 });
    }

    @Test
    void testK06_29_02() {
        testPackageK06("29-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo29.OBS29 });
    }

    @Test
    void testK06_29_03() {
        testPackageK06("29-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo29.OBS29 });
    }

    @Test
    void testK06_29_OK01() {
        testPackageK06("29-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo29.OBS29 },
                       new String[] {});
    }

    @Test
    void testK06_29_OK02() {
        testPackageK06("29-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo29.OBS29 },
                       new String[] {});
    }

    @Test
    void testK06_30_01() {
        testPackageK06("30-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo30.OBS30 });
    }

    @Test
    void testK06_30_02() {
        testPackageK06("30-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo30.OBS30 });
    }

    @Test
    void testK06_30_OK01() {
        testPackageK06("30-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo30.OBS30 },
                       new String[] {});
    }

    @Test
    void testK06_31_01() {
        testPackageK06("31-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo31.OBS31 });
    }

    @Test
    void testK06_31_02() {
        testPackageK06("31-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo31.OBS31 });
    }

    @Test
    void testK06_31_03() {
        testPackageK06("31-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo31.OBS31 });
    }

    @Test
    void testK06_31_OK01() {
        testPackageK06("31-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo31.OBS31 },
                       new String[] {});
    }

    @Test
    void testK06_33_01() {
        testPackageK06("33-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo33.OBS33 });
    }

    @Test
    void testK06_33_02() {
        testPackageK06("33-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo33.OBS33 });
    }

    @Test
    void testK06_33_OK01() {
        testPackageK06("33-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo33.OBS33 },
                       new String[] {});
    }

    @Test
    void testK06_34_01() {
        testPackageK06("34-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo34.OBS34 });
    }

    @Test
    void testK06_34_02() {
        testPackageK06("34-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo34.OBS34 });
    }

    @Test
    void testK06_34_OK01() {
        testPackageK06("34-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo34.OBS34 },
                       new String[] {});
    }

    @Test
    void testK06_35_01() {
        testPackageK06("35-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo35.OBS35 });
    }

    @Test
    void testK06_35_02() {
        testPackageK06("35-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo35.OBS35 });
    }

    @Test
    void testK06_35_03() {
        testPackageK06("35-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo35.OBS35 });
    }

    @Test
    void testK06_35_OK01() {
        testPackageK06("35-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo35.OBS35 },
                       new String[] {});
    }

    @Test
    void testK06_36_01() {
        testPackageK06("36-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo36.OBS36 });
    }

    @Test
    void testK06_36_OK01() {
        testPackageK06("36-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo36.OBS36 },
                       new String[] {});
    }

    @Test
    void testK06_37_01() {
        testPackageK06("37-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo37.OBS37 });
    }

    @Test
    void testK06_37_02() {
        testPackageK06("37-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo37.OBS37 });
    }

    @Test
    void testK06_37_OK01() {
        testPackageK06("37-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo37.OBS37 },
                       new String[] {});
    }

    @Test
    void testK06_38_01() {
        testPackageK06("38-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo38.OBS38 });
    }

    @Test
    void testK06_38_OK01() {
        testPackageK06("38-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo38.OBS38 },
                       new String[] {});
    }

    @Test
    void testK06_38_OK02() {
        testPackageK06("38-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo38.OBS38 },
                       new String[] {});
    }

    @Test
    void testK06_39_01() {
        testPackageK06("39-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo39.OBS39 });
    }

    @Test
    void testK06_39_02() {
        testPackageK06("39-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo39.OBS39 });
    }

    @Test
    void testK06_39_OK01() {
        testPackageK06("39-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo39.OBS39 },
                       new String[] {});
    }

    //------------- 4x ------------
    @Test
    void testK06_40_01() {
        testPackageK06("40-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo40.OBS40 });
    }

    @Test
    void testK06_40_OK01() {
        testPackageK06("40-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo40.OBS40 },
                       new String[] {});
    }

    @Test
    void testK06_40_OK02() {
        testPackageK06("40-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo40.OBS40 },
                       new String[] {});
    }

    @Test
    void testK06_44_01() {
        testPackageK06("44-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo44.OBS44 });
    }

    @Test
    void testK06_44_02() {
        testPackageK06("44-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo44.OBS44 });
    }

    @Test
    void testK06_44_OK01() {
        testPackageK06("44-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo44.OBS44 },
                       new String[] {});
    }

    @Test
    void testK06_45_01() {
        testPackageK06("45-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_02() {
        testPackageK06("45-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_03() {
        testPackageK06("45-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_04() {
        testPackageK06("45-chyba4", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_05() {
        testPackageK06("45-chyba5", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_06() {
        testPackageK06("45-chyba6", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_07() {
        testPackageK06("45-chyba7", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_08() {
        testPackageK06("45-chyba8", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo45.OBS45 });
    }

    @Test
    void testK06_45_OK01() {
        testPackageK06("45-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK02() {
        testPackageK06("45-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK03() {
        testPackageK06("45-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK04() {
        testPackageK06("45-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK05() {
        testPackageK06("45-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK06() {
        testPackageK06("45-OK6", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK07() {
        testPackageK06("45-OK7", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK08() {
        testPackageK06("45-OK8", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK09() {
        testPackageK06("45-OK9", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK10() {
        testPackageK06("45-OK10", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_45_OK11() {
        testPackageK06("45-OK11", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo45.OBS45 },
                       new String[] {});
    }

    @Test
    void testK06_46_01() {
        testPackageK06("46-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo46.OBS46 });
    }

    @Test
    void testK06_46_02() {
        testPackageK06("46-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo46.OBS46 });
    }

    @Test
    void testK06_46_OK01() {
        testPackageK06("46-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo46.OBS46 },
                       new String[] {});
    }

    @Test
    void testK06_46_OK02() {
        testPackageK06("46-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo46.OBS46 },
                       new String[] {});
    }

    @Test
    void testK06_47_01() {
        testPackageK06("47-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo47.OBS47 });
    }

    @Test
    void testK06_47_02() {
        testPackageK06("47-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo47.OBS47 });
    }

    @Test
    void testK06_47_OK01() {
        testPackageK06("47-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo47.OBS47 },
                       new String[] {});
    }

    @Test
    void testK06_47_OK02() {
        testPackageK06("47-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo47.OBS47 },
                       new String[] {});
    }

    @Test
    void testK06_48_01() {
        testPackageK06("48-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo48.OBS48 });
    }

    @Test
    void testK06_48_02() {
        testPackageK06("48-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo48.OBS48 });
    }

    @Test
    void testK06_48_OK01() {
        testPackageK06("48-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo48.OBS48 },
                       new String[] {});
    }

    @Test
    void testK06_49_01() {
        testPackageK06("49-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo49.OBS49 });
    }

    @Test
    void testK06_49_OK01() {
        testPackageK06("49-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo49.OBS49 },
                       new String[] {});
    }

    //------------- 50-59 ------------

    @Test
    void testK06_50_01() {
        testPackageK06("50-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo50.OBS50 });
    }

    @Test
    void testK06_50_02() {
        testPackageK06("50-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo50.OBS50 });
    }

    @Test
    void testK06_50_03() {
        testPackageK06("50-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo50.OBS50 });
    }

    @Test
    void testK06_50_OK01() {
        testPackageK06("50-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo50.OBS50 },
                       new String[] {});
    }

    @Test
    void testK06_50_OK02() {
        testPackageK06("50-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo50.OBS50 },
                       new String[] {});
    }

    @Test
    void testK06_50_OK03() {
        testPackageK06("50-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo50.OBS50 },
                       new String[] {});
    }

    @Test
    void testK06_51_01() {
        testPackageK06("51-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo51.OBS51 });
    }

    @Test
    void testK06_51_OK01() {
        testPackageK06("51-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo51.OBS51 },
                       new String[] {});
    }

    @Test
    void testK06_52_01() {
        testPackageK06("52-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo52.OBS52 });
    }

    @Test
    void testK06_52_02() {
        testPackageK06("52-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo52.OBS52 });
    }

    @Test
    void testK06_52_03() {
        testPackageK06("52-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo52.OBS52 });
    }

    @Test
    void testK06_52_04() {
        testPackageK06("52-chyba4", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo52.OBS52 });
    }

    @Test
    void testK06_52_OK01() {
        testPackageK06("52-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo52.OBS52 },
                       new String[] {});
    }

    @Test
    void testK06_52_OK02() {
        testPackageK06("52-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo52.OBS52 },
                       new String[] {});
    }

    @Test
    void testK06_53_01() {
        testPackageK06("53-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo53.OBS53 });
    }

    @Test
    void testK06_53_OK01() {
        testPackageK06("53-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo53.OBS53 },
                       new String[] {});
    }

    @Test
    void testK06_54_01() {
        testPackageK06("54-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_02() {
        testPackageK06("54-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_03() {
        testPackageK06("54-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_04() {
        testPackageK06("54-chyba4", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_05() {
        testPackageK06("54-chyba5", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_06() {
        testPackageK06("54-chyba6", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_07() {
        testPackageK06("54-chyba7", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_08() {
        testPackageK06("54-chyba8", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_09() {
        testPackageK06("54-chyba9", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_OK01() {
        testPackageK06("54-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54.OBS54 },
                       new String[] {});
    }

    @Test
    void testK06_54_OK02() {
        testPackageK06("54-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54.OBS54 },
                       new String[] {});
    }

    @Test
    void testK06_54a_01() {
        testPackageK06("54a-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54a.OBS54A });
    }

    @Test
    void testK06_54a_02() {
        testPackageK06("54a-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54a.OBS54A });
    }

    @Test
    void testK06_54a_03() {
        testPackageK06("54a-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54a.OBS54A });
    }

    @Test
    void testK06_54a_04() {
        testPackageK06("54a-chyba4", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54a.OBS54A });
    }

    @Test
    void testK06_54a_05() {
        testPackageK06("54a-chyba5", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54a.OBS54A });
    }

    @Test
    void testK06_54a_OK01() {
        testPackageK06("54a-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54a.OBS54A },
                       new String[] {});
    }

    @Test
    void testK06_54a_OK02() {
        testPackageK06("54a-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54a.OBS54A },
                       new String[] {});
    }

    @Test
    void testK06_54a_OK03() {
        testPackageK06("54a-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54a.OBS54A },
                       new String[] {});
    }

    @Test
    void testK06_55_01() {
        testPackageK06("55-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo55.OBS55 });
    }

    @Test
    void testK06_55_02() {
        testPackageK06("55-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo55.OBS55 });
    }

    @Test
    void testK06_55_OK01() {
        testPackageK06("55-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo55.OBS55 },
                       new String[] {});
    }

    @Test
    void testK06_56_01() {
        testPackageK06("56-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo56.OBS56 });
    }

    @Test
    void testK06_56_02() {
        testPackageK06("56-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo56.OBS56 });
    }

    @Test
    void testK06_56_OK01() {
        testPackageK06("56-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo56.OBS56 },
                       new String[] {});
    }

    @Test
    void testK06_57_01() {
        testPackageK06("57-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo57.OBS57 });
    }

    @Test
    void testK06_57_02() {
        testPackageK06("57-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo57.OBS57 });
    }

    @Test
    void testK06_57_03() {
        testPackageK06("57-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo57.OBS57 });
    }

    @Test
    void testK06_57_OK01() {
        testPackageK06("57-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo57.OBS57 },
                       new String[] {});
    }

    @Test
    void testK06_58_01() {
        testPackageK06("58-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo58.OBS58 });
    }

    @Test
    void testK06_58_02() {
        testPackageK06("58-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo58.OBS58 });
    }

    @Test
    void testK06_58_OK01() {
        testPackageK06("58-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo58.OBS58 },
                       new String[] {});
    }

    @Test
    void testK06_59_01() {
        testPackageK06("59-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_02() {
        testPackageK06("59-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_03() {
        testPackageK06("59-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_04() {
        testPackageK06("59-chyba4", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_05() {
        testPackageK06("59-chyba5", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_06() {
        testPackageK06("59-chyba6", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_07() {
        testPackageK06("59-chyba7", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_08() {
        testPackageK06("59-chyba8", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_OK01() {
        testPackageK06("59-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo59.OBS59 },
                       new String[] {});
    }

    @Test
    void testK06_59_OK02() {
        testPackageK06("59-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo59.OBS59 },
                       new String[] {});
    }

    @Test
    void testK06_59_OK03() {
        testPackageK06("59-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo59.OBS59 },
                       new String[] {});
    }

    @Test
    void testK06_59_OK04() {
        testPackageK06("59-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo59.OBS59 },
                       new String[] {});
    }

    @Test
    void testK06_59_OK05() {
        testPackageK06("59-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo59.OBS59 },
                       new String[] {});
    }

    @Test
    void testK06_59_OK06() {
        testPackageK06("59-OK6", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo59.OBS59 },
                       new String[] {});
    }

    //------------- 60-69 ------------
    @Test
    void testK06_60_01() {
        testPackageK06("60-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo60.OBS60 });
    }

    @Test
    void testK06_60_OK01() {
        testPackageK06("60-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo60.OBS60 },
                       new String[] {});
    }

    @Test
    void testK06_61_01() {
        testPackageK06("61-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo61.OBS61 });
    }

    @Test
    void testK06_61_OK01() {
        testPackageK06("61-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo61.OBS61 },
                       new String[] {});
    }

    @Test
    void testK06_61a_01() {
        testPackageK06("61a-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo61a.OBS61A });
    }

    @Test
    void testK06_61a_OK01() {
        testPackageK06("61a-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo61a.OBS61A },
                       new String[] {});
    }

    @Test
    void testK06_62_01() {
        testPackageK06("62-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo62.OBS62 });
    }

    @Test
    void testK06_62_OK01() {
        testPackageK06("62-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo62.OBS62 },
                       new String[] {});
    }

    @Test
    void testK06_62_OK02() {
        testPackageK06("62-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo62.OBS62 },
                       new String[] {});
    }

    void testK06_63_01() {
        testPackageK06("63-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo63.OBS63 });
    }

    @Test
    void testK06_63_OK01() {
        testPackageK06("63-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo63.OBS63 },
                       new String[] {});
    }

    @Test
    void testK06_64_01() {
        testPackageK06("64-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo64.OBS64 });
    }

    @Test
    @Disabled
    void testK06_64_02() {
        testPackageK06("64-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo64.OBS64 });
    }

    @Test
    void testK06_64_OK01() {
        testPackageK06("64-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo64.OBS64 },
                       new String[] {});
    }

    @Test
    void testK06_64_OK02() {
        testPackageK06("64-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo64.OBS64 },
                       new String[] {});
    }

    @Test
    void testK06_64_OK03() {
        testPackageK06("64-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo64.OBS64 },
                       new String[] {});
    }

    @Test
    void testK06_65_01() {
        testPackageK06("65-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo65.OBS65 });
    }

    @Test
    void testK06_65_02() {
        testPackageK06("65-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo65.OBS65 });
    }

    @Test
    void testK06_65_OK01() {
        testPackageK06("65-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo65.OBS65 },
                       new String[] {});
    }

    @Test
    void testK06_65_OK02() {
        testPackageK06("65-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo65.OBS65 },
                       new String[] {});
    }

    @Test
    void testK06_65_OK03() {
        testPackageK06("65-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo65.OBS65 },
                       new String[] {});
    }

    @Test
    void testK06_65_OK04() {
        testPackageK06("65-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo65.OBS65 },
                       new String[] {});
    }

    @Test
    void testK06_65_OK05() {
        testPackageK06("65-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo65.OBS65 },
                       new String[] {});
    }

    @Test
    void testK06_66_01() {
        testPackageK06("66-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo66.OBS66 });
    }

    @Test
    void testK06_66_OK01() {
        testPackageK06("66-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo66.OBS66 },
                       new String[] {});
    }

    @Test
    void testK06_67_01() {
        testPackageK06("67-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo67.OBS67 });
    }

    @Test
    void testK06_67_OK01() {
        testPackageK06("67-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo67.OBS67 },
                       new String[] {});
    }

    @Test
    void testK06_68_01() {
        testPackageK06("68-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo68.OBS68 });
    }

    @Test
    void testK06_68_02() {
        testPackageK06("68-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo68.OBS68 });
    }

    @Test
    void testK06_68_OK01() {
        testPackageK06("68-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo68.OBS68 },
                       new String[] {});
    }

    @Test
    void testK06_68_OK02() {
        testPackageK06("68-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo68.OBS68 },
                       new String[] {});
    }

    @Test
    void testK06_68_OK03() {
        testPackageK06("68-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo68.OBS68 },
                       new String[] {});
    }

    @Test
    void testK06_68_OK04() {
        testPackageK06("68-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo68.OBS68 },
                       new String[] {});
    }

    @Test
    void testK06_68_OK05() {
        testPackageK06("68-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo68.OBS68 },
                       new String[] {});
    }

    void testK06_69_01() {
        testPackageK06("69-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo69.OBS69 });
    }

    @Test
    void testK06_69_OK01() {
        testPackageK06("69-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo69.OBS69 },
                       new String[] {});
    }

    //------------- 70-79 ------------
    @Test
    void testK06_70_01() {
        testPackageK06("70-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo70.OBS70 });
    }

    @Test
    void testK06_70_OK01() {
        testPackageK06("70-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo70.OBS70 },
                       new String[] {});
    }

    @Test
    void testK06_71_01() {
        testPackageK06("71-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo71.OBS71 });
    }

    @Test
    void testK06_71_OK01() {
        testPackageK06("71-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo71.OBS71 },
                       new String[] {});
    }

    @Test
    void testK06_72_01() {
        testPackageK06("72-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo72.OBS72 });
    }

    @Test
    void testK06_72_OK01() {
        testPackageK06("72-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo72.OBS72 },
                       new String[] {});
    }

    @Test
    void testK06_72_OK02() {
        testPackageK06("72-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo72.OBS72 },
                       new String[] {});
    }

    @Test
    void testK06_73_01() {
        testPackageK06("73-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo73.OBS73 });
    }

    @Test
    void testK06_73_OK01() {
        testPackageK06("73-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo73.OBS73 },
                       new String[] {});
    }

    @Test
    void testK06_73_OK02() {
        testPackageK06("73-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo73.OBS73 },
                       new String[] {});
    }

    @Test
    void testK06_74_01() {
        testPackageK06("74-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo74.OBS74 });
    }

    @Test
    void testK06_74_OK01() {
        testPackageK06("74-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo74.OBS74 },
                       new String[] {});
    }

    @Test
    void testK06_74_OK02() {
        testPackageK06("74-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo74.OBS74 },
                       new String[] {});
    }

    @Test
    void testK06_75_01() {
        testPackageK06("75-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo75.OBS75 });
    }

    @Test
    void testK06_75_OK01() {
        testPackageK06("75-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo75.OBS75 },
                       new String[] {});
    }

    @Test
    void testK06_75_OK02() {
        testPackageK06("75-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo75.OBS75 },
                       new String[] {});
    }

    @Test
    void testK06_75_OK03() {
        testPackageK06("75-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo75.OBS75 },
                       new String[] {});
    }

    @Test
    void testK06_76_01() {
        testPackageK06("76-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo76.OBS76 });
    }

    @Test
    void testK06_76_OK01() {
        testPackageK06("76-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo76.OBS76 },
                       new String[] {});
    }

    @Test
    void testK06_77_01() {
        testPackageK06("77-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo77.OBS77 });
    }

    @Test
    void testK06_77_02() {
        testPackageK06("77-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo77.OBS77 });
    }

    @Test
    void testK06_77_OK01() {
        testPackageK06("77-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo77.OBS77 },
                       new String[] {});
    }

    @Test
    void testK06_77_OK02() {
        testPackageK06("77-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo77.OBS77 },
                       new String[] {});
    }

    @Test
    void testK06_77_OK03() {
        testPackageK06("77-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo77.OBS77 },
                       new String[] {});
    }

    @Test
    void testK06_78_01() {
        testPackageK06("78-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo78.OBS78 });
    }

    @Test
    void testK06_78_02() {
        testPackageK06("78-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo78.OBS78 });
    }

    @Test
    void testK06_78_OK01() {
        testPackageK06("78-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo78.OBS78 },
                       new String[] {});
    }

    @Test
    void testK06_79_01() {
        testPackageK06("79-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo79.OBS79 });
    }

    @Test
    void testK06_79_OK01() {
        testPackageK06("79-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo79.OBS79 },
                       new String[] {});
    }

    //------------- 80-89 ------------
    @Test
    void testK06_80_01() {
        testPackageK06("80-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo80.OBS80 });
    }

    @Test
    void testK06_80_OK01() {
        testPackageK06("80-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo80.OBS80 },
                       new String[] {});
    }

    @Test
    void testK06_81_01() {
        testPackageK06("81-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo81.OBS81 });
    }

    @Test
    void testK06_81_OK01() {
        testPackageK06("81-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo81.OBS81 },
                       new String[] {});
    }

    @Test
    void testK06_82_01() {
        testPackageK06("82-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo82.OBS82 });
    }

    @Test
    void testK06_82_OK01() {
        testPackageK06("82-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo82.OBS82 },
                       new String[] {});
    }

    @Test
    void testK06_82_OK02() {
        testPackageK06("82-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo82.OBS82 },
                       new String[] {});
    }

    @Test
    void testK06_83_01() {
        testPackageK06("83-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo83.OBS83 });
    }

    @Test
    void testK06_83_OK01() {
        testPackageK06("83-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo83.OBS83 },
                       new String[] {});
    }

    @Test
    void testK06_83_OK02() {
        testPackageK06("83-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo83.OBS83 },
                       new String[] {});
    }

    @Test
    void testK06_84_01() {
        testPackageK06("84-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo84.OBS84 });
    }

    @Test
    void testK06_84_OK01() {
        testPackageK06("84-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo84.OBS84 },
                       new String[] {});
    }

    @Test
    void testK06_85_01() {
        testPackageK06("85-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo85.OBS85 });
    }

    @Test
    void testK06_85_OK01() {
        testPackageK06("85-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo85.OBS85 },
                       new String[] {});
    }

    @Test
    void testK06_86_01() {
        testPackageK06("86-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo86.OBS86 });
    }

    @Test
    void testK06_86_OK01() {
        testPackageK06("86-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo86.OBS86 },
                       new String[] {});
    }

    @Test
    void testK06_87_01() {
        testPackageK06("87-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo87.OBS87 });
    }

    @Test
    void testK06_87_OK01() {
        testPackageK06("87-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo87.OBS87 },
                       new String[] {});
    }

    @Test
    void testK06_88_01() {
        testPackageK06("88-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo88.OBS88 });
    }

    @Test
    void testK06_88_OK01() {
        testPackageK06("88-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo88.OBS88 },
                       new String[] {});
    }

    @Test
    void testK06_89_01() {
        testPackageK06("89-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo89.OBS89 });
    }

    @Test
    void testK06_89_OK01() {
        testPackageK06("89-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo89.OBS89 },
                       new String[] {});
    }

    void testK06_89_OK02() {
        testPackageK06("89-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo89.OBS89 },
                       new String[] {});
    }
    //------------- 90-99 ------------

    @Test
    void testK06_90_01() {
        testPackageK06("90-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo90.OBS90 });
    }

    @Test
    void testK06_90_OK01() {
        testPackageK06("90-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo90.OBS90 },
                       new String[] {});
    }

    @Test
    void testK06_91_01() {
        testPackageK06("91-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo91.OBS91 });
    }

    @Test
    void testK06_91_OK01() {
        testPackageK06("91-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo91.OBS91 },
                       new String[] {});
    }

    @Test
    void testK06_92_01() {
        testPackageK06("92-chyba", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo92.OBS92 });
    }

    @Test
    void testK06_92_OK01() {
        testPackageK06("92-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo92.OBS92 },
                       new String[] {});
    }

    @Test
    void testK06_92_OK02() {
        testPackageK06("92-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo92.OBS92 },
                       new String[] {});
    }

    @Test
    void testK06_93_01() {
        testPackageK06("93-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93.OBS93 });
    }

    @Test
    void testK06_93_02() {
        testPackageK06("93-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93.OBS93 });
    }

    @Test
    void testK06_93_03() {
        testPackageK06("93-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93.OBS93 });
    }

    @Test
    void testK06_93_OK01() {
        testPackageK06("93-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo93.OBS93 },
                       new String[] {});
    }

    @Test
    void testK06_93_OK02() {
        testPackageK06("93-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo93.OBS93 },
                       new String[] {});
    }

    @Test
    void testK06_93_OK03() {
        testPackageK06("93-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo93.OBS93 },
                       new String[] {});
    }

    @Test
    void testK06_93a_01() {
        testPackageK06("93a-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93a.OBS93A });
    }

    @Test
    void testK06_93a_02() {
        testPackageK06("93a-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93a.OBS93A });
    }

    @Test
    void testK06_93a_OK01() {
        testPackageK06("93a-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo93a.OBS93A },
                       new String[] {});
    }

    @Test
    void testK06_93a_OK02() {
        testPackageK06("93a-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo93a.OBS93A },
                       new String[] {});
    }

    @Test
    void testK06_94_01() {
        testPackageK06("94-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo94.OBS94 });
    }

    @Test
    void testK06_94_02() {
        testPackageK06("94-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo94.OBS94 });
    }

    @Test
    void testK06_94_03() {
        testPackageK06("94-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo94.OBS94 });
    }

    @Test
    void testK06_94_OK01() {
        testPackageK06("94-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo94.OBS94 },
                       new String[] {});
    }

    @Test
    void testK06_94_OK02() {
        testPackageK06("94-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo94.OBS94 },
                       new String[] {});
    }

    @Test
    void testK06_94_OK03() {
        testPackageK06("94-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo94.OBS94 },
                       new String[] {});
    }

    @Test
    void testK06_94_OK04() {
        testPackageK06("94-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo94.OBS94 },
                       new String[] {});
    }

    @Test
    void testK06_94_OK05() {
        testPackageK06("94-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo94.OBS94 },
                       new String[] {});
    }

    @Test
    void testK06_95_01() {
        testPackageK06("95-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo95.OBS95 });
    }

    @Test
    void testK06_95_02() {
        testPackageK06("95-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo95.OBS95 });
    }

    @Test
    void testK06_95_03() {
        testPackageK06("95-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo95.OBS95 });
    }

    @Test
    void testK06_95_04() {
        testPackageK06("95-chyba4", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo95.OBS95 });
    }

    @Test
    void testK06_95_OK01() {
        testPackageK06("95-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo95.OBS95 },
                       new String[] {});
    }

    @Test
    void testK06_95_OK02() {
        testPackageK06("95-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo95.OBS95 },
                       new String[] {});
    }

    @Test
    void testK06_95_OK03() {
        testPackageK06("95-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo95.OBS95 },
                       new String[] {});
    }

    @Test
    void testK06_96_01() {
        testPackageK06("96-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo96.OBS96 });
    }

    @Test
    void testK06_96_02() {
        testPackageK06("96-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo96.OBS96 });
    }

    @Test
    void testK06_96_03() {
        testPackageK06("96-chyba3", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo96.OBS96 });
    }

    @Test
    void testK06_96_04() {
        testPackageK06("96-chyba4", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo96.OBS96 });
    }

    @Test
    void testK06_96_OK01() {
        testPackageK06("96-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo96.OBS96 },
                       new String[] {});
    }

    @Test
    void testK06_96_OK02() {
        testPackageK06("96-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo96.OBS96 },
                       new String[] {});
    }

    @Test
    void testK06_96_OK03() {
        testPackageK06("96-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo96.OBS96 },
                       new String[] {});
    }

    @Test
    void testK06_97_01() {
        testPackageK06("97-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo97.OBS97 });
    }

    @Test
    void testK06_97_02() {
        testPackageK06("97-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo97.OBS97 });
    }

    @Test
    void testK06_97_OK01() {
        testPackageK06("97-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo97.OBS97 },
                       new String[] {});
    }

    @Test
    void testK06_98_01() {
        testPackageK06("98-chyba1", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo98.OBS98 });
    }

    @Test
    void testK06_98_02() {
        testPackageK06("98-chyba2", StavKontroly.CHYBA,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo98.OBS98 });
    }

    @Test
    void testK06_98_OK01() {
        testPackageK06("98-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo98.OBS98 },
                       new String[] {});
    }

    @Test
    void testK06_99_OK01() {
        testPackageK06("99-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo99.OBS99 },
                       new String[] {});
    }

    @Test
    void testK06_99_OK02() {
        testPackageK06("99-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo99.OBS99 },
                       new String[] {});
    }

    @Test
    void testK06_99_01() {
        testPackageK06("99-chyba1", StavKontroly.CHYBA, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo99.OBS99 });
    }
}
