package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Corpname;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Famname;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.Origination;
import cz.zaf.schema.ead3.Persname;
import java.util.List;

public class Rule99 extends EadRule {

    static final public String CODE = "obs99";
    static final public String RULE_TEXT = "Každý element <ead:origination> obsahuje alespoň jeden element z následujících elementů: - <ead:persname> - <ead:famname> - <ead:corpname> - <ead:name> přičemž každý tento element má atribut \"localtype\" o hodnotě \"ORIGINATOR\".";
    static final public String RULE_ERROR = "Některý element <ead:origination> obsahuje nesprávný element. Případně vnořený element nemá atribut \"localtype\" nebo tento atribut neobsahuje hodnotu \"ORIGINATOR\".";
    static final public String RULE_SOURCE = "Část 8.1 profilu EAD3 MV ČR";
    private static boolean found = false;

    public Rule99() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validateDid(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validateDid(didC);
        });
    }

    private void validateDid(Did did) {
        List<Object> didChildren = did.getMDid();
        for (Object didChild : didChildren) {
            if (didChild instanceof Origination origination) {
                List<Object> originationChildList = origination.getCorpnameOrFamnameOrName();
                for (Object originationChild : originationChildList) {
                    if (originationChild instanceof Persname persname) {
                        String localtype = persname.getLocaltype();
                        validate(persname, localtype);
                    }
                    if (originationChild instanceof Corpname corpname) {
                        String localtype = corpname.getLocaltype();
                        validate(corpname, localtype);
                    }

                    if (originationChild instanceof Famname famname) {
                        String localtype = famname.getLocaltype();
                        validate(famname, localtype);
                    }

                    if (originationChild instanceof Name name) {
                        String localtype = name.getLocaltype();
                        validate(name, localtype);
                    }
                }
                if (!found) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen očekávaný element.", ctx.formatEadPosition(origination));
                }
            }
        }
    }

    private void validate(Object element, String localType) {
        found = true;
        if (!StringUtils.equals("ORIGINATOR", localType)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut localtype nemá očekávanou hodnotu.", ctx.formatEadPosition(element));
        }
    }

}
