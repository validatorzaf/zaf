package cz.zaf.premisvalidator;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.ValidationLayerContext;
import cz.zaf.common.validation.ValidationLayerType;

public class PremisValidationLayer<T extends ValidationLayerContext> 
	extends BaseValidationLayer<T, PremisValidationContext> {
	

	final List<Class<? extends BaseRule<PremisValidationContext>>> ruleClasses;
	final PremisValidationContext permisCtx;

	public PremisValidationLayer(ValidationLayerType validationType, 
			String innerFileName, final PremisValidationContext permisCtx,
			final List<Class<? extends BaseRule<PremisValidationContext>>> ruleClasses) {
		super(validationType, innerFileName);
		
		this.permisCtx = permisCtx;
		this.ruleClasses = ruleClasses;
	}


	@Override
	protected void validateImpl() {
		var rules = createRules(ruleClasses);
		// Here we can prepare layer specific context		
		provedKontrolu(permisCtx, rules);		
	}

}