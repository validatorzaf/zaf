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

    protected final List<ValidationLayer<T>> validationLayers = new ArrayList<>();
    
    /**
     * Validator listeners
     */
    private List<ValidatorListener<T>> listeners = new ArrayList<>();

    public BaseValidator(final List<ValidationLayer<T>> validations) {
        this.validationLayers.addAll(validations);
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
        
        ValidationResult validationResult = context.getValidationResult();
        int iPos = 0;
        try {
        	// validation layers might be dynamically added
        	// validationLayers is mutable ArrayList
            for ( ; iPos<validationLayers.size(); iPos++) {
                activeLayer = validationLayers.get(iPos);

                final ValidationLayer<T> vl = activeLayer; 
                listeners.forEach(l -> l.layerValidationStarted(context, vl));

                // add result
                ValidationLayerResult vlr = new ValidationLayerResult(activeLayer.getType(), activeLayer.getInnerFileName());
                // vychozi stav je ok
                vlr.setStav(ValidationStatus.OK);
                validationResult.getValidationLayerResults().add(vlr);

                activeLayer.validate(context, vlr);

                listeners.forEach(l -> l.layerValidationFinished(context, vl));

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
        } finally {
        	iPos++;
            for ( ; iPos<validationLayers.size(); iPos++) {
            	activeLayer = validationLayers.get(iPos);
            	// add results for unprocessed layers
            	// new layer is by defaut non executed
            	ValidationLayerResult vlr = new ValidationLayerResult(activeLayer.getType(), activeLayer.getInnerFileName());
            	validationResult.getValidationLayerResults().add(vlr);
            }
        	
        }
    }
}
