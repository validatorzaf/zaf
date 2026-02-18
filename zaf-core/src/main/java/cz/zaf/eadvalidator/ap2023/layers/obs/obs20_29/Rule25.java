package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.ProfileRevision;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Localcontrol;
import cz.zaf.schema.ead3.Term;
import java.util.List;
import cz.zaf.schemas.ead.EadNS;

public class Rule25 extends EadRule {

    static final public String CODE = "obs25";
    static final public String RULE_TEXT = "Soubor dle profilu musí mít uvedenu verzi profilu v elementu <localcontrol localtype=\"CZ_FINDING_AID_EAD_PROFILE\">.";
    static final public String RULE_ERROR = "Element <control> neobsahuje právě jeden element <localcontrol> s atributem localtype=\"CZ_FINDING_AID_EAD_PROFILE\" s očekávanou hodnotou.";
    static final public String RULE_SOURCE = "Část 2.6.2 profilu EAD3 MV ČR";

    public Rule25() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Localcontrol localControl = getSingleLocalControl(EadNS.LOCALTYPE_FINDING_AID_EAD_PROFILE);
        if(localControl==null) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezen element <localControl localType=\""+EadNS.LOCALTYPE_FINDING_AID_EAD_PROFILE+"\">.",
            		ctx.formatEadPosition(ctx.getEad().getControl()));        	
        }
        Term term = localControl.getTerm();
        if(term==null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element <localcontrol> neobsahuje element <term>.", ctx.formatEadPosition(localControl));	        
        }
        String identifier = term.getIdentifier();
        if (EadNS.IDENTIFIER_EAD3_PROFILE_20240301.equals(identifier)) {
        	ctx.setProfileRevision(ProfileRevision.CZ_EAD3_PROFILE_20240301);
        	// TODO: check value of element
        } else {
        	throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nerozponavá revize profilu, atribut identifier obsahuje hodnotu: " + identifier + ".", ctx.formatEadPosition(term));
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
