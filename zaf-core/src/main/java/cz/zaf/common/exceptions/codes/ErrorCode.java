package cz.zaf.common.exceptions.codes;

public interface ErrorCode {

    /**
     * Return error description
     * 
     * @return
     */
    String getDescription();

    /**
     * Error code ID
     * 
     * @return
     */
    String getErrorCode();
}
