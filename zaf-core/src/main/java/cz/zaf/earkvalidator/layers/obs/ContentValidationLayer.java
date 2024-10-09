package cz.zaf.earkvalidator.layers.obs;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule01;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule02;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule03;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule04;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule05;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule06;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule07;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule08;
import cz.zaf.earkvalidator.layers.obs.obs00_09.Rule09;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule10;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule11;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule12;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule13;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule14;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule15;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule16;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule17;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule18;
import cz.zaf.earkvalidator.layers.obs.obs10_19.Rule19;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule20;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule21;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule22;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule23;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule24;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule25;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule26;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule27;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule28;
import cz.zaf.earkvalidator.layers.obs.obs20_29.Rule29;

public class ContentValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	private static final List<Class<? extends BaseRule<AipValidationContext>>> aipRuleClasses = List.of(
			Rule01.class,
			Rule02.class,
			Rule03.class,
			Rule04.class,
			Rule05.class,
			Rule06.class,
			Rule07.class,
			Rule08.class,
			Rule09.class,
			Rule10.class,
			Rule11.class,
			Rule12.class,
			Rule13.class,
			Rule14.class,
			Rule15.class,
			Rule16.class,
			Rule17.class,
			Rule18.class,
			Rule19.class,
			Rule20.class,
			Rule21.class,
			Rule22.class,
			Rule23.class,
			Rule24.class,
			Rule25.class,
			Rule26.class,
			Rule27.class,
			Rule28.class,
			Rule29.class
		);
	
	private List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses;
	
	public ContentValidationLayer() {
		super(ValidationLayers.OBSAH);
	}
	
	@Override
	protected void validateImpl() {
		ruleClasses = aipRuleClasses;
		
		List<? extends BaseRule<AipValidationContext> > rules = createRules(ruleClasses);		
		this.provedKontrolu(ctx, rules);		
	}
}
