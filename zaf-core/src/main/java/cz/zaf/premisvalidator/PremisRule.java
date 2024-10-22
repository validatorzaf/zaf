package cz.zaf.premisvalidator;

import cz.zaf.common.validation.BaseRule;

public abstract class PremisRule extends BaseRule<PremisValidationContext> {
	
	protected PremisValidationContext ctx;

	public PremisRule(String code, String ruleText, String ruleError, String ruleSource) {
		super(code, ruleText, ruleError, ruleSource);
	}

	@Override
	public void eval(PremisValidationContext ctx) {
		this.ctx = ctx;
		evalImpl();
		this.ctx = null;
	}

	protected abstract void evalImpl();
}
