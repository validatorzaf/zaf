package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Otherrecordid;

public class Rule03 extends EadRule {
	
	static final public String CODE = "obs3";
	static final public String RULE_TEXT = "Každý element <ead:otherrecordid>, který nemá atribut \"localtype\" o hodnotě \"CZ_MVCR_FINDING_AID\", má atribut \"localtype\" o hodnotě \"INTERNAL_REV_ID\" a tento není tento prázdný.";
	static final public String RULE_ERROR = "Element <ead:otherrecordid>, který nemá atribut \"localtype\" o hodnotě \"CZ_MVCR_FINDING_AID\", nemá atribut \"localtype\" o hodnotě \"INTERNAL_REV_ID\" a  nebo je prázdný.";
	static final public String RULE_SOURCE = "Část 2.2 profilu EAD3 MV ČR"; 
	
	public Rule03() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		List<Otherrecordid> otherIds = ead.getControl().getOtherrecordid();

		if(CollectionUtils.isEmpty(otherIds)) {
			return;
		}
			
		for (Otherrecordid otherId : otherIds) {
			if (StringUtils.isBlank(otherId.getLocaltype())) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybi atribut localType.",
						ctx.formatEadPosition(otherId));
			}
			// ignorujeme
			if ("CZ_MVCR_FINDING_AID_ID".equals(otherId.getLocaltype())) {
				continue;
			}
			if ("INTERNAL_REV_ID".equals(otherId.getLocaltype())) {

				// kontrola hodnoty
				String content = otherId.getContent();
				if (StringUtils.isBlank(content)) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Prázdná hodnota",
							ctx.formatEadPosition(otherId));
				}
			} else {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT,
						"Nerozponaný typ, localType=\"" + otherId.getLocaltype() + "\"",
						ctx.formatEadPosition(otherId));
			}
		}

	}
}
