package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Dao;
import cz.zaf.schema.ead3.Did;
import java.util.List;

public class Rule94 extends EadRule {

    static final public String CODE = "obs94";
    static final public String RULE_TEXT = "V elementu <ead:did>, který je přímo obsažen v elementu <ead:archdesc>, není přímo obsažen žádný element <ead:dao>.";
    static final public String RULE_ERROR = "V elementu <ead:did>, který je přímo obsažen v elementu <ead:archdesc>, je přímo obsažen element <ead:dao>.";
    static final public String RULE_SOURCE = "Část 7 profilu EAD3 MV ČR";

    public Rule94() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        List<Object> didAChildren = didA.getMDid();
        for (Object didAChild : didAChildren) {
            if (didAChild instanceof Dao dao) {
                throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nenalezen nepovolený element <ead:dao>.", ctx.formatEadPosition(dao));
            }
        }
    }

}
