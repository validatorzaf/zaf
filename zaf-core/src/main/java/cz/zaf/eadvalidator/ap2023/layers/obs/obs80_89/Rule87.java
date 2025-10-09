package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Geogname;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import java.io.Serializable;
import java.util.List;

public class Rule87 extends EadRule {

    static final public String CODE = "obs87";
    static final public String RULE_TEXT = "Element <ead:part>, který je obsažen v elementu <ead:geogname>, obsahuje hodnotu \"5.2.6 Souřadnice\".";
    static final public String RULE_ERROR = "Elementu <ead:part>, který je obsažen v elementu <ead:relation> s atributem \"otherrelationtype\" o hodnotě \"COORDINATES\", neobsahuje hodnotu \"5.2.6 Souřadnice\".";
    static final public String RULE_SOURCE = "Část 6.10 profilu EAD3 MV ČR";

    public Rule87() {
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
                    Geogname geogname = relation.getGeogname();
                    if (geogname != null) {
                        List<Part> partList = geogname.getPart();
                        for (Part part : partList) {
                            validateContent(part);
                        }
                    }
                }
            }
        }
    }

    private void validateContent(Part part) {
        List<Serializable> content = part.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(part));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (!StringUtils.isNotBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(part));
            }
            if(!StringUtils.equals("5.2.6 Souřadnice", str)){
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu part.", ctx.formatEadPosition(part));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(part));
        }
    }

}
