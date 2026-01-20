package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import java.util.List;

public class Rule101 extends EadRule {

    static final public String CODE = "obs101";
    static final public String RULE_TEXT = "Každý element <relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", má atributy \"linktitle\" a zároveň \"linkrole\".";
    static final public String RULE_ERROR = "Některý element <relation>, který má atribut \"relationtype\" o hodnotě \"cpfrelation\" nebo \"resourcerelation\", nemá atribut \"linktitle\" a/nebo \"linkrole\" a/nebo \"altrender\".";
    static final public String RULE_SOURCE = "Část 8.2 profilu EAD3 MV ČR";

    public Rule101() {
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
                        String linkrole = relation.getLinkrole();
                        String linktitle = relation.getLinktitle();
                        if (StringUtils.isEmpty(linkrole) || StringUtils.isEmpty(linktitle)) {
                            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nenalezena požadovaná hodnota atributu linkrole a linktitle.", ctx.formatEadPosition(relation));
                        }
                    }
                }
            }
        }
    }
}
