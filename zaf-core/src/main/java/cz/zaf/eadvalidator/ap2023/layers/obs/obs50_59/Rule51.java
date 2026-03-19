package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.Ap2023Constants;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.C;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Unitid;
import cz.zaf.schemas.ead.EadNS;

/**
 * obs51 - Validace referenčního označení.
 *
 * Kontroluje:
 * 1. Hodnota začíná "CZ" + číslo archivu z agencycode
 * 2. Hodnota navazuje na rodičovskou úroveň popisu správným oddělovačem
 *    ("//" pro novou úroveň, "/" pro podúroveň)
 * 3. Hodnota je vyšší než u předchozí jednotky popisu na stejné úrovni
 */
public class Rule51 extends EadRule {

    static final public String CODE = "obs51";
    static final public String RULE_TEXT = "Pokud některý element <unitid> obsažený v elementu <did> má atribut \"localtype\" o hodnotě \"REFERENCNI_OZNACENI\", hodnota téhož elementu odpovídá hodnotě referenčního označení rodičovské jednotce popisu a oddělovače (lomítka) podle kontextu úrovní popisu. Hodnota referenčního označení je vyšší než přechozí jednotka popisu v rodičovské jednotce popisu. Hodnota referenčního označení obsahuje na začátku CZ a číslo archivu v elementu <agencycode>.";
    static final public String RULE_ERROR = "Některý element <unitid> s atributem \"localtype\" o hodnotě \"REFERENCNI_OZNACENI\" neobsahuje hodnotu v požadovaném formátu.";
    static final public String RULE_SOURCE = "Část 5.4 profilu EAD3 MV ČR";

    public Rule51() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        String agencyContent = ctx.getEad().getControl()
                .getMaintenanceagency().getAgencycode().getContent();
        String expectedPrefix = "CZ" + agencyContent;

