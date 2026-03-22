package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.eadvalidator.ap2023.profile.ProfileRevision;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Dao;
import cz.zaf.schema.ead3.Did;
import java.util.List;

public class Rule96a extends EadRule {

    static final public String CODE = "obs96a";
    static final public String RULE_TEXT = "Platné od května 2026: Každý element <dao> má neprázdný atribut \"identifier\". Pokud je součástí AIPu nebo na něj odkazuje pomocí atributu href musí být uveden i atribut coverge.";
    static final public String RULE_ERROR = "Element <dao> nemá atribut \"identifier\" nebo je tento atribut prázdný, nebo při odkazování na AIP chybí atribut coverage.";
    static final public String RULE_SOURCE = "Část 7.1 profilu EAD3 MV ČR";

    public Rule96a() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
    	if(ctx.getProfileRevision()!=ProfileRevision.CZ_EAD3_PROFILE_20260501) {
    		// applies only on newer profiles
    		return;
    	}    	
    	
    	final boolean requiresCoverage = ctx.getValidationProfile()==AP2023Profile.EARK_INHERENT_DESC ||
    			ctx.getValidationProfile()==AP2023Profile.EARK_CONTEXTUAL_DESC;
    	
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validateDid(didA, requiresCoverage);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validateDid(didC, requiresCoverage);
        });
    }

    private void validateDid(Did did, boolean requiresCoverage) {
        List<Object> didChildren = did.getMDid();
        for (Object didChild : didChildren) {
            if (didChild instanceof Dao dao) {
                String identifier = dao.getIdentifier();
                if(identifier == null){
                    throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut identifier elementu dao.", ctx.formatEadPosition(dao));
                }
                if (StringUtils.isBlank(identifier)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Hodnota atributu identifier elemetu dao není zadána.", ctx.formatEadPosition(dao));
                }
                ctx.markValidatedAttribute(dao, "identifier");
                
                String href = dao.getHref();
                
                String coverage = dao.getCoverage();
                if(coverage!=null) {
                	// mark as validated
                	ctx.markValidatedAttribute(dao, "coverage");
                }

                // pokud coverage neni pozadovano vzdy a neodkazuje na balicek 
                // -> je nepovinne
                if(!requiresCoverage && href==null) {
                	return;
                }
                if(coverage==null) {
                	throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut coverage elementu dao.", ctx.formatEadPosition(dao));
                }
            }
        }
    }

}
