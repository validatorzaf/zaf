package cz.zaf.common.validation;

import cz.zaf.common.result.ValidationResult;

public interface Validator {
    public ValidationResult validateBalicek(String balicekPath) throws Exception;
}
