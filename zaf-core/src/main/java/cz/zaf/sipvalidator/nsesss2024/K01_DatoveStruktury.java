package cz.zaf.sipvalidator.nsesss2024;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo1a;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo3;

/**
 * Kontrola datové struktury
 */
public class K01_DatoveStruktury
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>>
{
    static final public String NAME = "datové struktury";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class,
        Pravidlo1a.class,
        Pravidlo2.class,
        Pravidlo3.class
    };

    public K01_DatoveStruktury() {
        super(TypUrovenKontroly.DATOVE_STRUKTURY);
    }

    @Override
    public void validateImpl() {
        SimpleRuleContext<KontrolaNsessContext> datCheckContext = new SimpleRuleContext<>(ctx);
        this.provedKontrolu(datCheckContext, createRules(ruleClasses));
    }

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }
}
