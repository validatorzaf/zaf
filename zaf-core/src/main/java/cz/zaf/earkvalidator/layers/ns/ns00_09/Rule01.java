package cz.zaf.earkvalidator.layers.ns.ns00_09;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule01 extends AipRule {
	public static final String CODE = "ns01";
	public static final String RULE_TEXT = "Soubor obsahuje právě jeden kořenový element <mets>.";
	public static final String RULE_ERROR = "Chybí kořenový element <mets>.";
	public static final String RULE_SOURCE = "CZDAX-PMT0002"; 

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
        Element metsRoot = ctx.getMetsRootElement();

        if (metsRoot == null || !metsRoot.getNodeName().equals("mets")) {
        	String detailChyby = "Soubor neobsahuje kořenový element <mets>.";
            throw new ZafException(BaseCode.CHYBI_ELEMENT, detailChyby);
        }		
	}
}
