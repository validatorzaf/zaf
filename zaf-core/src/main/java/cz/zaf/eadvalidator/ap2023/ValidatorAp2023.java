package cz.zaf.eadvalidator.ap2023;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.Validator;
import cz.zaf.common.validation.ValidatorInfo;
import cz.zaf.eadvalidator.ap2023.layers.enc.EncodingValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.ns.NamespaceValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.obs.ContentValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.val.SchemaValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.wf.WellFormedLayer;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.validator.profiles.ValidatorType;

public class ValidatorAp2023 implements Validator, ValidationProfileInfo {
	
    private ValidationSubprofile profilValidace = null;
	private List<String> excludeChecks = null;
	
    public ValidatorAp2023(ValidationSubprofile profilValidace,
                           List<String> excludeChecks) {
		this.profilValidace = profilValidace;
		this.excludeChecks = excludeChecks;
	}

    @Override
    public ValidationResult validate(Path path) throws Exception
	{	
		// nahrani eadu
        try (EadLoader eadLoader = new EadLoader(path);) {
        	
            EadValidator eadValidator = new EadValidator(profilValidace, excludeChecks, null);

            //eadValidator.setHrozba(hrozba);
            eadValidator.validate(eadLoader);

            return eadLoader.getResult();
        }
	}

    @Override
    public ValidationProfileInfo getProfileInfo() {
        return this;
    }

    @Override
    public String getProfileName() {
    	return profilValidace.getName();
    }

    @Override
    public String getValidationType() {
    	return ValidatorType.AP2023.toString();        
    }

    @Override
    public String getRuleVersion() {
        return "1";
    }

	public static ValidatorInfo getValidatorInfo() {
		return new ValidatorInfo() {

			@Override
			public List<? extends ValidationLayerType> getValidationLayers() {
				return List.of(ValidationLayers.values());
			}

			@Override
			public List<? extends ValidationSubprofile> getValidationSubprofiles() {
				return List.of(AP2023Profile.values());
			}

			@Override
			public List<Rule<? extends RuleEvaluationContext>> getRules(ValidationLayerType layerType,
					ValidationSubprofile subProfile) {				
				if(!(layerType instanceof ValidationLayers validationLayer)) {
					throw new IllegalStateException("Unexpected layer type: "+layerType);
				}
				if(!(subProfile instanceof AP2023Profile profile)) {
					throw new IllegalStateException("Unexpected subprofile type: "+subProfile);
				}
				ValidationLayers layer = (ValidationLayers) layerType;
				
				List<? extends BaseRule<EadValidationContext> > rules = null;
				switch(validationLayer)
				{
				case ENCODING:
					rules = new EncodingValidationLayer(null).createRules();
					break;
				case WELL_FORMED:
					rules = new WellFormedLayer(null).createRules();
					break;
				case NAMESPACE:
					rules = new NamespaceValidationLayer(null).createRules();
					break;
				case VALIDATION:
					rules = new SchemaValidationLayer(null).createRules();
					break;
				case OBSAH:
					rules = new ContentValidationLayer(profile, null).createRules();
					break;
				default:
					throw new IllegalStateException("Unrecognized layer: " + layer);
				}
				
				if(rules==null) {
					return Collections.emptyList();
				}
				return rules.stream().map(Function.identity()).collect(Collectors.toList());				
			}
			
		};
	}
}
