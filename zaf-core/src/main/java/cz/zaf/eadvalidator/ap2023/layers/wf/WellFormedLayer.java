package cz.zaf.eadvalidator.ap2023.layers.wf;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.wf.wf00_09.Rule01;

public class WellFormedLayer
	extends BaseValidationLayer<EadValidationContext, EadValidationContext>
{
	private static final List<Class<? extends BaseRule<EadValidationContext>>> ruleClasses = List.of(
			Rule01.class
		);
	
    public WellFormedLayer(String innerFileName) {
		super(ValidationLayers.WELL_FORMED, innerFileName);
	}

    @Override
    protected void validateImpl() {
        this.provedKontrolu(ctx, createRules());
    }

	public List<? extends BaseRule<EadValidationContext>> createRules() {
		return createRules(ruleClasses);
	}

}
