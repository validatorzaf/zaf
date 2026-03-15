package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Corpname;
import cz.zaf.schema.ead3.Famname;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Persname;
import cz.zaf.schema.ead3.Publicationstmt;
import jakarta.xml.bind.JAXBElement;

// Pravidlo se uplatní pouze pro pomůcku, vyjadřuje povinnost uvedení elementu.
// Souvisí s pravidlem 14, které vyjadřuje vnitřní strukturu těchto elementů.
public class Rule13 extends EadRule {

    static final public String CODE = "obs13";
    static final public String RULE_TEXT = "Element <publicationstmt> obsahuje alespoň jeden element <p>, který obsahuje jeden z elementů <persname>, <famname>, <corpname> nebo <name> s atributem \"localtype\" o hodnotě \"ORIGINATOR\".";
    static final public String RULE_ERROR = "Struktura elementu <publicationstmt> neobsahuje alespoň jeden z elementů <persname>, <famname>, <corpname> nebo <name> s atributem \"localtype\" o hodnotě \"ORIGINATOR\" vnořený do elementu <p>.";
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

        for (Object obj : publisherOrDateOrAddress) {
            if (obj instanceof P p) {
                if (isWanted(p)) {
                    return;
                }
            }
        }

        throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nenalezen požadovaný element.", ctx.formatEadPosition(publicationstmt));
    }

    private boolean isWanted(P p) {
        List<Serializable> content = p.getContent();

        for (Object pCont : content) {
            if (pCont instanceof JAXBElement<?> jaxbElem) {
                Object obj = jaxbElem.getValue();
                if (obj instanceof Persname persname) {
					String localtype = persname.getLocaltype();
					if (StringUtils.equals("ORIGINATOR", localtype)) {
						return true;
					}
				} else if (obj instanceof Famname famname) {
					String localtype = famname.getLocaltype();
					if (StringUtils.equals("ORIGINATOR", localtype)) {
						return true;
					}
				} else if (obj instanceof Corpname corpname) {
					String localtype = corpname.getLocaltype();
					if (StringUtils.equals("ORIGINATOR", localtype)) {
						return true;
					}
				} else if (obj instanceof Name name) {
                    String localtype = name.getLocaltype();
                    if (StringUtils.equals("ORIGINATOR", localtype)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
