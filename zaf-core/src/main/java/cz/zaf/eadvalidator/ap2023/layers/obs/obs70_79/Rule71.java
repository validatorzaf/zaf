package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Processinfo;
import java.util.List;

public class Rule71 extends EadRule {

    static final public String CODE = "obs71";
    static final public String RULE_TEXT = "Každý element <processinfo> má atribut \"localtype\" o hodnotě \"ARCHIVIST_NOTE\", nebo \"RULES\", nebo \"DESCRIPTION_DATE\".";
    static final public String RULE_ERROR = "Některý element <processinfo> nemá atribut \"localtype\" nebo jej má špatně vyplněný.";
    static final public String RULE_SOURCE = "Část 5.23, 5.24 a 5.25 profilu EAD3 MV ČR";

    public Rule71() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:archdesc, c
        //archdesc

        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> childListA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(childListA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> childListC = c.getMDescBase();
            validate(childListC);
        });
    }

    private void validate(List<Object> childList) {
        for (Object child : childList) {
            if (child instanceof Processinfo processinfo) {
                String atrLocalType = processinfo.getLocaltype();
                if (StringUtils.isEmpty(atrLocalType)) {
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu processinfo.", ctx.formatEadPosition(processinfo));
                }
                if (!("ARCHIVIST_NOTE".equals(atrLocalType) || "RULES".equals(atrLocalType) || "DESCRIPTION_DATE".equals(atrLocalType))) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nepovolená hodnota atributu localtype: " + atrLocalType + ".", ctx.formatEadPosition(processinfo));
                }
            }
        }
    }

}
