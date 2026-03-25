package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.ProfileRevision;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Dao;
import cz.zaf.schema.ead3.Did;
import java.util.List;

public class Rule96b extends EadRule {

    static final public String CODE = "obs96b";
    static final public String RULE_TEXT = "Platné od května 2026: Element <dao> má při odkazování na AIP balíček uveden atribut \"href\". Pokud je uveden atribut s hodnotu \"borndigital\" musí být vyplněn atribut \"href\".";
    static final public String RULE_ERROR = "Element <dao> nemá atribut \"href\".";
    static final public String RULE_SOURCE = "Část 7.1 profilu EAD3 MV ČR";

    public Rule96b() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
    	if(ctx.getProfileRevision()!=ProfileRevision.CZ_EAD3_PROFILE_20260501) {
    		// applies only on newer profiles
    		return;
    	}
    	
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
            if (didChild instanceof Dao dao) {
            	String href = dao.getHref();
                String daotype = dao.getDaotype();
                if("borndigital".equals(daotype)) {
                	if(href==null) {
                		throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut href elementu dao pro digitální archiválii.", ctx.formatEadPosition(dao));
                	}
                } else {
                    // neni digitalni archivalie, neni nutna dalsi kontrola
                	if(href!=null) {
                		// digitalizat muze byt ulozen v balicku
                		ctx.markValidatedAttributeOnly(dao, "href");
                	}
                	return;
                }                
                
                // href ma nastavenu hodnotu (neni null)
                ctx.markValidatedAttributeOnly(dao, "href");
            }
        }
    }

}
