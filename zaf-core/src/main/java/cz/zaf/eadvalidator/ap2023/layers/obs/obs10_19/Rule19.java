package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Maintenanceagency;
import org.apache.commons.lang3.StringUtils;

public class Rule19 extends EadRule {

    static final public String CODE = "obs19";
    static final public String RULE_TEXT = "Element <ead:maintenanceagency> má atribut \"countrycode\" o hodnotě \"CZ\".";
    static final public String RULE_ERROR = "Element <ead:maintenanceagency> nemá atribut \"countrycode\" nebo tento atribut neobsahuje hodnotu \"CZ\".";
    static final public String RULE_SOURCE = "Část 2.5 profilu EAD3 MV ČR";

    public Rule19() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Ead ead = ctx.getEad();
        // must exist / ze schematu
        Maintenanceagency magency = ead.getControl().getMaintenanceagency();

        String value = magency.getCountrycode();
        if (StringUtils.isEmpty(value)) {
            throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí nebo je prázdný atribut countrycode.", ctx.formatEadPosition(magency));
        }

        if (!"CZ".equals(value)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut countrycode má chybnou hodnotu: " + value, ctx.formatEadPosition(magency));
        }
    }
}
