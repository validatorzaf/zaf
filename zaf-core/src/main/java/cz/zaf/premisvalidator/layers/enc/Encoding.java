package cz.zaf.premisvalidator.layers.enc;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.ValidationLayerContext;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.premisvalidator.PremisValidationContext;
import cz.zaf.premisvalidator.layers.enc.enc00_09.Rule01;

public class Encoding { 

	static public final List<Class<? extends BaseRule<PremisValidationContext>>> ruleClasses = List.of(
			Rule01.class
			);
	
}
