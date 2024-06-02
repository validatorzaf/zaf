package cz.zaf.eadvalidator.ap2023;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerContext;

public class EadValidationContext implements RuleEvaluationContext, ValidationLayerContext {

    private EadLoader eadLoader;

    EadValidationContext(final EadLoader eadLoader) {
        this.eadLoader = eadLoader;
    }

    public EadLoader getLoader() {
        return eadLoader;
    }

    @Override
    public ValidationResult getValidationResult() {
        return eadLoader;
    }

    @Override
    public boolean isExcluded(String code) {
        return false;
    }
}
