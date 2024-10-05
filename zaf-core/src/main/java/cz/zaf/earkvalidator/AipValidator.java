package cz.zaf.earkvalidator;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.earkvalidator.layers.dat.DataValidationLayer;

public class AipValidator {

	private List<String> excludeChecks;
	private List<ValidationLayer<AipValidationContext>> validations;

	public AipValidator(List<String> excludeChecks) {
		this.excludeChecks = excludeChecks;

		validations = prepareValidations();
	}

	private List<ValidationLayer<AipValidationContext>> prepareValidations() {
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
