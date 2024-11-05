package cz.zaf.earkvalidator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.Validator;
import cz.zaf.common.validation.ValidatorInfo;
import cz.zaf.earkvalidator.layers.dat.DataValidationLayer;
import cz.zaf.earkvalidator.layers.enc.EncodingValidationLayer;
import cz.zaf.earkvalidator.layers.fls.FilesValidationLayer;
import cz.zaf.earkvalidator.layers.ns.NamespaceValidationLayer;
import cz.zaf.earkvalidator.layers.obs.ContentValidationLayer;
import cz.zaf.earkvalidator.layers.val.SchemaValidationLayer;
import cz.zaf.earkvalidator.layers.wf.WellFormedLayer;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.validator.profiles.ValidationProfile;

public class ValidatorDAAIP2024 implements Validator {
	
	private final ValidationProfileInfo validationProfileInfo;	
	private List<String> excludeChecks = null;
	private DAAIP2024Profile daaip2024Profile;
	private final String workDir;
	private final boolean keepExtactedFiles;

	public ValidatorDAAIP2024(final DAAIP2024Profile daaip2024Profile, 
			final List<String> excludeChecks, 
			final String workDir, 
			final boolean keepExtactedFiles) {
		this.excludeChecks = excludeChecks;
		this.daaip2024Profile = daaip2024Profile;
		this.workDir = workDir;
		this.keepExtactedFiles = keepExtactedFiles;
		this.validationProfileInfo = new ValidationProfileInfo() {

			@Override
			public String getProfileName() {
				return ValidationProfile.DAAIP2024.toString();
			}

			@Override
			public String getValidationType() {
				return daaip2024Profile.getName();
			}

			@Override
			public String getProfileVersion() {
				return "1";
			}		
		}; 

	}

	@Override
	public ValidationResult validate(Path path) throws Exception {
		// nahrani eadu
        try (AipLoader aipLoader = new AipLoader(path, workDir, keepExtactedFiles);) {
        	aipLoader.init();
        	
            AipValidator aipValidator = new AipValidator(daaip2024Profile, excludeChecks);

            //eadValidator.setHrozba(hrozba);
            aipValidator.validate(aipLoader);

            return aipLoader.getResult();
        }
	}

	@Override
	public ValidationProfileInfo getProfileInfo() {
		return validationProfileInfo;
	}

	public static ValidatorInfo getValidatorInfo() {
		ValidatorInfo vi = new ValidatorInfo() {

			@Override
			public List<? extends ValidationLayerType> getValidationLayers() {
				return List.of(ValidationLayers.values());
			}

			@Override
			public List<? extends ValidationSubprofile> getValidationSubprofiles() {
				return List.of(DAAIP2024Profile.values());
			}

			@Override
			public List<Rule<? extends RuleEvaluationContext>> getRules(ValidationLayerType layerType,
					ValidationSubprofile subProfile)
			{
				if(!(layerType instanceof ValidationLayers)) {
					throw new IllegalStateException("Unexpected layer type: "+layerType);
				}
				if(!(subProfile instanceof DAAIP2024Profile)) {
					throw new IllegalStateException("Unexpected subprofile type: "+subProfile);
				}
				ValidationLayers layer = (ValidationLayers) layerType;
				DAAIP2024Profile profile = (DAAIP2024Profile) subProfile;
				
				List<? extends BaseRule<AipValidationContext> > rules = null;
				
				switch (layer) {
				case DATA:
					rules = new DataValidationLayer(profile).createRules();
					break;
				case ENCODING:
					rules = new EncodingValidationLayer().createRules();
					break;
				case WELL_FORMED:
					rules = new WellFormedLayer().createRules();
					break;
				case NAMESPACE:
					rules = new NamespaceValidationLayer().createRules();
					break;
				case VALIDATION:
					rules = new SchemaValidationLayer().createRules();
					break;
				case OBSAH:
					rules = new ContentValidationLayer(profile).createRules();
					break;
				case FILES:
					rules = new FilesValidationLayer(profile).createRules();
					break;
				default:
					throw new IllegalStateException("Unrecognized layer: " + layer);
				}
				
				List<Rule<? extends RuleEvaluationContext>> result = new ArrayList<>();
				if(rules!=null) {
					for(BaseRule<AipValidationContext> rule: rules) {
						result.add(rule);
					}
				}
					
				return result;
			}
			
		};
		return vi;
	}

}
