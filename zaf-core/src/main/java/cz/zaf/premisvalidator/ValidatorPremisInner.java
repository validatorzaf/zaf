package cz.zaf.premisvalidator;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.InnerFileValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidationLayerContext;

public class ValidatorPremisInner<T extends ValidationLayerContext> implements InnerFileValidator<T> {
	
	final Path premisPath;
	final List<ValidationLayer<T>> validations;
	
	public ValidatorPremisInner(Path inputPath, List<ValidationLayer<T>> validations) {
		this.premisPath = inputPath;
		this.validations = validations;
	}
	
	@Override
	public void validate(T context, String innerFileName) throws Exception {
		BaseValidator<T> validator = new BaseValidator<T>(validations);
		validator.validate(context);		
	}
}
