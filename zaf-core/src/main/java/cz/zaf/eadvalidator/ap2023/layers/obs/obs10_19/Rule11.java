package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Publicationstmt;
import jakarta.xml.bind.JAXBElement;
import cz.zaf.schemas.ead.EadNS;

public class Rule11 extends EadRule {
	
	static final public String CODE = "obs11";
	static final public String RULE_TEXT = "Element <ead:publicationstmt> obsahuje právě jeden takový element <ead:p>, který obsahuje právě jeden element <ead:name> s atributem \"localtype\" o hodnotě \"FINDING_AID_APPROVED_BY\", který obsahuje právě jeden neprázdný element <ead:part>.";
	static final public String RULE_ERROR = "Struktura elementu <ead:publicationstmt> neobsahuje správně vyplněný element <ead:name> s atributem \"localtype\" o hodnotě \"FINDING_AID_APPROVED_BY\" vnořený do elementu <ead:p>.";
	static final public String RULE_SOURCE = "Část 4.1.1 profilu EAD3 MV ČR, EAD TLV heslo <part>"; 
	
	public Rule11() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		Filedesc fileDesc = ead.getControl().getFiledesc();
		Publicationstmt publStmt = fileDesc.getPublicationstmt();		
		if(publStmt==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element.", ctx.formatEadPosition(fileDesc));
		}
		
		List<Object> pdas = publStmt.getPublisherOrDateOrAddress();
		if(CollectionUtils.isEmpty(pdas)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element.", ctx.formatEadPosition(pdas));
		}
		Name found=null;
		for(Object pda: pdas) {
			if(pda instanceof P) {
				P p = (P)pda;
				List<Serializable> pConts = p.getContent();
				for(Object pCont: pConts) {
					// unpack jaxb
					if(pCont instanceof JAXBElement<?> ) {
						JAXBElement<?> jaxbElem = (JAXBElement<?>)pCont;
						Object obj = jaxbElem.getValue();
						if(obj instanceof Name) {
							pCont = (Name)obj;
						}
					}
					
					if(pCont instanceof Name) {
						Name name = (Name)pCont;
						if(EadNS.LOCALTYPE_FINDING_AID_APPROVED_BY.equals(name.getLocaltype())) {
							if(found!=null) {
								throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt.", ctx.formatEadPosition(name));
							}
							found = name;
						}
					} 
				}
			}
		}
		if(found==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element.", ctx.formatEadPosition(publStmt));
		}
		// Kontrola obsahu elementu name
		List<Part> parts = found.getPart();
		if(CollectionUtils.isEmpty(parts)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element.", ctx.formatEadPosition(found));
		}
		if(parts.size()>1) {
			throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt.", ctx.formatEadPosition(parts.get(1)));
		}
		Part part = parts.get(0);
		
		// Kontrola obsahu part
		List<Serializable> partContentList = part.getContent();
		if(partContentList.size()!=1) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(part));
		}
		Serializable partContent = partContentList.get(0);
		if(partContent instanceof String) {
			String str = (String)partContent;
			if(StringUtils.isBlank(str)) {
				throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(part));
			}
		} else {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(part));
		}
	}
}
