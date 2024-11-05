package cz.zaf.earkvalidator;

import cz.zaf.common.validation.BaseValidationLayer;

public abstract class AipValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {
	
	public AipValidationLayer(ValidationLayers validationType) {
		super(validationType);		
	}
	
	public AipValidationLayer(final ValidationLayers validationType, 
			final String innerFileName) {
		super(validationType, innerFileName);
		
	}
}
