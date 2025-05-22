package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.DescriptionRules;
import cz.zaf.eadvalidator.ap2023.profile.FindingAidType;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Term;
import java.util.List;
import cz.zaf.schemas.ead.EadNS;

public class Rule27 extends EadRule {

    static final public String CODE = "obs27";
    static final public String RULE_TEXT = "Jsou uvedena pravidla tvorby archivního popisu v elementu <ead:localcontrol localtype=\"RULES\">. Pokud je uveden typ pomůcky, je v souladu s těmito pravidly.";
    static final public String RULE_ERROR = "Element <ead:term>, který je obsažen v elementu <ead:localcontrol> s atributem \"localtype\" o hodnotě \"RULES\", nemá atribut \"identifier\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 2.6, 2.6.1 profilu EAD3 MV ČR";

    public Rule27() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Localcontrol localControl = getSingleLocalControl(EadNS.LOCALTYPE_RULES);
        if(localControl==null) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezen element <localControl localType=\""+EadNS.LOCALTYPE_RULES+"\">.",
            		ctx.formatEadPosition(ctx.getEad().getControl()));        	
        }
        Term term = localControl.getTerm();
        if(term==null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element <ead:localcontrol> neobsahuje element <ead:term>.", ctx.formatEadPosition(localControl));	        
        }
        String identifier = term.getIdentifier();
        if(identifier==null) {
        	throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element <ead:localcontrol> neobsahuje element <ead:term> s atributem identifier.", ctx.formatEadPosition(localControl));
        }
        // zjištění druhu pravidel
        DescriptionRules descriptionRules;
        try {
        	descriptionRules = DescriptionRules.valueOf(identifier);
        } catch(Exception e) {
        	throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nerozponaná pravidla poppisu, atribut obsahuje hodnotu: " + identifier + ".", ctx.formatEadPosition(term));
        }
        ctx.setDescriptionRules(descriptionRules);

        // kontrola popisu
        String content = term.getContent();
        if(!descriptionRules.getRuleName().equals(content)) {
        	throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybně uveden název pravidel, očekávaná hodnota: "+descriptionRules.getRuleName()+", hodnota elementu: "+content, ctx.formatEadPosition(term));
        }
        
        // kontrola přípustnosti pomůcky dle pravidel
        FindingAidType findingAidType = ctx.getFindingAidType();
        if(findingAidType!=null) {
        	if(!descriptionRules.isApplicable(findingAidType)) {
        		throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Pomůcka typu :"+findingAidType + " nemůže být vytvořena dle pravidel: " + descriptionRules.getRuleName() + ".", ctx.formatEadPosition(term));
        	}
        }
    }
    
    private Localcontrol getSingleLocalControl(String localTypeValue) {
        Ead ead = ctx.getEad();
        Control control = ead.getControl();

        Localcontrol found = null;
        
        List<Localcontrol> loccontrol = control.getLocalcontrol();
        for (Localcontrol otherLoccontrol : loccontrol) {
            if (localTypeValue.equals(otherLoccontrol.getLocaltype())) {
            	if(found!=null) {
            		throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, 
            				"Duplicitní element <localControl localType=\""+localTypeValue+"\">.",
            				ctx.formatEadPosition(otherLoccontrol));
            	}
                found = otherLoccontrol;
            }
        }
        return found;
    }
}
