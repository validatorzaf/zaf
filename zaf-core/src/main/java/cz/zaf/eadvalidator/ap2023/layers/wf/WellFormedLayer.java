package cz.zaf.eadvalidator.ap2023.layers.wf;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.wf.wf00_09.Rule01;

public class WellFormedLayer
	extends BaseValidationLayer<EadValidationContext, EadValidationContext>
{
    public WellFormedLayer(String innerFileName) {
		super(ValidationLayers.WELL_FORMED, innerFileName);
	}

    @Override
    protected void validateImpl() {    	
        EadRule rules[] = {
                new Rule01()
        };

        this.provedKontrolu(ctx, rules);
    }

}
