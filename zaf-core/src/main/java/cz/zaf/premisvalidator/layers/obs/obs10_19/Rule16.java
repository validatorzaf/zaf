package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.LinkingAgentIdentifierComplexType;
import cz.zaf.schema.premis3.LinkingObjectIdentifierComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule16 extends PremisRule {

	public static final String CODE = "obs16";
	public static final String RULE_TEXT = "Vložení do digitálního archivu je správně uvedeno.";
	public static final String RULE_ERROR = "Chybně uvedena informace o vložení do digitálního archivu.";
	public static final String RULE_SOURCE = "CZDAX-PKG0601, CZDAX-PKG0602, CZDAX-PKG0603, CZDAX-PKG0604, CZDAX-PKG0605, CZDAX-PKG0606, CZDAX-PKG0607, CZDAX-PKG0608, CZDAX-PKG0609, CZDAX-PKG0610, CZDAX-PKG0611";

	public Rule16() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		EventComplexType eventIngest = null;
		for(EventComplexType event: premis.getEvent()) {
			if(PremisConstants.EVENT_TYPE_ING.equals(event.getEventType().getValue())) {
				if(eventIngest!=null) {
					throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Vnějde dva vznik archiválií v balíčku.", ctx.formatPosition(premis));
				}
				checkEventIngest(event);
				eventIngest = event;
			}
		}
		// CZDAX-PKG0601
		if(eventIngest==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/eventType=ING.", ctx.formatPosition(premis));
		}
	}

	private void checkEventIngest(EventComplexType event) {
		// CZDAX-PKG0602 - datace - ze schematu
		
		int submitterCnt = 0;
		int curatorCnt = 0;
		
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
			if(PremisConstants.ROLE_CURATOR.equals(role.getValue())) {
				// CZDAX-PKG0604: Přejímající archiv reprezentovaný agentem MUSÍ být uveden a to pomocí vztahu CURATOR. 
				//                V rámci jedné události MŮŽE být uveden nanejvýš jeden přejímající.
				if(curatorCnt>0) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nalezen opakovany element linkingAgentIdentifier/linkingAgentRole=CURATOR.", ctx.formatPosition(agentRef));
				}
				curatorCnt++;
								
				AgentComplexType agent = ctx.getAgentById(PremisConstants.IDENT_TYPE_LOCAL, agentRef.getLinkingAgentIdentifierValue());
				if(agent==null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen agent: "+agentRef.getLinkingAgentIdentifierValue()+".", ctx.formatPosition(agentRef));
				}
				
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozponana role agenta: "+role.getValue()+".", ctx.formatPosition(agentRef));
			}
		}
		
		if(submitterCnt==0) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingAgentIdentifier/linkingAgentRole=SUBMITTER.", ctx.formatPosition(event));
		}
		if(curatorCnt==0) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingAgentIdentifier/linkingAgentRole=CURATOR.", ctx.formatPosition(event));
		}

		String fondId = null, packageId = null;
		String ingestionId = null, referenceNumber = null, czNadVniz = null, czNadVnez = null;
		// Kontrola napojených objektů
		for(LinkingObjectIdentifierComplexType objRef: event.getLinkingObjectIdentifier()) {
			if(objRef.getLinkingObjectRole().size()!=1) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena jedna platna role objektu.", ctx.formatPosition(objRef));
			}
			StringPlusAuthority role = objRef.getLinkingObjectRole().get(0);
			if(PremisConstants.ROLE_SOU.equals(role.getValue())) {
				// CZDAX-PKG0605: Reprezentace (viz Reprezentace), která je základem balíčku MUSÍ být uvedena jako jeho zdroj. 
				//                Toto je zachyceno pomocí role sou ( viz source, URI: http://id.loc.gov/vocabulary/preservation/eventRelatedObjectRole/sou). 
				//				  Při předání balíčku mezi digitálními archivy MUSÍ být jako zdroj uveden balíček uložený v předávajícím archivu, 
				//				  tj. jako zdroj se neuvede reprezentace.

				if(!PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingObjectIdentifier/linkingObjectIdentifierType=LOCAL.", ctx.formatPosition(objRef));
				}
				
				IntellectualEntity obj = ctx.getIntelEntById(PremisConstants.IDENT_TYPE_LOCAL, objRef.getLinkingObjectIdentifierValue());
				if(obj==null) {
					// jeste muze byt reprezentace
					RepresentationInfo repres = ctx.getRepresentation(objRef.getLinkingObjectIdentifierValue());
					if(repres==null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen objekt: "+objRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(objRef));
					}					
				} else {
					// TODO: check if intel. entity is package
				}
			} else
			if(PremisConstants.ROLE_OUT.equals(role.getValue())) {
				// kontrola nepojených objektů
				if(PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					// muze byt balicek nebo fond
					// CZDAX-PKG0606, CZDAX-PKG0611
					IntellectualEntity obj = ctx.getIntelEntById(PremisConstants.IDENT_TYPE_LOCAL, objRef.getLinkingObjectIdentifierValue());
					if(obj==null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingObjectIdentifier/linkingObjectIdentifierType=LOCAL.", ctx.formatPosition(objRef));
					}
					// 
					if(EarkCz.OBJECT_IDENTIFIER_ITSELF.equals(objRef.getLinkingObjectIdentifierValue())) {
						if(packageId!=null) {
							throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice balicku: "+objRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(objRef));
						}
						packageId = objRef.getLinkingObjectIdentifierValue();
					} else {
						// stale muze byt balicek nebo fond
						for(ObjectIdentifierComplexType objIdent: obj.getObjectIdentifier()) {
							if(EarkCz.OBJECT_IDENTIFIER_FONDS_ID.equals(objIdent.getObjectIdentifierType().getValue())) {
								if(fondId!=null) {
									throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice fondu: "+objRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(objRef));
								}
								fondId = objIdent.getObjectIdentifierValue();
							} else 
							if(EarkCz.OBJECT_IDENTIFIER_AIP_ID.equals(objIdent.getObjectIdentifierType().getValue())) {
								if(packageId!=null) {
									throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice balicku: "+objRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(objRef));
								}
								packageId = objRef.getLinkingObjectIdentifierValue();								
							}
						}
					}
				} else
				// CZDAX-PKG0607
				if(PremisConstants.IDENT_TYPE_INGESTION.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					if(ingestionId!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice INGESTION_ID.", ctx.formatPosition(objRef));
					}
					ingestionId = objRef.getLinkingObjectIdentifierValue();
				} else
				// CZDAX-PKG0608
				if(PremisConstants.IDENT_TYPE_REFERENCE_NUMBER.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					if(referenceNumber!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice REFERENCE_NUMBER.", ctx.formatPosition(objRef));
					}
					referenceNumber = objRef.getLinkingObjectIdentifierValue();
				} else
				// CZDAX-PKG0609
				if(PremisConstants.IDENT_TYPE_CZ_NAD_VNEZ.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					if(czNadVnez!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice CZ_NAD_VNEZ.", ctx.formatPosition(objRef));
					}
					czNadVnez = objRef.getLinkingObjectIdentifierValue();
				} else
				// CZDAX-PKG0610
				if(PremisConstants.IDENT_TYPE_CZ_NAD_VNIZ.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					if(czNadVniz!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice CZ_NAD_VNIZ.", ctx.formatPosition(objRef));
					}
					czNadVniz = objRef.getLinkingObjectIdentifierValue();
				} else {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozpoznaný objekt: "+objRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(objRef));
				}
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozpoznaná role: "+role.getAuthority()+".", ctx.formatPosition(objRef));
			}
		}
		
		if(czNadVnez!=null && czNadVniz!=null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Definice CZ_NAD_VNEZ a CZ_NAD_VNIZ jsou vzájemně výlučné.", ctx.formatPosition(event));
		}
		if(packageId==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen odkaz na balíček.", ctx.formatPosition(event));
		}
		if(fondId==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen odkaz na fond.", ctx.formatPosition(event));
		}
	}
}
