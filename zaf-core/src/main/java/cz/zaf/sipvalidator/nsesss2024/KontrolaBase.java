package cz.zaf.sipvalidator.nsesss2024;


import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.ValidationLayerType;

abstract public class KontrolaBase<KontrolaContext>
        extends BaseValidationLayer<KontrolaNsessContext, KontrolaContext> {

    protected KontrolaBase(final ValidationLayerType validationType) {
        super(validationType);
    }
}
