package cz.zaf.sipvalidator.sip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.ValidationLayerContext;

/**
 * Kontext provadene kontroly
 *
 */
public abstract class KontrolaContext implements ValidationLayerContext {

	/**
	 * SIP nad nimz je provadena kontrola
	 */
	final protected SipInfo sip;
	
    /**
     * Seznam ignorovanych kontrol
     */
    final Set<String> excludeChecks = new HashSet<>();

    public KontrolaContext(final SipInfo sip, final List<String> excludeCheckList) {
		this.sip = sip;
        this.excludeChecks.addAll(excludeCheckList);
	}

    @Override
    public boolean isExcluded(String ruleCode) {
        return excludeChecks.contains(ruleCode);
    }

	public SipInfo getSip() {
		return sip;
	}

    @Override
    public ValidationResult getValidationResult() {
        return sip;
    }
}
