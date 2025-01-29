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

public class Rule28 extends EadRule {

    static final public String CODE = "obs28";
    static final public String RULE_TEXT = "Pokud element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", má atribut \"identifier\" o hodnotě \"CZ_ZP2013\", tak element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"FINDING_AID_TYPE\", má atribut \"identifier\" o některé z následujících hodnot: \"MANIP_SEZNAM\", \"INVENTAR\", \"KATALOG\"";
    static final public String RULE_ERROR = "Element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 2.6.1 profilu EAD3 MV ČR";

    public Rule28() {
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
        Term termCondition1 = null;
        Term termCondition2 = null;
        Localcontrol parent = null;
        if (!CollectionUtils.isEmpty(loccontrol)) {
            for (Localcontrol localcontrol : loccontrol) {
                if (EadNS.LOCALTYPE_RULES.equals(localcontrol.getLocaltype())) {
                    Term term = localcontrol.getTerm();
                    String termIdent = term.getIdentifier();
                    if (EadNS.IDENTIFIER_CZ_ZP2013.equals(termIdent)) {
                        termCondition1 = term;
                    }
                }
                if (EadNS.LOCALTYPE_FINDING_AID_TYPE.equals(localcontrol.getLocaltype())) {
                    parent = localcontrol;
                    termCondition2 = localcontrol.getTerm();
                }
            }

            if (termCondition1 != null) {
                if (parent == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element localcontrol.", ctx.formatEadPosition(control));
                }
                if (termCondition2 == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element term.", ctx.formatEadPosition(parent));
                }
                String identifier = termCondition2.getIdentifier();
                if (StringUtils.isEmpty(identifier)) {
                    throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí nebo je prázdný atribut identifier", ctx.formatEadPosition(termCondition2));
                }
                if (!(EadNS.IDENTIFIER_MANIP_SEZNAM.equals(identifier) || EadNS.IDENTIFIER_INVENTAR.equals(identifier) || EadNS.IDENTIFIER_KATALOG.equals(identifier))) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nesprávnou hodnotu " + identifier, ctx.formatEadPosition(termCondition2));
                }
            }
        }
    }
}
