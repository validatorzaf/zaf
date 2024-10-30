package cz.zaf.earkvalidator.layers.fls;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule01;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule02;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule03;
import cz.zaf.earkvalidator.layers.fls.fls00_09.Rule04;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class FilesValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private static final List<Class<? extends BaseRule<AipValidationContext>>> aipRuleClasses = List.of(
			Rule01.class,
			Rule02.class,
			Rule03.class
			);
	
	private static final List<Class<? extends BaseRule<AipValidationContext>>> sipChangeRuleClasses = List.of(
			Rule01.class,
			Rule02.class,
			Rule04.class
			);

	private List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses;
	
	public FilesValidationLayer(DAAIP2024Profile daaip2024Profile) {
		super(ValidationLayers.FILES);
		
		switch(daaip2024Profile) {
		case AIP:
		case DIP_METADATA:
		case DIP_CONTENT:
			ruleClasses = aipRuleClasses;
			break;
		case SIP_CHANGE:
			ruleClasses = sipChangeRuleClasses;
			break;
		}
	}
	
	@Override
	protected void validateImpl() {		
		List<? extends BaseRule<AipValidationContext> > rules = createRules(ruleClasses);		
		this.provedKontrolu(ctx, rules);		
	}

}
