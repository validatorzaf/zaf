package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.RuleEvaluationContext;

abstract public class KontrolaBase<KontrolaContext extends RuleEvaluationContext>
        extends BaseValidationLayer<KontrolaNsess2017Context, KontrolaContext> {

    KontrolaBase(final TypUrovenKontroly validationType) {
        super(validationType);
    }
}
