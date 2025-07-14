package cz.zaf.eadvalidator.ap2023;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.Validator;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
// import cz.zaf.validator.ap2023.profily.ProfilValidaceEad;
// import cz.zaf.sipvalidator.ead.EadInfo;
// import cz.zaf.sipvalidator.ead.EadLoader;
import cz.zaf.validator.profiles.ValidationProfile;

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
        return ValidationProfile.AP2023.toString();
    }

    @Override
    public String getValidationType() {
        return profilValidace.getName();
    }

    @Override
    public String getProfileVersion() {
        return "1";
    }
}
