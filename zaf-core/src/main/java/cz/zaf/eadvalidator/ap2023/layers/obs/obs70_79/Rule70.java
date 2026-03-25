package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Didnote;

import java.io.Serializable;
import java.util.List;

public class Rule70 extends EadRule {

    static final public String CODE = "obs70";
    static final public String RULE_TEXT = "Každý element <didnote> s atributem \"localtype\" o hodnotě \"INTERNAL\" má atribut \"audience\" o hodnotě \"internal\", jinak má atribut hodnotu \"PUBLIC\".";
    static final public String RULE_ERROR = "Element <didnote> s atributem \"localtype\" o hodnotě \"INTERNAL\" nemá atribut \"audience\" s hodnotou \"internal\", ani localtype nemá hodnotu \"PUBLIC\".";
    static final public String RULE_SOURCE = "Část 5.21, 5.22 profilu EAD3 MV ČR";

    public Rule70() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:archdesc, c
        //archdesc

        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        List<Object> childListA = didA.getMDid();
        validate(childListA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            List<Object> childListC = didC.getMDid();
            validate(childListC);
        });
    }

    private void validate(List<Object> childList) {
        for (Object child : childList) {
            if (child instanceof Didnote didNote) {
                String atrLocalType = didNote.getLocaltype();
                String atrAudience = didNote.getAudience();
				if ("INTERNAL".equals(atrLocalType)) {
					if (atrAudience==null) {
						throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Prázdná hodnota atributu audience.",
								ctx.formatEadPosition(didNote));
					}
					if (!"internal".equals(atrAudience)) {
						throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
								"Chybná hodnota atributu audience: " + atrAudience + ".",
								ctx.formatEadPosition(didNote));
					}
					ctx.markValidatedAttributeOnly(didNote, "audience");
				} else 
				if("PUBLIC".equals(atrLocalType)) {
					if (atrAudience!=null) {
						throw new ZafException(BaseCode.CHYBNY_ATRIBUT, "Neočekávaný atribut audience.",
								ctx.formatEadPosition(didNote));
					}					
				}
				// check content of didnote
				List<Serializable> content = didNote.getContent();
				if(CollectionUtils.isEmpty(content)||content.size()!=1) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
							"Chybná hodnota elementu didnote.",
							ctx.formatEadPosition(didNote));					
				}
				var cnt = content.get(0);
				if(cnt instanceof String) {
					// ok - nop
				} else {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
							"Chybná hodnota elementu didnote. Typ: " + cnt,
							ctx.formatEadPosition(didNote));					
				}
				
				ctx.markValidatedAttribute(didNote, "localtype");
				ctx.markValidatedContent(didNote);
            }
        }
    }

}
