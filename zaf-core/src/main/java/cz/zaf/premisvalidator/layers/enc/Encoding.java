package cz.zaf.premisvalidator.layers.enc;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.ValidationLayerContext;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.premisvalidator.PremisValidationContext;
import cz.zaf.premisvalidator.layers.enc.enc00_09.Rule01;

public class Encoding<T extends ValidationLayerContext> extends BaseValidationLayer<T, PremisValidationContext> { 

	//implements ValidationLayer<T> {
	static public final List<Class<? extends BaseRule<PremisValidationContext>>> ruleClasses = List.of(
			Rule01.class
			);
	
	final PremisValidationContext permisCtx;

	public Encoding(ValidationLayerType validationType, String innerFileName, PremisValidationContext permisCtx) {
		super(validationType, innerFileName);
		this.permisCtx = permisCtx;
	}

	@Override
	protected void validateImpl() {
		var rules = createRules(ruleClasses);
		// Here we can prepare layer specific context		
		provedKontrolu(permisCtx, rules);		
	}
}
