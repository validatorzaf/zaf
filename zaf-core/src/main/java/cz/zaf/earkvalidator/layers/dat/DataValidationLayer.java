package cz.zaf.earkvalidator.layers.dat;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.AipValidationContext;

public class DataValidationLayer extends BaseValidationLayer<AipValidationContext, AipValidationContext> {

	public DataValidationLayer() {
		super(ValidationLayers.DATA);
	}

	@Override
	protected void validateImpl() {
		// TODO Auto-generated method stub
		System.out.println("TODO");
	}

}
