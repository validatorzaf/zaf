package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Bibliography;
import java.util.List;

public class Rule93 extends EadRule {

    static final public String CODE = "obs93";
    static final public String RULE_TEXT = "Každý element <ead:bibliography> obsahuje právě jeden neprázdný element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:bibliography> neobsahuje právě jeden element <ead:p>. Případně element <ead:p> je prázdný.";
    static final public String RULE_SOURCE = "Část 6.16 profilu EAD3 MV ČR";

    public Rule93() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> listA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(listA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> listC = c.getMDescBase();
            validate(listC);
        });
    }

    private void validate(List<Object> list) {
        for (Object child : list) {
            if (child instanceof Bibliography bibliography) {
                List<Object> bibliographyChildren = bibliography.getChronlistOrListOrTable();
                checkSingleElementP(bibliographyChildren, bibliography);
            }
        }
    }
}
