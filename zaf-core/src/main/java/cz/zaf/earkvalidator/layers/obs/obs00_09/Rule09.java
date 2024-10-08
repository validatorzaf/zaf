package cz.zaf.earkvalidator.layers.obs.obs00_09;

import cz.zaf.earkvalidator.AipRule;

public class Rule09 extends AipRule {
	public static final String CODE = "obs09";
	public static final String RULE_TEXT = "Musí být uveden typ balíčku AIP v atributu: mets/metsHdr/@csip:OAISPACKAGETYP.";
	public static final String RULE_ERROR = "Není uveden platný typ balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PMT0204";

	public Rule09() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
