package cz.zaf.sipvalidator.nsesss2017.pravidla00;

import cz.zaf.common.validation.RuleEvaluationContext;

public class VirCheckContext implements RuleEvaluationContext {
    private boolean kontrolaOk = true;
    private String errorDescr;

    public VirCheckContext(final boolean kontrolaOk,
                           final String errorDescr) {
        this.kontrolaOk = kontrolaOk;
        this.errorDescr = errorDescr;
    }

    public boolean isKontrolaOk() {
        return kontrolaOk;
    }

    public String getErrorDescr() {
        return errorDescr;
    }

}
