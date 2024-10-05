package cz.zaf.earkvalidator.layers.dat;

import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule01;
import cz.zaf.earkvalidator.AipValidationContext;

public class DataValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {
	
	Class<?> ruleClasses[] = {
			Rule01.class,
	};	

	public DataValidationLayer() {
		super(ValidationLayers.DATA);
	}

	@Override
	protected void validateImpl() {
		
		List<Rule<AipValidationContext>	> rules = createRules(ruleClasses);
		
		this.provedKontrolu(ctx, rules);
	}

}
