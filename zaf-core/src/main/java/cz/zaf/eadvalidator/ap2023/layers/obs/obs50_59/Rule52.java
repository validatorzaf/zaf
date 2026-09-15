package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.DescriptionRules;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Unitid;
import java.util.List;

public class Rule52 extends EadRule {

    static final public String CODE = "obs52";
    static final public String RULE_TEXT = "Pokud některý element <unitid> obsažený v elementu <did> má atribut \"localtype\" o hodnotě \"INV_CISLO\", musí mít element <term>, který je obsažen v elementu <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", atribut \"identifier\" o hodnotě \"CZ_ZP1958\".";
    static final public String RULE_ERROR = "Některý element <unitid> má atribut \"localtype\"o hodnotě \"INV_CISLO\" navzdory tomu, že nebyla deklarována stará pravidla.";
    static final public String RULE_SOURCE = "Část 5.4 profilu EAD3 MV ČR";

    /**
     * Deklarovaná pravidla popisu
     *
     * Může být null, pokud nejsou deklarována nebo je deklarována neznámá hodnota.
     * Chybějící deklaraci hlásí obs24 (resp. obs24a).
     */
    private DescriptionRules descriptionRules;

    public Rule52() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        descriptionRules = ctx.getDescriptionRules();

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
                try {
                    String localtype = unitid.getLocaltype();
                    if ("INV_CISLO".equals(localtype)) {
                        if (descriptionRules == null) {
                            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Atribut localtype má hodnotu INV_CISLO, ale nebyla deklarována pravidla popisu v elementu <localcontrol localtype=\"RULES\">, očekávaná hodnota: CZ_ZP1958.", ctx.formatEadPosition(unitid));
                        }
                        if (descriptionRules != DescriptionRules.CZ_ZP1958) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut localtype má hodnotu INV_CISLO, ale deklarovaná pravidla popisu neobsahují očekávanou hodnotu: CZ_ZP1958, ale hodnotu: " + descriptionRules.name() + ".", ctx.formatEadPosition(unitid));
                        }
                    }
                } catch (ZafException e) {
                    // sběr chyby a pokračování v kontrole dalších elementů
                    ctx.addError(e);
                }
            }
        }
    }

}
