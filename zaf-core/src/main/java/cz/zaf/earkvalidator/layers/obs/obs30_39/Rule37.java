package cz.zaf.earkvalidator.layers.obs.obs30_39;

import cz.zaf.earkvalidator.AipRule;

public class Rule37 extends AipRule {
	public static final String CODE = "obs37";
	public static final String RULE_TEXT = "Správná podoba fyzické strukturální mapy.";
	public static final String RULE_ERROR = "Fyzická strukturální mapa obsahuje chybné záznamy.";
	public static final String RULE_SOURCE = "CZDAX-PMT0607, CZDAX-PMT0608, CZDAX-PMT0609, CZDAX-PMT0610, CZDAX-PMT0611, CZDAX-PMT0612, CZDAX-PMT0613, CZDAX-PMT0614, CZDAX-PMT0615, CZDAX-PMT0616, CZDAX-PMT0617, CZDAX-PMT0618, CZDAX-PMT0619, CZDAX-PMT0620, CZDAX-PMT0621, CZDAX-PMT0622, CZDAX-PMT0623, CZDAX-PMT0624, CZDAX-PMT0625, CZDAX-PMT0626, CZDAX-PMT0627";

	public Rule37() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
