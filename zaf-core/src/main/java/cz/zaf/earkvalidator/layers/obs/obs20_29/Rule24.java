package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule24 extends AipRule {
	public static final String CODE = "obs24";
	public static final String RULE_TEXT = "Element mets/amdSec/digiprovMD/mdRef má uvedeny všechny povinné atributy.";
	public static final String RULE_ERROR = "Element mets/amdSec/digiprovMD/mdRef není správně uveden.";
	public static final String RULE_SOURCE = "CZDAX-PMT0411, CZDAX-PMT0412, CZDAX-PMT0413, CZDAX-PMT0414, CZDAX-PMT0415, CZDAX-PMT0416";

	public Rule24() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
