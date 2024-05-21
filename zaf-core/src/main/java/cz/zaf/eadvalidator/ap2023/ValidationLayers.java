package cz.zaf.eadvalidator.ap2023;

import cz.zaf.common.validation.ValidationLayerInfo;
import cz.zaf.eadvalidator.ap2023.layers.fvl01.FormatValidationLayer;

public enum ValidationLayers implements ValidationLayerInfo {
    BASIC_FILE_FORMAT(FormatValidationLayer.NAME);

    private final String description;

    ValidationLayers(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
