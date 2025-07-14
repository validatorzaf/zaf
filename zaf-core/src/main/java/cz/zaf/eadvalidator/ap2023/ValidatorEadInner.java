package cz.zaf.eadvalidator.ap2023;

import cz.zaf.common.validation.InnerFileValidator;
import cz.zaf.common.validation.ValidationLayerContext;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class ValidatorEadInner<T extends ValidationLayerContext> implements InnerFileValidator<T> {	

	private EadLoader eadLoader;
	private AP2023Profile eadProfile;
		
	public ValidatorEadInner(String inputFileName, EadLoader eadLoader, 
			AP2023Profile eadProfile) {
		this.eadLoader = eadLoader;
		this.eadProfile = eadProfile;
	}

	@Override
	public void validate(T context, String innerFileName) throws Exception {
		var eadValidator = new EadValidator(eadProfile, null, innerFileName);
		eadValidator.validate(eadLoader);
	}
}
