package cz.zaf.premisvalidator.layers.wf;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.premisvalidator.PremisValidationContext;
import cz.zaf.premisvalidator.layers.wf.wf00_09.Rule01;
import cz.zaf.premisvalidator.layers.wf.wf00_09.Rule02;

public class WellFormed {
	static public final List<Class<? extends BaseRule<PremisValidationContext>>> ruleClasses = List.of(
			Rule01.class, Rule02.class
			);

}
