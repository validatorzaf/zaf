package cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79;

import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Processinfo;
import java.util.List;

public class Rule72 extends EadRule {

    static final public String CODE = "obs72";
    static final public String RULE_TEXT = "Každý element <ead:processinfo> obsahuje právě jeden element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:processinfo> neobsahuje právě jeden <ead:p>.";
    static final public String RULE_SOURCE = "Část 5.23, 5.24 a 5.25 profilu EAD3 MV ČR";

    public Rule72() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, processinfo
        //ead:archdesc
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
            if (child instanceof Processinfo mainElement) {
                List<Object> cHistChilds = mainElement.getChronlistOrListOrTable();
                checkSingleElementP(cHistChilds, mainElement);
            }
        }
    }
}
