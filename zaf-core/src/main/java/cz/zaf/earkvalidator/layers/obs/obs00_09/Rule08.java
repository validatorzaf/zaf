package cz.zaf.earkvalidator.layers.obs.obs00_09;

import cz.zaf.earkvalidator.AipRule;

public class Rule08 extends AipRule {
	public static final String CODE = "obs08";
	public static final String RULE_TEXT = "Neexistuje atribut: mets/metsHdr/@LASTMODDATE.";
	public static final String RULE_ERROR = "Uveden neplatný atribut mets/metsHdr/@LASTMODDATE.";
	public static final String RULE_SOURCE = "CZDAX-PMT0203";

	public Rule08() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
