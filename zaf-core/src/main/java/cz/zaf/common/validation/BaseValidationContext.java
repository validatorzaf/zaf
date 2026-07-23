package cz.zaf.common.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cz.zaf.common.exceptions.ZafException;

public abstract class BaseValidationContext implements ValidationLayerContext, RuleErrorCollector {

	/**
	 * Maximal number of errors collected during a single rule evaluation.
	 */
	public static final int MAX_RULE_ERRORS = 1000;

	private Set<String> excludeChecks = null;

	/**
	 * Recoverable errors collected during evaluation of a single rule.
	 *
	 * Drained by the validation layer after each rule evaluation.
	 */
	private List<ZafException> ruleErrors = new ArrayList<>();

	public BaseValidationContext(Collection<String> excludeChecks) {
		if(excludeChecks!=null) {
			this.excludeChecks = new HashSet<>(excludeChecks);
		} else {
			this.excludeChecks = Collections.emptySet();
		}
	}

	@Override
	public boolean isExcluded(String code) {
    	return excludeChecks.contains(code);
	}

	@Override
	public void addError(ZafException error) {
		Objects.requireNonNull(error);
		if (ruleErrors.size() >= MAX_RULE_ERRORS) {
			throw new TooManyRuleErrorsException(MAX_RULE_ERRORS);
		}
		ruleErrors.add(error);
	}

	@Override
	public List<ZafException> takeErrors() {
		if (ruleErrors.isEmpty()) {
			return Collections.emptyList();
		}
		List<ZafException> taken = ruleErrors;
		ruleErrors = new ArrayList<>();
		return taken;
	}
}
