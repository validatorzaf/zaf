package cz.zaf.earkvalidator.layers.dat.dat00_09;

import cz.zaf.earkvalidator.AipLoader;
import cz.zaf.earkvalidator.AipRule;

public class Rule01 extends AipRule {
	private static final String CODE = "dat01";
	static final public String RULE_TEXT = "Element <ead:recordid> obsahuje neprázdnou hodnotu, která splňuje syntaxi pro tvorbu UUID.";
	static final public String RULE_ERROR = "Element <ead:recordid> neobsahuje hodnotu v požadovaném formátu.";
	static final public String RULE_SOURCE = "Část 2.1 profilu EAD3 MV ČR"; 

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		AipLoader loader = ctx.getLoader();
		
	}
}
