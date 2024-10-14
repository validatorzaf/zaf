package cz.zaf.earkvalidator.layers.obs.obs30_39;

import cz.zaf.earkvalidator.AipRule;

public class Rule34 extends AipRule {
	public static final String CODE = "obs34";
	public static final String RULE_TEXT = "Správné uvedení odkazu na umístění souboru v elementu mets/fileSec/fileGrp/file/FLocat.";
	public static final String RULE_ERROR = "Chybně uveden odkaz na soubor.";
	public static final String RULE_SOURCE = "CZDAX-PMT0525, CZDAX-PMT0526, CZDAX-PMT0527, CZDAX-PMT0528";

	public Rule34() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
