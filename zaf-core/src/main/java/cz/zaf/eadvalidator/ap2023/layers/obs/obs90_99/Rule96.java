package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Dao;
import cz.zaf.schema.ead3.Did;
import java.util.List;

public class Rule96 extends EadRule {

    static final public String CODE = "obs96";
    static final public String RULE_TEXT = "Každý element <ead:dao> má neprázdný atribut \"identifier\".";
    static final public String RULE_ERROR = "Některý element <ead:dao> nemá atribut \"identifier\" nebo je tento atribut prázdný.";
    static final public String RULE_SOURCE = "Část 7.1 profilu EAD3 MV ČR";

    public Rule96() {
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
            if (didChild instanceof Dao dao) {
                String identifier = dao.getIdentifier();
                if(identifier == null){
                    throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut identifier elementu dao.", ctx.formatEadPosition(dao));
                }
                if (StringUtils.isBlank(identifier)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Hodnota atributu identifier elemetu dao není zadána.", ctx.formatEadPosition(dao));
                }
            }
        }
    }

}
