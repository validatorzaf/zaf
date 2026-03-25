package cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49;

import java.util.List;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Langmaterial;
import cz.zaf.schema.ead3.Language;

public class Rule45 extends EadRule {

	static final public String CODE = "obs45";
    static final public String RULE_TEXT = "Element <langmaterial> obsahuje pouze elemety <language>. Až na úroveň kořene archivní pomůcky je neopakovatelný, kde může být uveden jednou s nastaven hodnotu atributu \"altrender\" na hodnotu \"majority\".";
    static final public String RULE_ERROR = "Element <langmaterial> je použit opakovaně.";
    static final public String RULE_SOURCE = "Část 6.14 profilu EAD3 MV ČR";

    public Rule45() {
    	super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

	@Override
	protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validateRootLangMaterial(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validateLangMaterial(didC);
        });		
	}

	private void validateLangMaterial(Did did) {
        List<Object> list = did.getMDid();
        Langmaterial locatedLangmaterial = null;
        
        for (Object object : list) {
            if (object instanceof Langmaterial langmaterial) {
            	if(locatedLangmaterial!=null) {
            		throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Zjištěn opakovaný výskyt elementu langmaterial",
            				ctx.formatEadPosition(langmaterial));
            	}
            	ctx.markValidatedElement(langmaterial);
            	locatedLangmaterial = langmaterial;
            	
            	validateLangmaterialContent(langmaterial);            	
            }
        }		
	}

	private void validateLangmaterialContent(Langmaterial langmaterial) {
    	boolean existsLanguage = false;
        List<Object> languageOrLanguageset = langmaterial.getLanguageOrLanguageset();
        for (Object objLang : languageOrLanguageset) {
            if (objLang instanceof Language language) {
                existsLanguage = true;
                
                ctx.markValidatedElement(language);
            }
        }
        
        if(!existsLanguage) {
        	throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element language",
        			ctx.formatEadPosition(langmaterial));
        }		
	}

	private void validateRootLangMaterial(Did did) {
        List<Object> list = did.getMDid();
        Langmaterial locatedLangmaterial = null;
        Langmaterial majorLangmaterial = null;
        
        for (Object object : list) {
            if (object instanceof Langmaterial langmaterial) {
            	if("majority".equals( langmaterial.getAltrender() ) ) {
            		if(majorLangmaterial!=null) {
                		throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Zjištěn opakovaný výskyt elementu langmaterial",
                				ctx.formatEadPosition(langmaterial));            			
            		}
            		majorLangmaterial = langmaterial;
            		ctx.markValidatedAttribute(langmaterial, "altrender");
            	} else {            	
            		if(locatedLangmaterial!=null) {
            			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Zjištěn opakovaný výskyt elementu langmaterial",
            					ctx.formatEadPosition(langmaterial));
            		}
            		locatedLangmaterial = langmaterial;
            		ctx.markValidatedElement(langmaterial);
            	}
            	
            	validateLangmaterialContent(langmaterial);            	
            }
        }				
	}
}
