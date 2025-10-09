package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Dimensions;
import cz.zaf.schema.ead3.Physdescstructured;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Rule75 extends EadRule {

    static final public String CODE = "obs75";
    static final public String RULE_TEXT = "Každý element <ead:dimensions>, který je obsažen v elementu <ead:dimensions>, má atribut \"unit\" o hodnotě \"mm\" a zároveň atribut \"localtype\" o některé z následujících hodnot: - WIDTH - HEIGHT - DEPTH";
    static final public String RULE_ERROR = "Element <ead:dimensions>, který je obsažen v elementu <ead:dimensions>, nemá atribut \"localtype\" a/nebo \"unit\". Případně atribut \"localtype\" obsahuje nepovolenou hodnotu nebo atribut \"unit\" neobsahuje hodnotu \"mm\".";
    static final public String RULE_SOURCE = "Část 6.7.2 profilu EAD3 MV ČR";
    private final Set<String> allowed = Set.of("WIDTH", "HEIGHT", "DEPTH");

    public Rule75() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //did, physdescstructured
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        List<Object> childListA = didA.getMDid();
        validate(childListA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            List<Object> childListC = didC.getMDid();
            validate(childListC);
        });

    }

    private void validate(List<Object> childList) {
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                List<Object> physfacetOrDimensions = physdescstructured.getPhysfacetOrDimensions();
                for (Object pOrD : physfacetOrDimensions) {
                    if (pOrD instanceof Dimensions dimensions) {
                        List<Serializable> contents = dimensions.getContent();
                        boolean isW = false;
                        boolean isH = false;
                        boolean isD = false;
                        for (Object content : contents) {
                            if (content instanceof JAXBElement) {
                                JAXBElement<?> inner = (JAXBElement<?>) content;
                                Object value = inner.getValue();
                                if (value instanceof Dimensions dimensionsDimensions) {
                                    String localtype = validateDimensions(dimensionsDimensions);
                                    switch (localtype) {
                                        case "WIDTH" -> {
                                            if (isW) {
                                                duplicityError(localtype, dimensions);
                                            } else {
                                                isW = true;
                                            }
                                        }
                                        case "HEIGHT" -> {
                                            if (isH) {
                                                duplicityError(localtype, dimensions);
                                            } else {
                                                isH = true;
                                            }
                                        }
                                        case "DEPTH" -> {
                                            if (isD) {
                                                duplicityError(localtype, dimensions);
                                            } else {
                                                isD = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private String validateDimensions(Dimensions dimensions) {
        String unit = dimensions.getUnit();
        String localtype = dimensions.getLocaltype();
        if (!StringUtils.equals("mm", unit)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu unit: " + unit + ".", ctx.formatEadPosition(dimensions));
        }
        if (!allowed.contains(localtype)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu localtype: " + localtype + ".", ctx.formatEadPosition(dimensions));
        }
        return localtype;
    }

    private void duplicityError(String localtype, Dimensions dimensions) {
        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Hodnota atributu localtype: " + localtype + " elementu dimensions uvedena vícekrát.", ctx.formatEadPosition(dimensions));
    }

}
