package cz.zaf.eadvalidator.ap2023;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidatorListener;
import cz.zaf.eadvalidator.ap2023.layers.enc.EncodingValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.fvl01.FormatValidationLayer;
import cz.zaf.eadvalidator.ap2023.profile.EadValidationProfile;

public class EadValidator implements ValidatorListener<EadValidationContext> {

    List<ValidationLayer<EadValidationContext>> validations;

    public EadValidator(EadValidationProfile profilValidace, List<String> excludeChecks) {
        this.validations = prepareValidations(profilValidace);
    }

    private List<ValidationLayer<EadValidationContext>> prepareValidations(EadValidationProfile profilValidace) {
        List<ValidationLayer<EadValidationContext>> validations = new ArrayList<>(2);
        validations.add(new EncodingValidationLayer());
        validations.add(new FormatValidationLayer());
        return validations;
    }

    public void validate(EadLoader eadLoader) {

        EadValidationContext context = new EadValidationContext(eadLoader);

        BaseValidator<EadValidationContext> validator = new BaseValidator<>(validations);
        validator.registerListener(this);
        validator.validate(context);
    }

    @Override
    public void layerValidationStarted(EadValidationContext context, ValidationLayer<EadValidationContext> layer) {
        if (layer.getType() == ValidationLayers.BASIC_FILE_FORMAT) {
            // ead
            context.getLoader().load();
        }

    }

    @Override
    public void layerValidationFinished(EadValidationContext context, ValidationLayer<EadValidationContext> layer) {
        // TODO Auto-generated method stub

    }
}
