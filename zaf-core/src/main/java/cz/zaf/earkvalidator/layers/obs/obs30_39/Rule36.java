package cz.zaf.earkvalidator.layers.obs.obs30_39;

import cz.zaf.earkvalidator.AipRule;

public class Rule36 extends AipRule {
	public static final String CODE = "obs36";
	public static final String RULE_TEXT = "Balíček obsahuje fyzickou strukturální mapu.";
	public static final String RULE_ERROR = "Není uvedena fyzická strukturální mapa nebo je chybně popsána.";
	public static final String RULE_SOURCE = "CZDAX-PMT0603, CZDAX-PMT0604, CZDAX-PMT0605";

	public Rule36() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
