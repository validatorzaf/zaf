package cz.zaf.sipvalidator.nsesss2024;

import cz.zaf.sipvalidator.nsesss2024.pravidla05.val00_09.Pravidlo1;

/**
 * Kontrola souladu se schématem XSD
 */
public class K05_ProtiSchematu
        extends KontrolaBase<KontrolaNsessContext> {

    static final public String NAME = "proti schématu";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K05_ProtiSchematu() {
        super(TypUrovenKontroly.PROTI_SCHEMATU);
    }

    @Override
    public void validateImpl() {
        this.provedKontrolu(ctx, createRules(ruleClasses));
    }

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }
}
