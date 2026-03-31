package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Materialspec;
import java.io.Serializable;
import java.util.List;

public class Rule85 extends EadRule {

    static final public String CODE = "obs85";
    static final public String RULE_TEXT = "Každý element <materialspec> s atributem \"localtype\" o hodnotě \"SCALE_RATIO\" obsahuje prostou textovou hodnotu ve formátu M:N nebo [M:N].";
    static final public String RULE_ERROR = "Element <materialspec> s atributem \"localtype\" o hodnotě \"SCALE_RATIO\" neobsahuje prostou textovou podobu měřítka.";
    static final public String RULE_SOURCE = "Část 6.9.1 profilu EAD3 MV ČR";

    public Rule85() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validate(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validate(didC);
        });
    }

    private void validate(Did did) {
        List<Object> childList = did.getMDid();

        for (Object child : childList) {
            if (child instanceof Materialspec materialspec) {
                String localtype = materialspec.getLocaltype();
                if ("SCALE_RATIO".equals(localtype)) {
                    ctx.markValidatedAttribute(materialspec, "localtype");
                    ctx.markValidatedContent(materialspec);

                    validateContent(materialspec);
                }
            }
        }
    }

    private void validateContent(Materialspec materialspec) {
    	
        List<Serializable> content = materialspec.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(materialspec));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(materialspec));
            }
            
            if(str.length()>5) {
            	// ? odhad
            	if(str.charAt(0)=='[' && str.charAt(str.length()-1)==']') {
            		str=str.substring(1, str.length()-2);
            	}
            }
            String parts[] = str.split(":");
            if(parts.length!=2) {
            	throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Hodnota elementu.", ctx.formatEadPosition(materialspec));
            }
            for(var part: parts) {
            	try {
            		Integer.parseInt(part);
            	} catch(NumberFormatException nfe) {
            		throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Měřítko neobsahuje číslo, chybná část: "+part, ctx.formatEadPosition(materialspec));
            	}
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(materialspec));
        }
    }
}
