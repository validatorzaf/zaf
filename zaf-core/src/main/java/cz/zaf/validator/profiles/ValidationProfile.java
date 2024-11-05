package cz.zaf.validator.profiles;

import cz.zaf.common.validation.ValidatorInfo;
import cz.zaf.earkvalidator.ValidatorDAAIP2024;
import cz.zaf.sipvalidator.nsesss2017.ValidatorNsesss2017;

/**
 * Profil validace
 */
public enum ValidationProfile {
	NSESSS2017(ValidatorNsesss2017.getValidatorInfo()),
    NSESSS2023(null),
    AP2023(null),
    DAAIP2024(ValidatorDAAIP2024.getValidatorInfo()),
    DAAIP2024_PREMIS(ValidatorDAAIP2024.getPremisValidatorInfo());	
	
	private ValidatorInfo validatorInfo;

	ValidationProfile(final ValidatorInfo validatorInfo) {
		this.validatorInfo = validatorInfo;
	}

	public ValidatorInfo getValidatorInfo() {
		return validatorInfo;
	}
}
