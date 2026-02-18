package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Physdescstructured;
import cz.zaf.schema.ead3.Quantity;
import cz.zaf.schema.ead3.Unittype;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.math.NumberUtils;

public class Rule82 extends EadRule {

    static final public String CODE = "obs82";
    static final public String RULE_TEXT = "Na kořeni archivního popisu se nachází element <physdescstructured> s atributem \"physdescstructuredtype\" o hodnotě \"otherphysdescstructured\" a zároveň má atribut \"otherphysdescstructured\" o hodnotě \"UNIT_TYPE\" a má atribut \"coverage\" o hodnotě \"part\". Pro každý druh archiválie, který se v archivním popisu vyskytuje, je použit právě jeden takovýto blok <physdescstructured>. Podřízený element <unittype> obsahuje některou zkratku zpracovaného druhu archiválie (dle ZP a specifikace v 5.9) a podřízený element <quantity> obsahuje číslo, které je menší nebo rovno součtu hodnot elementů <quantity> všech podřízených jednotek popisu se stejnou hodnotou elementu <unittype>.";
    static final public String RULE_ERROR = "Element <physdescstructured> s atributem \"physdescstructured\" o hodnotě \"otherphysdescstructured\" a zároveň s atributem \"otherphysdescstructured\" o hodnotě \"UNIT_TYPE\", nemá atribut \"coverage\" o hodnotě \"part\" a/nebo se nenachází na kořeni archivního popisu a/nebo pro každý druh archiválie, který se v popisu vyskytuje, není použit právě jeden takovýto blok <physdescstructured> a/nebo podřízený element <unittype> neobsahuje zkratku příslušného druhu archiválie a/nebo podřízený element <quantity> neobsahuje číslo, které je menší nebo rovno součtu příslušných druhů archiválie.";
    static final public String RULE_SOURCE = "Část 3.5 a 4.2 profilu EAD3 MV ČR, část 2.9.3 Základních pravidel";

    private final Set<String> allowed = Set.of("lio", "lip", "ukn", "rkp", "ppr", "ind", "ele", "rep", "ktt", "pec", "raz", "otd", "map", "atl", "tvy", "gli", "kre", "fsn", "fsd", "lfi", "sfi", "kin", "mf", "mfis", "fal", "dfo", "kza", "zza", "tio", "tip", "poh", "pkt", "cpa", "sto", "pnp", "pfp", "jin", "file", "item", "itempart");
    private Map<String, Integer> rootValues;
    private Map<String, Integer> componentValues;

    public Rule82() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        rootValues = new HashMap();
        getPhysdescstructured(didA, true);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            componentValues = new HashMap();
            getPhysdescstructured(didC, false);
        });

        compareMaps(archDesc);
        countMaps(archDesc);
    }

    private void getPhysdescstructured(Did did, boolean root) {
        List<Object> childList = did.getMDid();
        for (Object child : childList) {
            if (child instanceof Physdescstructured physdescstructured) {
                String physdescstructuredtype = physdescstructured.getPhysdescstructuredtype();
                String otherphysdescstructuredtype = physdescstructured.getOtherphysdescstructuredtype();
                String coverage = physdescstructured.getCoverage();
                if (StringUtils.equals("otherphysdescstructuredtype", physdescstructuredtype) && StringUtils.equals("UNIT_TYPE", otherphysdescstructuredtype) && StringUtils.equals("part", coverage)) {
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
                    if (!NumberUtils.isCreatable(contentQuantity)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element quantity neobsajuje očekávanou hodnotu.", ctx.formatEadPosition(quantity));
                    }

                    if (!allowed.contains(contentUnitType)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype obsajuje nepovolenou hodnotu: " + contentUnitType + ".", ctx.formatEadPosition(unittype));
                    }

                    if (root) {
                        if (rootValues.containsKey(contentUnitType)) {
                            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unittype obsajuje již zaznamenanou hodnotu.", ctx.formatEadPosition(unittype));
                        }
                        rootValues.put(contentUnitType, NumberUtils.createInteger(contentQuantity));
                    } else {
                        componentValues.merge(contentUnitType, NumberUtils.createInteger(contentQuantity), Integer::sum);
                    }
                }
            }
        }
    }

    private void compareMaps(Archdesc archDesc) {
        Set<String> onlyInMap1 = new HashSet<>(rootValues.keySet());
        onlyInMap1.removeAll(componentValues.keySet());
        if (!onlyInMap1.isEmpty()) {
            String result = String.join(", ", onlyInMap1);
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen element unittype s hodnotou, kterou nepoužívá žádná archiválie: " + result + ".", ctx.formatEadPosition(archDesc));
        }

        Set<String> onlyInMap2 = new HashSet<>(componentValues.keySet());
        onlyInMap2.removeAll(rootValues.keySet());
        if (!onlyInMap1.isEmpty()) {
            String result = String.join(", ", onlyInMap2);
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezena archiválie používající element unittype s hodnotou, která není na kořeni archivního popisu: " + result + ".", ctx.formatEadPosition(archDesc));
        }
    }

    private void countMaps(Archdesc archDesc) {
        for (Map.Entry<String, Integer> entry : rootValues.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Integer componentCount = componentValues.get(key);
            if (!(value <= componentCount)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesedí počet archiválií s unittype: " + key + ". Počet uvedený na kořeni archivního popisu: " + value + ", součet hodnot archiválií: " + componentCount + ".", ctx.formatEadPosition(archDesc));
            }
        }
    }

}
