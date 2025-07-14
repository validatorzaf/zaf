package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidatorListener;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;

abstract public class SipValidatorBase implements ValidatorListener<KontrolaNsessContext> {


    final protected List<ValidationLayer<KontrolaNsessContext>> kontroly;

    /**
     * Seznam kontrol k vynechani
     */
    private List<String> excludeChecks;

    public SipValidatorBase(final List<ValidationLayer<KontrolaNsessContext>> kontroly,
                        final List<String> excludeChecks) {
        this.kontroly = kontroly;
        this.excludeChecks = excludeChecks;
    }


    public void validate(SipLoader sipLoader) {

        SipInfo sip = sipLoader.getSip();
        
        // provedeni kontrol
        KontrolaNsessContext ctx = new KontrolaNsessContext(sip, excludeChecks);
        BaseValidator<KontrolaNsessContext> validator = new BaseValidator<>(kontroly);
        validator.registerListener(this);
        validator.validate(ctx);
    }
}
