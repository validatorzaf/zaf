package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.PremisComplexType;

public class Rule16 extends PremisRule {

	public static final String CODE = "obs16";
	public static final String RULE_TEXT = "Vložení do digitálního archivu je správně uvedeno.";
	public static final String RULE_ERROR = "Chybně uvedena informace o vložení do digitálního archivu.";
	public static final String RULE_SOURCE = "CZDAX-PKG0601, CZDAX-PKG0602, CZDAX-PKG0603, CZDAX-PKG0604, CZDAX-PKG0605, CZDAX-PKG0606, CZDAX-PKG0607, CZDAX-PKG0608, CZDAX-PKG0609, CZDAX-PKG0610, CZDAX-PKG0611";

	public Rule16() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
	}
}
