package cz.zaf.common.validation;

/**
 * Validation rule
 * 
 * This is abstract interface with rule definition.
 */
public interface Rule {

    /**
     * Rule code
     * 
     * @return
     */
    public String getCode();

    /**
     * Rule source
     * 
     * @return
     */
    public String getRuleSource();

    /**
     * Return rule description
     * 
     * @return rule description
     */
    public String getDescription();

    /**
     * Return generic error description
     * 
     * @return generic error description
     */
    public String getGenericError();
}
