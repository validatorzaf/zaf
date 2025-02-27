package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Term;
import java.util.List;
import cz.zaf.schemas.ead.EadNS;

public class Rule25 extends EadRule {

    static final public String CODE = "obs25";
    static final public String RULE_TEXT = "Element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"CZ_FINDING_AID_EAD_PROFILE\", má atribut \"identifier\" s hodotou: CZ_EAD3_PROFILE_20240301";
    static final public String RULE_ERROR = "Element <ead:control> neobsahuje právě jeden element <ead:localcontrol> s atributem \"localtype\" s očekávanou hodnotou.";
    static final public String RULE_SOURCE = "Část 2.6.2 profilu EAD3 MV ČR";

    public Rule25() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Localcontrol localControl = getRequiredLocalControl();
        Term term = localControl.getTerm();
        String identifier = term.getIdentifier();

        if (!EadNS.IDENTIFIER_EAD3_PROFILE_20240301.equals(identifier)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nesprávnou hodnotu " + identifier + ".", ctx.formatEadPosition(term));
        }
    }

    private Localcontrol getRequiredLocalControl() {
        Ead ead = ctx.getEad();
        Control control = ead.getControl();

        List<Localcontrol> loccontrol = control.getLocalcontrol();
        for (Localcontrol otherLoccontrol : loccontrol) {
            if (EadNS.LOCALTYPE_FINDING_AID_EAD_PROFILE.equals(otherLoccontrol.getLocaltype())) {
                return otherLoccontrol;
            }
        }
        return null;
    }
}
