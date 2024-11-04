package cz.zaf.premisvalidator.layers.obs.obs10_19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.AgentIdentifierComplexType;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.LinkingAgentIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule14 extends PremisRule {

	public static final String CODE = "obs14";
	public static final String RULE_TEXT = "Archivní entita zachycená prostřednictvím agenta je správně uvedena.";
	public static final String RULE_ERROR = "Agent je chybně zapsán.";
	public static final String RULE_SOURCE = "CZDAX-PKG0401, CZDAX-PKG0402, CZDAX-PKG0403";

	private Map<String, AgentComplexType> agentsMap = new HashMap<>();	
	private Set<String> usedAgents = new HashSet<>();	

	public Rule14() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		// zjisteni vsech agentu
		for(AgentComplexType agent: premis.getAgent()) {
			String localIdentValue = readAgentInfo(agent);
			if(localIdentValue!=null) {
				agentsMap.put(localIdentValue, agent);
			}
		}
		
		// check if agents are used in events
		for(EventComplexType ev: premis.getEvent()) {
			switch(ev.getEventType().getValue()) {
			case PremisConstants.EVENT_TYPE_CRE:
				checkEventCreate(ev);
				break;
			case PremisConstants.EVENT_TYPE_ING:
				checkEventIngest(ev);
				break;
			case PremisConstants.EVENT_TYPE_TRA:
				checkEventTransfer(ev);
				break;
			case PremisConstants.EVENT_TYPE_EXP:
				checkEventExport(ev);
				break;
			case PremisConstants.EVENT_TYPE_DEL:
				checkEventDelete(ev);
				break;
			}
		}
		
		// check if all agents are used
		for(String agentId: agentsMap.keySet()) {
			if(!usedAgents.contains(agentId)) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezeno použití identifikátor AIPu " + agentId + ".", ctx.formatPosition(agentsMap.get(agentId)));
			}
		}
		
	}

	private void checkEventDelete(EventComplexType ev) {
		// neuvadi se agent jiny nez SW	
		
	}

	private void checkEventExport(EventComplexType ev) {
		// CZDAX-PKG0803: Archiv provádějící export MUSÍ být uvedena a to pomocí vztahu CURATOR. V rámci jedné události MŮŽE být uveden nanejvýš jeden exportující.
		for (LinkingAgentIdentifierComplexType agentRef : ev.getLinkingAgentIdentifier()) {
			for (StringPlusAuthority role : agentRef.getLinkingAgentRole()) {
				// rozpoznat lze jen local identifikatory
				if (PremisConstants.IDENT_TYPE_LOCAL.equals(agentRef.getLinkingAgentIdentifierType().getValue())) {
					if (PremisConstants.ROLE_CURATOR.equals(role.getValue())) {
						if (this.agentsMap.containsKey(agentRef.getLinkingAgentIdentifierValue())) {
							this.usedAgents.add(agentRef.getLinkingAgentIdentifierValue());
						}
					}
				}
			}
		}		
	}

	private void checkEventTransfer(EventComplexType ev) {
		// CZDAX-PKG0703: Nová přejímající archiv reprezentovaný agentem MUSÍ být uveden a to pomocí vztahu CURATOR. V rámci jedné události MŮŽE být uveden nanejvýš jeden přejímající archiv.
		for (LinkingAgentIdentifierComplexType agentRef : ev.getLinkingAgentIdentifier()) {
			for (StringPlusAuthority role : agentRef.getLinkingAgentRole()) {
				// rozpoznat lze jen local identifikatory
				if (PremisConstants.IDENT_TYPE_LOCAL.equals(agentRef.getLinkingAgentIdentifierType().getValue())) {
					if (PremisConstants.ROLE_CURATOR.equals(role.getValue())) {
						if (this.agentsMap.containsKey(agentRef.getLinkingAgentIdentifierValue())) {
							this.usedAgents.add(agentRef.getLinkingAgentIdentifierValue());
						}
					}
				}
			}
		}		
	}

	private void checkEventIngest(EventComplexType ev) {
		// CZDAX-PKG0603: Předávající strana MUSÍ být zapsána formou agenta a MUSÍ být uvedena pomocí vztahu SUBMITTER. 
		//                V rámci jedné události MŮŽE být uveden nanejvýš jeden předávající.
		// CZDAX-PKG0604: Přejímající archiv reprezentovaný agentem MUSÍ být uveden a to pomocí vztahu CURATOR. V rámci jedné události MŮŽE být uveden nanejvýš jeden přejímající.		for(LinkingAgentIdentifierComplexType agentRef: ev.getLinkingAgentIdentifier()) {
		for (LinkingAgentIdentifierComplexType agentRef : ev.getLinkingAgentIdentifier()) {
			for (StringPlusAuthority role : agentRef.getLinkingAgentRole()) {
				// rozpoznat lze jen local identifikatory
				if (PremisConstants.IDENT_TYPE_LOCAL.equals(agentRef.getLinkingAgentIdentifierType().getValue())) {
					if (PremisConstants.ROLE_SUBMITTER.equals(role.getValue())) {
						if (this.agentsMap.containsKey(agentRef.getLinkingAgentIdentifierValue())) {
							this.usedAgents.add(agentRef.getLinkingAgentIdentifierValue());
						}
					}
					if (PremisConstants.ROLE_CURATOR.equals(role.getValue())) {
						if (this.agentsMap.containsKey(agentRef.getLinkingAgentIdentifierValue())) {
							this.usedAgents.add(agentRef.getLinkingAgentIdentifierValue());
						}
					}
				}
			}
		}
	}

	private void checkEventCreate(EventComplexType ev) {
		// CZDAX-PKG0503: Původce archiválií reprezentovaný agentem MUSÍ být uveden pomocí vztahu ORIGINATOR. V rámci jedné události MŮŽE být uvedeno více původců.
		for(LinkingAgentIdentifierComplexType agentRef: ev.getLinkingAgentIdentifier()) {
			for(StringPlusAuthority role: agentRef.getLinkingAgentRole()) {
				// rozpoznat lze jen local identifikatory
				if(PremisConstants.IDENT_TYPE_LOCAL.equals(agentRef.getLinkingAgentIdentifierType().getValue())) {
					if(PremisConstants.ROLE_ORIGINATOR.equals(role.getValue())) {
						if(this.agentsMap.containsKey(agentRef.getLinkingAgentIdentifierValue())) {
							this.usedAgents.add(agentRef.getLinkingAgentIdentifierValue());
						}
					}
				}
			}
		}		
	}

	private String readAgentInfo(AgentComplexType agent) {
				
		// CZDAX-PKG0403 - zjisteni typu agenta
		StringPlusAuthority agentType = agent.getAgentType();
		if(PremisConstants.AGENT_TYPE_PERSON.equals(agentType.getValue())||
				PremisConstants.AGENT_TYPE_ORGANIZATION.equals(agentType.getValue())||
				PremisConstants.AGENT_TYPE_OTHER_ORGANIZATION.equals(agentType.getValue()))
		{
			String camId = null, institutionId = null;
			String localIdent = null;
			
			// read local identifier
			for(AgentIdentifierComplexType ident: agent.getAgentIdentifier()) {
				// CZDAX-PKG0401
				if(PremisConstants.IDENT_TYPE_LOCAL.equals(ident.getAgentIdentifierType().getValue())) {
					if(localIdent!=null) {
						throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Nalezen duplicitni lokalni identifikator agent/agentIdentifier.", ctx.formatPosition(ident));
					}
					localIdent = ident.getAgentIdentifierValue();
					continue;
				}
				if(PremisConstants.IDENT_TYPE_CAM.equals(ident.getAgentIdentifierType().getValue())) {
					if(camId!=null) {
						throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Nalezen duplicitni identifikator CAM agent/agentIdentifier.", ctx.formatPosition(ident));
					}
					camId = ident.getAgentIdentifierValue();
					continue;
				}
				if(PremisConstants.IDENT_TYPE_INSTITUTION.equals(ident.getAgentIdentifierType().getValue())) {
					if(institutionId!=null) {
						throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Nalezen duplicitni identifikator vlastního agenta agent/agentIdentifier.", ctx.formatPosition(ident));
					}
					institutionId = ident.getAgentIdentifierValue();
					continue;
				}
			}
			
			// CZDAX-PKG0402 - check name
			if(agent.getAgentName().size()==0) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element agent/agentName.", ctx.formatPosition(agent));				
			}
			
			if(localIdent==null) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element agent/agentIdentifier.", ctx.formatPosition(agent));
			}
			return localIdent;
		}
		// skip other types of agents
		return null;
	}
}
