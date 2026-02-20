package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.C;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import cz.zaf.schema.ead3.Quantity;
import cz.zaf.schema.ead3.Unittype;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.math.NumberUtils;

public class Rule83 extends EadRule {

    static final public String CODE = "obs83";
    static final public String RULE_TEXT = "Pokud element <physdescstructured> má atribut \"physdescstructured\" o hodnotě \"materialtype\", má atribut \"coverage\" o hodnotě \"whole\". Podřízený element <unittype> obsahuje některou zkratku zpracovaného druhu archiválie (dle ZP a specifikace v 5.9), přičemž hodnota \"file\" je použita jen na úrovni složky, hodnota \"item\" jen na úrovni jednotlivosti a \"itempart\" jen na úrovni části jednotlivosti. Podřízený element <quantity> obsahuje kladné celé číslo nebo je prázdný. Pokud tento element <physdescstructured> popisuje jednotku popisu na úrovni jednotlivosti (přímo nadřízený element <c> má atribut \"level\" o hodnotě \"item\"), obsahuje element <quantity> hodnotu 1.";
    static final public String RULE_ERROR = "Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"materialtype\", nemá atribut \"coverage\" o hodnotě \"whole\" a/nebo podřízený element <unittype> neobsahuje některou ze zkratek evidenčních jednotek a/nebo podřízený element <quantity> neobsahuje kladné celé číslo nebo není prázdný. Případně pokud element <physdescstructured> popisuje jednotku popisu na úrovni jednotlivosti, neobsahuje element <quantity> hodnotu 1.";
    static final public String RULE_SOURCE = "Část 3.3, 5.9 a 5.10 profilu EAD3 MV ČR";

    private final Set<String> allowed = Set.of("lio", "lip", "ukn", "rkp", "ppr", "ind", "ele", "rep", "ktt", "pec", "raz", "otd", "map", "atl", "tvy", "gli", "kre", "fsn", "fsd", "lfi", "sfi", "kin", "mf", "mfis", "fal", "dfo", "kza", "zza", "tio", "tip", "poh", "pkt", "cpa", "sto", "pnp", "pfp", "jin", "file", "item", "itempart");

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

    private void getPhysdescstructured(Did did, C c, Object parent) {
        List<Object> childList = did.getMDid();
        String levelC = c.getLevel();
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                String coverage = physdescstructured.getCoverage();
                if (StringUtils.equals("materialtype", physdescstructuredtype) && StringUtils.equals("whole", coverage)) {
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
                    if (StringUtils.isEmpty(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element unittype neobsahuje žádnou hodnotu.", ctx.formatEadPosition(unittype));
                    }
                    if (!allowed.contains(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype obsajuje nepovolenou hodnotu: " + contentUnitType + ".", ctx.formatEadPosition(unittype));
                    }

                    if (StringUtils.equals("file", contentUnitType) || StringUtils.equals("item", contentUnitType) || StringUtils.equals("itempart", contentUnitType)) {
                        if (!levelC.equals(contentUnitType)) {
                            if (!levelC.equals("otherlevel")) {
                                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype neobsajuje očekávanou hodnotu: " + levelC + ", ale hodnotu: " + contentUnitType + ".", ctx.formatEadPosition(unittype));
                            }
                        }
                    }

                    if (!StringUtils.isEmpty(contentQuantity)) {
                        if (!NumberUtils.isCreatable(contentQuantity)) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje očekávanou hodnotu.", ctx.formatEadPosition(quantity));
                        }
                        Integer createInteger = NumberUtils.createInteger(contentQuantity);
                        if (createInteger < 0) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity obsahuje nepovolenou hodnotu: " + createInteger + ".", ctx.formatEadPosition(quantity));
                        }
                    }
                    if (isCondition(parent)) {
                        if (!StringUtils.equals("1", contentQuantity)) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje očekávanou hodnotu 1.", ctx.formatEadPosition(quantity));
                        }
                    }

                }
            }
        }
    }

    private boolean isCondition(Object parent) {
        if (parent instanceof C cParent) {
            String level = cParent.getLevel();
            if (StringUtils.equals("item", level)) {
                return true;
            }
        }
        return false;
    }
}
