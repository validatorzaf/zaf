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

public class Rule27 extends EadRule {

    static final public String CODE = "obs27";
    static final public String RULE_TEXT = "Element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", má atribut \"identifier\" o některé z následujících hodnot: \"CZ_ZP1958\", \"CZ_ZP2013\"";
    static final public String RULE_ERROR = "Element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 2.6.1 profilu EAD3 MV ČR";

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

        if (!CollectionUtils.isEmpty(loccontrol)) {
            for (Localcontrol localcontrol : loccontrol) {
                if ("RULES".equals(localcontrol.getLocaltype())) {
                    Term term = localcontrol.getTerm();
                    if (term == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element term.", ctx.formatEadPosition(localcontrol));
                    }
                    String identifier = term.getIdentifier();
                    if (StringUtils.isEmpty(identifier)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybí nebo je prázdný atribut identifier", ctx.formatEadPosition(term));
                    }
                    if (!("CZ_ZP2013".equals(identifier) || "CZ_ZP1958".equals(identifier))) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nesprávnou hodnotu " + identifier, ctx.formatEadPosition(term));
                    }
                }
            }
        }
    }
}
