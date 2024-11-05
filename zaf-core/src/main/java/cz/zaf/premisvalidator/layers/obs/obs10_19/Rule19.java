package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.LinkingAgentIdentifierComplexType;
import cz.zaf.schema.premis3.LinkingObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule19 extends PremisRule {

	public static final String CODE = "obs19";
	public static final String RULE_TEXT = "Správné uvedení doplňujících informací ke vzniku balíčku.";
	public static final String RULE_ERROR = "Nesprávně uvedené informace v souvislosti se zvnikem balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PKG0901, CZDAX-PKG0902, CZDAX-PKG0903";

	public Rule19() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		for(EventComplexType event: premis.getEvent()) {
			if(PremisConstants.EVENT_TYPE_IPC.equals(event.getEventType().getValue())) {
				checkEventIpc(event);
			}
		}
	}

	private void checkEventIpc(EventComplexType event) {
		
		// CZDAX-PKG0903: Aplikace realizující vznik balíčku MUSÍ být reprezentovaná agentem a 
		//        to pomocí vztahu imp (viz implementer). V rámci jedné události MŮŽE být uveden nanejvýš jeden vztah tohoto typu.
		
		int impCount = 0;
		for(LinkingAgentIdentifierComplexType agentRef: event.getLinkingAgentIdentifier()) {
			if(agentRef.getLinkingAgentRole().size()!=1) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena jedna platna role agenta.", ctx.formatPosition(agentRef));
			}
			// rozpoznat lze jen local identifikatory
			if(!PremisConstants.IDENT_TYPE_LOCAL.equals(agentRef.getLinkingAgentIdentifierType().getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingAgentIdentifier/linkingAgentIdentifierType=LOCAL.", ctx.formatPosition(agentRef));
			}			
			StringPlusAuthority role = agentRef.getLinkingAgentRole().get(0);
			if(PremisConstants.ROLE_IMP.equals(role.getValue())) {
				if(impCount>0) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Uveden opakovaný element linkingAgentIdentifier/linkingAgentRole=IMP.", ctx.formatPosition(agentRef));
				}
				// vyhledani vztahu imp
				AgentComplexType impAgent = ctx.getAgentById(PremisConstants.IDENT_TYPE_LOCAL, agentRef.getLinkingAgentIdentifierValue());
				if(impAgent==null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen agent: "+agentRef.getLinkingAgentIdentifierValue()+".", ctx.formatPosition(agentRef));
				}
				// overeni, ze je agent typu software
				if(!PremisConstants.AGENT_TYPE_SOFTWARE.equals(impAgent.getAgentType().getValue())) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Agent: "+agentRef.getLinkingAgentIdentifierValue()+" neni typu software.", ctx.formatPosition(agentRef));
				}
				impCount++;
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozponana role agenta: "+role.getValue()+".", ctx.formatPosition(agentRef));
			}
		}
		
		if(impCount==0) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingAgentIdentifier/linkingAgentRole=IMP.", ctx.formatPosition(event));
		}

		String packageId = null;
		for(LinkingObjectIdentifierComplexType objRef: event.getLinkingObjectIdentifier()) {
			if(objRef.getLinkingObjectRole().size()!=1) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena jedna platna role objektu.", ctx.formatPosition(objRef));
			}
			StringPlusAuthority role = objRef.getLinkingObjectRole().get(0);
			if(PremisConstants.ROLE_OUT.equals(role.getValue())) {
				// CZDAX-PKG0903: Aplikace realizující vznik balíčku MUSÍ být reprezentovaná agentem a 
				//  to pomocí vztahu imp (viz implementer). V rámci jedné události MŮŽE být uveden nanejvýš jeden vztah tohoto typu.
				if(!PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingObjectIdentifier/linkingObjectIdentifierType=LOCAL.", ctx.formatPosition(objRef));
				}
				
				IntellectualEntity obj = ctx.getIntelEntById(PremisConstants.IDENT_TYPE_LOCAL, objRef.getLinkingObjectIdentifierValue());
				if(obj==null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen objekt: "+objRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(objRef));
				} else {
					if(packageId!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Uveden opkazný element linkingObjectIdentifier/linkingObjectIdentifierType=LOCAL.", ctx.formatPosition(objRef));
					}
					packageId = objRef.getLinkingObjectIdentifierValue();
				}				
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozpoznaná role: "+role.getAuthority()+".", ctx.formatPosition(objRef));
			}
		}
		if(packageId==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen odkaz na cílový balíček.", ctx.formatPosition(event));
		}
		
	}
}
