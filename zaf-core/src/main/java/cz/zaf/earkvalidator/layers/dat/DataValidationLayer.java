package cz.zaf.earkvalidator.layers.dat;

import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule01;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule02;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule03;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule04;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule05;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule06;
import cz.zaf.earkvalidator.layers.dat.dat00_09.Rule07;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.earkvalidator.AipValidationContext;

public class DataValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {
	
	static Class<?> aipRuleClasses[] = {
			Rule01.class,
			Rule02.class,
			Rule03.class,
			Rule04.class,
			Rule05.class,
			Rule06.class,
			Rule07.class,
	};	

	static Class<?> dipRuleClasses[] = {
			Rule01.class,
			Rule02.class,
			Rule03.class,
			Rule04.class,
			// Rule05.class,
			// Rule06.class,
			// Rule07.class,
	};	
	
	Class<?> ruleClasses[];

	public DataValidationLayer(DAAIP2024Profile daaip2024Profile) {
		super(ValidationLayers.DATA);
		switch(daaip2024Profile) {
		case AIP:
			ruleClasses = aipRuleClasses;
			break;
		case DIP_METADATA:
			ruleClasses = dipRuleClasses;
			break;
		case DIP_CONTENT:
			ruleClasses = dipRuleClasses;
			break;
		}
	}

	@Override
	protected void validateImpl() {	
		List<Rule<AipValidationContext>	> rules = createRules(ruleClasses);
		
		this.provedKontrolu(ctx, rules);
	}

}
