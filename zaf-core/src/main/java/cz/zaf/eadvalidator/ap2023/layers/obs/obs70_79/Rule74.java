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

public class Rule74 extends EadRule {

    static final public String CODE = "obs74";
    static final public String RULE_TEXT = "Každý element <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", který obsahuje element <unittype> s některou z následujících hodnot:\n"
            + "- pec\n"
            + "- raz\n"
            + "- otd,\n"
            + "současně obsahuje element <physfacet> s atributem \"localtype\" o některé z následujících hodnot:\n"
            + "- IMPRINT_IMAGE\n"
            + "- LEGEND.\n"
            + "Element <physfacet> se s vybranou hodnotou atributu může vyskytovat nejvýše jednou.";
    static final public String RULE_ERROR = "Některý element <physfacet>, který je obsažen v elementu <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", nemá atribut \"localtype\", obsahuje nepovolenou hodnotu anebo má nepovolený výskyt.";
    static final public String RULE_SOURCE = "Část 6.3 a 6.17 profilu EAD3 MV ČR";

    public Rule74() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
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
                boolean conditions = isPhysdescstructured(physdescstructured);
                if (conditions) {
                    List<Object> physfacetOrDimensions = physdescstructured.getPhysfacetOrDimensions();
                    boolean pfImprint = false;
                    boolean pfLegend = false;
                    for (Object object : physfacetOrDimensions) {
                        if (object instanceof Physfacet physfacet) {
                            String localtype = physfacet.getLocaltype();
                            if (StringUtils.equals("IMPRINT_IMAGE", localtype)) {
                                if (pfImprint) {
                                    throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physfacet.", ctx.formatEadPosition(physfacet));
                                }
                                pfImprint = true;

                            }
                            if (StringUtils.equals("LEGEND", localtype)) {
                                if (pfLegend) {
                                    throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physfacet.", ctx.formatEadPosition(physfacet));
                                }
                                pfLegend = true;
                            }
                        }
                    }
                    if (!pfImprint && !pfLegend) {
                        throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen element physfacet.", ctx.formatEadPosition(physdescstructured));
                    }
                }
            }
        }
    }

    private boolean isPhysdescstructured(Physdescstructured physdescstructured) {
        String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
        String coverage = physdescstructured.getCoverage();
        Unittype unittype = physdescstructured.getUnittype();

        boolean isAtributes = StringUtils.equals("materialtype", physdescstructuredtype) && StringUtils.equals("whole", coverage);
        boolean unitType = isUnitType(unittype);

        return isAtributes && unitType;
    }

    private boolean isUnitType(Unittype unittype) {
        if (unittype == null) {
            return false;
        }
        String content = unittype.getContent();
        return StringUtils.equalsAny(content, "pec", "raz", "otd");
    }

}
