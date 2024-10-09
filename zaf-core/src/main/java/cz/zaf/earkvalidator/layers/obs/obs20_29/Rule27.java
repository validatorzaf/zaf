package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule27 extends AipRule {
	public static final String CODE = "obs27";
	public static final String RULE_TEXT = "Element mets/fileSec má uveden atribut ID.";
	public static final String RULE_ERROR = "Element mets/fileSec nemá uveden atribut ID.";
	public static final String RULE_SOURCE = "CZDAX-PMT0506";

	public Rule27() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
