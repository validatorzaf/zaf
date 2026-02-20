package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Unitid;
import java.util.List;

public class Rule52 extends EadRule {

    static final public String CODE = "obs52";
    static final public String RULE_TEXT = "Pokud některý element <unitid> obsažený v elementu <did> má atribut \"localtype\" o hodnotě \"INV_CISLO\", musí mít element <term>, který je obsažen v elementu <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", atribut \"identifier\" o hodnotě \"CZ_ZP1958\".";
    static final public String RULE_ERROR = "Některý element <unitid> má atribut \"localtype\"o hodnotě \"INV_CISLO\" navzdory tomu, že nebyla deklarována stará pravidla.";
    static final public String RULE_SOURCE = "Část 5.4 profilu EAD3 MV ČR";

    public Rule52() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        List<Object> mDidDidA = didA.getMDid();
        validate(mDidDidA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            List<Object> mDidDidC = didC.getMDid();
            validate(mDidDidC);
        });
    }

    private void validate(List<Object> mDidDid) {
        for (Object object : mDidDid) {
            if (object instanceof Unitid unitid) {
                String localtype = unitid.getLocaltype();
                if (localtype.equals("INV_CISLO")) {
                    String profile = ctx.getDescriptionRules().name();
                    if (!"CZ_ZP1958".equals(profile)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut localtype neobsahuje očekávanou hodnotu: CZ_ZP1958, ale hodnotu: " + profile + ".", ctx.formatEadPosition(unitid));
                    }
                }
            }
        }
    }

}
