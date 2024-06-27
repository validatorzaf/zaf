package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.pravidla03.wf00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

public class SipValidatorK03Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K03 = "testdata/NSESSS2017/03 KONTROLA SPRAVNOSTI XML";

    void testPackageK03(String path,
                        ValidationStatus stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K03 + "/" + path, LoadType.LT_DIR,
                    ZakladniProfilValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.SPRAVNOSTI,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK03_01() {
        testPackageK03("wf1-chyba1", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK03_02() {
        testPackageK03("wf1-chyba2", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { Pravidlo1.KOD });
    }

    @Test
    void testK03_OK_01() {
        testPackageK03("wf1-OK1", ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD },
                       new String[] {});
    }

    @Test
    void testK03_OK_02() {
        testPackageK03("wf1-OK2", ValidationStatus.OK,
                       new String[] { Pravidlo1.KOD },
                       new String[] {});
    }
}
