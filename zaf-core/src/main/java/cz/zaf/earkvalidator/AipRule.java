package cz.zaf.earkvalidator;

import cz.zaf.common.validation.BaseRule;

public abstract class AipRule extends BaseRule<AipValidationContext> {

	protected AipValidationContext ctx;

	public AipRule(final String code, 
			final String ruleText, 
			final String ruleError, 
			final String ruleSource) {
		super(code, ruleText, ruleError, ruleSource);
	}

	@Override
	public void eval(AipValidationContext ctx) {
		this.ctx = ctx;
		evalImpl();
		this.ctx = null;
	}

	protected abstract void evalImpl();
}
