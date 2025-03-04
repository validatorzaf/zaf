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
import org.apache.commons.lang3.StringUtils;
import cz.zaf.schemas.ead.EadNS;

public class Rule27 extends EadRule {

    static final public String CODE = "obs27";
    static final public String RULE_TEXT = "Element <ead:localcontrol> s atributem \"localtype\" o hodnotě \"RULES\" obsahuje element <ead:term>, který má atribut \"identifier\" o některé z následujících hodnot profilu CZ_ZP1958, CZ_ZP2013. A element <ead:localcontrol> s atributem \"localtype\" o hodnotě \"FINDING_AID_TYPE\" obsahuje element <ead:term>, který má atribut \"identifier\" s hodnotou odpovídající profilu (PROZ_INV_SEZNAM, MANIP_SEZNAM, INVENTAR, KATALOG).";
    static final public String RULE_ERROR = "Element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu. Nebo element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"FINDING_AID_TYPE\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 2.6, 2.6.1 profilu EAD3 MV ČR";

    public Rule27() {
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

        Localcontrol localControl_RULES = null;
        Localcontrol localControl_FINDING_AID_TYPE = null;

        for (Localcontrol localcontrol : loccontrol) {
            if (EadNS.LOCALTYPE_RULES.equals(localcontrol.getLocaltype())) {
                localControl_RULES = localcontrol;
            }
            if (EadNS.LOCALTYPE_FINDING_AID_TYPE.equals(localcontrol.getLocaltype())) {
                localControl_FINDING_AID_TYPE = localcontrol;
            }
        }

        if (localControl_RULES == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element localcontrol s atributem \"localtype\" o hodnotě \"RULES\".", ctx.formatEadPosition(control));
        }

        if (localControl_FINDING_AID_TYPE == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element localcontrol s atributem \"localtype\" o hodnotě \"FINDING_AID_TYPE\".", ctx.formatEadPosition(control));
        }

        String profileValue = getProfileValue(localControl_RULES);
        String identifierValue = getIdentifierValue(localControl_FINDING_AID_TYPE);

        //Povolené hodnoty PROZ_INV_SEZNAM, MANIP_SEZNAM, INVENTAR, KATALOG
        if (!(EadNS.IDENTIFIER_MANIP_SEZNAM.equals(identifierValue) || EadNS.IDENTIFIER_INVENTAR.equals(identifierValue) || EadNS.IDENTIFIER_KATALOG.equals(identifierValue) || EadNS.IDENTIFIER_PROZ_INV_SEZNAM.equals(identifierValue))) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nesprávnou hodnotu " + identifierValue + ".", ctx.formatEadPosition(localControl_FINDING_AID_TYPE.getTerm()));
        }

        //CZ_ZP1958 PROZ_INV_SEZNAM, MANIP_SEZNAM, INVENTAR, KATALOG
        //povolené všechny hodnoty

        //CZ_ZP2013 MANIP_SEZNAM, INVENTAR, KATALOG
        //nepovolená hodnota PROZ_INV_SEZNAM
        if (profileValue.equals(EadNS.IDENTIFIER_CZ_ZP2013)) {
            if ((EadNS.IDENTIFIER_PROZ_INV_SEZNAM.equals(identifierValue))) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nesprávnou hodnotu " + identifierValue + ".", ctx.formatEadPosition(localControl_FINDING_AID_TYPE.getTerm()));
            }
        }

    }

    private String getProfileValue(Localcontrol localControl_RULES) {
        Term term = localControl_RULES.getTerm();

        if (term == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element term.", ctx.formatEadPosition(localControl_RULES));
        }

        String identifier = term.getIdentifier();
        if (StringUtils.isEmpty(identifier)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybí nebo je prázdný atribut identifier", ctx.formatEadPosition(term));
        }

        if (!(EadNS.IDENTIFIER_CZ_ZP1958.equals(identifier) || EadNS.IDENTIFIER_CZ_ZP2013.equals(identifier))) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nesprávnou hodnotu " + identifier, ctx.formatEadPosition(term));
        }

        return identifier;
    }

    private String getIdentifierValue(Localcontrol localControl_FINDING_AID_TYPE) {
        Term term = localControl_FINDING_AID_TYPE.getTerm();

        if (term == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element term.", ctx.formatEadPosition(localControl_FINDING_AID_TYPE));
        }

        String identifier = term.getIdentifier();
        if (StringUtils.isEmpty(identifier)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybí nebo je prázdný atribut identifier", ctx.formatEadPosition(term));
        }

        return identifier;

    }

}
