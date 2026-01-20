package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Subtitle;
import cz.zaf.schema.ead3.Titlestmt;

public class Rule05 extends EadRule {
	
	static final public String CODE = "obs5";
	static final public String RULE_TEXT = "Element <titlestmt> obsahuje právě jeden element <subtitle>. Ten obsahuje prostou textovou hodnotu.";
	static final public String RULE_ERROR = "Element <titlestmt> neobsahuje právě jeden element <subtitle>. Případně element <subtitle> neobsahuje prostou textovou hodnotu.";
	static final public String RULE_SOURCE = "Část 2.3 profilu EAD3 MV ČR"; 
	
	public Rule05() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		Titlestmt titleStmt = ead.getControl().getFiledesc().getTitlestmt();
		List<Subtitle> subtitles = titleStmt.getSubtitle();
		if(CollectionUtils.isEmpty(subtitles)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element.", ctx.formatEadPosition(titleStmt));
		}
		if(subtitles.size()>1) {
			throw new ZafException(BaseCode.DUPLICITA, "Opakování elementů subtitle.", ctx.formatEadPosition(subtitles.get(1)));
		}
		Subtitle subtitle = subtitles.get(0);
		List<Serializable> content = subtitle.getContent();
		if(CollectionUtils.isEmpty(content)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybi hodnota element.", ctx.formatEadPosition(subtitle));
		}
		if(content.size()>1) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element obsahuje více hodnot.", ctx.formatEadPosition(subtitle));
		}
		Serializable valueObj = content.get(0);
		if(valueObj instanceof String) {
			String value = (String)valueObj;
			if(StringUtils.isBlank(value)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element neobsahuje platnou hodnot.", ctx.formatEadPosition(subtitle));
			}
		} else {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota element.", ctx.formatEadPosition(subtitle));
		}

	}
}
