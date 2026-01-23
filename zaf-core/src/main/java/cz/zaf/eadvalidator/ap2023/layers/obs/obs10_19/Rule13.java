package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Publicationstmt;
import jakarta.xml.bind.JAXBElement;

public class Rule13 extends EadRule {

    static final public String CODE = "obs13";
    static final public String RULE_TEXT = "Element <publicationstmt> obsahuje alespoň jeden takový element <p>, který obsahuje právě jeden element <name> s atributem \"localtype\" o hodnotě \"ORIGINATOR\".";
    static final public String RULE_ERROR = "Struktura elementu <publicationstmt> neobsahuje správně vyplněný element <name> s atributem \"localtype\" o hodnotě \"ORIGINATOR\" vnořený do elementu <p>.";
    static final public String RULE_SOURCE = "Část 4.1.6 profilu EAD3 MV ČR";

    public Rule13() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {

        Filedesc filedesc = ctx.getEad().getControl().getFiledesc();
        Publicationstmt publicationstmt = filedesc.getPublicationstmt();
        if (publicationstmt == null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element <publicationstmt>.", ctx.formatEadPosition(filedesc));
		}
        List<Object> publisherOrDateOrAddress = publicationstmt.getPublisherOrDateOrAddress();
        P found = null;
        for (Object obj : publisherOrDateOrAddress) {
            if (obj instanceof P p) {
                if (isWanted(p)) {
                    found = p;
                    break;
                }
            }
        }
        if (found == null) {
            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nenalezen element P.", ctx.formatEadPosition(publicationstmt));
        }
    }

    private boolean isWanted(P p) {
        List<Serializable> content = p.getContent();
        Name found = null;

        for (Object pCont : content) {
            if (pCont instanceof JAXBElement<?> jaxbElem) {
                Object obj = jaxbElem.getValue();
                if (obj instanceof Name name) {
                    String localtype = name.getLocaltype();
                    if (StringUtils.equals("ORIGINATOR", localtype)) {
                        if (found != null) {
                            return false;
                        }
                        found = name;
                    }
                }
            }
        }
        return found != null;
    }
}
