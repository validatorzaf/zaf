package cz.zaf.sipvalidator.sip;

import java.util.List;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseValidationContext;

/**
 * Kontext provadene kontroly
 *
 */
public abstract class KontrolaContext extends BaseValidationContext {

	/**
	 * SIP nad nimz je provadena kontrola
	 */
	final protected SipInfo sip;
	
    public KontrolaContext(final SipInfo sip, final List<String> excludeCheckList) {
    	super(excludeCheckList);
		this.sip = sip;
	}

	public SipInfo getSip() {
		return sip;
	}

    @Override
    public ValidationResult getValidationResult() {
        return sip;
    }
}
