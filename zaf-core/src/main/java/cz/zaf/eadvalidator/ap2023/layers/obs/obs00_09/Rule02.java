package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Otherrecordid;

public class Rule02 extends EadRule {
	
	static final public String CODE = "obs2";
	static final public String RULE_TEXT = "Element <ead:control> obsahuje právě jeden element <ead:otherrecordid> s atributem \"localtype\" o hodnotě \"CZ_MVCR_FINDING_AID_ID\", jehož hodnota je kladné celé číslo.";
	static final public String RULE_ERROR = "Element <ead:control> neobsahuje právě jeden element <ead:otherrecordid> s atributem \"localtype\" o hodnotě \"CZ_MVCR_FINDING_AID_ID\". Případně tento element neobsahuje kladné celé číslo.";
	static final public String RULE_SOURCE = "Část 2.2 profilu EAD3 MV ČR"; 
	
	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		List<Otherrecordid> otherIds = ead.getControl().getOtherrecordid();
		Otherrecordid found = null;
		if(!CollectionUtils.isEmpty(otherIds)) {
			for (Otherrecordid otherId : otherIds) {
				if ("CZ_MVCR_FINDING_AID_ID".equals(otherId.getLocaltype())) {
					if (found != null) {
						throw new ZafException(BaseCode.DUPLICITA, "Hodnota uvedena vícekrát",
								ctx.formatEadPosition(otherId));
					}
					found = otherId;

					// kontrola hodnoty
					String content = otherId.getContent();
					if (StringUtils.isBlank(content)) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Prázdná hodnota",
								ctx.formatEadPosition(otherId));
					}
					// zkusime prevest na cislo
					try {
						int faId = Integer.parseInt(content);
						if (faId <= 0) {
							throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
									"Hodnota není kladné číslo: " + content, ctx.formatEadPosition(otherId));
						}
					} catch (NumberFormatException nfe) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota není číslo: " + content,
								ctx.formatEadPosition(otherId));
					}
				}
			}
		}
		if(found==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Hodnota nenalezena", ctx.formatEadPosition(ead.getControl()));
		}

	}
}
