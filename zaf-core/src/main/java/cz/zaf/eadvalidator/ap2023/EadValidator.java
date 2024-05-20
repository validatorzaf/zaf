package cz.zaf.eadvalidator.ap2023;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.fvl01.FormatValidationLayer;
import cz.zaf.eadvalidator.ap2023.profile.EadValidationProfile;

public class EadValidator {

    List<ValidationLayer<EadValidationContext>> validations;

    public EadValidator(EadValidationProfile profilValidace, List<String> excludeChecks) {
        this.validations = prepareValidations(profilValidace);
    }

    private List<ValidationLayer<EadValidationContext>> prepareValidations(EadValidationProfile profilValidace) {
        List<ValidationLayer<EadValidationContext>> validations = new ArrayList<>(1);
        validations.add(new FormatValidationLayer());
        return validations;
    }

    public void validate(EadLoader eadLoader) {

        EadValidationContext context = new EadValidationContext(eadLoader);

        BaseValidator<EadValidationContext> validator = new BaseValidator<>(validations);
        validator.validate(context);
    }
}
