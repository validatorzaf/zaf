package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.LinkingAgentIdentifierComplexType;
import cz.zaf.schema.premis3.LinkingObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule15 extends PremisRule {

	public static final String CODE = "obs15";
	public static final String RULE_TEXT = "Je uveden vznik archiválií zachycených v balíčku.";
	public static final String RULE_ERROR = "Chybně zachycen vznik archiválií v balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PKG0501, CZDAX-PKG0502, CZDAX-PKG0503, CZDAX-PKG0504";

	public Rule15() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		EventComplexType eventCreate = null;
		for(EventComplexType event: premis.getEvent()) {
			if(PremisConstants.EVENT_TYPE_CRE.equals(event.getEventType().getValue())) {
				if(eventCreate!=null) {
					throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Vnějde dva vznik archiválií v balíčku.", ctx.formatPosition(premis));
				}
				checkEventCreate(event);
				eventCreate = event;
			}
		}
		// CZDAX-PKG0501
		if(eventCreate==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/eventType=CRE.", ctx.formatPosition(premis));
		}
	}

	private void checkEventCreate(EventComplexType event) {
		// CZDAX-PKG0502
		String eventDateTime = event.getEventDateTime();
		// format datace kontroluje Rule09		
		// CZDAX-PMS0304 check special value NA
		if(EarkCz.EVENT_DATETIME_NA.equals(eventDateTime)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybně uvedena datace jako NA.", ctx.formatPosition(event));
		}
		
		int originatorCnt = 0;
		// CZDAX-PKG0503: Původce archiválií reprezentovaný agentem MUSÍ být uveden pomocí vztahu ORIGINATOR. V rámci jedné události MŮŽE být uvedeno více původců.
		for(LinkingAgentIdentifierComplexType agentRef: event.getLinkingAgentIdentifier()) {
			if(agentRef.getLinkingAgentRole().size()!=1) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen jeden platn type role agenta.", ctx.formatPosition(agentRef));
			}
			StringPlusAuthority role = agentRef.getLinkingAgentRole().get(0);
			if(!PremisConstants.ROLE_ORIGINATOR.equals(role.getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingAgentIdentifier/linkingAgentRole=ORIGINATOR.", ctx.formatPosition(agentRef));
			}
			
			// rozpoznat lze jen local identifikatory
			if(!PremisConstants.IDENT_TYPE_LOCAL.equals(agentRef.getLinkingAgentIdentifierType().getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingAgentIdentifier/linkingAgentIdentifierType=LOCAL.", ctx.formatPosition(agentRef));
			}
			
			AgentComplexType agent = ctx.getAgentById(PremisConstants.IDENT_TYPE_LOCAL, agentRef.getLinkingAgentIdentifierValue());
			if(agent==null) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen agent: "+agentRef.getLinkingAgentIdentifierValue()+".", ctx.formatPosition(agentRef));
			}
			originatorCnt++;
		}
		if(originatorCnt<1) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingAgentIdentifier/linkingAgentRole=ORIGINATOR.", ctx.formatPosition(event));
		}
		
		// CZDAX-PKG0504: Událost vzniku MUSÍ odkazovat na odpovídající reprezentaci, kde jsou data a metadata od daného původce. 
		//                Odkaz je realizován vztahem na objekt reprezentace pomocí role out ( viz output, 
		//                URI: http://id.loc.gov/vocabulary/preservation/eventRelatedObjectRole/out).
		LinkingObjectIdentifierComplexType subMissionRef = null;
		for(LinkingObjectIdentifierComplexType objRef:event.getLinkingObjectIdentifier()) {
			if(subMissionRef!=null) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Duplicitni odkaz na reprezentaci.", ctx.formatPosition(objRef));
			}
			StringPlusAuthority roleOut = null;
			for(StringPlusAuthority role: objRef.getLinkingObjectRole()) {
				if(!PremisConstants.ROLE_OUT.equals(role.getValue())) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nepodporovaná role.", ctx.formatPosition(role));
				}
				if(roleOut!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Duplicitní role.", ctx.formatPosition(role));
				}
				roleOut = role;
			}
			if(roleOut==null) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element linkingObjectIdentifier/linkingObjectRole=OUT.", ctx.formatPosition(objRef));
			}
			subMissionRef = objRef;
		}
		
		if(subMissionRef==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen odkaz na reprezentaci.", ctx.formatPosition(event));
		}
		// Kontrola existence submission reprezentace
		RepresentationInfo repres = ctx.getRepresentation(subMissionRef.getLinkingObjectIdentifierValue());
		if(repres==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezena reprezentace: "+subMissionRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(subMissionRef));
		}
	}
}
