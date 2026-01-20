package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relationentry;
import cz.zaf.schema.ead3.Relations;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

public class Rule104a extends EadRule {

    static final public String CODE = "obs104a";
    static final public String RULE_TEXT = "Element <relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", obsahuje právě jeden neprázdný element <relationentry>.";
    static final public String RULE_ERROR = "Element <relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", neobsahuje element <relationentry>.";
    static final public String RULE_SOURCE = "Část 8.2.3 profilu EAD3 MV ČR";

    public Rule104a() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> accessrestrictOrAccrualsOrAcqinfo = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(accessrestrictOrAccrualsOrAcqinfo);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> mDescBase = c.getMDescBase();
            validate(mDescBase);
        });
    }

    private void validate(List<Object> list) {
        for (Object mDescBaseObject : list) {
            if (mDescBaseObject instanceof Relations relations) {
                List<Relation> listRelation = relations.getRelation();
                for (Relation relation : listRelation) {
                    String relationtype = relation.getRelationtype();
                    if (StringUtils.equals("cpfrelation", relationtype) || StringUtils.equals("resourcerelation", relationtype)) {
                        List<Relationentry> relationentry = relation.getRelationentry();
                        if (CollectionUtils.isEmpty(relationentry)) {
                            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element relationentry.", ctx.formatEadPosition(relation));
                        }
                        if (relationentry.size() != 1) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nenalezen nepovolený element relationentry.", ctx.formatEadPosition(relationentry));
                        }
                        Relationentry re = relationentry.get(0);
                        String content = re.getContent();

                        if (StringUtils.isBlank(content)) {
                            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Nenalezena hodnota elementu relationentry.", ctx.formatEadPosition(re));
                        }
                    }
                }
            }
        }
    }

}
