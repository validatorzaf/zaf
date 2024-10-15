package cz.zaf.earkvalidator.layers.fls.fls00_09;

import cz.zaf.earkvalidator.AipRule;

public class Rule01 extends AipRule {
	public static final String CODE = "fls01";
	public static final String RULE_TEXT = "V balíčku jsou k dispozici všechny soubory uvedené v METS.xml.";
	public static final String RULE_ERROR = "Chybí deklarovaný soubor.";
	public static final String RULE_SOURCE = "CZDAX-PPR0307";

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
	}

}
