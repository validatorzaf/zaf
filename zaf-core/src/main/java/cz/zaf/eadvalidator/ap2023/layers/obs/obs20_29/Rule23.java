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

public class Rule23 extends EadRule {

    static final public String CODE = "obs23";
    static final public String RULE_TEXT = "Element <ead:control> obsahuje právě tři elementy <ead:localcontrol>, které mají v atributu \"localtype\" hodnotou \"FINDING_AID_TYPE\", \"RULES\", \"CZ_FINDING_AID_EAD_PROFILE\" a každý obsahuje právě jeden element <ead:term>.";
    static final public String RULE_ERROR = "Element <ead:control> neobsahuje element <ead:localcontrol> s atributem \"localtype\" s očekávanou hodnotou a elementem <ead:term>.";
    static final public String RULE_SOURCE = "Část 2.6, 2.6.1, 2.6.2 profilu EAD3 MV ČR. EAD TLV heslo <term>";

    public Rule23() {
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

        Localcontrol foundLocCon_FINDING_AID_TYPE = null;
        Localcontrol foundLocCon_RULES = null;
        Localcontrol foundLocCon_CZ_FINDING_AID_EAD_PROFILE = null;

        for (Localcontrol otherLoccontrol : loccontrol) {
            if (EadNS.LOCALTYPE_FINDING_AID_TYPE.equals(otherLoccontrol.getLocaltype())) {
                if (foundLocCon_FINDING_AID_TYPE != null) {
                    throw new ZafException(BaseCode.DUPLICITA, "Duplicitní element <ead:localcontrol> s hodnotou FINDING_AID_TYPE.", ctx.formatEadPosition(otherLoccontrol));
                }
                foundLocCon_FINDING_AID_TYPE = otherLoccontrol;
                validateTerm(otherLoccontrol);
            }

            if (EadNS.LOCALTYPE_RULES.equals(otherLoccontrol.getLocaltype())) {
                if (foundLocCon_RULES != null) {
                    throw new ZafException(BaseCode.DUPLICITA, "Duplicitní element <ead:localcontrol> s hodnotou RULES.", ctx.formatEadPosition(otherLoccontrol));
                }
                foundLocCon_RULES = otherLoccontrol;
                validateTerm(otherLoccontrol);
            }

            if (EadNS.LOCALTYPE_FINDING_AID_EAD_PROFILE.equals(otherLoccontrol.getLocaltype())) {
                if (foundLocCon_CZ_FINDING_AID_EAD_PROFILE != null) {
                    throw new ZafException(BaseCode.DUPLICITA, "Duplicitní element <ead:localcontrol> s hodnotou CZ_FINDING_AID_EAD_PROFILE.", ctx.formatEadPosition(otherLoccontrol));
                }
                foundLocCon_CZ_FINDING_AID_EAD_PROFILE = otherLoccontrol;
                validateTerm(otherLoccontrol);
            }
        }

        if (foundLocCon_FINDING_AID_TYPE == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element <ead:localcontrol> s hodnotou FINDING_AID_TYPE.", ctx.formatEadPosition(control));
        }
        if (foundLocCon_RULES == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element <ead:localcontrol> s hodnotou RULES.", ctx.formatEadPosition(control));
        }
        if (foundLocCon_CZ_FINDING_AID_EAD_PROFILE == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element <ead:localcontrol> s hodnotou CZ_FINDING_AID_EAD_PROFILE.", ctx.formatEadPosition(control));
        }

    }

    private void validateTerm(Localcontrol localControl) {
        Term term = localControl.getTerm();
        if (term == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element <ead:localcontrol> neobsahuje element <ead:term>.", ctx.formatEadPosition(localControl));
        }
    }
}
