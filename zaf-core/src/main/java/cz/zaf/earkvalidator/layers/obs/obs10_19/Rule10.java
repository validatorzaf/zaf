package cz.zaf.earkvalidator.layers.obs.obs10_19;

import cz.zaf.earkvalidator.AipRule;

public class Rule10 extends AipRule {
	public static final String CODE = "obs10";
	public static final String RULE_TEXT = "Musí být uveden agent odpovídající za vznik balíčku.";
	public static final String RULE_ERROR = "Není správně uveden agent v elementu mets/metsHdr/agent.";
	public static final String RULE_SOURCE = "CZDAX-PMT0205, CZDAX-PMT0206, CZDAX-PMT0207, CZDAX-PMT0208, CZDAX-PMT0209, CZDAX-PMT0210, CZDAX-PMT0211";

	public Rule10() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}
}
