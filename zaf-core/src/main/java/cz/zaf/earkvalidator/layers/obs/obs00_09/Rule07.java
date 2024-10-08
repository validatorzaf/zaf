package cz.zaf.earkvalidator.layers.obs.obs00_09;

import cz.zaf.earkvalidator.AipRule;

public class Rule07 extends AipRule {
	public static final String CODE = "obs07";
	public static final String RULE_TEXT = "V atributu mets/metsHdr/@CREATEDATE musí být zapsán čas vzniku balíčku.";
	public static final String RULE_ERROR = "Chybí atribut mets/metsHdr/@CREATEDATE.";
	public static final String RULE_SOURCE = "CZDAX-PMT0202";

	public Rule07() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
