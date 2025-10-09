package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Geogname;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import java.util.List;

public class Rule85 extends EadRule {

    static final public String CODE = "obs85";
    static final public String RULE_TEXT = "Každý element <ead:relation> s atributem \"relationtype\" o hodnotě \"otherrelationtype\" a zároveň s atributem \"otherrelationtype\" o hodnotě \"COORDINATES\" obsahuje právě jeden element <ead:geogname>.";
    static final public String RULE_ERROR = "Element <ead:relation> s atributem \"relationtype\" o hodnotě \"otherrelationtype\" a zároveň s atributem \"otherrelationtype\" o hodnotě \"COORDINATES\" neobsahuje právě jeden element <ead:geogname>.";
    static final public String RULE_SOURCE = "Část 6.10 profilu EAD3 MV ČR";

    public Rule85() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> childrenListA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(childrenListA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> childrenListC = c.getMDescBase();
            validate(childrenListC);
        });
    }

    private void validate(List<Object> childrenList) {
        for (Object child : childrenList) {
            if (child instanceof Relations relations) {
                List<Relation> relationList = relations.getRelation();
                for (Relation relation : relationList) {
                    String relationtype = relation.getRelationtype();
                    String otherrelationtype = relation.getOtherrelationtype();
                    if (StringUtils.equals("otherrelationtype", relationtype) && StringUtils.equals("COORDINATES", otherrelationtype)) {
                        Geogname geogname = relation.getGeogname();
                        if (geogname == null) {
                            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element geogname.", ctx.formatEadPosition(relation));
                        }
                    }
                }
            }
        }
    }

}
