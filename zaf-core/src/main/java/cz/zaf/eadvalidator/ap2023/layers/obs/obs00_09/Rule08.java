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

public class Rule08 extends EadRule {
	
	static final public String CODE = "obs8";
	static final public String RULE_TEXT = "Element <ead:publicationstmt> obsahuje právě jeden element <ead:date> s atributem \"localtype\" o hodnotě \"DESCRIPTION_DATE\". Tento element <ead:date> obsahuje neprázdnou hodnotu.";
	static final public String RULE_ERROR = "Element <ead:publicationstmt> neobsahuje právě jeden element <ead:date> s atributem \"localtype\" o hodnotě \"DESCRIPTION_DATE\". Případně je tento element <ead:date> prázdný.";
	static final public String RULE_SOURCE = "Část 4.1.3 profilu EAD3 MV ČR"; 
	
	public Rule08() {
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
				if("DESCRIPTION_DATE".equals(date.getLocaltype())) {
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
