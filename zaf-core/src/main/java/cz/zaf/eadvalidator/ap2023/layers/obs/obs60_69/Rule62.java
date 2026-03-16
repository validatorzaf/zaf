package cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69;

import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Scopecontent;

import java.util.List;

public class Rule62 extends EadRule {

    static final public String CODE = "obs62";
    static final public String RULE_TEXT = "Element <scopecontent> odpovídá pravidlům a obsahuje právě jeden neprázdný element <p>.";
    static final public String RULE_ERROR = "Element <scopecontent> neobsahuje právě jeden element <p>. Případně je element <p> prázdný.";
    static final public String RULE_SOURCE = "Část 5.13 profilu EAD3 MV ČR";

    public Rule62() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, scopecontent

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
            if (child instanceof Scopecontent mainElement) {
                List<Object> cHistChilds = mainElement.getChronlistOrListOrTable();
                checkSingleElementP(cHistChilds, mainElement);
            }
        }
    }
}
