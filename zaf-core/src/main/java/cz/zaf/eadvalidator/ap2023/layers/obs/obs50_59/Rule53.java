package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Unittitle;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Rule53 extends EadRule {

    static final public String CODE = "obs53";
    static final public String RULE_TEXT = "Každý element <unittitle>, který nemá atribut \"localtype\", obsažený v elementu <did> obsahuje prostou textovou hodnotu.";
    static final public String RULE_ERROR = "Některý element <unittile>, který nemá atribut \"localtype\", neobsahuje prostou textovou hodnotu.";
    static final public String RULE_SOURCE = "Část 5.6 a 5.7 profilu EAD3 MV ČR";

    public Rule53() {
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
        List<Object> mDid = did.getMDid();
        for (Object obj : mDid) {
            if (obj instanceof Unittitle unittitle) {
                String localtype = unittitle.getLocaltype();
                if (localtype == null) {
                    validateContent(unittitle);
                }
            }
        }
    }

    private void validateContent(Unittitle unittitle) {
        List<Serializable> content = unittitle.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(unittitle));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(unittitle));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(unittitle));
        }
    }

}
