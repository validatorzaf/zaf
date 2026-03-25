package cz.zaf.sipvalidator.nsesss2024;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2024.pravidla05.val00_09.Pravidlo1;

/**
 * Kontrola souladu se schématem XSD
 */
public class K05_ProtiSchematu
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>> {

    static final public String NAME = "proti schématu";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K05_ProtiSchematu() {
        super(TypUrovenKontroly.PROTI_SCHEMATU);
    }

    @Override
    public void validateImpl() {
        SimpleRuleContext<KontrolaNsessContext> schemaCheckContext = new SimpleRuleContext<>(ctx);
        this.provedKontrolu(schemaCheckContext, createRules(ruleClasses));
    }

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }
}
