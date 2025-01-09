package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Agencycode;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Maintenanceagency;
import org.apache.commons.lang3.StringUtils;

public class Rule20 extends EadRule {

    static final public String CODE = "obs20";
    static final public String RULE_TEXT = "Element <ead:maintenanceagency> obsahuje element <ead:agencycode>.Ten má atribut \"localtype\" o hodnotě \"CZ_MVCR_INSTITUTION_ID\".";
    static final public String RULE_ERROR = "Element <ead:maintenanceagency> neobsahuje element <ead:agencycode>. Případně tento element nemá atribut \"localtype\" nebo tento atribut neobsahuje hodnotu \"CZ_MVCR_INSTITUTION_ID\".";
    static final public String RULE_SOURCE = "Část 2.5 profilu EAD3 MV ČR";

    public Rule20() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Ead ead = ctx.getEad();
        // must exist / ze schematu
        Maintenanceagency magency = ead.getControl().getMaintenanceagency();
        // ma pouze jeden - hlídá schéma
        Agencycode agencycode = magency.getAgencycode();
        String value = agencycode.getLocaltype();

        if (StringUtils.isEmpty(value)) {
            throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí nebo je prázdný atribut localtype.", ctx.formatEadPosition(agencycode));
        }

        if (!"CZ_MVCR_INSTITUTION_ID".equals(value)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut localtype má chybnou hodnotu: " + value, ctx.formatEadPosition(agencycode));
        }
    }
}
