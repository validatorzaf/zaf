package cz.zaf.earkvalidator;

import cz.zaf.common.validation.ValidationLayerType;

public enum ValidationLayers implements ValidationLayerType {
	
	DATA("datová správnost");
	
	private final String description;
	
	private ValidationLayers(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
