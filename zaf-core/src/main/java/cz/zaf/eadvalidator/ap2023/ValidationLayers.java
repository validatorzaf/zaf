package cz.zaf.eadvalidator.ap2023;

import cz.zaf.common.validation.ValidationType;
import cz.zaf.eadvalidator.ap2023.layers.fvl01.FormatValidationLayer;

public enum ValidationLayers implements ValidationType {
    BASIC_FILE_FORMAT(FormatValidationLayer.NAME);

    private final String description;

    ValidationLayers(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
