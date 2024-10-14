package cz.zaf.earkvalidator.layers.obs.obs30_39;

import cz.zaf.earkvalidator.AipRule;

public class Rule35 extends AipRule {
	public static final String CODE = "obs35";
	public static final String RULE_TEXT = "Balíček obsahuje fyzickou nebo logickou strukturální mapu.";
	public static final String RULE_ERROR = "Uvedena strukturální mapa neznámého typu.";
	public static final String RULE_SOURCE = "CZDAX-PMT0601, CZDAX-PMT0602";

	public Rule35() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
