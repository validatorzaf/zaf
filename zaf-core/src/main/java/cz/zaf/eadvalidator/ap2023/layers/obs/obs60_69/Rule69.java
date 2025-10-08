package cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Altformavail;
import cz.zaf.schema.ead3.Archdesc;
import java.util.List;

public class Rule69 extends EadRule {

    static final public String CODE = "obs69";
    static final public String RULE_TEXT = "Element <ead:altformavail> se může vykytovat pouze jednou a obsahuje právě jeden neprázdný element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:altformavail> neobsahuje právě jeden element <ead:p>. Případně je element <ead:p> prázdný.";
    static final public String RULE_SOURCE = "Část 5.20 profilu EAD3 MV ČR";

    public Rule69() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //altformavail, archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> archDescChildList = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(archDescChildList);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> cChildList = c.getMDescBase();
            validate(cChildList);
        });
    }

    private void validate(List<Object> childList) {
        int mainElementCOunt = 0;
        for (Object child : childList) {
            if (child instanceof Altformavail mainElement) {
                mainElementCOunt++;
                if (mainElementCOunt > 1) {
                    throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu.", ctx.formatEadPosition(mainElement));
                }
                List<Object> cHistChilds = mainElement.getChronlistOrListOrTable();
                checkSingleElementP(cHistChilds, mainElement);
            }
        }
    }
}
