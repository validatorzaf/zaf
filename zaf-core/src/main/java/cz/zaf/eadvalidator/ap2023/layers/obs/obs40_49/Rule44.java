package cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Odd;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Term;
import java.io.Serializable;
import java.util.List;

public class Rule44 extends EadRule {

    static final public String CODE = "obs44";
    static final public String RULE_TEXT = "Pokud element <archdesc> obsahuje element <odd>, musí mít atribut \"localtype\" o hodnotě \"FINDING_AID_INTRO\", musí se vyskytovat pouze jednou a musí obsahovat právě jeden neprázdný element <p> a zároveň element <term>, který je obsažen v elementu <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", musí obsahovat atribut \"identifier\" o hodnotě \"CZ_ZP1958\".";
    static final public String RULE_ERROR = "Element <odd> nemá atribut \"localtype\" nebo tento atribut \"localtype\" obsahuje nepovolenou hodnotu, případně element <odd> neobsahuje právě jeden element <p>. Nebo se element <odd> vyskytuje, ačkoli element <term>, který je obsažen v elementu <localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", neobsahuje atribut \"identifier\" o hodnotě \"CZ_ZP1958\".";
    static final public String RULE_SOURCE = "Část 3.8 profilu EAD3 MV ČR";

    public Rule44() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        List<Object> accessrestrictOrAccrualsOrAcqinfo = ctx.getEad().getArchdesc().getAccessrestrictOrAccrualsOrAcqinfo();
        Odd found = null;
        for (Object object : accessrestrictOrAccrualsOrAcqinfo) {
            if (object instanceof Odd odd) {
                if (found != null) {
                    throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element odd.", ctx.formatEadPosition(odd));
                }
                found = odd;
                String localtype = odd.getLocaltype();
                if (!StringUtils.equals("FINDING_AID_INTRO", localtype)) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nepovolená hodnota atributu localtype: " + localtype + ".", ctx.formatEadPosition(odd));
                }
                List<Object> chronlistOrListOrTable = odd.getChronlistOrListOrTable();
                P pFound = null;
                for (Object child : chronlistOrListOrTable) {
                    if (child instanceof P p) {
                        if (pFound != null) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element p.", ctx.formatEadPosition(p));
                        }
                        pFound = p;
                        validateContent(p);
                    }
                }
            }
        }
        if (found != null) {
            validateLocalControl();
        }
    }

    private void validateContent(P p) {
        List<Serializable> content = p.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(p));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (!StringUtils.isNotBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(p));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(p));
        }
    }


    private void validateLocalControl() {
        Control control = ctx.getEad().getControl();
        List<Localcontrol> localcontrols = control.getLocalcontrol();
        for (Localcontrol localcontrol : localcontrols) {
            String localtype = localcontrol.getLocaltype();
            if (localtype.equals("RULES")) {
                Term term = localcontrol.getTerm();
                String identifier = term.getIdentifier();
                if (!StringUtils.equals("CZ_ZP1958", identifier)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut identifier obsahuje nepovolenou hodnotu: " + identifier + ".", ctx.formatEadPosition(term));
                }
            }
        }
    }

}
