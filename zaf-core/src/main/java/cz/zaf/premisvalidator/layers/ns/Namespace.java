package cz.zaf.premisvalidator.layers.ns;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule01;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule02;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule03;
import cz.zaf.premisvalidator.layers.ns.ns00_09.Rule04;
import cz.zaf.premisvalidator.PremisValidationContext;

public class Namespace {
	public static final List<Class<? extends BaseRule<PremisValidationContext>>> ruleClasses = List.of(
			Rule01.class,
			Rule02.class,
			Rule03.class,
			Rule04.class
		);

}
