package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule23 extends AipRule {
	public static final String CODE = "obs23";
	public static final String RULE_TEXT = "Metadata popisující uchovávání (preservation) MUSÍ být uložena v podsložce preservation.";
	public static final String RULE_ERROR = "Metadata popisující uchování jsou v neočekávaném místě.";
	public static final String RULE_SOURCE = "CZDAX-PSP0106";

	public Rule23() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
