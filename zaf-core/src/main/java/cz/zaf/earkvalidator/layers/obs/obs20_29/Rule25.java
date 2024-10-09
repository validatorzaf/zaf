package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule25 extends AipRule {
	public static final String CODE = "obs25";
	public static final String RULE_TEXT = "Předání metadat v elementu mets/amdSec/rightsMD není možné.";
	public static final String RULE_ERROR = "Uveden neočekávaný element rightsMD.";
	public static final String RULE_SOURCE = "CZDAX-PMT0417";

	public Rule25() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
