package cz.zaf.eadvalidator.ap2023.layers.ns;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule02a;
import cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09.Rule03;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class NamespaceValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	private List<Class<? extends BaseRule<EadValidationContext>>> ruleClasses = List.of(
			Rule01.class,
			Rule02.class,
			Rule03.class
	);
	
	// Rules used for eArk based AIP validation
	private List<Class<? extends BaseRule<EadValidationContext>>> earkRuleClasses = List.of(
			Rule01.class,
			Rule02a.class,
			Rule03.class
	);
	private ValidationSubprofile profilValidace;

	public NamespaceValidationLayer(ValidationSubprofile profilValidace, String innerFileName) {
		super(ValidationLayers.NAMESPACE, innerFileName);
		this.profilValidace = profilValidace;
	}

	@Override
	protected void validateImpl() {
        this.provedKontrolu(ctx, createRules());
		
	}

	public List<? extends BaseRule<EadValidationContext>> createRules() {
		if(profilValidace==AP2023Profile.EARK_INHERENT_DESC || 
				profilValidace==AP2023Profile.EARK_CONTEXTUAL_DESC)
		{
			return createRules(earkRuleClasses);
		}
		return createRules(ruleClasses);
	}

}
