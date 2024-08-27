package cz.zaf.eadvalidator.ap2023.layers.wf.wf00_09;

import org.w3c.dom.Document;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.XmlCode;
import cz.zaf.eadvalidator.ap2023.EadRule;

public class Rule01 extends EadRule {

    static final public String CODE = "wf1";

    public Rule01() {
        super(CODE,
                "Soubor je well-formed.",
                "Kontrolovaný soubor nedodržuje syntaxi jazyka XML.",
                "Část 1.1.3 profilu EAD3 MV ČR");
    }

    @Override
    protected void evalImpl() {        
        // Dokument se podařilo načíst, můžeme zkusit standardní načtení
        // load XML document if well-formatted
        Document doc = ctx.getLoader().load();
        if(doc==null) {            	            	
        	throw new ZafException(XmlCode.FAILED_TO_PARSE, "Nepodařilo se načíst soubor: " + ctx.getLoader().getFilePath(),
            			ctx.getLoader().getParserError());        	
        }
    }

}
