package cz.zaf.sipvalidator.sip;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseValidationContext;

/**
 * Kontext provadene kontroly
 *
 */
public abstract class KontrolaContext extends BaseValidationContext {

    /**
     * Property to set date of validation as external parameter
     * 
     * Format of date is yyyy-mm-dd
     */
    public static final String ZAF_VALIDATION_DATE = "zaf.validation.date";

    /**
     * Date when check is executed
     */
    final private LocalDate localDate;

    /**
	 * SIP nad nimz je provadena kontrola
	 */
	final protected SipInfo sip;
	
    public KontrolaContext(final SipInfo sip, final List<String> excludeCheckList) {
    	super(excludeCheckList);
		this.sip = sip;

		String extLocalDate = System.getProperty(ZAF_VALIDATION_DATE);
        if (StringUtils.isNotEmpty(extLocalDate)) {
            this.localDate = LocalDate.parse(extLocalDate);
        } else {
            this.localDate = LocalDate.now();
        }
	}

    /**
     * Return current date
     * 
     * @return
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

	public SipInfo getSip() {
		return sip;
	}

    @Override
    public ValidationResult getValidationResult() {
        return sip;
    }
}
