package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Title;
import cz.zaf.schema.ead3.Unittitle;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Rule54 extends EadRule {

    static final public String CODE = "obs54";
    static final public String RULE_TEXT = "Každý element <unittitle> s atributem \"localtype\" o hodnotě \"FORMAL_TITLE\", obsahuje prostou textovou hodnotu nebo element <title> s elementem <part> s prostou textovou hodnotou.";
    static final public String RULE_ERROR = "Některý element <title>, který je obsažen v elementu <unittitle> s atributem \"localtype\" o hodnotě \"FORMAL_TITLE\", neobsahuje <part> s prostou textovou hodnotu.";
    static final public String RULE_SOURCE = "Část 5.6 a 5.7 profilu EAD3 MV ČR";

    public Rule54() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validate(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validate(didC);
        });
    }

    private void validate(Did did) {
        List<Object> mDid = did.getMDid();
        for (Object obj : mDid) {
            if (obj instanceof Unittitle unittitle) {
                String localtype = unittitle.getLocaltype();
                if (StringUtils.equals("FORMAL_TITLE", localtype)) {
                    boolean firstCondition = validateContent(obj, false, unittitle.getContent());
                    if (!firstCondition) {
                        Part found = null;
                        List<Serializable> content = unittitle.getContent();
                        for (Object cont : content) {
                            if (cont instanceof JAXBElement) {
                                JAXBElement<?> inner = (JAXBElement<?>) cont;
                                Object value = inner.getValue();
                                if (value instanceof Title title) {
                                    List<Part> parts = title.getPart();
                                    for (Part part : parts) {
                                        if (found != null) {
                                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element part.", ctx.formatEadPosition(part));
                                        }
                                        List<Serializable> contentPart = part.getContent();
                                        validateContent(part, true, contentPart);
                                        found = part;
                                    }
                                }
                            }
                        }
                        if (found == null) {
                            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Element unittitle neobsahuje textovou hodnotu ani element title s elementem part.", ctx.formatEadPosition(unittitle));
                        }
                    }
                }
            }
        }
    }

    private boolean validateContent(Object object, boolean doException, List<Serializable> content) {
        if (content.size() != 1) {
            if (doException) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(object));
            }
            return false;
        }

        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isBlank(str)) {
                if (doException) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(object));
                }
                return false;
            }
        } else {
            if (doException) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(object));
            }
            return false;
        }
        return true;
    }

}
