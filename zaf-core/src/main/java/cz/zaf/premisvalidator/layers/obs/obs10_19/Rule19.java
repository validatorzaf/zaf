package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.PremisComplexType;

public class Rule19 extends PremisRule {

	public static final String CODE = "obs19";
	public static final String RULE_TEXT = "Správné uvedení doplňujících informací ke vzniku balíčku.";
	public static final String RULE_ERROR = "Nesprávně uvedené informace v souvislosti se zvnikem balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PKG0901, CZDAX-PKG0902, CZDAX-PKG0903";

	public Rule19() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
	}
}