        // Archdesc must not contain REFERENCNI_OZNACENI
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Unitid> archdescRefUnitids = findAllRefUnitids(archDesc.getDid());
        if (!archdescRefUnitids.isEmpty()) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Element <archdesc> nesmí obsahovat referenční označení.",
                    ctx.formatEadPosition(archdescRefUnitids.get(0)));
        }

        // Track ref designations and sibling ordering
        Map<C, String> refMap = new HashMap<>();
        Map<C, String> lastSiblingSuffix = new HashMap<>();

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Unitid> refUnitids = findAllRefUnitids(c.getDid());
            if (refUnitids.isEmpty()) {
                return;
            }

            // At most one REFERENCNI_OZNACENI is allowed per level
            if (refUnitids.size() > 1) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Úroveň popisu obsahuje více než jeden element <unitid> s atributem \"localtype\" o hodnotě \"REFERENCNI_OZNACENI\".",
                        ctx.formatEadPosition(refUnitids.get(1)));
            }

            Unitid unitid = refUnitids.get(0);
            ctx.markValidatedAttribute(unitid, "localtype");
            ctx.markValidatedContent(unitid);
            String ref = getUnitidTextContent(unitid);
            if (ref == null) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Element <unitid> s atributem \"localtype\" o hodnotě \"REFERENCNI_OZNACENI\" má prázdnou hodnotu.",
                        ctx.formatEadPosition(unitid));
            }
            refMap.put(c, ref);

            // 1. Validate CZ + agencycode prefix
            validatePrefix(ref, expectedPrefix, unitid);

            // 2. Validate parent-child relationship
            String parentRef = (parent != null) ? refMap.get(parent) : null;
            if (parentRef != null) {
                String parentLevel = getEffectiveLevel(parent);
                String childLevel = getEffectiveLevel(c);
                String separator = isNewLevelGroup(parentLevel, childLevel) ? "//" : "/";
                String expectedStart = parentRef + separator;

                if (!ref.startsWith(expectedStart)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                            "Hodnota referenčního označení nenavazuje na rodičovskou úroveň. "
                            + "Očekávaný prefix: " + expectedStart,
                            ctx.formatEadPosition(unitid));
                }

                String suffix = ref.substring(expectedStart.length());
                if (StringUtils.isEmpty(suffix)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                            "Hodnota referenčního označení neobsahuje číslo za oddělovačem.",
                            ctx.formatEadPosition(unitid));
                }

                // Extract the first segment (this level's number) before any deeper separators
                String firstSegment = extractFirstSegment(suffix);
                validateSegmentNumber(firstSegment, unitid);

                // 3. Check ordering among siblings
                // parentKey is always non-null here (parent != null implied by parentRef != null)
                String lastSuffix = lastSiblingSuffix.get(parent);
                if (lastSuffix != null) {
                    if (compareSegments(lastSuffix, firstSegment) >= 0) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                                "Hodnota referenčního označení není vyšší než u předchozí jednotky popisu. "
                                + "Předchozí: " + lastSuffix + ", aktuální: " + firstSegment,
                                ctx.formatEadPosition(unitid));
                    }
                }
                lastSiblingSuffix.put(parent, firstSegment);
            }
        });
    }

    /**
     * Finds all REFERENCNI_OZNACENI Unitid elements in a Did.
     */
    private List<Unitid> findAllRefUnitids(Did did) {
        List<Unitid> result = new ArrayList<>();
        if (did == null) {
            return result;
        }
        for (Object obj : did.getMDid()) {
            if (obj instanceof Unitid unitid) {
                if (Ap2023Constants.LOCALTYPE_REF_OZNACENI.equals(unitid.getLocaltype())) {
                    result.add(unitid);
                }
            }
        }
        return result;
    }

    /**
     * Gets text content from a Unitid element.
     */
    private String getUnitidTextContent(Unitid unitid) {
        List<Serializable> content = unitid.getContent();
        if (content.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Serializable s : content) {
            if (s instanceof String str) {
                sb.append(str);
            }
        }
        String result = sb.toString().trim();
        return result.isEmpty() ? null : result;
    }

    /**
     * Validates that the ref designation starts with CZ + agencycode.
     */
    private void validatePrefix(String ref, String expectedPrefix, Unitid unitid) {
        if (!ref.startsWith(expectedPrefix)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Hodnota referenčního označení nezačíná očekávaným prefixem: " + expectedPrefix
                    + ", skutečná hodnota: " + ref,
                    ctx.formatEadPosition(unitid));
        }
    }

    /**
     * Gets the effective level of a C element, resolving otherlevel.
     * Returns "fonds" when parent is null (archdesc).
     */
    private String getEffectiveLevel(C c) {
        if (c == null) {
            return EadNS.LEVEL_FONDS;
        }
        String level = c.getLevel();
        if (EadNS.LEVEL_OTHERLEVEL.equals(level)) {
            return c.getOtherlevel();
        }
        return level;
    }

    /**
     * Determines the separator between parent and child levels.
     *
     * "//" separates different level groups (e.g., fonds→series, series→file).
     * "/" separates sub-levels within the same group (e.g., fonds→subfonds,
     * series→series, file→file, item→itempart).
     */
    private boolean isNewLevelGroup(String parentLevel, String childLevel) {
        // Within fond group: fonds → subfonds
        if (EadNS.LEVEL_FONDS.equals(parentLevel) && EadNS.LEVEL_SUBFONDS.equals(childLevel)) {
            return false;
        }
        // Within series group: series → series (nested subseries)
        if (EadNS.LEVEL_SERIES.equals(parentLevel) && EadNS.LEVEL_SERIES.equals(childLevel)) {
            return false;
        }
        // Within file group: file → file (sub-file)
        if (EadNS.LEVEL_FILE.equals(parentLevel) && EadNS.LEVEL_FILE.equals(childLevel)) {
            return false;
        }
        // Within item group: item → item, item → itempart
        if (EadNS.LEVEL_ITEM.equals(parentLevel)
                && (EadNS.LEVEL_ITEM.equals(childLevel) || Ap2023Constants.LEVEL_ITEMPART.equals(childLevel))) {
            return false;
        }
        // itempart → itempart (nested parts)
        if (Ap2023Constants.LEVEL_ITEMPART.equals(parentLevel) && Ap2023Constants.LEVEL_ITEMPART.equals(childLevel)) {
            return false;
        }
        // All other transitions: new level group
        return true;
    }

    /**
     * Extracts the first segment from a suffix string (before any "/" or "//").
     * E.g., "3/15/2//1" → "3", "42" → "42", "1+2" → "1+2"
     */
    private String extractFirstSegment(String suffix) {
        int slashIdx = suffix.indexOf('/');
        if (slashIdx < 0) {
            return suffix;
        }
        return suffix.substring(0, slashIdx);
    }

    /**
     * Validates that a segment contains a valid number (optionally with +/- extension).
     * Valid formats: "1", "42", "1+1", "1-1", "1-1+2"
     */
    private void validateSegmentNumber(String segment, Unitid unitid) {
        if (parseSegmentParts(segment) == null) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Hodnota referenčního označení obsahuje neplatné číslo: " + segment,
                    ctx.formatEadPosition(unitid));
        }
    }

    /**
     * Parses a segment into three parts: [base, minusExt, plusExt].
     * Format: base[-N][+M]
     * Examples:
     *   "5"     → [5, 0, 0]
     *   "5-1"   → [5, -1, 0]
     *   "5+2"   → [5, 0, 2]
     *   "5-1+2" → [5, -1, 2]
     *
     * Ordering: base-N < base-N+1 < ... < base < base+1 < base+2 < base+1
     * Returns null if the segment is not valid.
     */
    private int[] parseSegmentParts(String segment) {
        if (StringUtils.isEmpty(segment)) {
            return null;
        }

        try {
            int base;
            int minusExt = 0;
            int plusExt = 0;

            int minusIdx = segment.indexOf('-');
            int plusIdx = segment.indexOf('+');

            if (minusIdx > 0 && plusIdx > minusIdx) {
                // format: base-N+M
                base = Integer.parseInt(segment.substring(0, minusIdx));
                minusExt = -Integer.parseInt(segment.substring(minusIdx + 1, plusIdx));
                plusExt = Integer.parseInt(segment.substring(plusIdx + 1));
            } else if (minusIdx > 0) {
                // format: base-N
                base = Integer.parseInt(segment.substring(0, minusIdx));
                minusExt = -Integer.parseInt(segment.substring(minusIdx + 1));
            } else if (plusIdx > 0) {
                // format: base+M
                base = Integer.parseInt(segment.substring(0, plusIdx));
                plusExt = Integer.parseInt(segment.substring(plusIdx + 1));
            } else {
                // format: base
                base = Integer.parseInt(segment);
            }

            return (base > 0) ? new int[]{base, minusExt, plusExt} : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Compares two segments for ordering.
     * Returns negative if a < b, 0 if equal, positive if a > b.
     *
     * Comparison order: base number, then minus extension, then plus extension.
     * Examples: 1-1 < 1-1+1 < 1-1+2 < 1 < 1+1 < 1+2 < 2-1 < 2
     */
    private int compareSegments(String a, String b) {
        int[] partsA = parseSegmentParts(a);
        int[] partsB = parseSegmentParts(b);

        for (int i = 0; i < 3; i++) {
            int cmp = Integer.compare(partsA[i], partsB[i]);
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }
}
