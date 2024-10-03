package cz.zaf.common.result;

import java.util.ArrayList;
import java.util.List;

public class ValidationResultImpl implements ValidationResult {
	
	final String validatedObjectId;
	final String validatedObjectName;
	
	final List<ValidationLayerResult> layerResults = new ArrayList<>();
	
	public ValidationResultImpl(final String validatedObjectId, final String validatedObjectName) {
		this.validatedObjectId = validatedObjectId;
		this.validatedObjectName = validatedObjectName;
	}

	@Override
	public String getValidatedObjectId() {
		return validatedObjectId;
	}

	@Override
	public String getValidatedObjectName() {
		return validatedObjectName;
	}

	@Override
	public List<ValidationLayerResult> getValidationLayerResults() {
		return layerResults;
	}

}
