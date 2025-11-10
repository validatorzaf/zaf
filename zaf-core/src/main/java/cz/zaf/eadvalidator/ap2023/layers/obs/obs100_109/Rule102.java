package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import java.util.List;

public class Rule102 extends EadRule {

    static final public String CODE = "obs102";
    static final public String RULE_TEXT = "Hodnota atributu \"altrender\" elementu <ead:relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", je \"inherited\".";
    static final public String RULE_ERROR = "Atribut \"altrender\" některého elementu <ead:relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", neobsahuje \"inherited\".";
    static final public String RULE_SOURCE = "Část 8.2 profilu EAD3 MV ČR";

    public Rule102() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> accessrestrictOrAccrualsOrAcqinfo = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        for (Object archObject : accessrestrictOrAccrualsOrAcqinfo) {
            if (archObject instanceof Relations relations) {
                validate(relations);
            }
        }

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> mDescBase = c.getMDescBase();
            for (Object mDescBaseObject : mDescBase) {
                if (mDescBaseObject instanceof Relations relations) {
                    validate(relations);
                }
            }
        });
    }

    private void validate(Relations relations) {
        List<Relation> listRelation = relations.getRelation();
        for (Relation relation : listRelation) {
            String relationtype = relation.getRelationtype();
            if (StringUtils.equals("cpfrelation", relationtype) || StringUtils.equals("resourcerelation", relationtype)) {
                String altrender = relation.getAltrender();
                if (!StringUtils.equals("inherited", altrender)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezena požadovaná hodnota atributu altrender elementu relation.", ctx.formatEadPosition(relation));
                }
            }
        }
    }

}
