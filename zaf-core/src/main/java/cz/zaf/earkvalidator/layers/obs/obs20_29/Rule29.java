package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.earkvalidator.AipRule;

public class Rule29 extends AipRule {
	public static final String CODE = "obs29";
	public static final String RULE_TEXT = "Předaná schémata jsou uvedena v elementu mets/fileSec/fileGrp[@USE='Schemas'].";
	public static final String RULE_ERROR = "Chybně odkazované soubory schémat.";
	public static final String RULE_SOURCE = "CZDAX-PMT0508, CZDAX-PMT0513";

	public Rule29() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {

	}


}
