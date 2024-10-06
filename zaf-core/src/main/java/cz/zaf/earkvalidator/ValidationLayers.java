package cz.zaf.earkvalidator;

import cz.zaf.common.validation.ValidationLayerType;

public enum ValidationLayers implements ValidationLayerType {
	
	DATA("Datová správnost"),
    ENCODING("Kódování"),
    WELL_FORMED("Správnost formátu"),
	NAMESPACE("Jmenné prostory");
	
	private final String description;
	
	private ValidationLayers(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
