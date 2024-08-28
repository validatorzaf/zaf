package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Recordid;

public class Rule01 extends EadRule {
	
	static final public String CODE = "obs1";
	static final public String RULE_TEXT = "Element <ead:recordid> obsahuje neprázdnou hodnotu, která splňuje syntaxi pro tvorbu UUID.";
	static final public String RULE_ERROR = "Element <ead:recordid> neobsahuje hodnotu v požadovaném formátu.";
	static final public String RULE_SOURCE = "Část 2.1 profilu EAD3 MV ČR"; 
	
	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		Recordid recordId = ead.getControl().getRecordid();
		String value = ead.getControl().getRecordid().getContent();
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element recorddid je prázdný", ctx.formatEadPosition(recordId));
		}
		// check UUID format
		try {
	        UUID.fromString(value);
	    } catch (IllegalArgumentException e) {
	    	throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element recorddid má chybnou hodnotu: "+ value, ctx.formatEadPosition(recordId), e);
	    }		
	}

}
