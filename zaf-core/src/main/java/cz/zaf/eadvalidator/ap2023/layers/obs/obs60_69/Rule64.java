package cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69;

import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Accruals;
import cz.zaf.schema.ead3.Archdesc;
import java.util.List;

public class Rule64 extends EadRule {

    static final public String CODE = "obs64";
    static final public String RULE_TEXT = "Každý element <ead:accruals> obsahuje právě jeden neprázdný element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:accruals> neobsahuje právě jeden element <ead:p>. Případně je element <ead:p> prázdný.";
    static final public String RULE_SOURCE = "Část 5.15 profilu EAD3 MV ČR";

    public Rule64() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:accruals, archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> archDescChildList = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(archDescChildList);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> cChildList = c.getMDescBase();
            validate(cChildList);
        });
    }

    private void validate(List<Object> childList) {
        for (Object child : childList) {
            if (child instanceof Accruals mainElement) {
                List<Object> cHistChilds = mainElement.getChronlistOrListOrTable();
                checkSingleElementP(cHistChilds, mainElement);
            }
        }
    }
}
