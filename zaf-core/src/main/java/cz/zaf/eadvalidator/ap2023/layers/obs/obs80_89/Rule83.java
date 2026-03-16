package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.Ap2023Constants;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.schema.ead3.C;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import cz.zaf.schema.ead3.Quantity;
import cz.zaf.schema.ead3.Unittype;
import cz.zaf.schemas.ead.EadNS;

import java.util.List;
import java.util.Set;

public class Rule83 extends EadRule {

    static final public String CODE = "obs83";
    static final public String RULE_TEXT = "Pokud element <physdescstructured> má atribut \"physdescstructured\" o hodnotě \"materialtype\", má atribut \"coverage\" o hodnotě \"whole\". Podřízený element <unittype> obsahuje některou zkratku zpracovaného druhu archiválie (dle ZP a specifikace v 5.9), přičemž hodnota \"file\" je použita jen na úrovni složky, hodnota \"item\" jen na úrovni jednotlivosti a \"itempart\" jen na úrovni části jednotlivosti. Podřízený element <quantity> obsahuje kladné celé číslo nebo je prázdný. Pokud tento element <physdescstructured> popisuje jednotku popisu na úrovni jednotlivosti (přímo nadřízený element <c> má atribut \"level\" o hodnotě \"item\"), obsahuje element <quantity> hodnotu 1.";
    static final public String RULE_ERROR = "Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"materialtype\", nemá atribut \"coverage\" o hodnotě \"whole\" a/nebo podřízený element <unittype> neobsahuje některou ze zkratek evidenčních jednotek a/nebo podřízený element <quantity> neobsahuje kladné celé číslo nebo není prázdný. Případně pokud element <physdescstructured> popisuje jednotku popisu na úrovni jednotlivosti, neobsahuje element <quantity> hodnotu 1.";
    static final public String RULE_SOURCE = "Část 3.3, 5.9 a 5.10 profilu EAD3 MV ČR";

    private static final Set<String> allowed = Set.of("lio", "lip", "ukn", "rkp", "ppr", "ind", "ele", "rep", "ktt", "pec", "raz", "otd", "map", "atl", "tvy", "gli", "kre", "fsn", "fsd", "lfi", "sfi", "kin", "mf", "mfis", "fal", "dfo", "kza", "zza", "tio", "tip", "poh", "pkt", "cpa", "sto", "pnp", "pfp", "jin", 
        "file", "item", "itempart");

    public Rule83() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            getPhysdescstructured(didC, c, parent);
        });
    }

    private void getPhysdescstructured(Did did, C c, C parentC) {
        List<Object> childList = did.getMDid();
        String levelC = c.getLevel();
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                String coverage = physdescstructured.getCoverage();
                if (EadNS.PHYSDESCSTRUCTURED_TYPE_MATERIALTYPE.equals(physdescstructuredtype) && 
                    "whole".equals(coverage)) {
                    Unittype unittype = physdescstructured.getUnittype();
                    if (unittype == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element unittype.", ctx.formatEadPosition(physdescstructured));
                    }
                    Quantity quantity = physdescstructured.getQuantity();
                    if (quantity == null) {
                        throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element quantity.", ctx.formatEadPosition(physdescstructured));
                    }
                    String contentUnitType = unittype.getContent();
                    if (StringUtils.isEmpty(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element unittype neobsahuje žádnou hodnotu.", ctx.formatEadPosition(unittype));
                    }
                    if (!allowed.contains(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype obsajuje nepovolenou hodnotu: " + contentUnitType + ".", ctx.formatEadPosition(unittype));
                    }

                    // kontrola deklarovaneho typu ve vztahu k urovni
                    // neuplatni se pro inherentni popis, kde mohou být jiné typy úrovní
                    if(AP2023Profile.EARK_INHERENT_DESC!=ctx.getValidationProfile()) {
						if (EadNS.LEVEL_FILE.equals(contentUnitType) || EadNS.LEVEL_ITEM.equals(contentUnitType)) {
							if (!levelC.equals(contentUnitType)) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
										"Element unittype neobsajuje očekávanou hodnotu: " + levelC + ", ale hodnotu: "
												+ contentUnitType + ".",
										ctx.formatEadPosition(unittype));
							}
						} else if (Ap2023Constants.LEVEL_ITEMPART.equals(contentUnitType)) {
							if (!EadNS.LEVEL_OTHERLEVEL.equals(levelC)) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
										"Element unittype neobsajuje očekávanou hodnotu: " + levelC + ", ale hodnotu: "
												+ contentUnitType + ".",
										ctx.formatEadPosition(unittype));
							}
							if (!Ap2023Constants.LEVEL_ITEMPART.equals(c.getOtherlevel())) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
										"Element unittype obsajuje hodnotu: " + Ap2023Constants.LEVEL_ITEMPART
												+ ", což neodpovídá hodnotě atributu otherlevel: " + contentUnitType
												+ ".",
										ctx.formatEadPosition(unittype));
							}
						}
                    }

                    String contentQuantity = quantity.getContent();
                    if (!StringUtils.isEmpty(contentQuantity)) {
                    	// try to parse int
                    	try {
                    		var value = Integer.valueOf(contentQuantity);
                    		if(value<1) {
                    			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje celé kladné číslo, hodnota: " + contentQuantity + ".", ctx.formatEadPosition(quantity));
                    		}
                    	} catch (NumberFormatException nfe) {
                    		throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity není číslo.", ctx.formatEadPosition(quantity));
                    	}
                    }
                    // Jednotlivost musi mit pocet 1
                    if (EadNS.LEVEL_ITEM.equals(contentUnitType)) {
                        if (!"1".equals(contentQuantity)) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje očekávanou hodnotu 1.", ctx.formatEadPosition(quantity));
                        }
                    }                    
                }
            }
        }
    }

}
