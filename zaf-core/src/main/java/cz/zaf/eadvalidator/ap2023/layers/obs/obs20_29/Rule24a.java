package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Term;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Rule24a extends EadRule {

    static final public String CODE = "obs24a";
    static final public String RULE_TEXT = "Element <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\" obsahuje element <term>, který má atribut \"identifier\" o některé z následujících hodnot profilu CZ_ZP1958, CZ_ZP2013.";
    static final public String RULE_ERROR = "Element <term>, který je obsažen v elementu <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 2.6, 2.6.1 profilu EAD3 MV ČR";

    public Rule24a() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Control control = ctx.getEad().getControl();
        List<Localcontrol> localcontrols = control.getLocalcontrol();
        String rules = null;
        for (Localcontrol localcontrol : localcontrols) {
            String localtype = localcontrol.getLocaltype();
            Term term = localcontrol.getTerm();
            if (StringUtils.equals("RULES", localtype)) {
                if(term == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element term.", ctx.formatEadPosition(localcontrol));
                }
                String identifier = term.getIdentifier();
                if (!("CZ_ZP1958".equals(identifier) || "CZ_ZP2013".equals(identifier))) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nepovolenou hodnotu: " + identifier + ".", ctx.formatEadPosition(term));
                }
                rules = identifier;
                ctx.markValidatedAttribute(localcontrol, "localtype");
                ctx.markValidatedAttribute(term, "identifier");
            }
        }

        if(rules==null) {
        	throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element obsahující informace o pravidlech.", ctx.formatEadPosition(control));
        }
    }

}
