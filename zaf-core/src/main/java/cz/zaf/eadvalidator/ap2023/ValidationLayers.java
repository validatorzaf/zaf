package cz.zaf.eadvalidator.ap2023;

import cz.zaf.common.validation.ValidationLayerType;

public enum ValidationLayers implements ValidationLayerType {
    ENCODING("Kódování"),
    WELL_FORMED("Správnost formátu");

    private final String description;

    ValidationLayers(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
