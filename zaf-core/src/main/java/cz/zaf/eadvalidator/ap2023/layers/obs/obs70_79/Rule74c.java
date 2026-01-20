package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import cz.zaf.schema.ead3.Physfacet;
import cz.zaf.schema.ead3.Unittype;
import java.util.List;
import java.util.Set;

public class Rule74c extends EadRule {

    static final public String CODE = "obs74c";
    static final public String RULE_TEXT = "Každý element <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", který obsahuje element <unittype> s některou z následujících hodnot:\n"
            + "- gli\n"
            + "- kre,\n"
            + "současně obsahuje element <physfacet> s atributem \"localtype\" s hodnotou LEGEND.\n"
            + "Element <physfacet> se s vybranou hodnotou atributu může vyskytovat nejvýše jednou.";
    static final public String RULE_ERROR = "Některý element <physfacet>, který je obsažen v elementu <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", nemá atribut \"localtype\", obsahuje nepovolenou hodnotu anebo má nepovolený výskyt.";
    static final public String RULE_SOURCE = "Část 6.17 profilu EAD3 MV ČR";

    private final Set<String> allowedPhysfacet = Set.of("LEGEND");
    private final Set<String> allowedUnitTime = Set.of("gli", "kre");

    public Rule74c() {
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
                    Unittype unittype = physdescstructured.getUnittype();
                    if (unittype != null) {
                        String contentUnitType = unittype.getContent();
                        if (!StringUtils.isEmpty(contentUnitType)) {
                            if (allowedUnitTime.contains(contentUnitType)) {
                                checkPhysfacet(physdescstructured);
                            }
                        }
                    }
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
                    if (allowedPhysfacet.contains(localtype)) {
                        if (found != null) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physfacet.", ctx.formatEadPosition(physfacet));
                        }
                        found = physfacet;
                    }
                }
            }
        }
        if (found == null) {
            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nenalezen element physfacet.", ctx.formatEadPosition(physdescstructured));
        }
    }

}
