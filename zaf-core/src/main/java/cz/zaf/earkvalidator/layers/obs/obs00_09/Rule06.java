package cz.zaf.earkvalidator.layers.obs.obs00_09;

import cz.zaf.earkvalidator.AipRule;

public class Rule06 extends AipRule {
	public static final String CODE = "obs06";
	public static final String RULE_TEXT = "Element <metsHdr> musí existovat.";
	public static final String RULE_ERROR = "Chybí element <metsHdr>.";
	public static final String RULE_SOURCE = "CZDAX-PMT0201";

	public Rule06() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
