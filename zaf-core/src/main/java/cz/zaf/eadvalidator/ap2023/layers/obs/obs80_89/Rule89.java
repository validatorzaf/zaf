package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Materialspec;
import java.io.Serializable;
import java.util.List;

public class Rule89 extends EadRule {

    static final public String CODE = "obs89";
    static final public String RULE_TEXT = "Každý element <ead:materialspec> s atributem \"localtype\" o hodnotě \"ORIENTATION\" obsahuje prostou textovou hodnotu.";
    static final public String RULE_ERROR = "Některý element <ead:materialspec> s atributem \"localtype\" o hodnotě \"ORIENTATION\" neobsahuje prostou textovou hodnotu.";
    static final public String RULE_SOURCE = "Část 6.11 profilu EAD3 MV ČR";

    public Rule89() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validate(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validate(didC);
        });
    }

    private void validate(Did did) {
        List<Object> mDidList = did.getMDid();
        Materialspec found = null;
        for (Object child : mDidList) {
            if (child instanceof Materialspec materialspec) {
                String localtype = materialspec.getLocaltype();
                if (StringUtils.equals("ORIENTATION", localtype)) {
                    if (found != null) {
                        throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element materialspec.", ctx.formatEadPosition(materialspec));
                    }
                    validateContent(materialspec);
                    found = materialspec;
                }
            }
        }
    }

    private void validateContent(Materialspec materialspec) {
        List<Serializable> content = materialspec.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(materialspec));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (!StringUtils.isNotBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(materialspec));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(materialspec));
        }
    }

}
