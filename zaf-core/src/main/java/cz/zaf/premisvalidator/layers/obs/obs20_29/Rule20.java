package cz.zaf.premisvalidator.layers.obs.obs20_29;

import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.PremisComplexType;

public class Rule20 extends PremisRule {

	public static final String CODE = "obs20";
	public static final String RULE_TEXT = "Je správně uvedena vazba na měněný AIP.";
	public static final String RULE_ERROR = "Chybně uvedena vazba na měněný AIP.";
	public static final String RULE_SOURCE = "CZDAP-IPF0201";

	public Rule20() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
	}
}
