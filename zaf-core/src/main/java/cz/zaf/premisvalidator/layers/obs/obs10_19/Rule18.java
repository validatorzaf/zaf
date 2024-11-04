package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.PremisComplexType;

public class Rule18 extends PremisRule {

	public static final String CODE = "obs18";
	public static final String RULE_TEXT = "Export balíčku je správně zapsán.";
	public static final String RULE_ERROR = "Chybně zapsána informace o exportu balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PKG0801, CZDAX-PKG0802, CZDAX-PKG0803, CZDAX-PKG0804, CZDAX-PKG0805";

	public Rule18() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
	}
}
