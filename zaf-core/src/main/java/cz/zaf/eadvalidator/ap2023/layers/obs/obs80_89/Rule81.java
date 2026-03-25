package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import java.util.List;

public class Rule81 extends EadRule {
    static final public String CODE = "obs81";
    static final public String RULE_TEXT = "Na kořeni archivního popisu se nachází právě jeden element <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"otherphysdescstructuredtype\" a zároveň s atributem \"otherphysdescstructuredtype\" o hodnotě \"quantity\" a zároveň s atributem \"coverage\" o hodnotě \"whole\" (týká se povinnosti uvedení \"desc_units\"). Na koření archivního popisu se dále nachází jeden nebo dva elementy <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"spaceoccupied\" a atributem \"coverage\" o hodnotě \"whole\" (týká se povinnosti uvedení \"bm\" nebo \"byte\").";
    static final public String RULE_ERROR = "Na kořeni archivního popisu se nenachází právě jeden element <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"otherphysdescstructuredtype\" a zároveň s atributem \"otherphysdescstructuredtype\" o hodnotě \"quantity\" a zároveň s atributem \"coverage\" o hodnotě \"whole\" a/nebo právě jeden element <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"spaceoccupied\" a atributem \"coverage\" o hodnotě \"whole\".";
    static final public String RULE_SOURCE = "Část 6.7.4 profilu EAD3 MV ČR";

    public Rule81() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validate(didA);

    }

    private void validate(Did did) {
        List<Object> childList = did.getMDid();
        // used to check if exists at most once
        Physdescstructured foundQuantity = null;
        int other = 0;
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                // ze schematu non null            
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                // muze byt null
                String otherphysdescstructuredtype = physdescstructured.getOtherphysdescstructuredtype();
                // muze byt null
                String coverage = physdescstructured.getCoverage();
                if ("otherphysdescstructuredtype".equals(physdescstructuredtype) && 
                    "quantity".equals(otherphysdescstructuredtype) && 
                    "whole".equals(coverage)) {
                    if (foundQuantity != null) {
                        throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physdescstructured.", ctx.formatEadPosition(child));
                    }
                    foundQuantity = physdescstructured;
                }
                if ("spaceoccupied".equals(physdescstructuredtype) && 
                    otherphysdescstructuredtype==null &&
                    "whole".equals(coverage)) {
                    other++;
                }
            }
        }
        if(foundQuantity == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element physdescstructured s atributem otherphysdescstructuredtype=\"quantity\" a coverage=\"whole\".", ctx.formatEadPosition(did));
        }

        if (other == 0) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element physdescstructured s atributem physdescstructuredtype=\"spaceoccupied\" a coverage=\"whole\".", ctx.formatEadPosition(did));
        }
        if (other > 2) {
            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezeno více elementů physdescstructured s atributem physdescstructuredtype=\"spaceoccupied\" a coverage=\"whole\".", ctx.formatEadPosition(did));
        }
    }

}
