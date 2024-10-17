package cz.zaf.premisvalidator;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.InnerFileValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidationLayerContext;
import cz.zaf.premisvalidator.layers.enc.Encoding;
import cz.zaf.premisvalidator.layers.ns.Namespace;
import cz.zaf.premisvalidator.layers.val.SchemaVal;
import cz.zaf.premisvalidator.layers.wf.WellFormed;

public class ValidatorPremisInner<T extends ValidationLayerContext> implements InnerFileValidator<T> {
	
	final List<ValidationLayer<T>> validations;
	
	// Alternative constructor to allow insert custom rules
	public ValidatorPremisInner(List<ValidationLayer<T>> validations) {
		this.validations = validations;
	}
	
	public ValidatorPremisInner(String inputFileName, PremisValidationContext permisCtx) {
		this.validations = new ArrayList<>();
		// Default rule inicialization
		validations.add(new PremisValidationLayer<>(ValidationLayers.ENCODING, inputFileName, permisCtx, Encoding.ruleClasses));
		validations.add(new PremisValidationLayer<>(ValidationLayers.WELL_FORMED, inputFileName, permisCtx, WellFormed.ruleClasses));
		validations.add(new PremisValidationLayer<>(ValidationLayers.NAMESPACE, inputFileName, permisCtx, Namespace.ruleClasses));		
		validations.add(new PremisValidationLayer<>(ValidationLayers.VALIDATION, inputFileName, permisCtx, SchemaVal.ruleClasses));
	}

	@Override
	public void validate(T context, String innerFileName) throws Exception {
		BaseValidator<T> validator = new BaseValidator<T>(validations);
		validator.validate(context);		
	}
}
