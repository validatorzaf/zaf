package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Titleproper;
import cz.zaf.schema.ead3.Titlestmt;

public class Rule04a extends EadRule {

	static final public String CODE = "obs4a";
	static final public String RULE_TEXT = "Element <titlestmt> obsahuje právě jeden element <titleproper>. Ten obsahuje prostou textovou hodnotu.";
	static final public String RULE_ERROR = "Element <titlestmt> neobsahuje právě jeden element <titleproper>, případně element <titleproper> neobsahuje prostou textovou hodnotu.";
	static final public String RULE_SOURCE = "Část 2.3 profilu EAD3 MV ČR"; 
	
	public Rule04a() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		Titlestmt titleStmt = ead.getControl().getFiledesc().getTitlestmt();
		List<Titleproper> titlepropes = titleStmt.getTitleproper();
		if(CollectionUtils.isEmpty(titlepropes)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element titleproper.", ctx.formatEadPosition(titleStmt));
		}
		if(titlepropes.size()>1) {
			throw new ZafException(BaseCode.DUPLICITA, "Opakování elementů titleproper.", ctx.formatEadPosition(titlepropes.get(1)));
		}
		Titleproper titleproper = titlepropes.get(0);
		List<Serializable> content = titleproper.getContent();
		if(CollectionUtils.isEmpty(content)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybi hodnota elementu titleproper.", ctx.formatEadPosition(titleproper));
		}
		if(content.size()>1) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element obsahuje více hodnot.", ctx.formatEadPosition(titleproper));
		}
		Serializable valueObj = content.get(0);
		if(valueObj instanceof String) {
			String value = (String)valueObj;
			if(StringUtils.isBlank(value)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element neobsahuje platnou hodnot.", ctx.formatEadPosition(titleproper));
			}
		} else {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota element.", ctx.formatEadPosition(titleproper));
		}
	}

}
