package cz.zaf.earkvalidator.layers.obs.obs10_19;

import cz.zaf.earkvalidator.AipRule;

public class Rule15 extends AipRule {
	public static final String CODE = "obs15";
	public static final String RULE_TEXT = "Chybně uvedená reference na soubor s popisnými metadaty v elementu mets/dmdSec.";
	public static final String RULE_ERROR = "Reference z elementu mets/dmdSec na fyzický soubor je správně zapsána v elementu mdRef.";
	public static final String RULE_SOURCE = "CZDAX-PMT0306, CZDAX-PMT0307, CZDAX-PMT0308, CZDAX-PMT0309";

	public Rule15() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}

}
