package cz.zaf.sipvalidator.nsesss2024;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerType;

abstract public class KontrolaBase<KontrolaContext extends RuleEvaluationContext>
        extends BaseValidationLayer<KontrolaNsessContext, KontrolaContext> {

    protected KontrolaBase(final ValidationLayerType validationType) {
        super(validationType);
    }

    /**
     * Instantiate rules from class array. Static variant usable from
     * contexts without a layer instance (e.g. ValidatorInfo).
     */
    public static List<Rule<? extends RuleEvaluationContext>> instantiateRules(Class<?>[] ruleClasses) {
        List<Rule<? extends RuleEvaluationContext>> rules = new ArrayList<>(ruleClasses.length);
        for (Class<?> ruleClass : ruleClasses) {
            try {
                Constructor<?> constr = ruleClass.getDeclaredConstructor();
                @SuppressWarnings("unchecked")
                Rule<? extends RuleEvaluationContext> rule = (Rule<? extends RuleEvaluationContext>) constr.newInstance();
                rules.add(rule);
            } catch (Exception e) {
                throw new IllegalStateException("Nelze vytvořit třídu pravidla: " + ruleClass.getName(), e);
            }
        }
        return rules;
    }
}
