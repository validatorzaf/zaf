package cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Relatedmaterial;
import java.io.Serializable;
import java.util.List;

public class Rule68 extends EadRule {

    static final public String CODE = "obs68";
    static final public String RULE_TEXT = "Každý element <ead:relatedmaterial> obsahuje právě jeden neprázdný element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:relatedmaterial> neobsahuje právě jeden element <ead:p>. Případně je element <ead:p> prázdný.";
    static final public String RULE_SOURCE = "Část 5.19 profilu EAD3 MV ČR";

    public Rule68() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, relatedmaterial
        //ead:archdesc
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> archDescChildList = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        for (Object archDescChild : archDescChildList) {
            if (archDescChild instanceof Relatedmaterial) {
                validateMainElement(archDescChild);
            }
        }

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> cChildList = c.getMDescBase();
            for (Object cChild : cChildList) {
                if (cChild instanceof Relatedmaterial) {
                    validateMainElement(cChild);
                }
            }
        });
    }

    private void validateMainElement(Object instanceOfObject) {
        Relatedmaterial elementObject = (Relatedmaterial) instanceOfObject;
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
            if (child instanceof Relatedmaterial) {
                validateMainElement(child);
            }
        }
        if (p == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element <ead:relatedmaterial> neobsahuje element <ead:p>.", ctx.formatEadPosition(elementObject));
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
