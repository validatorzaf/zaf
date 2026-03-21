package cz.zaf.sipvalidator.nsesss2024;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2024.pravidla02.kod00_09.Pravidlo1;

/**
 * Kontroluje kódování SIP souboru.
 */
public class K02_ZnakoveSady
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>> {

    static final public String NAME = "znakové sady";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K02_ZnakoveSady() {
        super(TypUrovenKontroly.ZNAKOVE_SADY);
    }

    @Override
    public void validateImpl() {
        SimpleRuleContext<KontrolaNsessContext> kodCheckContext = new SimpleRuleContext<>(ctx);
        this.provedKontrolu(kodCheckContext, createRules(ruleClasses));
    }

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }
}
