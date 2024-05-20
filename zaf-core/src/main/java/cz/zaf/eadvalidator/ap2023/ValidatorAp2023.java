package cz.zaf.eadvalidator.ap2023;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.Validator;
import cz.zaf.eadvalidator.ap2023.profile.EadValidationProfile;
// import cz.zaf.validator.ap2023.profily.ProfilValidaceEad;
// import cz.zaf.sipvalidator.ead.EadInfo;
// import cz.zaf.sipvalidator.ead.EadLoader;

public class ValidatorAp2023 implements Validator {
	
    private EadValidationProfile profilValidace = null;
	private List<String> excludeChecks = null;
	
    public ValidatorAp2023(EadValidationProfile profilValidace,
                           List<String> excludeChecks) {
		this.profilValidace = profilValidace;
		this.excludeChecks = excludeChecks;
	}

    @Override
    public ValidationResult validate(Path path) throws Exception
	{	
		// nahrani eadu
        try (EadLoader eadLoader = new EadLoader(path);) {
        	
            EadValidator eadValidator = new EadValidator(profilValidace, excludeChecks);
            //eadValidator.setHrozba(hrozba);
            eadValidator.validate(eadLoader);

            return eadLoader.getResult();
        }
	}

}
