package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Dao;
import cz.zaf.schema.ead3.Did;
import java.util.List;

public class Rule95 extends EadRule {

    static final public String CODE = "obs95";
    static final public String RULE_TEXT = "Každý element <dao> má atribut \"daotype\" o hodnotě \"derived\" nebo \"borndigital\".";
    static final public String RULE_ERROR = "Některý element <dao> nemá atribut \"daotype\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 7 profilu EAD3 MV ČR";

    public Rule95() {
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
                String daotype = dao.getDaotype();
                if (!(StringUtils.equals("derived", daotype) || StringUtils.equals("borndigital", daotype))) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nepovolená hodnota atributu daotype.", ctx.formatEadPosition(dao));
                }
            }
        }
    }

}
