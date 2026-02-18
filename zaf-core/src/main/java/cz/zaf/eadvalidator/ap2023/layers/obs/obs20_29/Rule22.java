package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Agencyname;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Maintenanceagency;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class Rule22 extends EadRule {

    static final public String CODE = "obs22";
    static final public String RULE_TEXT = "Element <maintenanceagency> obsahuje právě jeden element <agencyname>. Ten má neprázdnou hodnotu.";
    static final public String RULE_ERROR = "Element <maintenanceagency> neobsahuje právě jeden element <agencyname>. Případně je tento element prázdný.";
    static final public String RULE_SOURCE = "Část 2.5 profilu EAD3 MV ČR, EAD TLV heslo <agencyname>";

    public Rule22() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Ead ead = ctx.getEad();
        // must exist / ze schematu
        Maintenanceagency magency = ead.getControl().getMaintenanceagency();
        List<Agencyname> agnames = magency.getAgencyname();

        if (CollectionUtils.isEmpty(agnames)) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element agencyname.", ctx.formatEadPosition(magency));
        }

        for (Agencyname otherAgencyName : agnames) {
            String value = otherAgencyName.getContent();
            if (StringUtils.isEmpty(value)) {
                throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element agencyname má prázdnou hodnotu.", ctx.formatEadPosition(otherAgencyName));
            }
        }
    }
}
