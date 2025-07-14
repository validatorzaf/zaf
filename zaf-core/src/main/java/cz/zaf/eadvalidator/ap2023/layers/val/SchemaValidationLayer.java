package cz.zaf.eadvalidator.ap2023.layers.val;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.val.val00_09.Rule01;

public class SchemaValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	public SchemaValidationLayer(String innerFileName) {
		super(ValidationLayers.VALIDATION, innerFileName);
	}

	@Override
	protected void validateImpl() {
		EadRule rules[] = {
                new Rule01(),
        };

        this.provedKontrolu(ctx, rules);
		
		
	}

}
