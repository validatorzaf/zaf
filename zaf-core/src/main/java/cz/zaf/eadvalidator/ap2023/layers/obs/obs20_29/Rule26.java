package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Term;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import cz.zaf.schemas.ead.EadNS;

public class Rule26 extends EadRule {

    static final public String CODE = "obs26";
    static final public String RULE_TEXT = "Každý element <ead:localcontrol>, který má atribut \"localtype\" o hodnotě \"FINDING_AID_TYPE\" nebo \"RULES\" nebo \"CZ_FINDING_AID_EAD_PROFILE\", obsahuje právě jeden element <ead:term>.";
    static final public String RULE_ERROR = "Některý z elementů <ead:localcontrol>, který má atribut \"localtype\" o hodnotě \"FINDING_AID_TYPE\" nebo \"RULES\" nebo \"CZ_FINDING_AID_EAD_PROFILE\", neobsahuje právě jeden element <ead:term>.";
    static final public String RULE_SOURCE = "Část 2.6 profilu EAD3 MV ČR, EAD TLV heslo <term>";

    public Rule26() {
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

        if (!CollectionUtils.isEmpty(loccontrol)) {
            for (Localcontrol localcontrol : loccontrol) {
                if (EadNS.LOCALTYPE_FINDING_AID_TYPE.equals(localcontrol.getLocaltype()) || EadNS.LOCALTYPE_RULES.equals(localcontrol.getLocaltype()) || EadNS.LOCALTYPE_FINDING_AID_EAD_PROFILE.equals(localcontrol.getLocaltype())) {
                    Term term = localcontrol.getTerm();
                    if (term == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element term.", ctx.formatEadPosition(localcontrol));
                    }
                }
            }
        }
    }
}
