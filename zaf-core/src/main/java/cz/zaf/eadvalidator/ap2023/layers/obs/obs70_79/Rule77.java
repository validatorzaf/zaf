package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import cz.zaf.schema.ead3.Quantity;
import cz.zaf.schema.ead3.Unittype;
import cz.zaf.schemas.ead.EadNS;

import java.util.List;

public class Rule77 extends EadRule {

    static final public String CODE = "obs77";
    static final public String RULE_TEXT = "Pokud element <physdescstructured> má atribut \"physdescstructured\" o hodnotě \"otherphysdescstructuredtype\" a zároveň atribut \"otherphysdescstructuredtype\" o hodnotě \"weight\", má atribut \"coverage\" o hodnotě \"whole\". Podřízený element <unittype> obsahuje hodnotu \"g\" a podřízený element <quantity> obsahuje kladné celé číslo.";
    static final public String RULE_ERROR = "Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"otherphysdescstructuredtype\" a zároveň s atributem \"otherphysdescstructuredtype\" o hodnotě \"weight\" má špatně nastavený atribut \"coverage\" a/nebo v něm obsažené elementy <unittype> a/nebo <quantity> obsahují nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 6.7.3 profilu EAD3 MV ČR";

    public Rule77() {
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
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                String otherphysdescstructuredtype = physdescstructured.getOtherphysdescstructuredtype();

                if (EadNS.PHYSDESCSTRUCTURED_TYPE_OTHERTYPE.equals(physdescstructuredtype) &&
                      "weight".equals(otherphysdescstructuredtype)) {
                    String coverage = physdescstructured.getCoverage();
                    if(!"whole".equals(coverage)) {
                    	throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut coverage musí mít hodnotu whole.", ctx.formatEadPosition(physdescstructured));
                    }
                    Unittype unittype = physdescstructured.getUnittype();
                    Quantity quantity = physdescstructured.getQuantity();
                    if (unittype == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element unittype.", ctx.formatEadPosition(physdescstructured));
                    }
                    if (quantity == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element quantity.", ctx.formatEadPosition(physdescstructured));
                    }
                    String contentUnitType = unittype.getContent();
                    String contentQuantity = quantity.getContent();
                    if (!StringUtils.equals("g", contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype neobsajuje očekávanou hodnotu.", ctx.formatEadPosition(unittype));
                    }
                    try {
                        var value = Integer.parseInt(contentQuantity);
                        if (value <= 0) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje kladné celé číslo.", ctx.formatEadPosition(quantity));
                        }
                    } catch (NumberFormatException nfe) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje očekávanou hodnotu.", ctx.formatEadPosition(quantity));
                    }
                }
            }
        }
    }
}
