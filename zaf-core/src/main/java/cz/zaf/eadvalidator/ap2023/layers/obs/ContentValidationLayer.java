package cz.zaf.eadvalidator.ap2023.layers.obs;

import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.ValidationSubprofile;
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

public class ContentValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	private ValidationSubprofile profilValidace;
	
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

	public ContentValidationLayer(ValidationSubprofile profilValidace) {
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
		
		List<Rule<EadValidationContext>> rules = createRules(ruleClasses);

		this.provedKontrolu(ctx, rules);
		
		
	}
}
