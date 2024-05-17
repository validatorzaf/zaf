package cz.zaf.sipvalidator.nsesss2017;

import org.junit.jupiter.api.Test;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

public class SipValidatorK05Test extends SipValidatorTestBase {

    static public final String PATH_DATA_K05 = "testdata/05 KONTROLA PROTI SCHEMATU";

    void testPackageK05(String path,
                        ValidationStatus stavKontroly, String[] pravidlaOk, String[] pravidlaChybna) {
        testPackage(PATH_DATA_K05 + "/" + path, LoadType.LT_DIR,
                    ZakladniProfilValidace.SKARTACE_METADATA,
                    TypUrovenKontroly.PROTI_SCHEMATU,
                    stavKontroly, pravidlaOk, pravidlaChybna);
    }

    @Test
    void testK05_01() {
        testPackageK05("val1-chyba1", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { K05_ProtiSchematu.VAL1 });
    }

    @Test
    void testK05_02() {
        testPackageK05("val1-chyba2", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { K05_ProtiSchematu.VAL1 });
    }

    @Test
    void testK05_03() {
        testPackageK05("val1-chyba3", ValidationStatus.ERROR,
                       new String[] {},
                       new String[] { K05_ProtiSchematu.VAL1 });
    }

    @Test
    void testK05_OK01() {
        testPackageK05("val1-OK1", ValidationStatus.OK,
                       new String[] { K05_ProtiSchematu.VAL1 },
                       new String[] {});
    }

    @Test
    void testK05_OK02() {
        testPackageK05("val1-OK2", ValidationStatus.OK,
                       new String[] { K05_ProtiSchematu.VAL1 },
                       new String[] {});
    }

    @Test
    void testK05_OK03() {
        testPackageK05("val1-OK3", ValidationStatus.OK,
                       new String[] { K05_ProtiSchematu.VAL1 },
                       new String[] {});
    }
}
