package cz.zaf.sipvalidator.nsesss2017.pravidla04;

import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.sipvalidator.nsesss2017.KontrolaNsess2017Context;

public class NsCheckContext implements RuleEvaluationContext {
    private KontrolaNsess2017Context kontrolaCtx;

    public NsCheckContext(final KontrolaNsess2017Context kontrolaCtx) {
        this.kontrolaCtx = kontrolaCtx;
    }

    public KontrolaNsess2017Context getContext() {
        return kontrolaCtx;
    }
}