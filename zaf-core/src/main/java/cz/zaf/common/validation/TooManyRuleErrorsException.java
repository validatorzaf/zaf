package cz.zaf.common.validation;

/**
 * Thrown by {@link RuleErrorCollector#addError} when the maximal number
 * of errors collected during a single rule evaluation is reached.
 *
 * Intentionally not a ZafException so a rule catching ZafException
 * cannot swallow it. The exception aborts the rule evaluation and is
 * handled by the validation layer which reports the errors collected
 * so far.
 */
public class TooManyRuleErrorsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int limit;

    public TooManyRuleErrorsException(final int limit) {
        super("Dosažen maximální počet chyb pravidla: " + limit);
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
