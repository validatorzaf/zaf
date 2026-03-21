package cz.zaf.sipvalidator.nsesss2024;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2024.pravidla03.wf00_09.Pravidlo1;

/**
 * Kontrola správnosti XML
 */
public class K03_Spravnosti
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>> {

    static final public String NAME = "správnosti XML";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K03_Spravnosti() {
        super(TypUrovenKontroly.SPRAVNOSTI);
    }

    @Override
    public void validateImpl() {
        SimpleRuleContext<KontrolaNsessContext> wfCheckContext = new SimpleRuleContext<>(ctx);
        this.provedKontrolu(wfCheckContext, createRules(ruleClasses));
    }

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }
}
