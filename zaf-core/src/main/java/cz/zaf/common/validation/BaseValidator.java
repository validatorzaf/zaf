package cz.zaf.common.validation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationStatus;

/**
 * Base implementation of validator
 * 
 * This implementation will call all required validations
 * with given context
 */
public class BaseValidator<T extends ValidationLayerContext> {

    static private Logger log = LoggerFactory.getLogger(BaseValidator.class);

    protected final List<ValidationLayer<T>> validationLayers;
    
    /**
     * Validator listeners
     */
    private List<ValidatorListener<T>> listeners = new ArrayList<>();

    public BaseValidator(final List<ValidationLayer<T>> validations) {
        this.validationLayers = validations;
    }

    public void registerListener(ValidatorListener<T> listener) {
        listeners.add(listener);
    }

    public void unregisterListener(ValidatorListener<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Run validation with given context
     * 
     * @param context
     */
    public void validate(T context) {
        ValidationLayer<T> activeLayer = null;

        try {
            for (ValidationLayer<T> validationLayer : validationLayers) {
                activeLayer = validationLayer;

                listeners.forEach(l -> l.layerValidationStarted(context, validationLayer));

                // po selhane kontrole se jiz nepokracuje
                ValidationResult validationResult = context.getValidationResult();
                // add result
                ValidationLayerResult vlr = new ValidationLayerResult(validationLayer.getType());
                context.addLayerResult(vlr);
                // vychozi stav je ok
                vlr.setStav(ValidationStatus.OK);

                validationLayer.validate(context, vlr);

                listeners.forEach(l -> l.layerValidationFinished(context, validationLayer));

                if (validationResult.isFailed()) {
                    // return as non executed
                    return;
                }
            }
            activeLayer = null;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Uncatched exception");
            if (activeLayer != null) {
                sb.append(", aktivniKontrola: ").append(activeLayer.getType().getDescription());
            }
            log.error(sb.toString() + ", detail: " + e.toString(), e);
            throw new IllegalStateException(sb.toString(), e);
        }

    }
}
