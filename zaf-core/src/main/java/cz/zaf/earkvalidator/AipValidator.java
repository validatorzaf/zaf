package cz.zaf.earkvalidator;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.earkvalidator.layers.dat.DataValidationLayer;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class AipValidator {

	private List<String> excludeChecks;
	private List<ValidationLayer<AipValidationContext>> validations;

	public AipValidator(DAAIP2024Profile daaip2024Profile, List<String> excludeChecks) {
		this.excludeChecks = excludeChecks;

		validations = prepareValidations(daaip2024Profile);
	}

	private List<ValidationLayer<AipValidationContext>> prepareValidations(DAAIP2024Profile daaip2024Profile) {
		List<ValidationLayer<AipValidationContext>> validations = new ArrayList<>();
		validations.add(new DataValidationLayer());
		return validations; 
	}

	public void validate(AipLoader aipLoader) {

		AipValidationContext aipValidationContext = new AipValidationContext(aipLoader, excludeChecks);		
		
		BaseValidator<AipValidationContext> validator = new BaseValidator<>(validations);
		validator.validate(aipValidationContext);		
	}

}
