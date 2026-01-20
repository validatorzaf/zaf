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

public class Rule74b extends EadRule {

    static final public String CODE = "obs74b";
    static final public String RULE_TEXT = "Každý element <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", obsahuje element <physfacet> s atributem \"localtype\" o některé z následujících hodnot:\n"
            + "- TECHNIQUE\n"
            + "- TECHNIQUE_DETAIL\n"
            + "- MEDIUM_ADJUSTMENT\n"
            + "- MEDIUM\n"
            + "- MATERIAL\n"
            + "- WRITTING.\n"
            + "Element <physfacet> s výjimkou WRITTING se pro <unittype> s některou z následujících hodnot:\n"
            + "- lio\n"
            + "- lip\n"
            + "- fsn\n"
            + "- fsd\n"
            + "- lfi\n"
            + "- sfi\n"
            + "- kin\n"
            + "- mf\n"
            + "- mfis\n"
            + "- fal\n"
            + "- dfo\n"
            + "- zza\n"
            + "- kza\n"
            + "vyskytuje právě jednou. V ostatních případech se může vyskytovat nejvýše jednou.";
    static final public String RULE_ERROR = "Některý element <physfacet>, který je obsažen v elementu <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"materialtype\" a zároveň atributem \"coverage\" o hodnotě \"whole\", nemá atribut \"localtype\", obsahuje nepovolenou hodnotu anebo má nepovolený výskyt.";
    static final public String RULE_SOURCE = "Část 6.12.1 a 6.15 profilu EAD3 MV ČR";

    private final Set<String> allowedPhysfacet = Set.of("TECHNIQUE", "TECHNIQUE_DETAIL", "MEDIUM_ADJUSTMENT", "MEDIUM", "MATERIAL", "WRITTING");
    private final Set<String> allowedUnitTime = Set.of("lio", "lip", "fsn", "fsd", "lfi", "sfi", "kin", "mf", "mfis", "fal", "dfo", "zza", "kza");

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
                    List<Object> physfacetOrDimensions = physdescstructured.getPhysfacetOrDimensions();
                    Physfacet found = null;
                    for (Object object : physfacetOrDimensions) {
                        if (object instanceof Physfacet physfacet) {
                            boolean isWanted = isWantedPhysfacet(physfacet);
                            if (isWanted) {
                                if (found != null) {
                                    throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physfacet.", ctx.formatEadPosition(physfacet));
                                }
                                found = physfacet;
                                checkUnitType(physdescstructured, physfacet.getLocaltype());
                            }
                        }
                    }
                    if (found == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nelezen element physfacet s atributem localtype s požadovanou hodnotou.", ctx.formatEadPosition(physdescstructured));
                    }
                }
            }
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

    private void checkUnitType(Physdescstructured physdescstructured, String localtype) {
        Unittype unittype = physdescstructured.getUnittype();
        if (unittype == null) {
            if (!("WRITTING").equals(localtype)) {
                throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element unittype", ctx.formatEadPosition(physdescstructured));
            }
        }
        String content = unittype.getContent();
        if (StringUtils.isEmpty(content)) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element unittype je prázdný.", ctx.formatEadPosition(unittype));
        }
        if (!allowedUnitTime.contains(content)) {
            if (!("WRITTING").equals(localtype)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype obsahue nepovolenou hodnotu: " + content + ".", ctx.formatEadPosition(unittype));
            }
        }
    }

}
