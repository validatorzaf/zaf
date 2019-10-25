package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilyValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

public class SipValidatorK02Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K02 = "testdata/02 KONTROLA ZNAKOVE SADY";

    void testPackageK02(String path,
                        StavKontroly stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K02 + "/" + path, LoadType.LT_DIR,
                    ProfilyValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.ZNAKOVE_SADY,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK02_01() {
        testPackageK02("kod1-chyba1", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_02() {
        testPackageK02("kod1-chyba2", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_03() {
        testPackageK02("kod1-chyba3", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_04() {
        testPackageK02("kod1-chyba4", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_05() {
        testPackageK02("kod1-chyba5", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_06() {
        testPackageK02("kod1-chyba6", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_07() {
        testPackageK02("kod1-chyba7", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_08() {
        testPackageK02("kod1-chyba8", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_09() {
        testPackageK02("kod1-chyba9", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    @Test
    void testK02_10() {
        testPackageK02("kod1-chyba10", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }

    /*
    @Test
    void testK02_11() {
        testPackageK02("kod1-chyba11", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }
    
    @Test
    void testK02_12() {
        testPackageK02("kod1-chyba12", StavKontroly.CHYBA,
                       new String[] {},
                       new String[] { K02_ZnakoveSady.KOD1 });
    }
    */
    @Test
    void testK02_OK_1() {
        testPackageK02("kod1-OK", StavKontroly.OK,
                       new String[] { K02_ZnakoveSady.KOD1 },
                       new String[] {});
    }
}
