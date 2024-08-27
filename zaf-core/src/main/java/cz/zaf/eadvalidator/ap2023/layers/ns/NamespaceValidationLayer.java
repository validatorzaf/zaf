package cz.zaf.eadvalidator.ap2023.layers.ns;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule03;

public class NamespaceValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	public NamespaceValidationLayer() {
		super(ValidationLayers.NAMESPACE);
	}

	@Override
	protected void validateImpl() {
        EadRule rules[] = {
                new Rule01(),
                new Rule02(),
                new Rule03(),
        };

        this.provedKontrolu(ctx, rules);
		
	}

}
