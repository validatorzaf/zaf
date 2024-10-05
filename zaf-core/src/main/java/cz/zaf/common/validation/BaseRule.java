package cz.zaf.common.validation;

import java.util.Objects;

public abstract class BaseRule<Ctx extends RuleEvaluationContext> implements Rule<Ctx> {

    final protected String code;
    final protected String ruleText;
    final protected String ruleError;
    final protected String ruleSource;

    public BaseRule(final String code,
                    final String ruleText,
                    final String ruleError,
                    final String ruleSource) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(ruleText);

        this.code = code;
        this.ruleText = ruleText;
        this.ruleError = ruleError;
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
        return ruleText;
    }

    @Override
    public String getGenericError() {
        return ruleError;
    }

}
