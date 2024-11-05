package cz.zaf.premisvalidator.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.LinkingAgentIdentifierComplexType;
import cz.zaf.schema.premis3.LinkingObjectIdentifierComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.SignificantPropertiesComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule20 extends PremisRule {

	public static final String CODE = "obs20";
	public static final String RULE_TEXT = "Je správně uvedena vazba na měněný AIP.";
	public static final String RULE_ERROR = "Chybně uvedena vazba na měněný AIP.";
	public static final String RULE_SOURCE = "CZDAP-IPF0201";

	public Rule20() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		EventComplexType eventIngest = null;
		for(EventComplexType event: premis.getEvent()) {
			if(PremisConstants.EVENT_TYPE_ING.equals(event.getEventType().getValue())) {
				if(eventIngest!=null) {
					throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Duplicitní událost ING.", ctx.formatPosition(premis));
				}
				checkEventIngest(event);
				eventIngest = event;
			}
		}
		if(eventIngest==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/eventType=ING.", ctx.formatPosition(premis));
		}
	}

	private void checkEventIngest(EventComplexType event) {

		// Kontrola software a submitter
		int submitterCnt = 0;
		int softwareCnt = 0;
		
		for(LinkingAgentIdentifierComplexType agentRef: event.getLinkingAgentIdentifier()) {
			if(agentRef.getLinkingAgentRole().size()!=1) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena jedna platna role agenta.", ctx.formatPosition(agentRef));
			}
			// rozpoznat lze jen local identifikatory
			if(!PremisConstants.IDENT_TYPE_LOCAL.equals(agentRef.getLinkingAgentIdentifierType().getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingAgentIdentifier/linkingAgentIdentifierType=LOCAL.", ctx.formatPosition(agentRef));
			}

			StringPlusAuthority role = agentRef.getLinkingAgentRole().get(0);
			if(PremisConstants.ROLE_SUBMITTER.equals(role.getValue())) {
				// CZDAX-PKG0603: Předávající strana MUSÍ být zapsána formou agenta a MUSÍ být uvedena pomocí vztahu SUBMITTER. 
				//                V rámci jedné události MŮŽE být uveden nanejvýš jeden předávající.
				if(submitterCnt>0) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen opakovany element linkingAgentIdentifier/linkingAgentRole=SUBMITTER.", ctx.formatPosition(agentRef));
				}
				submitterCnt++;
								
				AgentComplexType agent = ctx.getAgentById(PremisConstants.IDENT_TYPE_LOCAL, agentRef.getLinkingAgentIdentifierValue());
				if(agent==null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen agent: "+agentRef.getLinkingAgentIdentifierValue()+".", ctx.formatPosition(agentRef));
				}
				
			} else 
			if(PremisConstants.ROLE_IMP.equals(role.getValue())) {
				// CZDAX-PKG0604: Přejímající archiv reprezentovaný agentem MUSÍ být uveden a to pomocí vztahu CURATOR. 
				//                V rámci jedné události MŮŽE být uveden nanejvýš jeden přejímající.
				if(softwareCnt>0) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen opakovany element linkingAgentIdentifier/linkingAgentRole=IMP.", ctx.formatPosition(agentRef));
				}
				softwareCnt++;
								
				AgentComplexType agent = ctx.getAgentById(PremisConstants.IDENT_TYPE_LOCAL, agentRef.getLinkingAgentIdentifierValue());
				if(agent==null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen agent: "+agentRef.getLinkingAgentIdentifierValue()+".", ctx.formatPosition(agentRef));
				}
				// kontrola, zda je agent software
				if(!PremisConstants.AGENT_TYPE_SOFTWARE.equals(agent.getAgentType().getValue())) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Agent: "+agentRef.getLinkingAgentIdentifierValue()+" neni typu software.", ctx.formatPosition(agentRef));
				}
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozponana role agenta: "+role.getValue()+".", ctx.formatPosition(agentRef));
			}
		}
		
		if(submitterCnt==0) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingAgentIdentifier/linkingAgentRole=SUBMITTER.", ctx.formatPosition(event));
		}
		if(softwareCnt==0) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingAgentIdentifier/linkingAgentRole=sof.", ctx.formatPosition(event));
		}
		
		String srcPackageId = null, thisPackageId = null;
		// Kontrola napojených objektů
		for(LinkingObjectIdentifierComplexType objRef: event.getLinkingObjectIdentifier()) {
			if(objRef.getLinkingObjectRole().size()!=1) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena jedna platna role objektu.", ctx.formatPosition(objRef));
			}
			
			// kontrola nepojených objektů
			if(!PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingObjectIdentifier/linkingObjectIdentifierType=LOCAL.", ctx.formatPosition(objRef));
			}
			
			StringPlusAuthority role = objRef.getLinkingObjectRole().get(0);
			if(PremisConstants.ROLE_SOU.equals(role.getValue())) {
				if(srcPackageId!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen opakovany element linkingObjectIdentifier/linkingObjectRole=SOU.", ctx.formatPosition(objRef));
				}
				srcPackageId = objRef.getLinkingObjectIdentifierValue();
			} else
			if(PremisConstants.ROLE_OUT.equals(role.getValue())) {
				if(thisPackageId!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen opakovany element linkingObjectIdentifier/linkingObjectRole=OUT.", ctx.formatPosition(objRef));
				}
				if(!EarkCz.OBJECT_IDENTIFIER_ITSELF.equals(objRef.getLinkingObjectIdentifierValue())) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Výsledkem změny musí být objekt: "+EarkCz.OBJECT_IDENTIFIER_ITSELF+".", ctx.formatPosition(objRef));
				}
				thisPackageId = objRef.getLinkingObjectIdentifierValue();
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozpoznaná role: "+role.getAuthority()+".", ctx.formatPosition(objRef));
			}
				
		}
		
		if(srcPackageId==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingObjectIdentifier/linkingObjectRole=SOU.", ctx.formatPosition(event));
		}
		
		if(thisPackageId==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingObjectIdentifier/linkingObjectRole=OUT.", ctx.formatPosition(event));
		}
		
		// Check if source package definition includes AIP_ID and AIP_VERSION
		IntellectualEntity srcPackage = ctx.getIntelEntById(PremisConstants.IDENT_TYPE_LOCAL, srcPackageId);
		if(srcPackage==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen objekt: "+srcPackageId+".", ctx.formatPosition(event));
		}
		String aipId = null;
		for(ObjectIdentifierComplexType objIdent: srcPackage.getObjectIdentifier()) {
			StringPlusAuthority identType = objIdent.getObjectIdentifierType();
			if(EarkCz.OBJECT_IDENTIFIER_AIP_ID.equals(identType.getValue())) {
				if(aipId!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen opakovany element objectIdentifier/objectIdentifierType=AIP_ID.", ctx.formatPosition(objIdent));
				}
				aipId = objIdent.getObjectIdentifierValue();
			}
		}
		if(aipId==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element objectIdentifier/objectIdentifierType=AIP_ID.", ctx.formatPosition(srcPackage));
		}
		// Kontrola verze
		String aipVersion = null;
		for(SignificantPropertiesComplexType signProp: srcPackage.getSignificantProperties()) {
			if(EarkCz.SIGNIFICANT_PROPS_AIP_VERSION.equals(signProp.getSignificantPropertiesType1().getValue())) {
				if(aipVersion!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen opakovany element significantProperties/significantPropertiesType=AIP_VERSION.", ctx.formatPosition(signProp));
				}
				aipVersion = signProp.getSignificantPropertiesValue1();
			}
		}
		if(aipVersion==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element significantProperties/significantPropertiesType=AIP_VERSION.", ctx.formatPosition(srcPackage));
		}
	}
}
