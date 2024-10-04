package cz.zaf.eadvalidator.ap2023.layers.obs;

import java.lang.reflect.Constructor;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule03;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule04;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule04a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule05;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule06;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule07;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule08;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule09;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule11;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule12;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.eadvalidator.ap2023.profile.EadValidationProfile;

public class ContentValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	private EadValidationProfile profilValidace;
	
	Class<?> archDescRules[] = {
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
	};
	
	Class<?> findingAidRules[] = {
			Rule01.class,
			Rule02.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
			Rule05.class,
			Rule06.class,
			Rule07.class,
			Rule08.class,
			Rule09.class,
			Rule11.class,
			Rule12.class,
	};

	public ContentValidationLayer(EadValidationProfile profilValidace) {
		super(ValidationLayers.OBSAH);
		this.profilValidace = profilValidace;
	}

	@Override
	protected void validateImpl() {
		
		Class<?> ruleClasses[];
		if(profilValidace==AP2023Profile.ARCH_DESC) {
			ruleClasses = archDescRules;
		} else if(profilValidace==AP2023Profile.FINDING_AID) {
			ruleClasses = findingAidRules;
		} else {
			throw new IllegalStateException("Neznámý profil: " + profilValidace);
		}
		
		// List<EadRule> rules = new ArrayList<>(ruleClasses.length);
		EadRule rules[] = new EadRule[ruleClasses.length];
		for(int i=0; i<ruleClasses.length; i++) {
			Class<?> clazz = ruleClasses[i];
			try {
				Constructor<?> constr = clazz.getDeclaredConstructor();
				EadRule rule = (EadRule)constr.newInstance();
				rules[i] = rule;
			} catch (Exception e) {
				throw new IllegalStateException("Nelze vytvořit třídu pravidla: " + clazz.getName());
			}
		}

		this.provedKontrolu(ctx, rules);
		
		
	}
}
