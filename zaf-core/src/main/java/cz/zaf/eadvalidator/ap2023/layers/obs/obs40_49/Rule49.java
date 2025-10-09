package cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Container;
import cz.zaf.schema.ead3.Did;
import java.io.Serializable;
import java.util.List;

public class Rule49 extends EadRule {

    static final public String CODE = "obs49";
    static final public String RULE_TEXT = "Každý element <ead:container> obsahuje prostou textovou hodnotu.";
    static final public String RULE_ERROR = "Některý element <ead:container> neobsahuje prostou textovou hodnotu.";
    static final public String RULE_SOURCE = "Část 5.3 profilu EAD3 MV ČR";

    public Rule49() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validateDid(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validateDid(didC);
        });
    }

    private void validateDid(Did did) {
        List<Object> list = did.getMDid();
        for (Object object : list) {
            if (object instanceof Container container) {
                validateContent(container);
            }
        }
    }

    private void validateContent(Container container) {
        List<Serializable> content = container.getContent();
        if (content.size() != 1) {
        throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(container));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (!StringUtils.isNotBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(container));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(container));
        }
    }

}
