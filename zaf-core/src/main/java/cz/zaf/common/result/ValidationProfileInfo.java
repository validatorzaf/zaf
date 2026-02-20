package cz.zaf.common.result;

/**
 * Basic information about used validation profile
 */
public interface ValidationProfileInfo {

    /**
     * Return name of validation type
     * 
     * 
     * @return
     */
    String getValidationType();

    /**
     * Return name of validation profile
     * 
     * Each validation type has one or more set of rules (validation profiles).
     * 
     * @return
     */
    String getProfileName();

    /**
     * Return version of applied rule set
     * 
     * @return
     */
    String getRuleVersion();
}
