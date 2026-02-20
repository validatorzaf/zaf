package cz.zaf.eadvalidator.ap2023.layers.ns;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule03;

public class NamespaceValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	private List<Class<? extends BaseRule<EadValidationContext>>> ruleClasses = List.of(
			Rule01.class,
			Rule02.class,
			Rule03.class
	);

	public NamespaceValidationLayer(String innerFileName) {
		super(ValidationLayers.NAMESPACE, innerFileName);
	}

	@Override
	protected void validateImpl() {
        this.provedKontrolu(ctx, createRules());
		
	}

	public List<? extends BaseRule<EadValidationContext>> createRules() {
		return createRules(ruleClasses);
	}

}
