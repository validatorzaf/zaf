package cz.zaf.sipvalidator.nsesss2024;

import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerType;

abstract public class KontrolaBase<KontrolaContext extends RuleEvaluationContext>
        extends BaseValidationLayer<KontrolaNsessContext, KontrolaContext> {
	
	/**
	 * List of rule classes
	 */
	protected List<Class<? extends PravidloBase >> ruleClasses;

    protected KontrolaBase(final ValidationLayerType validationType) {
        super(validationType);
    }

    /*
	@Override
	protected void validateImpl() {
		SimpleRuleContext<KontrolaContext> virtContext = new SimpleRuleContext<>();
		
		var rules = createRules();		
		provedKontrolu(this, rules);
	}
	
	public List<? extends Rule<KontrolaContext> > createRules() {
		return createRules(ruleClasses);
	}*/
}
