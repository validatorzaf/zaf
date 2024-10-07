package cz.zaf.earkvalidator.layers.ns;

import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule01;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule02;

public class NamespaceValidationLayer  extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private Class<?>[] ruleClasses = {
			Rule01.class,
			Rule02.class,
	};

	public NamespaceValidationLayer() {
		super(ValidationLayers.NAMESPACE);
	}

	@Override
	protected void validateImpl() {
		List<Rule<AipValidationContext>	> rules = createRules(ruleClasses);
		
		this.provedKontrolu(ctx, rules);		
	}
}
