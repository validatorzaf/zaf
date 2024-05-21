package cz.zaf.sipvalidator.nsesss2017;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.Validator;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.validator.profiles.ValidationProfiles;

public class ValidatorNsesss2017 implements Validator {
	
    private final String hrozba;
    private final String workDir;
    private final boolean isKeepFiles;
    private final ProfilValidace profilValidace;
    private final List<String> excludeChecks;
	
	public ValidatorNsesss2017(String hrozba, String workDir, boolean isKeepFiles,
		ProfilValidace profilValidace, List<String> excludeChecks) {
		this.hrozba = hrozba;
		this.workDir = workDir;
		this.isKeepFiles = isKeepFiles;
		this.profilValidace = profilValidace;
		this.excludeChecks = excludeChecks;
	}

	@Override
    public ValidationResult validate(Path path) throws Exception
	{	
		// nahrani sipu
        try (SipLoader sipLoader = new SipLoader(path, workDir, isKeepFiles);) {
        	
            SipValidator sipValidator = new SipValidator(profilValidace, excludeChecks);
            sipValidator.setHrozba(hrozba);
            sipValidator.validate(sipLoader);
        
            return sipLoader.getSip();
        }
	}

    @Override
    public ValidationProfiles getValidatorType() {
        return ValidationProfiles.NSESSS2017;
    }

}
