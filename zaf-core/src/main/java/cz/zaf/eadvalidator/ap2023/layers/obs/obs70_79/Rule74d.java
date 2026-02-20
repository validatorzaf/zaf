package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import cz.zaf.schema.ead3.Physfacet;
import java.io.Serializable;
import java.util.List;

public class Rule74d extends EadRule {

    static final public String CODE = "obs74d";
    static final public String RULE_TEXT = """
    Každý element <physdescstructured> s atributem "physdescstructuredtype" o hodnotě "materialtype" a zároveň atributem "coverage" o hodnotě "whole", 
    může obsahovat element <physfacet> s atributem "localtype" s hodnotou EXTRA_PART a hodnotou elementu "ANALOG" nebo "DIGITAL".
    Element <physfacet> obsahuje pouze prostý text a s vybranou hodnotou atributu se může vyskytovat nejvýše jednou.
    """;
    static final public String RULE_ERROR = "Některý element <physfacet>, který je obsažen v elementu <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", nemá atribut \"localtype\", obsahuje nepovolenou hodnotu anebo má nepovolený výskyt.";
    static final public String RULE_SOURCE = "Část 6.2.1 profilu EAD3 MV ČR";

    public Rule74d() {
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
        List<Object> childList = did.getMDid();
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                if (isPhysdescstructured(physdescstructured)) {
                    checkPhysfacet(physdescstructured);
                }
            }
        }
    }

    private boolean isPhysdescstructured(Physdescstructured physdescstructured) {
        String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
        String coverage = physdescstructured.getCoverage();
        boolean isAtributes = StringUtils.equals("materialtype", physdescstructuredtype) && StringUtils.equals("whole", coverage);
        return isAtributes;
    }

    private void checkPhysfacet(Physdescstructured physdescstructured) {
        List<Object> physfacetOrDimensions = physdescstructured.getPhysfacetOrDimensions();
        Physfacet found = null;
        for (Object object : physfacetOrDimensions) {
            if (object instanceof Physfacet physfacet) {
                String localtype = physfacet.getLocaltype();
                if (!StringUtils.isEmpty(localtype)) {
                    if (localtype.equals("EXTRA_PART")) {
                        if (found != null) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physfacet.", ctx.formatEadPosition(physfacet));
                        }
                        found = physfacet;
                        chceckContent(physfacet);
                    }
                }
            }
        }
    }

    private void chceckContent(Physfacet physfacet) {
        List<Serializable> content = physfacet.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(physfacet));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isEmpty(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(physfacet));
            }
            if (!("ANALOG".equals(str) || "DIGITAL".equals(str))) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element physfacet obsahuje nepovolenou hodnotu: " + str + ".", ctx.formatEadPosition(physfacet));
            }

        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(physfacet));
        }
    }

}
