package cz.zaf.earkvalidator.layers.ns;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule01;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule02;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule03;
import cz.zaf.earkvalidator.layers.ns.ns00_09.Rule04;

public class NamespaceValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {
	
	private static final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses = List.of(
			Rule01.class,
			Rule02.class,
			Rule03.class,
			Rule04.class
		);
	
	public NamespaceValidationLayer() {
		super(ValidationLayers.NAMESPACE);
	}

	@Override
	protected void validateImpl() {
		var rules = createRules(ruleClasses);		
		this.provedKontrolu(ctx, rules);		
	}
}
