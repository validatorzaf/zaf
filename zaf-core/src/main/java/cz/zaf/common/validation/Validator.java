package cz.zaf.common.validation;

import java.nio.file.Path;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;

public interface Validator {

    /**
     * Run validation
     * 
     * @param path
     *            Absolute path to the validated object
     * @return Returns validation results
     * @throws Exception
     */
    public ValidationResult validate(Path path) throws Exception;

    /**
     * Return type of validator
     * 
     * @return
     */
    public ValidationProfileInfo getProfileInfo();
}
