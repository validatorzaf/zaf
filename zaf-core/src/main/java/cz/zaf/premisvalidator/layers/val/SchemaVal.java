package cz.zaf.premisvalidator.layers.val;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.premisvalidator.PremisValidationContext;
import cz.zaf.premisvalidator.layers.val.val00_09.Rule01;

/**
 * Schema validation
 */
public class SchemaVal {
	static public final List<Class<? extends BaseRule<PremisValidationContext>>> ruleClasses = List.of(
			Rule01.class
			);

}
