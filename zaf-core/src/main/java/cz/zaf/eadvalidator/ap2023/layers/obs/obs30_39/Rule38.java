package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import java.io.Serializable;
import java.util.List;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.ProfileRevision;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Otherfindaid;
import cz.zaf.schema.ead3.P;

public class Rule38 extends EadRule {

    static final public String CODE = "obs38";
    static final public String RULE_TEXT = "Element <otherfindaid> musí obsahovat atribut localtype s hodnotout \"MIGHT_EXIST\". Pokud je uveden v podřízené jednotce popisu, tak v rodičovské musí existovat shodný element. Každý takový element obsahuje právě jeden podřízený element <p> s hodnotou: \"Pro úroveň popisu existují nebo vzniknou další archivní pomůcky.\" a může být v dané úrovni nanejvýš jednou.";
    static final public String RULE_ERROR = "Element <otherfindaid> neobsahuje správně nastavený atribut \"localtype\" nebo je použit opakovaně, či neobsahuje správně uvedený vnořený element <p>.";
    static final public String RULE_SOURCE = "Část 3.4 profilu EAD3 MV ČR";

    static final String EXPECTED_LOCALTYPE_OLD = "MightExist";
    static final String EXPECTED_LOCALTYPE = "MIGHT_EXIST";
    static final String EXPECTED_P_VALUE = "Pro úroveň popisu existují nebo vzniknou další archivní pomůcky.";
    
    boolean tolerateOldLocaltype;

    public Rule38() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
    	// flag if old type MightExist may be used
    	tolerateOldLocaltype = (ctx.getProfileRevision()==ProfileRevision.CZ_EAD3_PROFILE_20230601||
    			ctx.getProfileRevision()==ProfileRevision.CZ_EAD3_PROFILE_20240301);
    	
        Archdesc archDesc = ctx.getEad().getArchdesc();
        boolean archDescHasOtherfindaid = validateLevel(archDesc.getAccessrestrictOrAccrualsOrAcqinfo(), archDesc);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            boolean parentHasOtherfindaid;
            if (parent == null) {
                parentHasOtherfindaid = archDescHasOtherfindaid;
            } else {
                parentHasOtherfindaid = hasOtherfindaid(parent.getMDescBase());
            }

            boolean childHasOtherfindaid = validateLevel(c.getMDescBase(), c);

            if (childHasOtherfindaid && !parentHasOtherfindaid) {
                throw new ZafException(BaseCode.CHYBNY_ELEMENT,
                        "Element otherfindaid je uveden v podřízené úrovni, ale chybí v rodičovské úrovni.",
                        ctx.formatEadPosition(c));
            }
        });
    }

    private boolean hasOtherfindaid(List<Object> children) {
        for (Object obj : children) {
            if (obj instanceof Otherfindaid) {
                return true;
            }
        }
        return false;
    }

    private boolean validateLevel(List<Object> children, Object levelElement) {
        Otherfindaid found = null;
        for (Object obj : children) {
            if (obj instanceof Otherfindaid otherfindaid) {
                if (found != null) {
                    throw new ZafException(BaseCode.DUPLICITA,
                            "Opakovaný výskyt elementu otherfindaid.",
                            ctx.formatEadPosition(otherfindaid));
                }
                found = otherfindaid;
                validateOtherfindaid(otherfindaid);
            }
        }
        return found != null;
    }

    private void validateOtherfindaid(Otherfindaid otherfindaid) {
        ctx.markValidatedElement(otherfindaid);

        String localtype = otherfindaid.getLocaltype();
        if (!EXPECTED_LOCALTYPE.equals(localtype)&&
        	!(tolerateOldLocaltype&&EXPECTED_LOCALTYPE_OLD.equals(localtype)) ) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                    "Atribut localtype nemá hodnotu \"MIGHT_EXIST\".",
                    ctx.formatEadPosition(otherfindaid));
        }
        ctx.markValidatedAttribute(otherfindaid, "localtype");

        List<Object> chronlistOrListOrTable = otherfindaid.getChronlistOrListOrTable();
        P pFound = null;
        for (Object child : chronlistOrListOrTable) {
            if (child instanceof P p) {
                if (pFound != null) {
                    throw new ZafException(BaseCode.DUPLICITA,
                            "Opakovaný výskyt elementu p v otherfindaid.",
                            ctx.formatEadPosition(p));
                }
                pFound = p;
            } else {
                throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT,
                        "Nepovolený element v otherfindaid.",
                        ctx.formatEadPosition(child));
            }
        }
        if (pFound == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT,
                    "Chybí element p v otherfindaid.",
                    ctx.formatEadPosition(otherfindaid));
        }
        ctx.markValidatedElement(pFound);
        ctx.markValidatedContent(pFound);

        List<Serializable> pContent = pFound.getContent();
        if (pContent.size() != 1 || !(pContent.get(0) instanceof String str) || !EXPECTED_P_VALUE.equals(str.trim())) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Element p neobsahuje očekávanou hodnotu.",
                    ctx.formatEadPosition(pFound));
        }
    }
}
