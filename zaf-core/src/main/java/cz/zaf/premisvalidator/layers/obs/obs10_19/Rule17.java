package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.PremisComplexType;

public class Rule17 extends PremisRule {

	public static final String CODE = "obs17";
	public static final String RULE_TEXT = "Přesun/delimitace balíčku je správně zapsána.";
	public static final String RULE_ERROR = "Chybně zachycena událost přesun/delimitace archiválií.";
	public static final String RULE_SOURCE = "CZDAX-PKG0701, CZDAX-PKG0702, CZDAX-PKG0703, CZDAX-PKG0704, CZDAX-PKG0705, CZDAX-PKG0706";

	public Rule17() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
	}
}
