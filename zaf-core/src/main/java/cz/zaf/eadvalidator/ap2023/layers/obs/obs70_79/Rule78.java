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
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;

public class Rule78 extends EadRule {

    static final public String CODE = "obs78";
    static final public String RULE_TEXT = "Pokud element <physdescstructured> má atribut \"physdescstructuredtype\" o hodnotě \"otherphysdescstructuredtype\" a zároveň atribut \"otherphysdescstructuredtype\" o hodnotě \"quantity\", má atribut \"coverage\" o hodnotě \"whole\". Pokud se element nachází na kořeni archivního popisu, podřízený element <unittype> obsahuje hodnotu \"desc_units\". V ostatních případech obsahuje podřízený element <unittype> některou z hodnot \"byte\", \"pieces\", \"pages\" nebo \"sheets\". Pokud se element nachází na kořeni archivního popisu, podřízený element <quantity> obsahuje součet všech jednotek popisu včetně jednotky popisu, v níž se sám nachází. V ostatních případech obsahuje podřízený element <quantity> kladné celé číslo.";
    static final public String RULE_ERROR = "Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"otherphysdescstructuredtype\" a zároveň s atributem \"otherphysdescstructuredtype\" o hodnotě \"quantity\" má špatně nastavený atribut \"coverage\" a/nebo v něm obsažené elementy element <unittype> a/nebo <quantity> obsahují nepovolenou hodnotu, příp. je použita hodnota \"unit_desc\" na jiné úrovni než na kořeni archivního popisu nebo není správně proveden součet jednotek popisu na kořeni archivního souboru.";
    static final public String RULE_SOURCE = "Část 6.8.2 profilu EAD3 MV ČR";

    public Rule78() {
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
                String coverage = physdescstructured.getCoverage();

                if (StringUtils.equals("otherphysdescstructuredtype", physdescstructuredtype)
                        && StringUtils.equals("duration", otherphysdescstructuredtype)
                        && StringUtils.equals("whole", coverage)) {
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
                    if (!StringUtils.equals("s", contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype neobsajuje očekávanou hodnotu.", ctx.formatEadPosition(unittype));
                    }
                    if (!NumberUtils.isCreatable(contentQuantity)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje očekávanou hodnotu.", ctx.formatEadPosition(quantity));
                    } else {
                        if (Integer.parseInt(contentQuantity) < 0) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje kladné celé číslo.", ctx.formatEadPosition(quantity));
                        }
                    }
                }
            }
        }
    }
}
