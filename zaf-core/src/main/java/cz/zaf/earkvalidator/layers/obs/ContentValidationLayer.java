package cz.zaf.earkvalidator.layers.obs;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule02;

public class ContentValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private static final List<Class<? extends BaseRule<AipValidationContext>>> aipRuleClasses = List.of(
			Rule01.class,
			Rule02.class
		);
	
	private List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses;
	
	public ContentValidationLayer() {
		super(ValidationLayers.OBSAH);
	}
	
	@Override
	protected void validateImpl() {
		ruleClasses = aipRuleClasses;
		
		List<? extends BaseRule<AipValidationContext> > rules = createRules(ruleClasses);		
		this.provedKontrolu(ctx, rules);		
	}
}
