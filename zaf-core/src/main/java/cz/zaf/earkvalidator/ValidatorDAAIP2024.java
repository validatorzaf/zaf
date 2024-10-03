package cz.zaf.earkvalidator;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.Validator;
import cz.zaf.eadvalidator.ap2023.EadLoader;
import cz.zaf.eadvalidator.ap2023.EadValidator;

public class ValidatorDAAIP2024 implements Validator {
	
	private static ValidationProfileInfo validationProfileInfo = new ValidationProfileInfo() {

		@Override
		public String getProfileName() {
			return "standard";
		}

		@Override
		public String getValidationType() {
			return "default";
		}

		@Override
		public String getProfileVersion() {
			return "1.0";
		}		
	}; 
	
	private List<String> excludeChecks = null;

	public ValidatorDAAIP2024(final List<String> excludeChecks) {
		this.excludeChecks = excludeChecks;
	}

	@Override
	public ValidationResult validate(Path path) throws Exception {
		// nahrani eadu
        try (AipLoader aipLoader = new AipLoader(path);) {
        	
            AipValidator aipValidator = new AipValidator(excludeChecks);

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
