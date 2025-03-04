package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.FindingAidType;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Term;
import java.util.List;
import cz.zaf.schemas.ead.EadNS;

public class Rule23 extends EadRule {

    static final public String CODE = "obs23";
    static final public String RULE_TEXT = "Druh archivní pomůcky se uvádí v elementu <localcontrol> s uvedením atributu localtype=\"FINDING_AID_TYPE\".";
    static final public String RULE_ERROR = "Element <ead:control> neobsahuje element <ead:localcontrol> s atributem \"localtype\" s očekávanou hodnotou a elementem <ead:term>.";
    static final public String RULE_SOURCE = "Část 2.6 profilu EAD3 MV ČR. EAD TLV heslo <term>";

    public Rule23() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Localcontrol localControl = getSingleLocalControl(EadNS.LOCALTYPE_FINDING_AID_TYPE);
        if(localControl==null) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezen element <localControl localType=\""+EadNS.LOCALTYPE_FINDING_AID_TYPE+"\">.",
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
        FindingAidType findingAidType;
        try {
			findingAidType = FindingAidType.valueOf(identifier);
		} catch (Exception e) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nerozponaná druh archivní pomůcky, atribut obsahuje hodnotu: " + identifier + ".", ctx.formatEadPosition(term));
		}
        ctx.setFindingAidType(findingAidType);
        
        // kontrola popisu
        String content = term.getContent();
        if(!findingAidType.getFindingAidName().equals(content)) {
        	throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybně uveden název typu archivní pomůcky, očekávaná hodnota: "+findingAidType.getFindingAidName()+", hodnota elementu: "+content, ctx.formatEadPosition(term));
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
