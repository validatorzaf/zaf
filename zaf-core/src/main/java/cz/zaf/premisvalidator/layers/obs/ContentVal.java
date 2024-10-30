package cz.zaf.premisvalidator.layers.obs;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.premisvalidator.PremisValidationContext;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule03;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule04;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule05;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule06;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule07;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule08;
import cz.zaf.premisvalidator.layers.obs.obs00_09.Rule09;
import cz.zaf.premisvalidator.layers.obs.obs10_19.Rule10;
import cz.zaf.premisvalidator.layers.obs.obs10_19.Rule11;
import cz.zaf.premisvalidator.profile.PremisProfile;

public class ContentVal {
	static private	 final List<Class<? extends BaseRule<PremisValidationContext>>> packageRuleClasses = List.of(
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule05.class,
			Rule06.class,
			Rule07.class,
			Rule08.class,
			Rule09.class,
			Rule10.class,
			Rule11.class
			);
	
	static private	 final List<Class<? extends BaseRule<PremisValidationContext>>> metadataRuleClasses = List.of(
			Rule02.class,
			Rule03.class,
			Rule04.class,
			Rule05.class,
			Rule06.class,
			Rule07.class,
			Rule08.class,
			Rule09.class
			);

	public static List<Class<? extends BaseRule<PremisValidationContext>>> getRuleClasses(PremisProfile profile) {
		switch(profile) {
		case PACKAGE_INFO:
			return packageRuleClasses;
		case METADATA:
			return metadataRuleClasses;
		}
		return metadataRuleClasses;
	}
}
