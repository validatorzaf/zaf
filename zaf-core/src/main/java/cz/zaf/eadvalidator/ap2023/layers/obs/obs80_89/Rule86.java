package cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Geogname;
import cz.zaf.schema.ead3.Geographiccoordinates;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import java.util.List;

public class Rule86 extends EadRule {

    static final public String CODE = "obs86";
    static final public String RULE_TEXT = "Každý element <ead:geogname> obsahuje právě jeden element <ead:part> a právě jeden element <ead:geographiccoordinates>.";
    static final public String RULE_ERROR = "Některý element <ead:geogname> neobsahuje právě jeden element <ead:part> a/nebo právě jeden element <ead:geographiccoordinates>.";
    static final public String RULE_SOURCE = "Část 6.10 profilu EAD3 MV ČR";

    public Rule86() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> childrenListA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(childrenListA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> childrenListC = c.getMDescBase();
            validate(childrenListC);
        });
    }

    private void validate(List<Object> childrenList) {
        for (Object child : childrenList) {
            if (child instanceof Relations relations) {
                List<Relation> relationList = relations.getRelation();
                for (Relation relation : relationList) {
                    Geogname geogname = relation.getGeogname();
                    if (geogname != null) {
                        List<Part> part = geogname.getPart();
                        List<Geographiccoordinates> geographiccoordinates = geogname.getGeographiccoordinates();
                        if (part.isEmpty() || geographiccoordinates.isEmpty()) {
                            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen povinný element.", ctx.formatEadPosition(geogname));
                        }
                        if (part.size() != 1) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nenalezen nepovolený element part.", ctx.formatEadPosition(geogname));
                        }
                        if (geographiccoordinates.size() != 1) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nenalezen nepovolený element geographiccoordinates.", ctx.formatEadPosition(geogname));
                        }
                    }
                }
            }
        }
    }

}
