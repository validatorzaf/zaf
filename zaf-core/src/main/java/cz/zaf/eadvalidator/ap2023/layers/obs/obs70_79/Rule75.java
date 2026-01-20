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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.math.NumberUtils;

public class Rule75 extends EadRule {

    static final public String CODE = "obs75";
    static final public String RULE_TEXT = "Každý element <dimensions>, který je obsažen v elementu <dimensions>, má atribut \"localtype\" o některé z následujících hodnot:\n"
            + " - WIDTH\n"
            + " - HEIGHT\n"
            + " - DEPTH\n"
            + " a zároveň obsahuje číselnou hodnotu bez mezer a s tečkou pro oddělení desetinných míst a má atribut \"unit\" o hodnotě \"mm\". Element <dimensions> s danou hodnotou atributu \"localtype\" není opakovatelný.";
    static final public String RULE_ERROR = "Element <dimensions>, který je obsažen v elementu <dimensions>, nemá atribut \"localtype\" a/nebo \"unit\". Případně atribut \"localtype\" obsahuje nepovolenou hodnotu nebo atribut \"unit\" neobsahuje hodnotu \"mm\". A/nebo se element <dimensions> s danou hodnotou atributu \"localtype\" opakuje.";
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
        validate(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validate(didC);
        });
    }

    private void validate(Did did) {
        List<Object> childList = did.getMDid();
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                List<Object> physfacetOrDimensions = physdescstructured.getPhysfacetOrDimensions();
                Set<String> existingDimensions = new HashSet<>();
                for (Object pOrD : physfacetOrDimensions) {
                    if (pOrD instanceof Dimensions dimensions) {
                        List<Serializable> contents = dimensions.getContent();
                        for (Object content : contents) {
                            if (content instanceof JAXBElement) {
                                JAXBElement<?> inner = (JAXBElement<?>) content;
                                Object value = inner.getValue();
                                if (value instanceof Dimensions dimensionsDimensions) {
                                    String localtype = validateDimensions(dimensionsDimensions);
                                    if (!existingDimensions.add(localtype)) {
                                        throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen další element dimensions s localtype: " + localtype + ".", ctx.formatEadPosition(dimensions));
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
        chceckContent(dimensions);
        return localtype;
    }

    private void chceckContent(Dimensions dimensions) {
        List<Serializable> content = dimensions.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(dimensions));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isEmpty(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(dimensions));
            }
            if (!NumberUtils.isCreatable(str)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element dimensions obsahuje nepovolenou hodnotu: " + str + ".", ctx.formatEadPosition(dimensions));
            }

        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(dimensions));
        }
    }

    private void duplicityError(String localtype, Dimensions dimensions) {
        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Hodnota atributu localtype: " + localtype + " elementu dimensions uvedena vícekrát.", ctx.formatEadPosition(dimensions));
    }

}
