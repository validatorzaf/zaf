package cz.zaf.common.validation;

import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationResult;

public interface ValidationLayerContext {

    /**
     * Return validation results
     * 
     * @return
     */
    ValidationResult getValidationResult();

    default void addLayerResult(ValidationLayerResult vlr) {
        getValidationResult().getValidationLayerResults().add(vlr);
    }

    /**
     * Return if rule is excluded
     * 
     * @param code
     * @return
     */
    boolean isExcluded(String code);
}
