package cz.zaf.earkvalidator.layers.obs.obs00_09;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.Mets;

public class Rule02 extends AipRule {
	public static final String CODE = "obs02";
	public static final String RULE_TEXT = "V elementu <mets> musí být atribut TYPE s neprázdnou hodnotou.";
	public static final String RULE_ERROR = "Chybí atribut TYPE v elementu <mets>.";
	public static final String RULE_SOURCE = "CZDAX-PMT0103, CZDAX-PMT0104";

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Mets mets = ctx.getMets();
		String type = mets.getTYPE();
		if(StringUtils.isBlank(type)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Prazdná hodnota atributu TYPE.",
					ctx.formatMetsPosition(ctx.getMets()));
		}
	}

}
