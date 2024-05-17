package cz.zaf.common.result;

import java.util.List;

public interface ValidationResult {

    /**
     * Return primary identifier of validated object
     * 
     * @return Return primary identifier or return null if not exists
     */
    String getValidatedObjectId();

    /**
     * Return name of validated object
     * 
     * This is typically name of validated file or directory
     * 
     * @return
     */
    String getValidatedObjectName();

    List<ValidationLayerResult> getValidationLayerResults();

}
