package cz.zaf.common.validation;

import java.util.List;

import cz.zaf.common.exceptions.ZafException;

/**
 * Collector of recoverable rule errors.
 *
 * Allows a rule to report an error and continue with further checks
 * instead of throwing the exception and aborting the evaluation.
 * Collected errors are drained by the validation layer after the rule
 * evaluation finishes and are reported as separate validation errors,
 * see {@link BaseValidationLayer#provedKontrolu}.
 */
public interface RuleErrorCollector {

    /**
     * Add recoverable error and continue with the evaluation.
     *
     * @param error detected error
     * @throws TooManyRuleErrorsException when the maximal number of
     *         collected errors is reached
     */
    void addError(ZafException error);

    /**
     * Return collected errors and reset the collector.
     *
     * @return collected errors in order of detection, empty list if none
     */
    List<ZafException> takeErrors();
}
