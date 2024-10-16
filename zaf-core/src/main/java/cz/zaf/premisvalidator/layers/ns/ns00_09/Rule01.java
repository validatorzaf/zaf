package cz.zaf.premisvalidator.layers.ns.ns00_09;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.premisvalidator.PremisRule;

public class Rule01 extends PremisRule {
	public static final String CODE = "ns01";
	public static final String RULE_TEXT = "Soubor obsahuje právě jeden kořenový element <premis>.";
	public static final String RULE_ERROR = "Chybí kořenový element <premis>.";
	public static final String RULE_SOURCE = "CZDAX-PMT0002"; 

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
        Element root = ctx.getLoader().getRootElement();

        if (root == null || !root.getNodeName().equals("premis")) {
        	String detailChyby = "Soubor neobsahuje kořenový element <premis>.";
            throw new ZafException(BaseCode.CHYBI_ELEMENT, detailChyby);
        }		
	}
}
