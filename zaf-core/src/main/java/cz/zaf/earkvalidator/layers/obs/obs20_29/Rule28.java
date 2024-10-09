package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule28 extends AipRule {
	public static final String CODE = "obs28";
	public static final String RULE_TEXT = "Předaná dokumentace je uvedena v elementu mets/fileSec/fileGrp[@USE='Documentation'].";
	public static final String RULE_ERROR = "Chybně odkazované soubory dokumentace.";
	public static final String RULE_SOURCE = "CZDAX-PMT0507, CZDAX-PMT0513";

	public Rule28() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
