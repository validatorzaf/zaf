package cz.zaf.eadvalidator.ap2023;

import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationStatus;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.validator.TestHelper;

public class EadValidatorTestBase {

    static final Logger log = LoggerFactory.getLogger(EadValidatorTestBase.class);

    protected void testEad(String inputPath,
                           AP2023Profile validationProfile,
                           ValidationLayerType validationType,
                           ValidationStatus stavKontroly,
                           String[] pravidlaOk, String[] pravidlaChybna) {
        log.debug("Loading EAD: {}, urovenKontroly: {}", inputPath, validationProfile);
        ValidatorAp2023 vap = new ValidatorAp2023(validationProfile, null);


        ValidationResult result;
        try {
            Path sourcePath = TestHelper.getPath(inputPath);
            Path absPath = sourcePath.toAbsolutePath();

            result = vap.validate(absPath);
        } catch (Exception e) {
            if (e instanceof ZafException) {
                throw (ZafException) e;
            }
            throw new ZafException(BaseCode.CHYBA, "Error to run validate", e);
        }

        for (ValidationLayerResult vlr : result.getValidationLayerResults()) {
            if (vlr.getValidationType() == validationType) {
                testEad(inputPath, vlr, stavKontroly, pravidlaOk, pravidlaChybna);
                return;
            }
        }

        fail("Validation results not found, name: " + validationType);
    }

    protected void testEad(String inputPath,
                           ValidationLayerResult validationResult,
                           ValidationStatus stavKontroly,
                           String[] pravidlaOk, String[] pravidlaChybna) {

        TestHelper.checkTestResult(inputPath, stavKontroly, validationResult, pravidlaOk, pravidlaChybna);
    }

}
