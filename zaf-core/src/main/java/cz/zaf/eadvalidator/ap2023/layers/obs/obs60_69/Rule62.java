package cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Scopecontent;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

public class Rule62 extends EadRule {

    static final public String CODE = "obs62";
    static final public String RULE_TEXT = "Každý element <ead:scopecontent> obsahuje právě jeden neprázdný element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:scopecontent> neobsahuje právě jeden element <ead:p>. Případně je element <ead:p> prázdný.";
    static final public String RULE_SOURCE = "Část 5.13 profilu EAD3 MV ČR";

    public Rule62() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, scopecontent

        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> archDescChildList = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        for (Object archDescChild : archDescChildList) {
            if (archDescChild instanceof Scopecontent) {
                validateMainElement(archDescChild);
            }
        }

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> cChildList = c.getMDescBase();
            for (Object cChild : cChildList) {
                if (cChild instanceof Scopecontent) {
                    validateMainElement(cChild);
                }
            }
        });
    }

    private void validateMainElement(Object instanceOfObject) {
        Scopecontent elementObject = (Scopecontent) instanceOfObject;
        List<Object> childList = elementObject.getChronlistOrListOrTable();
        P p = null;
        for (Object child : childList) {
            if (child instanceof P) {
                if (p == null) {
                    p = validateP(child);
                } else {
                    throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu.", ctx.formatEadPosition(p));
                }
            }
            //ead:custodhist
            if (child instanceof Scopecontent) {
                validateMainElement(child);
            }
        }
        if (p == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element <ead:scopecontent> neobsahuje element <ead:p>.", ctx.formatEadPosition(elementObject));
        }
    }

    private P validateP(Object instanceOfP) {
        P p = (P) instanceOfP;
        // Kontrola obsahu p
        List<Serializable> pContentList = p.getContent();
        if (pContentList.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(p));
        }
        Serializable partContent = pContentList.get(0);
        if (partContent instanceof String) {
            String str = (String) partContent;
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(p));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(p));
        }
        return p;
    }

}
