package cz.zaf.eadvalidator.ap2023.layers.fvl01;

import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.fvl01.fvl00_09.Rule01;

public class FormatValidationLayer
        implements ValidationLayer<EadValidationContext>
{

    final public static String NAME = "Základní formátová kontrola";

    @Override
    public void validate(EadValidationContext context, 
                         ValidationLayerResult result) throws Exception {
        Rule01 rule01 = new Rule01();
        rule01.eval(context);

        // result.add(null);
        return;
    }

    @Override
    public ValidationLayerType getType() {
        return ValidationLayers.BASIC_FILE_FORMAT;
    }

}
