package cz.zaf.sipvalidator.nsesss2024;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2024.pravidla00.vir00_09.Pravidlo1;

/**
 * Kontrola skodliveho kodu
 */
public class K00_SkodlivehoKodu
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>> {

    static public final String NAME = "škodlivého kódu";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K00_SkodlivehoKodu() {
        super(TypUrovenKontroly.SKODLIVY_KOD);
    }

    @Override
    public void validateImpl() {
        SimpleRuleContext<KontrolaNsessContext> virtCheckContext = new SimpleRuleContext<>(ctx);
        this.provedKontrolu(virtCheckContext, createRules(ruleClasses));
    }

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }
}
