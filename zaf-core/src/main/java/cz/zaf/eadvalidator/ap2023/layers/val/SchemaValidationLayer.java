package cz.zaf.eadvalidator.ap2023.layers.val;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.val.val00_09.Rule01;

public class SchemaValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	private List<Class<? extends BaseRule<EadValidationContext>>> ruleClasses = List.of(
			Rule01.class
	);

	public SchemaValidationLayer(String innerFileName) {
		super(ValidationLayers.VALIDATION, innerFileName);
	}

	@Override
	protected void validateImpl() {
        this.provedKontrolu(ctx, createRules());				
	}

	public List<? extends BaseRule<EadValidationContext>> createRules() {
		return createRules(ruleClasses);
	}

}
