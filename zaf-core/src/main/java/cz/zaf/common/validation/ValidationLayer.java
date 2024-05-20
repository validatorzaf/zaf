package cz.zaf.common.validation;

import cz.zaf.common.result.ValidationLayerResult;

/**
 * Layer of validation
 */
public interface ValidationLayer<T extends ValidationLayerContext> {

    /**
     * Return validation type
     * 
     * @return
     */
    public ValidationType getType();
    
    /**
     * Validate rules in layer
     * 
     * @param context
     * @param result
     *            Result of validation. Result object is constructed before calling
     *            this validation.
     *            Any validation might end with unexpected exception.
     * 
     */
    public void validate(T context, ValidationLayerResult result) throws Exception;
}
