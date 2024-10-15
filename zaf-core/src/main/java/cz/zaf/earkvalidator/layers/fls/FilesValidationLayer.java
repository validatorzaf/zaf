package cz.zaf.earkvalidator.layers.fls;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;

public class FilesValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private static final List<Class<? extends BaseRule<AipValidationContext>>> aipRuleClasses = List.of(
			);
	
	private List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses;
	
	public FilesValidationLayer() {
		super(ValidationLayers.FILES);
	}
	
	@Override
	protected void validateImpl() {
		ruleClasses = aipRuleClasses;
		
		List<? extends BaseRule<AipValidationContext> > rules = createRules(ruleClasses);		
		this.provedKontrolu(ctx, rules);		
	}

}
