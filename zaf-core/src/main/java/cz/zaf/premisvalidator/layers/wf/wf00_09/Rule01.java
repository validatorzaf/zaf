package cz.zaf.premisvalidator.layers.wf.wf00_09;

import org.w3c.dom.Document;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.XmlCode;
import cz.zaf.premisvalidator.PremisRule;

public class Rule01 extends PremisRule  {
	public static final String CODE = "wf01";
	public static final String RULE_TEXT = "Soubor je dle standardu XML v 1.0.";
	public static final String RULE_ERROR = "Kontrolovaný soubor nedodržuje syntaxi jazyka XML.";
	public static final String RULE_SOURCE = "CZDAX-PSP020"; 

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
        // Dokument se podařilo načíst, můžeme zkusit standardní načtení
        // load XML document if well-formatted
        Document doc = ctx.getLoader().load();
        if(doc==null) {            	            	
        	throw new ZafException(XmlCode.FAILED_TO_PARSE, 
        			"Nepodařilo se načíst soubor: " + ctx.getActiveFile(),
        			ctx.getLoader().getParserError());        	
        }
	}
}
