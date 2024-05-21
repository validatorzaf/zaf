package cz.zaf.common.result;

/**
 * Basic information about used validation profile
 */
public interface ValidationProfileInfo {

    /**
     * Return name of validation profile
     * 
     * @return
     */
    String getProfileName();

    /**
     * Return name of applied validation
     * 
     * Each Profile has one or more set of rules (validation types).
     * 
     * @return
     */
    String getValidationType();

    /**
     * Return version of applied rule set
     * 
     * @return
     */
    String getProfileVersion();
}
