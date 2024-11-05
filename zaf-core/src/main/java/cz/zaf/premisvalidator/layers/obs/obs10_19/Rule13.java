package cz.zaf.premisvalidator.layers.obs.obs10_19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.LinkingObjectIdentifierComplexType;
import cz.zaf.schema.premis3.ObjectComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule13 extends PremisRule {

	public static final String CODE = "obs13";
	public static final String RULE_TEXT = "Balíček má správně uvedenu hodnotu identifikátoru.";
	public static final String RULE_ERROR = "Chybně uvedena hodnota identifikátoru balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PKG0301, CZDAX-PKG0302, CZDAX-PKG0303, CZDAX-PKG0304";
	
	private Map<String, IntellectualEntity> aipsMap  = new HashMap<>();	
	private Set<String> usedAips = new HashSet<>();


	public Rule13() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		// zjisteni vsech popsanych balicku a jejich lokalnich identifikatoru
		for(ObjectComplexType obj: premis.getObject()) {
			if(obj instanceof IntellectualEntity) {
				IntellectualEntity intent = (IntellectualEntity) obj;
				String localIdentValue = readAipInfo(intent);
				if(localIdentValue!=null) {
					aipsMap.put(localIdentValue, intent);
				}
			}
		}
		
		// overeni vazeb na balicky z udalosti
		// vazby ve vsech udalostech musi byt prostrednictvim definovanych intel. entit
		// nesmi existovat intel. entita, ktera by byla nevyuzita
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
			case PremisConstants.EVENT_TYPE_IPC:
				checkEventIpc(ev);
				break;	
			default:
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozpoznaný typ udalosti: " + ev.getEventType()+".", ctx.formatPosition(ev));
			}
		}
		// check if all AIPs were used
		for(String aipId: aipsMap.keySet()) {
			if(!usedAips.contains(aipId)) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezeno použití identifikátor AIPu " + aipId + ".", ctx.formatPosition(aipsMap.get(aipId)));
			}
		}
	}
	
	private void checkEventIpc(EventComplexType ev) {
		// local ident
		LinkingObjectIdentifierComplexType localRef = null;
		for(LinkingObjectIdentifierComplexType objRef: ev.getLinkingObjectIdentifier()) {			
			for(StringPlusAuthority role: objRef.getLinkingObjectRole()) {
				if(PremisConstants.ROLE_OUT.equals(role.getValue())) {
					// has to exist exactly one local with package identifier
					if(PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {	
						// check if valid package ref
						if(aipsMap.containsKey(objRef.getLinkingObjectIdentifierValue())) {
							if(localRef!=null) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Více odkazů na balíčky.", ctx.formatPosition(objRef));
							}
							usedAips.add(objRef.getLinkingObjectIdentifierValue());
							localRef = objRef;
						}
					}
				}
			}
		}
		if(localRef==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí odkaz na balíček.", ctx.formatPosition(ev));
		}		
	}

	private void checkEventExport(EventComplexType ev) {
		// local ident
		LinkingObjectIdentifierComplexType localRef = null;
		for(LinkingObjectIdentifierComplexType objRef: ev.getLinkingObjectIdentifier()) {			
			for(StringPlusAuthority role: objRef.getLinkingObjectRole()) {
				if(PremisConstants.ROLE_SOU.equals(role.getValue())) {
					// has to exist exactly one local with package identifier
					if(PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {	
						// check if valid package ref
						if(aipsMap.containsKey(objRef.getLinkingObjectIdentifierValue())) {
							if(localRef!=null) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Více odkazů na balíčky.", ctx.formatPosition(objRef));
							}
							usedAips.add(objRef.getLinkingObjectIdentifierValue());
							localRef = objRef;
						}
					}
				}
			}
		}
		if(localRef==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí odkaz na balíček.", ctx.formatPosition(ev));
		}		
	}

	private void checkEventTransfer(EventComplexType ev) {
		// local ident
		LinkingObjectIdentifierComplexType localRef = null;
		for(LinkingObjectIdentifierComplexType objRef: ev.getLinkingObjectIdentifier()) {			
			for(StringPlusAuthority role: objRef.getLinkingObjectRole()) {
				if(PremisConstants.ROLE_SOU.equals(role.getValue())) {
					// has to exist exactly one local with package identifier
					if(PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {	
						// check if valid package ref
						if(aipsMap.containsKey(objRef.getLinkingObjectIdentifierValue())) {
							if(localRef!=null) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Více odkazů na balíčky.", ctx.formatPosition(objRef));
							}
							usedAips.add(objRef.getLinkingObjectIdentifierValue());
							localRef = objRef;
						}
					}
				}
			}
		}
		if(localRef==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí odkaz na balíček.", ctx.formatPosition(ev));
		}		
	}

	private void checkEventIngest(EventComplexType ev) {
		// local ident
		LinkingObjectIdentifierComplexType localRef = null;
		for(LinkingObjectIdentifierComplexType objRef: ev.getLinkingObjectIdentifier()) {			
			for(StringPlusAuthority role: objRef.getLinkingObjectRole()) {
				if(PremisConstants.ROLE_OUT.equals(role.getValue())) {
					// has to exist exactly one local with package identifier
					if(PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {	
						// check if valid package ref
						if(aipsMap.containsKey(objRef.getLinkingObjectIdentifierValue())) {
							if(localRef!=null) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Více odkazů na balíčky.", ctx.formatPosition(objRef));
							}
							usedAips.add(objRef.getLinkingObjectIdentifierValue());
							localRef = objRef;
						}
					}
				}
			}
		}
		if(localRef==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí odkaz na balíček.", ctx.formatPosition(ev));
		}
	}

	private void checkEventCreate(EventComplexType ev) {
		// no objects associated with this event		
	}

	/**
	 * 
	 * @param intent
	 * @return return local ident. Return null if not identified as valid AIP
	 */
	
	private String readAipInfo(IntellectualEntity intent) {
		// zjisteni lokalnich identifikatoru
		String localIdentValue = null;
		String aipId = null;
		
		for(ObjectIdentifierComplexType objIdent: intent.getObjectIdentifier()) {
			if(objIdent.getObjectIdentifierType() == null) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybí typ identifikátoru.", ctx.formatPosition(intent));
			}
			if(PremisConstants.IDENT_TYPE_LOCAL.equals(objIdent.getObjectIdentifierType().getValue())) {
				if(localIdentValue!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Opakovaná hodnota lokálního identifikátoru.", ctx.formatPosition(objIdent));
				}
				localIdentValue = objIdent.getObjectIdentifierValue();
			} else
			if(EarkCz.OBJECT_IDENTIFIER_AIP_ID.equals(objIdent.getObjectIdentifierType().getValue())) {
				if(aipId!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Opakovaná hodnota identifikátoru AIPu.", ctx.formatPosition(objIdent));
				}
				aipId = objIdent.getObjectIdentifierValue();
				continue;
			} else {
				// pokud obsahuje jiny typ atributu, tak se ignoruje
				if(aipId!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Uveden identifik8tor AIPu, jiné typy identifikátorů nelze kombinovat.", ctx.formatPosition(objIdent));
				}
				return null;
			}
		}

		// CZDAX-PKG0302
		// CZDAX-PKG0304		
		if(!EarkCz.OBJECT_IDENTIFIER_ITSELF.equals(localIdentValue)) {
			if(!ValidatorId.checkFormatId(localIdentValue)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná podoba lokálního identifikátoru.", ctx.formatPosition(intent));
			}
		}
		
		return localIdentValue;
	}
}
