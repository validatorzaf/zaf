package cz.zaf.earkvalidator.layers.obs.obs10_19;

import cz.zaf.earkvalidator.AipRule;

public class Rule12 extends AipRule {
	public static final String CODE = "obs12";
	public static final String RULE_TEXT = "Popisná metadata v elementu mets/dmdSec mají uveden atribut CREATED.";
	public static final String RULE_ERROR = "U popisných metadat v elementu mets/dmdSec není uveden atribut CREATED.";
	public static final String RULE_SOURCE = "CZDAX-PMT0303";

	public Rule12() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
