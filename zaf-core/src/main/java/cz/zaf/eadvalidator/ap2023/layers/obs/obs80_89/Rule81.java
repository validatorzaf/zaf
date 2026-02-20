package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

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
        Physdescstructured found = null;
        int other = 0;
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                String otherphysdescstructuredtype = physdescstructured.getOtherphysdescstructuredtype();
                String coverage = physdescstructured.getCoverage();
                if (StringUtils.equals("otherphysdescstructuredtype", physdescstructuredtype) && StringUtils.equals("quantity", otherphysdescstructuredtype) && StringUtils.equals("whole", coverage)) {
                    if (found != null) {
                        List<Physdescstructured> list = List.of(found, physdescstructured);
                        throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nenalezen nepovolený element physdescstructured.", ctx.formatEadPosition(list));
                    }
                    found = physdescstructured;
                }
                if (StringUtils.equals("spaceoccupied", physdescstructuredtype) && StringUtils.equals("whole", coverage)) {
                    other++;
                }
            }
        }
        if (other == 0) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element physdescstructured.", ctx.formatEadPosition(did));
        }
        if (other > 2) {
            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element physdescstructured.", ctx.formatEadPosition(did));
        }
    }

}
