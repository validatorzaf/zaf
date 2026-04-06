package cz.zaf.sipvalidator.nsesss2017;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.result.ValidationStatus;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.SipLoader;

/**
 * Zakladni impl. testu pro NSESSS2017
 *
 */
public abstract class SipValidatorTestBase extends cz.zaf.validator.SipValidatorTestBase {

    private static final Logger log = LoggerFactory.getLogger(SipValidatorTestBase.class);

    // set fixed date for all tests
    static {
        System.setProperty(KontrolaNsessContext.ZAF_VALIDATION_DATE, "2022-06-01");
    }

    void testPackage(String path, LoadType expLoadType,
                     ProfilValidace profilValidace,
                     TypUrovenKontroly typUrovneKontroly,
                     ValidationStatus stavKontroly,
                     String[] pravidlaOk,
                     String[] pravidlaChybna) {
        testPackage(path, expLoadType, profilValidace, typUrovneKontroly, stavKontroly,
                    pravidlaOk, pravidlaChybna, null);
    }

    void testPackage(String path, LoadType expLoadType,
                     ProfilValidace profilValidace,
                     TypUrovenKontroly typUrovneKontroly,
                     ValidationStatus stavKontroly,
                     String[] pravidlaOk,
                     String[] pravidlaChybna,
                     String[] exclChecks) {
        log.debug("Loading SIP: {}, loadType: {}, urovenKontroly: {}", path, expLoadType, typUrovneKontroly);

        List<String> exclCheckList = exclChecks == null ? Collections.emptyList() : Arrays.asList(exclChecks);

        SipLoader sipLoader = loadSip(path, expLoadType);
        SipValidator sipValidator = new SipValidator(profilValidace, exclCheckList);
        sipValidator.validate(sipLoader);
        SipInfo sipInfo = sipLoader.getSip();

        checkValidationResult(path, sipInfo, typUrovneKontroly, stavKontroly, pravidlaOk, pravidlaChybna);
    }
}
