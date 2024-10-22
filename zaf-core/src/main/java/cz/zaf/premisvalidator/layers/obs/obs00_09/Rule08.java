package cz.zaf.premisvalidator.layers.obs.obs00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.EventIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;
import cz.zaf.schemas.premis.PremisNS;

public class Rule08 extends PremisRule {

	public static final String CODE = "obs08";
	public static final String RULE_TEXT = "Každá intelektuální entita má uveden svůj lokální identifikátor dle specifikace.";
	public static final String RULE_ERROR = "Chybný identifikátor intelektuální entity.";
	public static final String RULE_SOURCE = "CZDAX-PMS0201";


	public Rule08() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
	}
}
