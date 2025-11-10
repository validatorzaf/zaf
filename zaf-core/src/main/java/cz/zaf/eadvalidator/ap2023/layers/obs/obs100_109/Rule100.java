package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Corpname;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Famname;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.Origination;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Persname;
import cz.zaf.schema.ead3.Ref;
import cz.zaf.schema.ead3.Source;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.List;

public class Rule100 extends EadRule {

    static final public String CODE = "obs100";
    static final public String RULE_TEXT = "Element s atributem \"localtype\" o hodnotě \"ORIGINATOR\", který je obsažen v elementu <ead:origination>, obsahuje právě jeden element <ead:part>, který obsahuje právě jeden element <ead:ref>. Element <ead:ref> má atribut \"target\" o hodnotě, která odkazuje na element <control>/<sources>/<source>.";
    static final public String RULE_ERROR = "Element s atributem \"localtype\" o hodnotě \"ORIGINATOR\", který je obsažen v elementu <ead:origination, nemá správný obsah.";
    static final public String RULE_SOURCE = "Část 8.1 profilu EAD3 MV ČR";

    public Rule100() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validateDid(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validateDid(didC);
        });
    }

    private void validateDid(Did did) {
        List<Object> didChildren = did.getMDid();
        for (Object didChild : didChildren) {
            if (didChild instanceof Origination origination) {
                List<Object> originationChildList = origination.getCorpnameOrFamnameOrName();
                for (Object originationChild : originationChildList) {
                    if (originationChild instanceof Persname persname) {
                        String localtype = persname.getLocaltype();
                        List<Part> partList = persname.getPart();
                        validate(persname, localtype, partList);
                    }
                    if (originationChild instanceof Corpname corpname) {
                        String localtype = corpname.getLocaltype();
                        List<Part> partList = corpname.getPart();
                        validate(corpname, localtype, partList);
                    }
                    if (originationChild instanceof Name name) {
                        String localtype = name.getLocaltype();
                        List<Part> partList = name.getPart();
                        validate(name, localtype, partList);
                    }
                    if (originationChild instanceof Famname famname) {
                        String localtype = famname.getLocaltype();
                        List<Part> partList = famname.getPart();
                        validate(famname, localtype, partList);
                    }
                }
            }
        }
    }

    private void validate(Object parent, String localType, List<Part> partList) {
        if (StringUtils.equals("ORIGINATOR", localType)) {
            if (partList.size() != 1) {
                throw new ZafException(BaseCode.DUPLICITA, "Nalezen duplicitní element part.", ctx.formatEadPosition(parent));
            }
            validatePart(partList);
        }
    }

    private void validatePart(List<Part> partList) {
        Ref found = null;
        for (Part part : partList) {
            List<Serializable> content = part.getContent();
            for (Serializable item : content) {
                if (item instanceof JAXBElement) {
                    JAXBElement<?> inner = (JAXBElement<?>) item;
                    Object value = inner.getValue();
                    if (value instanceof Ref ref) {
                        if (found != null) {
                            throw new ZafException(BaseCode.DUPLICITA, "Nalezen duplicitní element ref.", ctx.formatEadPosition(ref));
                        }
                        found = ref;
                        Object target = ref.getTarget();
                        if (!(target instanceof Source source)) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezena očkávaná hodnota atributu target.", ctx.formatEadPosition(ref));
                        }
                    }
                }
            }
        }
        if (found == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element ref.", ctx.formatEadPosition(partList));
        }
    }

}
