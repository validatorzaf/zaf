package cz.zaf.sipvalidator.nsesss2024;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2024.pravidla04.ns00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2024.pravidla04.ns00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

public class SipValidatorK04Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K04 = "testdata/NSESSS2024/04 KONTROLA JMENNYCH PROSTORU XML";

    void testPackageK04(String path,
                        ValidationStatus stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K04 + "/" + path, LoadType.LT_DIR,
                    ZakladniProfilValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.JMENNE_PROSTORY_XML,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK04_01() {
        testPackageK04("ns1-chyba1", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK04_03() {
        testPackageK04("ns1-chyba2", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK04_NS1_OK1() {
        testPackageK04("ns1-OK1", ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD },
                       new String[] {});
    }

    @Test
    void testK04_NS1_OK2() {
        testPackageK04("ns1-OK2", ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD },
                       new String[] {});
    }

    @Test
    void testK04_NS1_OK3() {
        testPackageK04("ns1-OK3", ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { });
    }

    @Test
    void testK04_ns2_01() {
        testPackageK04("ns2-chyba1", ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD });
    }

    @Test
    void testK04_ns2_02() {
        testPackageK04("ns2-chyba2", ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD });
    }

    @Test
    void testK04_ns2_03() {
        testPackageK04("ns2-chyba3", ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD });
    }

    @Test
    void testK04_ns2_04() {
        testPackageK04("ns2-chyba4", ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD });
    }

    @Test
    void testK04_ns2_05() {
        testPackageK04("ns2-chyba5", ValidationStatus.ERROR,
                       new String[] { Pravidlo1.KOD },
                       new String[] { Pravidlo2.KOD });
    }

    @Test
    void testK04_NS2_OK1() {
        testPackageK04("ns2-OK", ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD,
                               Pravidlo2.KOD },
                       new String[] {});
    }

}
