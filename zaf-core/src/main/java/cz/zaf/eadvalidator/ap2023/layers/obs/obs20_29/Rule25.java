package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Localcontrol;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import cz.zaf.schemas.ead.EadNS;

public class Rule25 extends EadRule {

    static final public String CODE = "obs25";
    static final public String RULE_TEXT = "Element <ead:control> obsahuje právě jeden element <ead:localcontrol>, který má atribut \"localtype\" o hodnotě \"CZ_FINDING_AID_EAD_PROFILE\".";
    static final public String RULE_ERROR = "Element <ead:control> neobsahuje právě jeden element <ead:localcontrol> s atributem \"localtype\" o hodnotě \"CZ_FINDING_AID_EAD_PROFILE\".";
    static final public String RULE_SOURCE = "Část 2.6.2 profilu EAD3 MV ČR";

    public Rule25() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Ead ead = ctx.getEad();
        // must exist / ze schematu
        Control control = ead.getControl();

        List<Localcontrol> loccontrol = control.getLocalcontrol();
        if (CollectionUtils.isEmpty(loccontrol)) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element localcontrol.", ctx.formatEadPosition(control));
        }

        Localcontrol found = null;
        if (!CollectionUtils.isEmpty(loccontrol)) {
            for (Localcontrol otherLoccontrol : loccontrol) {
                if (EadNS.LOCALTYPE_FINDING_AID_EAD_PROFILE.equals(otherLoccontrol.getLocaltype())) {
                    if (found != null) {
                        throw new ZafException(BaseCode.DUPLICITA, "Hodnota uvedena vícekrát", ctx.formatEadPosition(otherLoccontrol));
                    }
                    found = otherLoccontrol;
                }
            }
        }
        if (found == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Hodnota nenalezena", ctx.formatEadPosition(control));
        }
    }
}
