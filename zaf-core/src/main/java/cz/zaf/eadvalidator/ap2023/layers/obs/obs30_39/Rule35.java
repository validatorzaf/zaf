package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;

public class Rule35 extends EadRule {

    static final public String CODE = "obs35";
    static final public String RULE_TEXT = "Element <ead:archdesc> má atribut \"level\" o hodnotě \"fonds\".";
    static final public String RULE_ERROR = "Elementu <ead:archdesc> chybí atribut \"level\" nebo tento atribut neobsahuje hodnotu \"fonds\".";
    static final public String RULE_SOURCE = "Část 3.1 profilu EAD3 MV ČR";

    public Rule35() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archdesc = ctx.getEad().getArchdesc();
        String level = archdesc.getLevel();
        if (!StringUtils.equals("fonds", level)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut level elementu archdesc není vyplněn.", ctx.formatEadPosition(archdesc));
        }
    }

}
