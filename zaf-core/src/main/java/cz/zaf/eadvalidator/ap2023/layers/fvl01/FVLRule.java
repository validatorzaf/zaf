package cz.zaf.eadvalidator.ap2023.layers.fvl01;

import cz.zaf.common.validation.Rule;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;

public abstract class FVLRule implements Rule<EadValidationContext> {

    private final String code;
    private final String description;
    private final String ruleSource;
    private final String genericError;

    public FVLRule(final String code, String description,
                   String genericError,
                   String ruleSource) {
        this.code = code;
        this.description = description;
        this.genericError = genericError;
        this.ruleSource = ruleSource;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getRuleSource() {
        return ruleSource;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getGenericError() {
        return genericError;
    }

}
