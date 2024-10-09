package cz.zaf.earkvalidator.layers.obs.obs10_19;

import cz.zaf.earkvalidator.AipRule;

public class Rule16 extends AipRule {
	public static final String CODE = "obs16";
	public static final String RULE_TEXT = "Reference z elementu mets/dmdSec/mdRef na fyzický soubor má správně uvedeny všechny atributy.";
	public static final String RULE_ERROR = "CZDAX-PMT0310, CZDAX-PMT0311, CZDAX-PMT0312, CZDAX-PMT0313, CZDAX-PMT0314, CZDAX-PMT0315";
	public static final String RULE_SOURCE = "Neúplná reference na soubor s popisnými metadaty v elementu mets/dmdSec/mdRef.";

	public Rule16() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
