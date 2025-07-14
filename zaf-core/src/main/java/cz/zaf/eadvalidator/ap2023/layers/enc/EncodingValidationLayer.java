package cz.zaf.eadvalidator.ap2023.layers.enc;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.enc.enc00_09.Rule01;

public class EncodingValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

    public EncodingValidationLayer(String innerFileName) {
        super(ValidationLayers.ENCODING, innerFileName);
    }

    @Override
    protected void validateImpl() {
        EadRule rules[] = {
                new Rule01()
        };

        this.provedKontrolu(ctx, rules);
    }

}
