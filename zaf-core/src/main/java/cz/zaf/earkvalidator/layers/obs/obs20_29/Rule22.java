package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule22 extends AipRule {
	public static final String CODE = "obs22";
	public static final String RULE_TEXT = "Reference z elementu mets/amdSec/digiprovMD na fyzický soubor je správně zapsána v elementu mdRef.";
	public static final String RULE_ERROR = "Chybně zapsaná reference na fyzický soubor s administrativními metadaty.";
	public static final String RULE_SOURCE = "CZDAX-PMT0407, CZDAX-PMT0408, CZDAX-PMT0409, CZDAX-PMT0410";

	public Rule22() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
