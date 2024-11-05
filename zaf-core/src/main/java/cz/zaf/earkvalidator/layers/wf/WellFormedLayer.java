package cz.zaf.earkvalidator.layers.wf;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.wf.wf00_09.Rule01;
import cz.zaf.earkvalidator.layers.wf.wf00_09.Rule02;

public class WellFormedLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private static final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses = List.of(
			Rule01.class,
			Rule02.class
	);
	
	public WellFormedLayer() {
		super(ValidationLayers.WELL_FORMED);
	}
	
	
	public List<? extends BaseRule<AipValidationContext> > createRules() {
		return createRules(ruleClasses);
	}
	
	@Override
	protected void validateImpl() {
		var rules = createRules();
		
		provedKontrolu(ctx, rules);		
	}
}
