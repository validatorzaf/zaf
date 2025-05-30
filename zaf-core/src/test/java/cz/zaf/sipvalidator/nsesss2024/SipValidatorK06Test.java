package cz.zaf.sipvalidator.nsesss2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs00_09.Pravidlo3;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.obs00_09.Pravidlo9;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo100;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo101;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo102;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo103;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo104;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo105;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo106;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo107;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109.Pravidlo108;
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
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo41;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo43a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo44;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo46;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo49;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo50;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo51;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo52;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo53;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo54;
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
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo92;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo94;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo95;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo96;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo97;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo98;
import cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

public class SipValidatorK06Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K06 = "testdata/NSESSS2024/06 KONTROLA OBSAHU";

    void testPackageK06(String path,
                        LoadType loadType,
                        ValidationStatus stavKontroly,
                        ProfilValidace profilValidace,
                        String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K06 + "/" + path, loadType,
                    profilValidace,
                    TypUrovenKontroly.OBSAHOVA,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    void testPackageK06(String path,
                        ValidationStatus stavKontroly,
                        ProfilValidace profilValidace,
                        String[] pravidlaOk, String[] pravidlaChybna) {
        testPackageK06(path, LoadType.LT_DIR,
                       stavKontroly,
                       profilValidace,
                       pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK06_01() {
        testPackageK06("obs1-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] {},
                       new String[] { Pravidlo1.OBS1 });
    }

    @Test
    void testK06_OK01() {
        testPackageK06("obs1-OK", ValidationStatus.OK,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo1.OBS1 },
                       new String[] {});
    }

    @Test
    void testK06_02_01() {
        testPackageK06("obs2-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo1.OBS1 },
                       new String[] { Pravidlo2.OBS2 });
    }

    @Test
    void testK06_02_02() {
        testPackageK06("obs2-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo1.OBS1 },
                       new String[] { Pravidlo2.OBS2 });
    }

    @Test
    void testK06_02_03() {
        testPackageK06("obs2-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo1.OBS1 },
                       new String[] { Pravidlo2.OBS2 });
    }

    @Test
    void testK06_02_OK01() {
        testPackageK06("obs2-OK1", ValidationStatus.OK,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2 },
                       new String[] {});
    }

    @Test
    void testK06_02_OK02() {
        testPackageK06("obs2-OK2", null,
                       ZakladniProfilValidace.SKARTACE_METADATA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2 },
                       new String[] {});
    }

    @Test
    void testK06_03_01() {
        testPackageK06("obs3-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2 },
                       new String[] { Pravidlo3.OBS3 });
    }

    @Test
    void testK06_03_02() {
        testPackageK06("obs3-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2 },
                       new String[] { Pravidlo3.OBS3 });
    }

    @Test
    void testK06_03_03() {
        testPackageK06("obs3-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2 },
                       new String[] { Pravidlo3.OBS3 });
    }

    @Test
    void testK06_03_OK01() {
        testPackageK06("obs3-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2, Pravidlo3.OBS3 },
                       new String[] {});
    }

    @Test
    void testK06_09_01() {
        testPackageK06("obs9-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2, Pravidlo3.OBS3 },
                       new String[] { Pravidlo9.OBS9 });
    }

    @Test
    void testK06_09_02() {
        testPackageK06("obs9-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo9.OBS9 });
    }

    @Test
    void testK06_09_OK01() {
        testPackageK06("obs9-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo1.OBS1, Pravidlo2.OBS2, Pravidlo3.OBS3, Pravidlo9.OBS9 },
                       new String[] {});
    }

    @Test
    void testK06_10_01() {
        testPackageK06("obs10-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo10.OBS10 });
    }

    @Test
    void testK06_10_OK01() {
        testPackageK06("obs10-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo10.OBS10 },
                       new String[] {});
    }

    @Test
    void testK06_11_01() {
        testPackageK06("obs11-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo11.OBS11 });
    }

    @Test
    void testK06_11_02() {
        testPackageK06("obs11-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo11.OBS11 });
    }

    @Test
    void testK06_11_OK01() {
        testPackageK06("obs11-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo11.OBS11 },
                       new String[] {});
    }

    @Test
    void testK06_12_01() {
        testPackageK06("obs12-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo12.OBS12 });
    }

    @Test
    void testK06_12_OK01() {
        testPackageK06("obs12-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo12.OBS12 },
                       new String[] {});
    }

    @Test
    void testK06_12_OK02() {
        testPackageK06("obs12-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo12.OBS12 },
                       new String[] {});
    }

    @Test
    void testK06_13_01() {
        testPackageK06("obs13-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo13.OBS13 });
    }

    @Test
    void testK06_13_OK01() {
        testPackageK06("obs13-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo13.OBS13 },
                       new String[] {});
    }

    @Test
    void testK06_14_01() {
        testPackageK06("obs14-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo14.OBS14 });
    }

    @Test
    void testK06_14_OK01() {
        testPackageK06("obs14-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo14.OBS14 },
                       new String[] {});
    }

    @Test
    void testK06_15_01() {
        testPackageK06("obs15-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo15.OBS15 });
    }

    @Test
    void testK06_15_OK01() {
        testPackageK06("obs15-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo15.OBS15 },
                       new String[] {});
    }

    @Test
    void testK06_16_01() {
        testPackageK06("obs16-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo16.OBS16 });
    }

    @Test
    void testK06_16_02() {
        testPackageK06("obs16-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo16.OBS16 });
    }

    @Test
    void testK06_16_OK01() {
        testPackageK06("obs16-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo16.OBS16 },
                       new String[] {});
    }

    @Test
    void testK06_16_OK02() {
        testPackageK06("obs16-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo16.OBS16 },
                       new String[] {});
    }

    @Test
    void testK06_17_01() {
        testPackageK06("obs17-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo17.OBS17 });
    }

    @Test
    void testK06_17_OK01() {
        testPackageK06("obs17-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo17.OBS17 },
                       new String[] {});
    }

    @Test
    void testK06_18_01() {
        testPackageK06("obs18-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo18.OBS18 });
    }

    @Test
    void testK06_18_02() {
        testPackageK06("obs18-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo18.OBS18 });
    }

    @Test
    void testK06_18_OK01() {
        testPackageK06("obs18-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo18.OBS18 },
                       new String[] {});
    }

    @Test
    void testK06_19_01() {
        testPackageK06("obs19-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo19.OBS19 });
    }

    @Test
    void testK06_19_02() {
        testPackageK06("obs19-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo19.OBS19 });
    }

    @Test
    void testK06_19_OK01() {
        testPackageK06("obs19-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo19.OBS19 },
                       new String[] {});
    }

    @Test
    void testK06_20_01() {
        testPackageK06("obs20-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo20.OBS20 });
    }

    @Test
    void testK06_20_OK01() {
        testPackageK06("obs20-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo20.OBS20 },
                       new String[] {});
    }

    @Test
    void testK06_22_01() {
        testPackageK06("obs22-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo22.OBS22 });
    }

    @Test
    void testK06_22_OK01() {
        testPackageK06("obs22-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo22.OBS22 },
                       new String[] {});
    }

    @Test
    void testK06_23_01() {
        testPackageK06("obs23-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo23.OBS23 });
    }

    @Test
    void testK06_23_02() {
        testPackageK06("obs23-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo23.OBS23 });
    }

    @Test
    void testK06_23_OK01() {
        testPackageK06("obs23-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo23.OBS23 },
                       new String[] {});
    }

    @Test
    void testK06_24_01() {
        testPackageK06("obs24-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo24.OBS24 });
    }

    @Test
    void testK06_24_OK01() {
        testPackageK06("obs24-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo24.OBS24 },
                       new String[] {});
    }

    @Test
    void testK06_25_01() {
        testPackageK06("obs25-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo25.OBS25 });
    }

    @Test
    void testK06_25_OK01() {
        testPackageK06("obs25-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo25.OBS25 },
                       new String[] {});
    }

    @Test
    void testK06_26_01() {
        testPackageK06("obs26-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo26.OBS26 });
    }

    @Test
    void testK06_26_02() {
        testPackageK06("obs26-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo26.OBS26 });
    }

    @Test
    void testK06_26_OK01() {
        testPackageK06("obs26-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo26.OBS26 },
                       new String[] {});
    }

    @Test
    void testK06_27_01() {
        testPackageK06("obs27-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo27.OBS27 });
    }

    @Test
    void testK06_27_OK01() {
        testPackageK06("obs27-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo27.OBS27 },
                       new String[] {});
    }

/*    @Test
    void testK06_28_01() {
        testPackageK06("28-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo28.OBS28 });
    }

    @Test
    void testK06_28_02() {
        testPackageK06("28-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("29-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo29.OBS29 });
    }

    @Test
    void testK06_29_02() {
        testPackageK06("29-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo29.OBS29 });
    }

    @Test
    void testK06_29_03() {
        testPackageK06("29-chyba3", ValidationStatus.ERROR,
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
    }*/

    @Test
    void testK06_30_01() {
        testPackageK06("obs30-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo30.OBS30 });
    }

    @Test
    void testK06_30_02() {
        testPackageK06("obs30-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo30.OBS30 });
    }

    @Test
    void testK06_30_OK01() {
        testPackageK06("obs30-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo30.OBS30 },
                       new String[] {});
    }

    @Test
    void testK06_31_01() {
        testPackageK06("obs31-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo31.OBS31 });
    }

    @Test
    void testK06_31_02() {
        testPackageK06("obs31-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo31.OBS31 });
    }

    @Test
    void testK06_31_03() {
        testPackageK06("obs31-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo31.OBS31 });
    }

    @Test
    void testK06_31_OK01() {
        testPackageK06("obs31-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo31.OBS31 },
                       new String[] {});
    }

    @Test
    void testK06_33_01() {
        testPackageK06("obs33-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo33.OBS33 });
    }

    @Test
    void testK06_33_02() {
        testPackageK06("obs33-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo33.OBS33 });
    }

    @Test
    void testK06_33_OK01() {
        testPackageK06("obs33-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo33.OBS33 },
                       new String[] {});
    }

    @Test
    void testK06_34_01() {
        testPackageK06("obs34-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo34.OBS34 });
    }

    @Test
    void testK06_34_02() {
        testPackageK06("obs34-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo34.OBS34 });
    }

    @Test
    void testK06_34_OK01() {
        testPackageK06("obs34-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo34.OBS34 },
                       new String[] {});
    }

    @Test
    void testK06_35_01() {
        testPackageK06("obs35-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo35.OBS35 });
    }

    @Test
    void testK06_35_02() {
        testPackageK06("obs35-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo35.OBS35 });
    }

    @Test
    void testK06_35_03() {
        testPackageK06("obs35-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo35.OBS35 });
    }

    @Test
    void testK06_35_OK01() {
        testPackageK06("obs35-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo35.OBS35 },
                       new String[] {});
    }

    @Test
    void testK06_36_01() {
        testPackageK06("obs36-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo36.OBS36 });
    }

    @Test
    void testK06_36_OK01() {
        testPackageK06("obs36-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo36.OBS36 },
                       new String[] {});
    }

    @Test
    void testK06_37_01() {
        testPackageK06("obs37-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo37.OBS37 });
    }

    @Test
    void testK06_37_02() {
        testPackageK06("obs37-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo37.OBS37 });
    }

    @Test
    void testK06_37_OK01() {
        testPackageK06("obs37-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo37.OBS37 },
                       new String[] {});
    }

    @Test
    void testK06_38_01() {
        testPackageK06("obs38-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo38.OBS38 });
    }

    @Test
    void testK06_38_OK01() {
        testPackageK06("obs38-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo38.OBS38 },
                       new String[] {});
    }

    @Test
    void testK06_38_OK02() {
        testPackageK06("obs38-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo38.OBS38 },
                       new String[] {});
    }

    @Test
    void testK06_39_01() {
        testPackageK06("obs39-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo39.OBS39 });
    }

    @Test
    void testK06_39_02() {
        testPackageK06("obs39-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo39.OBS39 });
    }

    @Test
    void testK06_39_OK01() {
        testPackageK06("obs39-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo39.OBS39 },
                       new String[] {});
    }

    /*
    //------------- 4x ------------
    @Test
    void testK06_40_01() {
        testPackageK06("40-chyba", ValidationStatus.ERROR,
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
    void testK06_41_OK01() {
        testPackageK06("41-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo41.OBS41 },
                       new String[] {});
    }
    
        @Test
    void testK06_41_01() {
        testPackageK06("41-CHYBA1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo41.OBS41 });
    }
    
        @Test
    void testK06_41_02() {
        testPackageK06("41-CHYBA2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo41.OBS41 });
    }
    
    @Test
    void testK06_44_01() {
        testPackageK06("44-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo44.OBS44 });
    }

    @Test
    void testK06_43a_01() {
        testPackageK06("43a-chyba", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo43a.OBS43A });
    }

    @Test
    void testK06_43a_OK01() {
        testPackageK06("43a-OK", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo43a.OBS43A },
                       new String[] {});
    }

    @Test
    void testK06_44_02() {
        testPackageK06("44-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo44.OBS44 });
    }

    @Test
    void testK06_44_03() {
        testPackageK06("44-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo44.OBS44 });
    }

    @Test
    void testK06_44_OK01() {
        testPackageK06("44-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo44.OBS44 },
                       new String[] {});
    }

    @Test
    void testK06_44_OK02() {
        testPackageK06("44-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo44.OBS44 },
                       new String[] {});
    }

    @Test
    void testK06_46_01() {
        testPackageK06("46-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo46.OBS46 });
    }

    @Test
    void testK06_46_02() {
        testPackageK06("46-chyba2", ValidationStatus.ERROR,
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
    void testK06_49_01() {
        testPackageK06("49-chyba", ValidationStatus.ERROR,
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
        testPackageK06("50-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo50.OBS50 });
    }

    @Test
    void testK06_50_02() {
        testPackageK06("50-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo50.OBS50 });
    }

    @Test
    void testK06_50_03() {
        testPackageK06("50-chyba3", ValidationStatus.ERROR,
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
        testPackageK06("51-chyba", ValidationStatus.ERROR,
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
        testPackageK06("52-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo52.OBS52 });
    }

    @Test
    void testK06_52_02() {
        testPackageK06("52-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo52.OBS52 });
    }

    @Test
    void testK06_52_03() {
        testPackageK06("52-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo52.OBS52 });
    }

    @Test
    void testK06_52_04() {
        testPackageK06("52-chyba4", ValidationStatus.ERROR,
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
    void testK06_52_OK03() {
        testPackageK06("52-OK3.zip", LoadType.LT_ZIP,
                       null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo52.OBS52 },
                       new String[] {});
    }

    @Test
    void testK06_52_OK04() {
        testPackageK06("52-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo52.OBS52 },
                       new String[] {});
    }

    @Test
    void testK06_53_01() {
        testPackageK06("53-chyba", ValidationStatus.ERROR,
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
        testPackageK06("54-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_02() {
        testPackageK06("54-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_03() {
        testPackageK06("54-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_04() {
        testPackageK06("54-chyba4", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_05() {
        testPackageK06("54-chyba5", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_06() {
        testPackageK06("54-chyba6", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_07() {
        testPackageK06("54-chyba7", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_08() {
        testPackageK06("54-chyba8", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_09() {
        testPackageK06("54-chyba9", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_10() {
        testPackageK06("54-chyba10", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_11() {
        testPackageK06("54-chyba11", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_12() {
        testPackageK06("54-chyba12", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_13() {
        testPackageK06("54-chyba13", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_14() {
        testPackageK06("54-chyba14", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_15() {
        testPackageK06("54-chyba15", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_16() {
        testPackageK06("54-chyba16", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_17() {
        testPackageK06("54-chyba17", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_18() {
        testPackageK06("54-chyba18", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_19() {
        testPackageK06("54-chyba19", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_20() {
        testPackageK06("54-chyba20", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_21() {
        testPackageK06("54-chyba21", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_22() {
        testPackageK06("54-chyba22", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_23() {
        testPackageK06("54-chyba23", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_24() {
        testPackageK06("54-chyba24", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_25() {
        testPackageK06("54-chyba25", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_26() {
        testPackageK06("54-chyba26", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_27() {
        testPackageK06("54-chyba27", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_28() {
        testPackageK06("54-chyba28", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_29() {
        testPackageK06("54-chyba29", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_30() {
        testPackageK06("54-chyba30", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_31() {
        testPackageK06("54-chyba31", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_32() {
        testPackageK06("54-chyba32", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_33() {
        testPackageK06("54-chyba33", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo54.OBS54 });
    }

    @Test
    void testK06_54_34() {
        testPackageK06("54-chyba34", ValidationStatus.ERROR,
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
    void testK06_54_OK03() {
        testPackageK06("54-OK3", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54.OBS54 },
                       new String[] {});
    }

    @Test
    void testK06_54_OK04() {
        testPackageK06("54-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54.OBS54 },
                       new String[] {});
    }

    @Test
    void testK06_54_OK05() {
        testPackageK06("54-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo54.OBS54 },
                       new String[] {});
    }

    @Test
    void testK06_55_01() {
        testPackageK06("55-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo55.OBS55 });
    }

    @Test
    void testK06_55_02() {
        testPackageK06("55-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("56-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo56.OBS56 });
    }

    @Test
    void testK06_56_02() {
        testPackageK06("56-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo56.OBS56 });
    }

    @Test
    void testK06_56_OK01() {
        testPackageK06("56-OK1", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo56.OBS56 },
                       new String[] {});
    }

    @Test
    void testK06_56_OK02() {
        testPackageK06("56-OK2", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo56.OBS56 },
                       new String[] {});
    }

    @Test
    void testK06_57_01() {
        testPackageK06("57-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo57.OBS57 });
    }

    @Test
    void testK06_57_02() {
        testPackageK06("57-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo57.OBS57 });
    }

    @Test
    void testK06_57_03() {
        testPackageK06("57-chyba3", ValidationStatus.ERROR,
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
        testPackageK06("58-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo58.OBS58 });
    }

    @Test
    void testK06_58_02() {
        testPackageK06("58-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("59-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_02() {
        testPackageK06("59-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_03() {
        testPackageK06("59-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_04() {
        testPackageK06("59-chyba4", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_05() {
        testPackageK06("59-chyba5", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_06() {
        testPackageK06("59-chyba6", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_07() {
        testPackageK06("59-chyba7", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo59.OBS59 });
    }

    @Test
    void testK06_59_08() {
        testPackageK06("59-chyba8", ValidationStatus.ERROR,
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
        testPackageK06("60-chyba", ValidationStatus.ERROR,
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
        testPackageK06("61-chyba", ValidationStatus.ERROR,
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
        testPackageK06("61a-chyba", ValidationStatus.ERROR,
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
        testPackageK06("62-chyba", ValidationStatus.ERROR,
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
        testPackageK06("63-chyba", ValidationStatus.ERROR,
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
        testPackageK06("64-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo64.OBS64 });
    }

    @Test
    void testK06_64_02() {
        testPackageK06("64-chyba2", ValidationStatus.ERROR,
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
    void testK06_64_OK04() {
        testPackageK06("64-OK4", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo64.OBS64 },
                       new String[] {});
    }

    @Test
    void testK06_65_01() {
        testPackageK06("65-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo65.OBS65 });
    }

    @Test
    void testK06_65_02() {
        testPackageK06("65-chyba2", ValidationStatus.ERROR,
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
    void testK06_65_OK06() {
        testPackageK06("65-OK6", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo65.OBS65 },
                       new String[] {});
    }

    @Test
    void testK06_65_OK07() {
        testPackageK06("65-OK7", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo65.OBS65 },
                       new String[] {});
    }

    @Test
    void testK06_67_01() {
        testPackageK06("67-chyba", ValidationStatus.ERROR,
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
        testPackageK06("68-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo68.OBS68 });
    }

    @Test
    void testK06_68_02() {
        testPackageK06("68-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("69-chyba", ValidationStatus.ERROR,
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
        testPackageK06("70-chyba", ValidationStatus.ERROR,
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
        testPackageK06("71-chyba", ValidationStatus.ERROR,
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
        testPackageK06("72-chyba", ValidationStatus.ERROR,
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
        testPackageK06("73-chyba", ValidationStatus.ERROR,
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
        testPackageK06("74-chyba", ValidationStatus.ERROR,
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
        testPackageK06("75-chyba", ValidationStatus.ERROR,
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
        testPackageK06("76-chyba", ValidationStatus.ERROR,
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
        testPackageK06("77-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo77.OBS77 });
    }

    @Test
    void testK06_77_02() {
        testPackageK06("77-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("78-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo78.OBS78 });
    }

    @Test
    void testK06_78_02() {
        testPackageK06("78-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("79-chyba", ValidationStatus.ERROR,
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
        testPackageK06("80-chyba", ValidationStatus.ERROR,
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
        testPackageK06("81-chyba", ValidationStatus.ERROR,
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
        testPackageK06("82-chyba", ValidationStatus.ERROR,
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
        testPackageK06("83-chyba", ValidationStatus.ERROR,
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
        testPackageK06("84-chyba", ValidationStatus.ERROR,
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
        testPackageK06("85-chyba", ValidationStatus.ERROR,
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
        testPackageK06("86-chyba", ValidationStatus.ERROR,
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
        testPackageK06("87-chyba", ValidationStatus.ERROR,
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
        testPackageK06("88-chyba", ValidationStatus.ERROR,
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

    //------------- 90-99 ------------

    @Test
    void testK06_92_01() {
        testPackageK06("92-chyba", ValidationStatus.ERROR,
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
        testPackageK06("93-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93.OBS93 });
    }

    @Test
    void testK06_93_02() {
        testPackageK06("93-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93.OBS93 });
    }

    @Test
    void testK06_93_03() {
        testPackageK06("93-chyba3", ValidationStatus.ERROR,
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
        testPackageK06("93a-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo93a.OBS93A });
    }

    @Test
    void testK06_93a_02() {
        testPackageK06("93a-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("94-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo94.OBS94 });
    }

    @Test
    void testK06_94_02() {
        testPackageK06("94-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo94.OBS94 });
    }

    @Test
    void testK06_94_03() {
        testPackageK06("94-chyba3", ValidationStatus.ERROR,
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
    void testK06_94_OK05() {
        testPackageK06("94-OK5", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo94.OBS94 },
                       new String[] {});
    }

    @Test
    void testK06_94_OK06() {
        testPackageK06("94-OK6", null,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo94.OBS94 },
                       new String[] {});
    }

    @Test
    void testK06_95_01() {
        testPackageK06("95-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo95.OBS95 });
    }

    @Test
    void testK06_95_02() {
        testPackageK06("95-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo95.OBS95 });
    }

    @Test
    void testK06_95_03() {
        testPackageK06("95-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo95.OBS95 });
    }

    @Test
    void testK06_95_04() {
        testPackageK06("95-chyba4", ValidationStatus.ERROR,
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
        testPackageK06("96-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo96.OBS96 });
    }

    @Test
    void testK06_96_02() {
        testPackageK06("96-chyba2", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo96.OBS96 });
    }

    @Test
    void testK06_96_03() {
        testPackageK06("96-chyba3", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo96.OBS96 });
    }

    @Test
    void testK06_96_04() {
        testPackageK06("96-chyba4", ValidationStatus.ERROR,
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
        testPackageK06("97-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo97.OBS97 });
    }

    @Test
    void testK06_97_02() {
        testPackageK06("97-chyba2", ValidationStatus.ERROR,
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
        testPackageK06("98-chyba1", ValidationStatus.ERROR,
                       ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo98.OBS98 });
    }

    @Test
    void testK06_98_02() {
        testPackageK06("98-chyba2", ValidationStatus.ERROR,
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
    void testK06_100_OK01() {
        testPackageK06("100-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo100.OBS100 },
                       new String[] {});
    }

    @Test
    void testK06_100_OK02() {
        testPackageK06("100-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo100.OBS100 },
                       new String[] {});
    }

    @Test
    void testK06_100_OK3() {
        testPackageK06("100-OK3", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo100.OBS100 },
                       new String[] {});
    }
    @Test
    void testK06_100_OK4() {
        testPackageK06("100-OK4", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo100.OBS100 },
                       new String[] {});
    }

    @Test
    void testK06_100_01() {
        testPackageK06("100-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo100.OBS100 });
    }

    @Test
    void testK06_101_OK01() {
        testPackageK06("101-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo101.OBS101 },
                       new String[] {});
    }

    @Test
    void testK06_101_OK02() {
        testPackageK06("101-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo101.OBS101 },
                       new String[] {});
    }

    @Test
    void testK06_101_01() {
        testPackageK06("101-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo101.OBS101 });
    }

    @Test
    void testK06_102_OK01() {
        testPackageK06("102-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo102.OBS102 },
                       new String[] {});
    }

    @Test
    void testK06_102_OK02() {
        testPackageK06("102-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo102.OBS102 },
                       new String[] {});
    }

    @Test
    void testK06_102_01() {
        testPackageK06("102-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo102.OBS102 });
    }

    @Test
    void testK06_102_02() {
        testPackageK06("102-chyba2", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo102.OBS102 });
    }

    @Test
    void testK06_103_OK01() {
        testPackageK06("103-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo103.OBS103 },
                       new String[] {});
    }

    @Test
    void testK06_103_OK02() {
        testPackageK06("103-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo103.OBS103 },
                       new String[] {});
    }

    @Test
    void testK06_103_OK03() {
        testPackageK06("103-OK3", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo103.OBS103 },
                       new String[] {});
    }

    @Test
    void testK06_103_OK04() {
        testPackageK06("103-OK4", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo103.OBS103 },
                       new String[] {});
    }

    @Test
    void testK06_103_01() {
        testPackageK06("103-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo103.OBS103 });
    }

    @Test
    void testK06_103_02() {
        testPackageK06("103-chyba2", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo103.OBS103 });
    }

    @Test
    void testK06_104_OK01() {
        testPackageK06("104-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo104.OBS104 },
                       new String[] {});
    }

    @Test
    void testK06_104_OK02() {
        testPackageK06("104-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo104.OBS104 },
                       new String[] {});
    }

    @Test
    void testK06_104_OK03() {
        testPackageK06("104-OK3", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo104.OBS104 },
                       new String[] {});
    }

    @Test
    void testK06_104_OK04() {
        testPackageK06("104-OK4", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo104.OBS104 },
                       new String[] {});
    }

    @Test
    void testK06_104_01() {
        testPackageK06("104-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo104.OBS104 });
    }

    @Test
    void testK06_105_OK01() {
        testPackageK06("105-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo105.OBS105 },
                       new String[] {});
    }

    @Test
    void testK06_105_OK02() {
        testPackageK06("105-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo105.OBS105 },
                       new String[] {});
    }

    @Test
    void testK06_105_OK03() {
        testPackageK06("105-OK3", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo105.OBS105 },
                       new String[] {});
    }

    @Test
    void testK06_105_OK04() {
        testPackageK06("105-OK4", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo105.OBS105 },
                       new String[] {});
    }

    @Test
    void testK06_105_OK05() {
        testPackageK06("105-OK5", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo105.OBS105 },
                       new String[] {});
    }

    @Test
    void testK06_105_01() {
        testPackageK06("105-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo105.OBS105 });
    }

    @Test
    void testK06_105_02() {
        testPackageK06("105-chyba2", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo105.OBS105 });
    }

    @Test
    void testK06_105_03() {
        testPackageK06("105-chyba3", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo105.OBS105 });
    }

    @Test
    void testK06_105_04() {
        testPackageK06("105-chyba4", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo105.OBS105 });
    }

    @Test
    void testK06_105_05() {
        testPackageK06("105-chyba5", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo105.OBS105 });
    }

    @Test
    void testK06_106_OK01() {
        testPackageK06("106-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo106.OBS106 },
                       new String[] {});
    }

    @Test
    void testK06_106_OK02() {
        testPackageK06("106-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo106.OBS106 },
                       new String[] {});
    }

    @Test
    void testK06_106_01() {
        testPackageK06("106-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo106.OBS106 });
    }

    @Test
    void testK06_107_OK01() {
        testPackageK06("107-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK02() {
        testPackageK06("107-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK03() {
        testPackageK06("107-OK3", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK04() {
        testPackageK06("107-OK4", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK05() {
        testPackageK06("107-OK5", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK06() {
        testPackageK06("107-OK6", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK07() {
        testPackageK06("107-OK7", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK08() {
        testPackageK06("107-OK8", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK09() {
        testPackageK06("107-OK9", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK10() {
        testPackageK06("107-OK10", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK11() {
        testPackageK06("107-OK11", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK12() {
        testPackageK06("107-OK12", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK13() {
        testPackageK06("107-OK13", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK14() {
        testPackageK06("107-OK14", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK15() {
        testPackageK06("107-OK15", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK16() {
        testPackageK06("107-OK16", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK17() {
        testPackageK06("107-OK17", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_OK18() {
        testPackageK06("107-OK18", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo107.OBS107 },
                       new String[] {});
    }

    @Test
    void testK06_107_01() {
        testPackageK06("107-chyba1", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo107.OBS107 });
    }

    @Test
    void testK06_107_02() {
        testPackageK06("107-chyba2", ValidationStatus.ERROR, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo107.OBS107 });
    }

    @Test
    void testK06_108_OK01() {
        testPackageK06("108-OK1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK02() {
        testPackageK06("108-OK2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK03() {
        testPackageK06("108-OK3", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK04() {
        testPackageK06("108-OK4", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK05() {
        testPackageK06("108-OK5", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK06() {
        testPackageK06("108-OK6", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK07() {
        testPackageK06("108-OK7", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK08() {
        testPackageK06("108-OK8", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK09() {
        testPackageK06("108-OK9", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK10() {
        testPackageK06("108-OK10", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK11() {
        testPackageK06("108-OK11", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_OK12() {
        testPackageK06("108-OK12", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { Pravidlo108.OBS108 },
                       new String[] {});
    }

    @Test
    void testK06_108_chyba01() {
        testPackageK06("108-chyba1", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] { },
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba02() {
        testPackageK06("108-chyba2", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba03() {
        testPackageK06("108-chyba3", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba04() {
        testPackageK06("108-chyba4", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba05() {
        testPackageK06("108-chyba5", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba06() {
        testPackageK06("108-chyba6", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba07() {
        testPackageK06("108-chyba7", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba08() {
        testPackageK06("108-chyba8", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba09() {
        testPackageK06("108-chyba9", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba10() {
        testPackageK06("108-chyba10", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba11() {
        testPackageK06("108-chyba11", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }

    @Test
    void testK06_108_chyba12() {
        testPackageK06("108-chyba12", null, ZakladniProfilValidace.PREJIMKA,
                       new String[] {},
                       new String[] { Pravidlo108.OBS108 });
    }*/
}
