package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

public class SipValidatorK04Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K04 = "testdata/04 KONTROLA JMENNYCH PROSTORU XML";

    void testPackageK04(String path,
                        StavKontroly stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K04 + "/" + path, LoadType.LT_DIR,
                    ZakladniProfilValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.JMENNE_PROSTORY_XML,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK04_01() {
        testPackageK04("ns1-chyba1", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K04_JmennychProstoruXML.NS1 });
    }

    @Test
    void testK04_03() {
        testPackageK04("ns1-chyba2", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K04_JmennychProstoruXML.NS1 });
    }

    @Test
    void testK04_NS1_OK1() {
        testPackageK04("ns1-OK1", StavKontroly.OK,
                       new String[] { K04_JmennychProstoruXML.NS1 },
                       new String[] {});
    }

    @Test
    void testK04_NS1_OK2() {
        testPackageK04("ns1-OK2", StavKontroly.OK,
                       new String[] { K04_JmennychProstoruXML.NS1 },
                       new String[] {});
    }

    @Test
    void testK04_NS1_OK3() {
        testPackageK04("ns1-OK3", StavKontroly.CHYBA,
                       new String[] { K04_JmennychProstoruXML.NS1 },
                       new String[] { K04_JmennychProstoruXML.NS2 });
    }

    @Test
    void testK04_ns2_01() {
        testPackageK04("ns2-chyba1", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K04_JmennychProstoruXML.NS2 });
    }

    @Test
    void testK04_ns2_02() {
        testPackageK04("ns2-chyba2", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K04_JmennychProstoruXML.NS2 });
    }

    @Test
    void testK04_ns2_03() {
        testPackageK04("ns2-chyba3", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K04_JmennychProstoruXML.NS2 });
    }

    @Test
    void testK04_ns2_04() {
        testPackageK04("ns2-chyba4", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K04_JmennychProstoruXML.NS2 });
    }

    @Test
    void testK04_ns2_05() {
        testPackageK04("ns2-chyba5", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K04_JmennychProstoruXML.NS2 });
    }

    @Test
    void testK04_NS2_OK1() {
        testPackageK04("ns2-OK1", StavKontroly.OK,
                       new String[] { K04_JmennychProstoruXML.NS1,
                               K04_JmennychProstoruXML.NS2 },
                       new String[] {});
    }

    @Test
    void testK04_NS2_OK2() {
        testPackageK04("ns2-OK2", StavKontroly.OK,
                       new String[] { K04_JmennychProstoruXML.NS1,
                               K04_JmennychProstoruXML.NS2 },
                       new String[] {});
    }
}
