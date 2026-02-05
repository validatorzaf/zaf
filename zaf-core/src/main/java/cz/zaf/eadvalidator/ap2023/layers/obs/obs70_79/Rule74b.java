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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule74b extends EadRule {

    static final public String CODE = "obs74b";
    static final public String RULE_TEXT = """
    Každý element <physdescstructured> s atributem "physdescstructuredtype" o hodnotě "materialtype" a zároveň atributem "coverage" o hodnotě "whole", 
    může obsahovat element <physfacet> s atributem "localtype" o některé z následujících hodnot:
    - TECHNIQUE
    - TECHNIQUE_DETAIL
    - MEDIUM_ADJUSTMENT
    - MEDIUM
    - MATERIAL
    - WRITTING.
    Element <physfacet> obsahuje pouze prostý text a s vybranou hodnotou atributu se může vyskytovat nejvýše jednou.
    """;
    static final public String RULE_ERROR = "Některý element <physfacet>, který je obsažen v elementu <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", nemá atribut \"localtype\", obsahuje nepovolenou hodnotu anebo má nepovolený výskyt.";
    static final public String RULE_SOURCE = "Část 6.12.1 a 6.15 profilu EAD3 MV ČR";
    private final Set<String> allowedPhysfacet = Set.of("TECHNIQUE", "TECHNIQUE_DETAIL", "MEDIUM_ADJUSTMENT", "MEDIUM", "MATERIAL", "WRITTING");

    public Rule74b() {
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
                boolean isWantedPhysdescstructured = isWantedPhysdescstructured(physdescstructured);
                if (isWantedPhysdescstructured) {
                    Set<String> founded = new HashSet<>();
                    List<Object> physfacetOrDimensions = physdescstructured.getPhysfacetOrDimensions();
                    for (Object object : physfacetOrDimensions) {
                        if (object instanceof Physfacet physfacet) {
                            boolean isWanted = isWantedPhysfacet(physfacet);
                            if (isWanted) {
                                String localtype = physfacet.getLocaltype();
                                if (!founded.add(localtype)) {
                                    throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physfacet.", ctx.formatEadPosition(physfacet));
                                }
                                chceckContent(physfacet);
                            }
                        }
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
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(physfacet));
            }

        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(physfacet));
        }
    }

    private boolean isWantedPhysfacet(Physfacet physfacet) {
        String localtype = physfacet.getLocaltype();
        if (!StringUtils.isEmpty(localtype)) {
            if (allowedPhysfacet.contains(localtype)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWantedPhysdescstructured(Physdescstructured physdescstructured) {
        String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
        String coverage = physdescstructured.getCoverage();
        boolean isAtributes = StringUtils.equals("materialtype", physdescstructuredtype) && StringUtils.equals("whole", coverage);
        return isAtributes;
    }

}
