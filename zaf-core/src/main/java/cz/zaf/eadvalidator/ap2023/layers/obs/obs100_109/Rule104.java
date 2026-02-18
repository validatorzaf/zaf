package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Descriptivenote;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relationentry;
import cz.zaf.schema.ead3.Relations;
import java.util.List;

public class Rule104 extends EadRule {

    static final public String CODE = "obs104";
    static final public String RULE_TEXT = "Element <relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", obsahuje právě jeden neprázdný element <relationentry> a element <descriptivenote>.";
    static final public String RULE_ERROR = "Element <relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", neobsahuje element <relationentry> a/nebo element <descriptivenote>. Případně je element <relationentry> prázdný.";
    static final public String RULE_SOURCE = "Část 8.2.1 a 8.2.2 profilu EAD3 MV ČR";

    public Rule104() {
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
                List<Relationentry> relationentrys = relation.getRelationentry();
                if (relationentrys.isEmpty()) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element relationentry.", ctx.formatEadPosition(relation));
                }
                if (relationentrys.size() != 1) {
                    throw new ZafException(BaseCode.DUPLICITA, "Nenalezen nepovolený element relationentry.", ctx.formatEadPosition(relation));
                }
                Relationentry relationentry = relationentrys.get(0);
                String content = relationentry.getContent();
                if (StringUtils.isBlank(content)) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element relationentry neobsahuje žádnou hodnotu.", ctx.formatEadPosition(relationentry));
                }

                Descriptivenote descriptivenote = relation.getDescriptivenote();
                if (descriptivenote == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element descriptivenote.", ctx.formatEadPosition(relation));
                }
            }
        }
    }

}
