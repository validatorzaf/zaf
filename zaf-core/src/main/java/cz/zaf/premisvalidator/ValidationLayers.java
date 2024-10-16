package cz.zaf.premisvalidator;

import cz.zaf.common.validation.ValidationLayerType;

public enum ValidationLayers implements ValidationLayerType {
	
    ENCODING("Kódování"),
    WELL_FORMED("Správnost formátu"),
	NAMESPACE("Jmenné prostory"),
	VALIDATION("Soulad se schématem"),
	OBSAH("Obsahové kontroly");

	private final String description;
	
	private ValidationLayers(final String description) {
		this.description = description;
	}	

	@Override
	public String getDescription() {
		return description;
	}

}
