package cz.zaf.eadvalidator.ap2023;

import cz.zaf.common.validation.BaseRule;

public abstract class EadRule extends BaseRule<EadValidationContext> {

    protected EadValidationContext ctx;

    public EadRule(final String code, String description,
                   String genericError,
                   String ruleSource) {
        super(code, description, genericError, ruleSource);
    }

    @Override
    public void eval(final EadValidationContext ctx) {
        this.ctx = ctx;
        evalImpl();
        this.ctx = null;
    }

    protected abstract void evalImpl();
}
