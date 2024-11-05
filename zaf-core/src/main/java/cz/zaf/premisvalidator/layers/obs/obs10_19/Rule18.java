package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.LinkingAgentIdentifierComplexType;
import cz.zaf.schema.premis3.LinkingObjectIdentifierComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule18 extends PremisRule {

	public static final String CODE = "obs18";
	public static final String RULE_TEXT = "Export balíčku je správně zapsán.";
	public static final String RULE_ERROR = "Chybně zapsána informace o exportu balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PKG0801, CZDAX-PKG0802, CZDAX-PKG0803, CZDAX-PKG0804, CZDAX-PKG0805";

	public Rule18() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		for(EventComplexType event: premis.getEvent()) {
			if(PremisConstants.EVENT_TYPE_EXP.equals(event.getEventType().getValue())) {
				checkEventExport(event);
			}
		}
	}

	private void checkEventExport(EventComplexType event) {
		// CZDAX-PKG0802: Datace - ze schematu
		
		// CZDAX-PKG0803: Archiv provádějící export MUSÍ být uvedena a to pomocí vztahu CURATOR. 
		//         V rámci jedné události MŮŽE být uveden nanejvýš jeden exportující.
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
			if(PremisConstants.ROLE_CURATOR.equals(role.getValue())) {
				if(curatorCnt > 0) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Uveden opkazný element linkingAgentIdentifier/linkingAgentRole=CURATOR.", ctx.formatPosition(agentRef));
				}
				curatorCnt++;
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nerozponana role agenta: "+role.getValue()+".", ctx.formatPosition(agentRef));
			}
		}				
		if(curatorCnt==0) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/linkingAgentIdentifier/linkingAgentRole=CURATOR.", ctx.formatPosition(event));
		}

		String srcPackageId = null;
		String czNadVniz = null, czNadVnez = null;
		// Kontrola napojených objektů
		for(LinkingObjectIdentifierComplexType objRef: event.getLinkingObjectIdentifier()) {
			if(objRef.getLinkingObjectRole().size()!=1) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena jedna platna role objektu.", ctx.formatPosition(objRef));
			}
			StringPlusAuthority role = objRef.getLinkingObjectRole().get(0);
			if(PremisConstants.ROLE_SOU.equals(role.getValue())) {
				// CZDAX-PKG0704: Událost přesunu MUSÍ odkazovat na předmětný balíček (viz Balíček). 
				//   Odkaz je pomocí role sou ( viz source, URI: http://id.loc.gov/vocabulary/preservation/eventRelatedObjectRole/sou) 
				//   a s uvedením lokální hodnoty identifikátoru balíčku (local).				
				if(!PremisConstants.IDENT_TYPE_LOCAL.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen element linkingObjectIdentifier/linkingObjectIdentifierType=LOCAL.", ctx.formatPosition(objRef));
				}
				
				IntellectualEntity obj = ctx.getIntelEntById(PremisConstants.IDENT_TYPE_LOCAL, objRef.getLinkingObjectIdentifierValue());
				if(obj==null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezen objekt: "+objRef.getLinkingObjectIdentifierValue()+".", ctx.formatPosition(objRef));
				} else {
					// TODO: check if intel. entity is package
					if(srcPackageId!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Uveden opkazný element linkingObjectIdentifier/linkingObjectIdentifierType=LOCAL.", ctx.formatPosition(objRef));
					}
					srcPackageId = objRef.getLinkingObjectIdentifierValue();
				}
			} else
			if(PremisConstants.ROLE_OUT.equals(role.getValue())) {
				// CZDAX-PKG0805: Událost exportu BY MĚLA odkazovat na související změnu v rámci evidence NAD. 
				//   Odkaz je realizován pomocí role out ( viz output, URI: http://id.loc.gov/vocabulary/preservation/eventRelatedObjectRole/out) a s u uvedením typu identifikátoru (sémantická jednotka linkingObjectIdentifierType) vnější změny (CZ_NAD_VNEZ) a jeho hodnoty nebo vnitřní změny (CZ_NAD_VNIZ).
				//   Číslo změny je specifické pro přejímající archiv.
				if(PremisConstants.IDENT_TYPE_CZ_NAD_VNEZ.equals(objRef.getLinkingObjectIdentifierType().getValue())) {
					if(czNadVnez!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovana definice CZ_NAD_VNEZ.", ctx.formatPosition(objRef));
					}
					czNadVnez = objRef.getLinkingObjectIdentifierValue();
				} else
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
		if(srcPackageId==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen odkaz na zdrojový balíček.", ctx.formatPosition(event));
		}
	}
}
