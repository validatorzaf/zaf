package cz.zaf.common.validation;

/**
 * Validator listener will receive events about status of processing
 * 
 * 
 */
public interface ValidatorListener<T extends ValidationLayerContext> {
    void layerValidationStarted(T context, ValidationLayer<T> layer);

    void layerValidationFinished(T context, ValidationLayer<T> layer);
}
