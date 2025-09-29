package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Processinfo;
import java.io.Serializable;
import java.util.List;

public class Rule72 extends EadRule {

    static final public String CODE = "obs72";
    static final public String RULE_TEXT = "Každý element <ead:processinfo> obsahuje právě jeden element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:processinfo> neobsahuje právě jeden <ead:p>.";
    static final public String RULE_SOURCE = "Část 5.23, 5.24 a 5.25 profilu EAD3 MV ČR";

    public Rule72() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, processinfo
        //ead:archdesc
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> archDescChildList = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(archDescChildList);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> cChildList = c.getMDescBase();
            validate(cChildList);
        });
    }

    private void validate(List<Object> childList) {
        for (Object child : childList) {
            if (child instanceof Processinfo mainElement) {
                List<Object> cHistChilds = mainElement.getChronlistOrListOrTable();
                P p = null;
                for (Object cHistChild : cHistChilds) {
                    if (cHistChild instanceof P) {
                        if (p == null) {
                            p = validateP(cHistChild);
                        } else {
                            throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu.", ctx.formatEadPosition(p));
                        }
                    }
                }
                if (p == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element <ead:p>.", ctx.formatEadPosition(mainElement));
                }
            }
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
        if (partContent instanceof String str) {
            if (StringUtils.isEmpty(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(p));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(p));
        }
        return p;
    }
}
