package cz.zaf.eadvalidator.ap2023.layers.obs;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule01;

public class ContentValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	public ContentValidationLayer() {
		super(ValidationLayers.OBSAH);
	}

	@Override
	protected void validateImpl() {
		EadRule rules[] = {
                new Rule01(),
        };

        this.provedKontrolu(ctx, rules);
		
		
	}
}
