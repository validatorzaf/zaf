package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Unitid;

/**
 * obs50a - Validace bezvýznamového prvku popisu.
 *
 * Bezvýznamový prvek popisu je element <unitid> s atributem localtype="ID"
 * (část 3.9 profilu). Slouží k naplnění požadavku schématu EAD3 na neprázdný
 * element <did> u jednotky popisu bez prvků popisu. Kontroluje se:
 * 1. Element je jediným elementem v rodičovském elementu <did>
 * 2. Hodnota elementu se shoduje s hodnotou atributu "id" jednotky popisu
 *    (element <archdesc> nebo <c>)
 *
 * Povolené hodnoty atributů localtype a label kontroluje obs50.
 */
public class Rule50a extends EadRule {

    static final public String CODE = "obs50a";
    static final public String RULE_TEXT = "Pokud element <did> obsahuje element <unitid> s atributem \"localtype\" o hodnotě \"ID\" (bezvýznamový prvek popisu), je tento element jediným elementem v elementu <did> a jeho hodnota se shoduje s hodnotou atributu \"id\" jednotky popisu.";
    static final public String RULE_ERROR = "Některý element <unitid> s atributem \"localtype\" o hodnotě \"ID\" není jediným elementem v elementu <did> nebo jeho hodnota neodpovídá hodnotě atributu \"id\" jednotky popisu.";
    static final public String RULE_SOURCE = "Část 3.9 profilu EAD3 MV ČR";

    static final public String LOCALTYPE_ID = "ID";

    public Rule50a() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        validate(archDesc.getDid(), archDesc.getId());

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            validate(c.getDid(), c.getId());
        });
    }

    private void validate(Did did, String unitOfDescId) {
        if (did == null) {
            return;
        }
        List<Object> mDid = did.getMDid();
        for (Object obj : mDid) {
            if (obj instanceof Unitid unitid && LOCALTYPE_ID.equals(unitid.getLocaltype())) {
                try {
                    validateIdUnitid(unitid, mDid.size(), unitOfDescId);
                } catch (ZafException zfe) {
                    // recoverable error, report it and continue with next unitid
                    ctx.addError(zfe);
                }
            }
        }
    }

    private void validateIdUnitid(Unitid unitid, int didElementCount, String unitOfDescId) {
        if (didElementCount > 1) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Bezvýznamový prvek popisu (element <unitid> s atributem localtype=\"ID\") není jediným elementem v elementu <did>.",
                    ctx.formatEadPosition(unitid));
        }
        String value = getTextContent(unitid);
        if (value == null) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Bezvýznamový prvek popisu má prázdnou hodnotu.",
                    ctx.formatEadPosition(unitid));
        }
        if (StringUtils.isEmpty(unitOfDescId)) {
            throw new ZafException(BaseCode.CHYBI_ATRIBUT,
                    "Jednotka popisu s bezvýznamovým prvkem popisu nemá atribut \"id\".",
                    ctx.formatEadPosition(unitid));
        }
        if (!value.equals(unitOfDescId)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Hodnota bezvýznamového prvku popisu (" + value
                    + ") se neshoduje s hodnotou atributu \"id\" jednotky popisu (" + unitOfDescId + ").",
                    ctx.formatEadPosition(unitid));
        }
        ctx.markValidatedContent(unitid);
    }

    /**
     * Gets text content from a Unitid element.
     */
    private String getTextContent(Unitid unitid) {
        StringBuilder sb = new StringBuilder();
        for (Serializable s : unitid.getContent()) {
            if (s instanceof String str) {
                sb.append(str);
            }
        }
        String result = sb.toString().trim();
        return result.isEmpty() ? null : result;
    }

}
