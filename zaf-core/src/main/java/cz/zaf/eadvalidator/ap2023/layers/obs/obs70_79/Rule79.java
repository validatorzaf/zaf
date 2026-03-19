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
import java.util.Set;

public class Rule79 extends EadRule {

    static final public String CODE = "obs79";
    static final public String RULE_TEXT = "Pokud element <physdescstructured> má atribut \"physdescstructuredtype\" o hodnotě \"otherphysdescstructuredtype\" a zároveň atribut \"otherphysdescstructured\" o hodnotě \"quantity\", má atribut \"coverage\" o hodnotě \"whole\". Pokud se element nachází na kořeni archivního popisu, podřízený element <unittype> obsahuje hodnotu \"desc_units\". V ostatních případech obsahuje podřízený element <unittype> některou z hodnot \"byte\", \"pieces\", \"pages\" nebo \"sheets\". Pokud se element nachází na kořeni archivního popisu, podřízený element <quantity> obsahuje součet všech jednotek popisu včetně jednotky popisu, v níž se sám nachází. V ostatních případech obsahuje podřízený element <quantity> kladné celé číslo.";
    static final public String RULE_ERROR = "Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"otherphysdescstructuredtype\" a zároveň s atributem \"otherphysdescstructured\" o hodnotě \"quantity\" má špatně nastavený atribut \"coverage\" a/nebo v něm obsažené elementy element <unittype> a/nebo <quantity> obsahují nepovolenou hodnotu, příp. je použita hodnota \"unit_desc\" na jiné úrovni než na kořeni archivního popisu nebo není správně proveden součet jednotek popisu na kořeni archivního souboru.";
    static final public String RULE_SOURCE = "Část 6.7.4 profilu EAD3 MV ČR";

    private static final Set<String> ALLOWED_NON_ROOT = Set.of("byte", "pieces", "pages", "sheets");

    int descUnitCount = 0;
    int totalCElements = 0;

    public Rule79() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        descUnitCount = 0;
        totalCElements = 0;

        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validate(didA, true);

        // Count all C elements (description units)
        ctx.getEadLevelIterator().iterate((c, parent) -> {
            totalCElements++;
            Did didC = c.getDid();
            validate(didC, false);
        });

        // Validate root desc_units count: should equal total C elements + 1 (for archdesc itself)
        if (descUnitCount > 0) {
            int expectedCount = totalCElements + 1;
            if (descUnitCount != expectedCount) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Kořen archivního popisu neobsahuje správný součet všech jednotek popisu. "
                        + "Očekávaná hodnota: " + expectedCount + ", skutečná hodnota: " + descUnitCount + ".",
                        ctx.formatEadPosition(archDesc));
            }
        }
    }

    private void validate(Did did, boolean root) {
        List<Object> childList = did.getMDid();
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                String otherphysdescstructuredtype = physdescstructured.getOtherphysdescstructuredtype();

                if (!EadNS.PHYSDESCSTRUCTURED_TYPE_OTHERTYPE.equals(physdescstructuredtype)
                        || !"quantity".equals(otherphysdescstructuredtype)) {
                    continue;
                }

                // Found physdescstructured with otherphysdescstructuredtype="quantity"
                // Coverage must be "whole"
                String coverage = physdescstructured.getCoverage();
                if (!"whole".equals(coverage)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                            "Element <physdescstructured> s atributem otherphysdescstructuredtype=\"quantity\" nemá atribut coverage=\"whole\".",
                            ctx.formatEadPosition(physdescstructured));
                }

                Unittype unittype = physdescstructured.getUnittype();
                Quantity quantity = physdescstructured.getQuantity();
                if (unittype == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element unittype.",
                            ctx.formatEadPosition(physdescstructured));
                }
                if (quantity == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element quantity.",
                            ctx.formatEadPosition(physdescstructured));
                }

                String contentUnitType = unittype.getContent();
                String contentQuantity = quantity.getContent();

                // Validate unittype
                if (StringUtils.isEmpty(contentUnitType)) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU,
                            "Element unittype neobsahuje žádnou hodnotu.",
                            ctx.formatEadPosition(unittype));
                }
                if (root) {
                    if (!"desc_units".equals(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                                "Element unittype na kořeni archivního popisu obsahuje nesprávnou hodnotu: "
                                + contentUnitType + ", očekávaná hodnota: desc_units.",
                                ctx.formatEadPosition(unittype));
                    }
                } else {
                    if (!ALLOWED_NON_ROOT.contains(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                                "Element unittype obsahuje nesprávnou hodnotu: " + contentUnitType
                                + ", očekávané hodnoty: byte, pieces, pages, sheets.",
                                ctx.formatEadPosition(unittype));
                    }
                }

                // Validate quantity - must be positive integer
                if (StringUtils.isEmpty(contentQuantity)) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU,
                            "Element quantity neobsahuje žádnou hodnotu.",
                            ctx.formatEadPosition(quantity));
                }
                try {
                    int value = Integer.parseInt(contentQuantity);
                    if (value <= 0) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                                "Element quantity neobsahuje kladné celé číslo: " + contentQuantity + ".",
                                ctx.formatEadPosition(quantity));
                    }
                    if (root) {
                        descUnitCount = value;
                    }
                    ctx.markValidatedAttribute(physdescstructured, "physdescstructuredtype");
                    ctx.markValidatedAttributeOnly(physdescstructured, "otherphysdescstructuredtype");
                    ctx.markValidatedAttributeOnly(physdescstructured, "coverage");
                    ctx.markValidatedElement(unittype);
                    ctx.markValidatedContent(unittype);
                    ctx.markValidatedElement(quantity);
                    ctx.markValidatedContent(quantity);
                } catch (NumberFormatException e) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                            "Element quantity neobsahuje platné celé číslo: " + contentQuantity + ".",
                            ctx.formatEadPosition(quantity));
                }
            }
        }
    }
}
