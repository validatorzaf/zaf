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

public class Rule84 extends EadRule {

    static final public String CODE = "obs84";
    static final public String RULE_TEXT = "Každý element <ead:materialspec> s atributem \"localtype\" o hodnotě \"SCALE\", nebo \"ORIENTATION\", nebo \"VOLUME\" obsahuje prostou textovou hodnotu.";
    static final public String RULE_ERROR = "Některý element <ead:materialspec> s atributem \"localtype\" o hodnotě \"SCALE\", nebo \"ORIENTATION\", nebo \"VOLUME\" neobsahuje prostou textovou hodnotu.";
    static final public String RULE_SOURCE = "Část 6.9, 6.11 a 6.13 profilu EAD3 MV ČR";

    public Rule84() {
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
        List<Object> childList = did.getMDid();
        int scaleCount = 0;
        int orientationCOunt = 0;
        int volumeCOunt = 0;

        for (Object child : childList) {
            if (child instanceof Materialspec materialspec) {
                String localtype = materialspec.getLocaltype();
                if (StringUtils.equals("SCALE", localtype)) {
                    scaleCount++;
                    validateContent(materialspec);
                }
                if (StringUtils.equals("ORIENTATION", localtype)) {
                    orientationCOunt++;
                    validateContent(materialspec);
                }
                if (StringUtils.equals("VOLUME", localtype)) {
                    volumeCOunt++;
                    validateContent(materialspec);
                }
                if (scaleCount > 1 || orientationCOunt > 1 || volumeCOunt > 1) {
                    duplicityError(materialspec, localtype);
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
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(materialspec));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(materialspec));
        }
    }

    private void duplicityError(Materialspec materialspec, String localtype) {
        throw new ZafException(BaseCode.DUPLICITA, "Nalezen duplicitní element materialspec s atributem localtype o hodnotě: " + localtype + ".", ctx.formatEadPosition(materialspec));
    }

}
