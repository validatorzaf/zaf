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
import org.apache.commons.lang3.math.NumberUtils;

public class Rule76 extends EadRule {

    static final public String CODE = "obs76";
    static final public String RULE_TEXT = "Každý element <ead:dimensions>, který je obsažen v elementu <ead:dimensions>, obsahuje číselnou hodnotu ve formátu bez mezer a s tečkou pro oddělení desetinných míst.";
    static final public String RULE_ERROR = "Některý element <ead:dimensions>, který je obsažen v elementu <ead:dimensions>, neobsahuje hodnotu v požadovaném formátu.";
    static final public String RULE_SOURCE = "Část 6.7.2 profilu EAD3 MV ČR";

    public Rule76() {
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
                        List<Serializable> content = dimensions.getContent();
                        for (Serializable ser : content) {
                            if (ser instanceof JAXBElement) {
                                JAXBElement<?> inner = (JAXBElement<?>) ser;
                                Object value = inner.getValue();
                                if (value instanceof Dimensions dim) {
                                    validateDimensions(dim);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    

    private void validateDimensions(Dimensions dimensions) {
        List<Serializable> content = dimensions.getContent();
        validateContent(content, dimensions);
    }

    private void validateContent(List<Serializable> content, Dimensions dimensions) {
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(dimensions));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(dimensions));
            }
            if (!NumberUtils.isCreatable(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(dimensions));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(dimensions));
        }
    }

}
