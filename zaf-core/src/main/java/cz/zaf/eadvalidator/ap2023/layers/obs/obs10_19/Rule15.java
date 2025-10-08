package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Publicationstmt;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import cz.zaf.schemas.ead.EadNS;

public class Rule15 extends EadRule {

    static final public String CODE = "obs15";
    static final public String RULE_TEXT = "Element <ead:publicationstmt> obsahuje alespoň jeden takový element <ead:p>, který obsahuje právě jeden element <ead:name> s atributem \"localtype\" o hodnotě \"ARRANGER\".";
    static final public String RULE_ERROR = "Struktura elementu <ead:publicationstmt> neobsahuje právě jeden element <ead:name> s atributem \"localtype\" o hodnotě \"ARRANGER\" vnořený do elementu <ead:p>.";
    static final public String RULE_SOURCE = "Část 4.1.7 profilu EAD3 MV ČR";

    public Rule15() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Ead ead = ctx.getEad();
        // must exist / ze schematu
        Filedesc fileDesc = ead.getControl().getFiledesc();
        Publicationstmt publStmt = fileDesc.getPublicationstmt();
        if (publStmt == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element ead:publicationstmt.", ctx.formatEadPosition(fileDesc));
        }

        List<Object> pdas = publStmt.getPublisherOrDateOrAddress();
        if (CollectionUtils.isEmpty(pdas)) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element ead:publicationstmt neobsahuje žádné elementy.", ctx.formatEadPosition(publStmt));
        }
        //hledám elementy P
        boolean foundP = false;
        for (Object pda : pdas) {
            if (pda instanceof P p) {
                List<Serializable> pConts = p.getContent();
                Name foundName = null;
                for (Object pCont : pConts) {
                    // unpack jaxb
                    if (pCont instanceof JAXBElement<?>) {
                        JAXBElement<?> jaxbElem = (JAXBElement<?>) pCont;
                        Object obj = jaxbElem.getValue();
                        //hleadám elementy Name
                        if (obj instanceof Name name) {
                            if (foundName != null) {
                                throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu ead:name.", ctx.formatEadPosition(name));
                            }
                            foundName = name;
                            if (isWantedElementP(name)) {
                                foundP = true;
                            }
                        }
                    }
                }
                if (foundName == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element name.", ctx.formatEadPosition(p));
                }
            }
        }
        if (!foundP) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi hledaný element p.", ctx.formatEadPosition(publStmt));
        }
    }

    private boolean isWantedElementP(Name name) {
        return EadNS.LOCALTYPE_ARRANGER.equals(name.getLocaltype());
    }

}
