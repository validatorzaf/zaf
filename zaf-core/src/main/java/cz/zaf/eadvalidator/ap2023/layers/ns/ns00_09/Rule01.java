package cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;

public class Rule01 extends EadRule {
	
	static final public String CODE = "ns1";
	
	public Rule01() {
        super(CODE,
                "Soubor obsahuje právě jeden kořenový element <ead:ead>.",
                "Chybí kořenový element <ead:ead>.",
                "Část 1.4 profilu EAD3 MV ČR");		
	}

	@Override
	protected void evalImpl() {
        Element eadRoot = ctx.getRootElement();

        if (eadRoot == null || !eadRoot.getNodeName().equals("ead:ead")) {
        	String detailChyby = "Soubor neobsahuje kořenový element <ead:ead>.";
            throw new ZafException(BaseCode.CHYBI_ELEMENT, detailChyby);
        }

	}

}
