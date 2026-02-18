package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Term;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Rule24 extends EadRule {

    static final public String CODE = "obs24";
    static final public String RULE_TEXT = "Element <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\" obsahuje element <term>, který má atribut \"identifier\" o některé z následujících hodnot profilu CZ_ZP1958, CZ_ZP2013. A element <localcontrol> s atributem \"localtype\" o hodnotě \"FINDING_AID_TYPE\" obsahuje element <term>, který má atribut \"identifier\" s hodnotou odpovídající profilu (PROZ_INV_SEZNAM, MANIP_SEZNAM, INVENTAR, KATALOG).";
    static final public String RULE_ERROR = "Element <term>, který je obsažen v elementu <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu. Nebo element <term>, který je obsažen v elementu <localcontrol> s atributem \"localtype\" o hodnotě \"FINDING_AID_TYPE\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 2.6, 2.6.1 profilu EAD3 MV ČR";

    public Rule24() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Control control = ctx.getEad().getControl();
        List<Localcontrol> localcontrols = control.getLocalcontrol();
        String profile = "", type = "";
        for (Localcontrol localcontrol : localcontrols) {
            String localtype = localcontrol.getLocaltype();
            if (StringUtils.equals("RULES", localtype)) {
                Term term = localcontrol.getTerm();
                String identifier = term.getIdentifier();
                if (!(StringUtils.equals("CZ_ZP1958", identifier) || StringUtils.equals("CZ_ZP2013", identifier))) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nepovolenou hodnotu: " + identifier + ".", ctx.formatEadPosition(term));
                }
                profile = identifier;
            }
            if (StringUtils.equals("FINDING_AID_TYPE", localtype)) {
                Term term = localcontrol.getTerm();
                String identifier = term.getIdentifier();
                if (!(StringUtils.equals("PROZ_INV_SEZNAM", identifier) || StringUtils.equals("MANIP_SEZNAM", identifier) || StringUtils.equals("INVENTAR", identifier) || StringUtils.equals("KATALOG", identifier))) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nepovolenou hodnotu: " + identifier + ".", ctx.formatEadPosition(term));
                }
                type = identifier;
            }
        }

        if (type.equals("PROZ_INV_SEZNAM") && !profile.equals("CZ_ZP1958")) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Hodnota: " + type + " neodpovídá profilu: " + profile + ".", ctx.formatEadPosition(control));
        }
    }

}
