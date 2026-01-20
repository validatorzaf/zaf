package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Daterange;
import cz.zaf.schema.ead3.Dateset;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import cz.zaf.schema.ead3.Unitdatestructured;
import java.util.Arrays;
import java.util.List;

public class Rule56 extends EadRule {

    static final public String CODE = "obs56";
    static final public String RULE_TEXT = "Hodnota atributu \"localtype\" elementu <daterange>, který je obsažený v elementu <dateset> odpovídá některé z následujících hodnot: "
            + " CONTENT"
            + " DECLARED"
            + " ORIGIN"
            + " COPY"
            + " SEALING"
            + " ACT_PUBLISHING"
            + " INSERT"
            + " MOLD_CREATION"
            + " USAGE"
            + " PUBLISHING"
            + " MAP_UPDATE"
            + " CAPTURING"
            + " RECORDING"
            + " AWARDING"
            + " AWARD_CER"
            + " WITHDRAWAL"
            + " LEGALLY_EFFECTIVE_FROM"
            + " VALID_FROM"
            + " LEGALLY_EFFECTIVE_TO"
            + " VALID_TO";

    static final public String RULE_ERROR = "Atribut \"localtype\" některého elementu <daterange>, který je obsažen v elementu <dateset> a má atribut \"localtype\", obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 5.8 a 6.1 profilu EAD3 MV ČR";

    static final List<String> validValues = Arrays.asList(
            "CONTENT", "DECLARED", "ORIGIN", "COPY", "SEALING", "ACT_PUBLISHING", "INSERT", "MOLD_CREATION", "USAGE", "PUBLISHING",
            "MAP_UPDATE", "CAPTURING", "RECORDING", "AWARDING", "AWARD_CER", "WITHDRAWAL", "LEGALLY_EFFECTIVE_FROM", "VALID_FROM", "LEGALLY_EFFECTIVE_TO", "VALID_TO"
    );

    public Rule56() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> accessrestrictOrAccrualsOrAcqinfo = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        for (Object archObject : accessrestrictOrAccrualsOrAcqinfo) {
            if (archObject instanceof Relations relations) {
                validateRelations(relations);
            }
        }
        Did didA = archDesc.getDid();
        validateDid(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> mDescBase = c.getMDescBase();
            for (Object mDescBaseObject : mDescBase) {
                if (mDescBaseObject instanceof Relations relations) {
                    validateRelations(relations);
                }
            }
            Did didC = c.getDid();
            validateDid(didC);
        });
    }

    private void validateRelations(Relations relations) {
        List<Relation> listRelation = relations.getRelation();
        for (Relation relation : listRelation) {
            Dateset dateset = relation.getDateset();
            validateDateSet(dateset);
        }
    }

    private void validateDid(Did did) {
        List<Object> mDidList = did.getMDid();
        for (Object didChild : mDidList) {
            if (didChild instanceof Unitdatestructured unitdatestructured) {
                Dateset dateset = unitdatestructured.getDateset();
                validateDateSet(dateset);
            }
        }
    }

    private void validateDateSet(Dateset dateset) {
        if (dateset != null) {
            Daterange daterange = dateset.getDaterange();
            if (daterange != null) {
                String localtype = daterange.getLocaltype();
                if (localtype != null) {
                    if (!validValues.contains(localtype)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut localtype obsahuje nepovolenou hodnotu: " + localtype + ".", ctx.formatEadPosition(daterange));
                    }
                }
            }
        }
    }

}
