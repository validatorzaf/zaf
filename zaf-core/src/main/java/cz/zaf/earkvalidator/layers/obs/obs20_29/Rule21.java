package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule21 extends AipRule {
	public static final String CODE = "obs21";
	public static final String RULE_TEXT = "Administrativní metadata v elementu mets/amdSec/digiprovMD mají uveden atribut GROUPID s očekávanou hodnotou.";
	public static final String RULE_ERROR = "Administrativní metadata v elementu mets/amdSec/digiprovMD nemají správně uveden atribut GROUPID.";
	public static final String RULE_SOURCE = "CZDAX-PMT0406";

	public Rule21() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
