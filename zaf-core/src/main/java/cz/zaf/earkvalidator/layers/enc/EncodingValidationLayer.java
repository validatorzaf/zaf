package cz.zaf.earkvalidator.layers.enc;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.enc.enc00_09.Rule01;

public class EncodingValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private static final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses = List.of(
		Rule01.class	
	);

	public EncodingValidationLayer() {
		super(ValidationLayers.ENCODING);
	}

	@Override
	protected void validateImpl() {
		provedKontrolu(ctx, createRules());		
	}

	public List<? extends BaseRule<AipValidationContext>> createRules() {
		return createRules(ruleClasses);
	}
}
