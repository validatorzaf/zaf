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

    /**
     * Get validation results for all validated layers
     * 
     * @return
     */
    List<ValidationLayerResult> getValidationLayerResults();

    /**
     * Check if validation failed
     * 
     * @return
     */
    default boolean isFailed() {
        return isFailed(this);
    }

    static boolean isFailed(ValidationResult vr) {
        List<ValidationLayerResult> kontroly = vr.getValidationLayerResults();
        if (kontroly == null || kontroly.size() == 0) {
            // not failed if empty
            return false;
        }

        // dle stavu posledni kontroly se rozhodneme
        ValidationLayerResult posledniKontrola = kontroly.get(kontroly.size() - 1);
        ValidationStatus stav = posledniKontrola.getValidationStatus();
        if (stav != ValidationStatus.OK) {
            // error or not executed -> selhani jiz nekde drive
            return true;
        }
        return false;
    }
}
