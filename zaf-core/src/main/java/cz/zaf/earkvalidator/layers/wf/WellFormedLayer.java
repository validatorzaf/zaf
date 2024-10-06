package cz.zaf.earkvalidator.layers.wf;

import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.wf.wf00_09.Rule01;
import cz.zaf.earkvalidator.layers.wf.wf00_09.Rule02;

public class WellFormedLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private Class<?>[] ruleClasses = {
			Rule01.class,
			Rule02.class,
	};
	
	public WellFormedLayer() {
		super(ValidationLayers.WELL_FORMED);
	}
	
	@Override
	protected void validateImpl() {
		List<Rule<AipValidationContext>	> rules = createRules(ruleClasses);
		
		this.provedKontrolu(ctx, rules);		
	}
}
