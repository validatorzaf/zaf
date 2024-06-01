package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerType;

abstract public class KontrolaBase<KontrolaContext extends RuleEvaluationContext>
        extends BaseValidationLayer<KontrolaNsess2017Context, KontrolaContext> {

    KontrolaBase(final ValidationLayerType validationType) {
        super(validationType);
    }
}
