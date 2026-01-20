package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Persname;
import cz.zaf.schema.ead3.Publicationstmt;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Rule15 extends EadRule {

    static final public String CODE = "obs15";
    static final public String RULE_TEXT = "Element <publicationstmt> obsahuje alespoň jednoho zpracovatele, tj. jeden element <p>, který obsahuje právě jeden element <persname> s atributem \"localtype\" o hodnotě \"ARRANGER\".";
    static final public String RULE_ERROR = "Struktura elementu <publicationstmt> neobsahuje právě jeden element <persname> s atributem \"localtype\" o hodnotě \"ARRANGER\" vnořený do elementu <p>.";
    static final public String RULE_SOURCE = "Část 4.1.7 profilu EAD3 MV ČR";

    public Rule15() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {

        Filedesc filedesc = ctx.getEad().getControl().getFiledesc();
        Publicationstmt publicationstmt = filedesc.getPublicationstmt();
        List<Object> publisherOrDateOrAddress = publicationstmt.getPublisherOrDateOrAddress();
        Persname found = null;
        for (Object pobj : publisherOrDateOrAddress) {
            if (pobj instanceof P p) {
                List<Serializable> content = p.getContent();
                for (Object pCont : content) {
                    if (pCont instanceof JAXBElement<?> jaxbElem) {
                        Object contentObj = jaxbElem.getValue();
                        if (contentObj instanceof Persname persname) {
                            String localtype = persname.getLocaltype();
                            if (StringUtils.equals("ARRANGER", localtype)) {
                                found = persname;
                            }
                        }
                    }
                }
            }
        }
        if (found == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element se zpracovatelem.", ctx.formatEadPosition(publicationstmt));
        }
    }

}
