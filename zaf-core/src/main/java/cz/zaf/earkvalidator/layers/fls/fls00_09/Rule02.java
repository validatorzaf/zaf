package cz.zaf.earkvalidator.layers.fls.fls00_09;

import cz.zaf.earkvalidator.AipRule;

public class Rule02 extends AipRule {
	public static final String CODE = "fls02";
	public static final String RULE_TEXT = "V balíčku neexistují soubory, které nejsou uvedené v METS.xml.";
	public static final String RULE_ERROR = "V balíčku jsou nedeklarované soubory.";
	public static final String RULE_SOURCE = "CZDAX-PPR0307";

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}


}
