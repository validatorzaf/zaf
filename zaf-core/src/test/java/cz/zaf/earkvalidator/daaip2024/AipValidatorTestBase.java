package cz.zaf.earkvalidator.daaip2024;

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
import cz.zaf.earkvalidator.ValidatorDAAIP2024;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.validator.TestHelper;

public class AipValidatorTestBase {
	
	private Logger log = LoggerFactory.getLogger(AipValidatorTestBase.class);

	protected void testAip(String inputPath, 
			DAAIP2024Profile validationProfile, 
			ValidationLayerType validationType, 
			ValidationStatus status,
			String[] pravidlaOk, String[] pravidlaChybna) {
        log.debug("Loading EAD: {}, urovenKontroly: {}", inputPath, validationProfile);
        ValidatorDAAIP2024 vdaaip = new ValidatorDAAIP2024(validationProfile, null, null, false);


        ValidationResult result;
        try {
            Path sourcePath = TestHelper.getPath(inputPath);
            Path absPath = sourcePath.toAbsolutePath();

            result = vdaaip.validate(absPath);
        } catch (Exception e) {
            if (e instanceof ZafException) {
                throw (ZafException) e;
            }
            throw new ZafException(BaseCode.CHYBA, "Error to run validate", e);
        }

        for (ValidationLayerResult vlr : result.getValidationLayerResults()) {
            if (vlr.getValidationType() == validationType) {
                testAip(inputPath, vlr, status, pravidlaOk, pravidlaChybna);
                return;
            }
        }

        fail("Validation results not found, name: " + validationType);		
	}

	private void testAip(String inputPath, 
			ValidationLayerResult validationResult, 
			ValidationStatus status, 
			String[] pravidlaOk,
			String[] pravidlaChybna) {
		TestHelper.checkTestResult(inputPath, status, validationResult, pravidlaOk, pravidlaChybna);		
	}

}
