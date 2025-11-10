package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Origination;
import java.util.List;

public class Rule98 extends EadRule {

    static final public String CODE = "obs98";
    static final public String RULE_TEXT = "Pokud element <ead:origination> má atribut \"altrender\", má hodnotu \"inherited\".";
    static final public String RULE_ERROR = "Některý element <ead:origination> má atribut \"altrender\" neobsahující hodnotu \"inherited\".";
    static final public String RULE_SOURCE = "Část 8.1 profilu EAD3 MV ČR";

    public Rule98() {
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
        List<Object> didChildren = did.getMDid();
        for (Object didChild : didChildren) {
            if (didChild instanceof Origination origination) {
                String altrender = origination.getAltrender();
                if (altrender != null) {
                    if (StringUtils.isBlank(altrender)) {
                        throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Atribut altrender není vyplněn.", ctx.formatEadPosition(origination));
                    }
                    if (!"inherited".equals(altrender)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut altrender obsahuje nepovolenou hodnotu: " + altrender + ".", ctx.formatEadPosition(origination));
                    }
                }
            }
        }
    }

}
