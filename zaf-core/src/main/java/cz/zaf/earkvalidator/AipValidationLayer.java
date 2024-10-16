package cz.zaf.earkvalidator;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;

public class AipValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {
	
	private final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses;

	public AipValidationLayer(ValidationLayers validationType) {
		super(validationType);
		
		ruleClasses = validationType.getRuleClasses();
	}
	
	public AipValidationLayer(final ValidationLayers validationType, 
			final String innerFileName) {
		super(validationType, innerFileName);
		
		ruleClasses = validationType.getRuleClasses();
		
	}

	@Override
	protected void validateImpl() {
		var rules = createRules(ruleClasses);
		// Here we can prepare layer specific context		
		provedKontrolu(ctx, rules);		
	}

}
