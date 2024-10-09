package cz.zaf.earkvalidator.layers.obs.obs10_19;

import cz.zaf.earkvalidator.AipRule;

public class Rule19 extends AipRule {
	public static final String CODE = "obs19";
	public static final String RULE_TEXT = "Administrativní metadata v elementu mets/amdSec/digiprovMD mají uveden atribut ID.";
	public static final String RULE_ERROR = "Administrativní metadata v elementu mets/amdSec/digiprovMD nemají uveden atribut ID.";
	public static final String RULE_SOURCE = "CZDAX-PMT0404";

	public Rule19() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
