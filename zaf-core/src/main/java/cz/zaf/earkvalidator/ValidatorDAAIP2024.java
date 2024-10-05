package cz.zaf.earkvalidator;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.Validator;
import cz.zaf.eadvalidator.ap2023.EadLoader;
import cz.zaf.eadvalidator.ap2023.EadValidator;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.validator.profiles.ValidationProfile;

public class ValidatorDAAIP2024 implements Validator {
	
	private final ValidationProfileInfo validationProfileInfo;	
	private List<String> excludeChecks = null;
	private DAAIP2024Profile daaip2024Profile;

	public ValidatorDAAIP2024(final DAAIP2024Profile daaip2024Profile, 
			final List<String> excludeChecks) {
		this.excludeChecks = excludeChecks;
		this.daaip2024Profile = daaip2024Profile;
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
        try (AipLoader aipLoader = new AipLoader(path);) {
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

}
