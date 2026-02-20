package cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Originalsloc;
import java.util.List;

public class Rule67 extends EadRule {

    static final public String CODE = "obs67";
    static final public String RULE_TEXT = "Element <originalsloc> se může vykytovat pouze jednou a obsahuje právě jeden neprázdný element <p>.";
    static final public String RULE_ERROR = "Některý element <originalsloc> neobsahuje právě jeden element <p>. Případně je element <p> prázdný.";
    static final public String RULE_SOURCE = "Část 5.18 profilu EAD3 MV ČR";

    public Rule67() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, originalsloc
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
            if (child instanceof Originalsloc mainElement) {
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
