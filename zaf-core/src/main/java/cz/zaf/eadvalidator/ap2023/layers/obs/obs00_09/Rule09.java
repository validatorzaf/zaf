package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Date;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.Publicationstmt;
import cz.zaf.schemas.ead.EadNS;

public class Rule09 extends EadRule {
	
	static final public String CODE = "obs9";
	static final public String RULE_TEXT = "Element <publicationstmt> obsahuje právě jeden element <date> s atributem \"localtype\" o hodnotě \"FINDING_AID_DATE\". Tento element <date> obsahuje neprázdnou hodnotu.";
	static final public String RULE_ERROR = "Element <publicationstmt> neobsahuje právě jeden element <date> s atributem \"localtype\" o hodnotě \"FINDING_AID_DATE\". Případně je tento element <date> prázdný.";
	static final public String RULE_SOURCE = "Část 4.1.4 profilu EAD3 MV ČR"; 
	
	public Rule09() {
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
		Date found = null;
		for(Object pda: pdas) {
			if(pda instanceof Date) {
				Date date = (Date)pda;
				if(EadNS.LOCALTYPE_FINDING_AID_DATE.equals(date.getLocaltype())) {
					if(found!=null) {
						throw new ZafException(BaseCode.DUPLICITA, "Duplicitní element.", ctx.formatEadPosition(date));
					}
					found = date;
					List<Serializable> content = date.getContent();
					if(content.size()!=1) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu.", ctx.formatEadPosition(date));
					}
					Serializable valueObj = content.get(0);
					if(valueObj instanceof String) {
						var v = (String)valueObj;
						if(StringUtils.isBlank(v)) {
							throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(date));
						}
					} else {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu: "+valueObj, ctx.formatEadPosition(date));
					}
				}
			}
		}
		if(found==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element.", ctx.formatEadPosition(pdas));
		}
	}
}
