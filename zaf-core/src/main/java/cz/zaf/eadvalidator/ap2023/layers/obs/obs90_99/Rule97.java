package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Dao;
import cz.zaf.schema.ead3.Descriptivenote;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.P;
import java.util.List;

public class Rule97 extends EadRule {

    static final public String CODE = "obs97";
    static final public String RULE_TEXT = "V elementu <dao> je nejvýš jeden element <descriptivenote>. Ten obsahuje právě jeden neprázdný element <p>.";
    static final public String RULE_ERROR = "V elementu <dao> je víc než jeden element <descriptivenote>. Nebo element <descriptivenote> neobsahuje právě jeden element <p>.";
    static final public String RULE_SOURCE = "Část 7.1 profilu EAD3 MV ČR";

    public Rule97() {
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
                Descriptivenote descriptivenote = dao.getDescriptivenote();
                if (descriptivenote != null) {
                    List<P> pList = descriptivenote.getP();
                    if (pList == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element p.", ctx.formatEadPosition(descriptivenote));
                    }
                    if (pList.size() > 1) {
                        throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element p.", ctx.formatEadPosition(pList));
                    }
                    validateP(pList.get(0));
                }
            }
        }
    }

}
