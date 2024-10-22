package cz.zaf.premis;

import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationResultImpl;
import cz.zaf.common.result.ValidationStatus;
import cz.zaf.common.validation.BaseValidationContext;
import cz.zaf.premisvalidator.PremisValidationContext;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.premisvalidator.ValidationLayers;
import cz.zaf.premisvalidator.ValidatorPremisInner;
import cz.zaf.premisvalidator.profile.PremisProfile;
import cz.zaf.validator.TestHelper;

public class PremisValidatorTestBase {
	
	private Logger log = LoggerFactory.getLogger(PremisValidatorTestBase.class);
	
	static private class ValidatorTestContext extends BaseValidationContext {
		
		final ValidationResult vr;

		public ValidatorTestContext(String filePath) {
			super(null);
			vr = new ValidationResultImpl(null, filePath);
		}

		@Override
		public ValidationResult getValidationResult() {
			return vr;
		}
		
	};

	public void testPremis(String inputPath, 
			ValidationLayers validationType, 
			ValidationStatus status,
			PremisProfile premisProfile,
			String[] pravidlaOk,
			String[] pravidlaChybna) {
		testPremis(inputPath, 
				validationType, 
				status,
				premisProfile,
				pravidlaOk, pravidlaChybna, null);
	}
	public void testPremis(String inputPath, 
			ValidationLayers validationType, 
			ValidationStatus status,
			PremisProfile premisProfile,
			String[] pravidlaOk,
			String[] pravidlaChybna,
			Function<String, RepresentationInfo> representationReader) {

		log.debug("Loading PREMIS: {}, urovenKontroly: {}", inputPath, validationType);
		ValidatorTestContext vtx = new ValidatorTestContext(inputPath);		
		ValidationResult result = vtx.getValidationResult();
		try {
            Path sourcePath = TestHelper.getPath(inputPath);
            Path absPath = sourcePath.toAbsolutePath();
            
            PremisValidationContext premisCtx = new PremisValidationContext(absPath);
            premisCtx.setRepresentationReader(representationReader);
			ValidatorPremisInner<ValidatorTestContext> vdaaip = new ValidatorPremisInner<>(inputPath, premisCtx, premisProfile);
			vdaaip.validate(vtx, inputPath);
        } catch (Exception e) {
            if (e instanceof ZafException) {
                throw (ZafException) e;
            }
            throw new ZafException(BaseCode.CHYBA, "Error to run validate", e);
        }		

        for (ValidationLayerResult vlr : result.getValidationLayerResults()) {
            if (vlr.getValidationType() == validationType) {
                TestHelper.checkTestResult(inputPath, status, vlr, pravidlaOk, pravidlaChybna, null);
                return;
            }
        }

        StringBuilder sb = new StringBuilder();
		sb.append("Validation results not found, name: ").append(validationType);
		if(result.getValidationLayerResults().size() > 0) {
			// add validation layer results
			sb.append(" Other results: ");
			for(ValidationLayerResult vlr : result.getValidationLayerResults()) {
				sb.append("\n").append(vlr.getValidationType()).append(": ").append(vlr.isOk()?"OK":"FAIL");
				if(vlr.isFailed()) {
					for(var failedRule: vlr.getPravidla()) {
						sb.append("\n\t").append(failedRule.getId()).append(": ").append(failedRule.getVypisChyby());
					}
				}
			}
		}
        fail(sb.toString());		
				
				
	}
}
