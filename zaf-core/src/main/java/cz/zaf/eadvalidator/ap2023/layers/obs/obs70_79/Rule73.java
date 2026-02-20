package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdesc;
import java.io.Serializable;
import java.util.List;

public class Rule73 extends EadRule {

    static final public String CODE = "obs73";
    static final public String RULE_TEXT = "Každý element <physdesc> obsahuje prostou textovou hodnotu.";
    static final public String RULE_ERROR = "Některý element <physdesc> neobsahuje prostou textovou hodnotu.";
    static final public String RULE_SOURCE = "Část 6.2 profilu EAD3 MV ČR";

    public Rule73() {
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
            if (child instanceof Physdesc physdesc) {
                List<Serializable> physdescContentList = physdesc.getContent();
                if (physdescContentList.size() != 1) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(physdesc));
                }
                Serializable partContent = physdescContentList.get(0);
                if (partContent instanceof String str) {
                    if (!StringUtils.isNotBlank(str)) {
                        throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(physdesc));
                    }
                } else {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(physdesc));
                }
            }
        }
    }

}
