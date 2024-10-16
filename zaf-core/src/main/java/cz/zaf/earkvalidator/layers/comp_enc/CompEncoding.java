package cz.zaf.earkvalidator.layers.comp_enc;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.layers.comp_enc.comp_enc00_09.Rule01;

public class CompEncoding {
	static public final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses = List.of(
			Rule01.class
			);
}
