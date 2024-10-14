package cz.zaf.earkvalidator.layers.obs.obs30_39;

import cz.zaf.earkvalidator.AipRule;

public class Rule33 extends AipRule {
	public static final String CODE = "obs33";
	public static final String RULE_TEXT = "Správně uveden odkaz na soubor v elementu mets/fileSec/fileGrp/file.";
	public static final String RULE_ERROR = "Odkaz na soubor v elementu mets/fileSec/fileGrp/file není úplný a správně vyplněný.";
	public static final String RULE_SOURCE = "CZDAX-PMT0515, CZDAX-PMT0516, CZDAX-PMT0517, CZDAX-PMT0518, CZDAX-PMT0519, CZDAX-PMT0520, CZDAX-PMT0521, CZDAX-PMT0522, CZDAX-PMT0524";

	public Rule33() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
