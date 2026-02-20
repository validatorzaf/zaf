package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Filedesc;

public class Rule04 extends EadRule {
	
	static final public String CODE = "obs4";
	static final public String RULE_TEXT = "Element <filedesc> má atribut \"encodinganalog\" o hodnotě kladného celého čísla.";
	static final public String RULE_ERROR = "Element <filedesc> nemá atribut \"encodinganalog\" nebo tento atribut neobsahuje kladné celé číslo.";
	static final public String RULE_SOURCE = "Část 2.3 profilu EAD3 MV ČR"; 
	
	public Rule04() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		Filedesc filedesc = ead.getControl().getFiledesc();
		String encAnal = filedesc.getEncodinganalog();
		if(StringUtils.isEmpty(encAnal)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybi nebo je prázdný atribut encodinganalog.", ctx.formatEadPosition(filedesc));			
		}
		// zkusime prevest na cislo
		try {
			int faId = Integer.parseInt(encAnal);
			if (faId <= 0) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
						"Hodnota není kladné číslo: " + encAnal, ctx.formatEadPosition(filedesc));
			}
		} catch (NumberFormatException nfe) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota není číslo: " + encAnal,
					ctx.formatEadPosition(filedesc));
		}

	}
}
