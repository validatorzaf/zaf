package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Publicationstmt;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Rule17 extends EadRule {

    static final public String CODE = "obs17";
    static final public String RULE_TEXT = "Element <publicationstmt> obsahuje právě jeden element <p>, který obsahuje právě jeden element <name> s atributem \"localtype\" o hodnotě \"ARRANGER_BRIEF\", obsahuje právě jeden element <part>.";
    static final public String RULE_ERROR = "Struktura elementu <publicationstmt> neobsahuje právě jeden element <name> s atributem \"localtype\" o hodnotě \"ARRANGER_BRIEF\" vnořený do elementu <p>. Případně je element <name> špatně vyplněný.";
    static final public String RULE_SOURCE = "Část 4.1.7 profilu EAD3 MV ČR";

    public Rule17() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {

        Filedesc filedesc = ctx.getEad().getControl().getFiledesc();
        Publicationstmt publicationstmt = filedesc.getPublicationstmt();
        List<Object> publisherOrDateOrAddress = publicationstmt.getPublisherOrDateOrAddress();
        P found = null;
        for (Object pobj : publisherOrDateOrAddress) {
            if (pobj instanceof P p) {
                List<Serializable> content = p.getContent();
                for (Object pCont : content) {
                    if (pCont instanceof JAXBElement<?> jaxbElem) {
                        Object contentObj = jaxbElem.getValue();
                        if (contentObj instanceof Name name) {
                            String localtype = name.getLocaltype();
                            if (StringUtils.equals("ARRANGER_BRIEF", localtype)) {
                                List<Part> listPart = name.getPart();
                                if (listPart.isEmpty() || listPart.size() != 1) {
                                    throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element part.", ctx.formatEadPosition(name));
                                }
                                if (found != null) {
                                    throw new ZafException(BaseCode.DUPLICITA, "Nalezen nepovolený element p.", ctx.formatEadPosition(publicationstmt));
                                }
                                found = p;
                            }
                        }
                    }
                }
            }
        }
        if(found == null){
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen hledaný element p.", ctx.formatEadPosition(publicationstmt));
        }
    }

}
