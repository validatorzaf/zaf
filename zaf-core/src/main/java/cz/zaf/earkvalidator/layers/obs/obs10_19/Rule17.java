package cz.zaf.earkvalidator.layers.obs.obs10_19;

import cz.zaf.earkvalidator.AipRule;

public class Rule17 extends AipRule {
	public static final String CODE = "obs17";
	public static final String RULE_TEXT = "Popisná metadata jsou uvedena ve složce descriptive.";
	public static final String RULE_ERROR = "Popisná metadata jsou uvedena v chybném umístění.";
	public static final String RULE_SOURCE = "CZDAX-PSP0107";

	public Rule17() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
