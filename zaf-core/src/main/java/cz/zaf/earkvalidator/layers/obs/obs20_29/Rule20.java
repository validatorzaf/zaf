package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule20 extends AipRule {
	public static final String CODE = "obs20";
	public static final String RULE_TEXT = "Administrativní metadata v elementu mets/amdSec/digiprovMD mají uveden atribut STATUS s očekávanou hodnotou.";
	public static final String RULE_ERROR = "Administrativní metadata v elementu mets/amdSec/digiprovMD nemají správně uveden atribut STATUS.";
	public static final String RULE_SOURCE = "CZDAX-PMT0405";

	public Rule20() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
