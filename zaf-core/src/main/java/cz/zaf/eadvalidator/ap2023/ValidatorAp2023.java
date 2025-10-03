package cz.zaf.eadvalidator.ap2023;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.Validator;
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
}
