package cz.zaf.earkvalidator.layers.comp_wf;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.layers.comp_wf.comp_wf00_09.Rule01;
import cz.zaf.earkvalidator.layers.comp_wf.comp_wf00_09.Rule02;

public class CompWellFormed {
	static public final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses = List.of(
			Rule01.class, Rule02.class
			);

}
