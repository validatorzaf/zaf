package cz.zaf.earkvalidator.layers.obs.obs10_19;

import cz.zaf.earkvalidator.AipRule;

public class Rule18 extends AipRule {
	public static final String CODE = "obs18";
	public static final String RULE_TEXT = "Administrativní metadata jsou uvedena v elementu mets/amdSec/digiprovMD.";
	public static final String RULE_ERROR = "Administrativní metadata jsou uvedena chybně.";
	public static final String RULE_SOURCE = "CZDAX-PMT0403, CZDAX-PMT0418";

	public Rule18() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
