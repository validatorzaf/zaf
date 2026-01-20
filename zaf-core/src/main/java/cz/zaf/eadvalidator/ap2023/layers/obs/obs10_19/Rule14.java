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
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Persname;
import cz.zaf.schema.ead3.Publicationstmt;
import cz.zaf.schema.ead3.Ref;
import jakarta.xml.bind.JAXBElement;
import org.apache.commons.collections4.CollectionUtils;

public class Rule14 extends EadRule {

    static final public String CODE = "obs14";
    static final public String RULE_TEXT = "Element <persname>, <famname>, <corpname> nebo <name>, který je obsažen ve struktuře elementu <publicationstmt>, s atributem \"localtype\" o hodnotě \"ORIGINATOR\", obsahuje právě jeden neprázdný element <ref>, který je obsažen v elementu <part>, s atributem \"target\" o hodnotě, která odkazuje na element <control>/<sources>/<source>.";
    static final public String RULE_ERROR = "Element <name>, který je obsažen ve struktuře <publicationstmt>, s atributem \"localtype\" o hodnotě \"ORIGINATOR\" neobsahuje správně vyplněný element <ref>.";
    static final public String RULE_SOURCE = "Část 4.1.6 profilu EAD3 MV ČR";

    public Rule14() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {

        Filedesc filedesc = ctx.getEad().getControl().getFiledesc();
        Publicationstmt publicationstmt = filedesc.getPublicationstmt();
        List<Object> publisherOrDateOrAddress = publicationstmt.getPublisherOrDateOrAddress();
        for (Object pobj : publisherOrDateOrAddress) {
            if (pobj instanceof P p) {
                List<Serializable> content = p.getContent();
                for (Object pCont : content) {
                    if (pCont instanceof JAXBElement<?> jaxbElem) {
                        Object contentObj = jaxbElem.getValue();
                        if (contentObj instanceof Persname persname) {
                            String localtype = persname.getLocaltype();
                            if (StringUtils.equals("ORIGINATOR", localtype)) {
                                validate(persname, persname.getPart());
                            }
                        }
                        if (contentObj instanceof Famname famname) {
                            String localtype = famname.getLocaltype();
                            if (StringUtils.equals("ORIGINATOR", localtype)) {
                                validate(famname, famname.getPart());
                            }
                        }
                        if (contentObj instanceof Corpname corpname) {
                            String localtype = corpname.getLocaltype();
                            if (StringUtils.equals("ORIGINATOR", localtype)) {
                                validate(corpname, corpname.getPart());
                            }
                        }
                        if (contentObj instanceof Name name) {
                            String localtype = name.getLocaltype();
                            if (StringUtils.equals("ORIGINATOR", localtype)) {
                                validate(name, name.getPart());
                            }
                        }
                    }
                }
            }
        }
    }

    private void validate(Object parent, List<Part> listPart) {
        if (CollectionUtils.isEmpty(listPart)) {
            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nenalezen element Part.", ctx.formatEadPosition(parent));
        }
        for (Part part : listPart) {
            List<Serializable> content = part.getContent();
            Ref found = null;
            for (Object pCont : content) {
                if (pCont instanceof JAXBElement<?> jaxbElem) {
                    Object obj = jaxbElem.getValue();
                    if (obj instanceof Ref ref) {
                        if (found != null) {
                            throw new ZafException(BaseCode.DUPLICITA, "Nenalezen nepovolený element ref.", ctx.formatEadPosition(ref));
                        }
                        found = ref;
                        Object target = ref.getTarget();
                        if (target == null) {
                            throw new ZafException(BaseCode.CHYBNY_ATRIBUT, "Chybí odkaz atributu target.", ctx.formatEadPosition(ref));
                        }
                        validateContent(ref);
                    }
                }
            }
            if (found == null) {
                throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element Ref.", ctx.formatEadPosition(parent));
            }
        }
    }

    private void validateContent(Ref ref) {
        List<Serializable> content = ref.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(ref));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (!StringUtils.isNotBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(ref));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(ref));
        }
    }
}
