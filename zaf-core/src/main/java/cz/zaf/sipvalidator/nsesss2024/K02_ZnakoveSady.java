package cz.zaf.sipvalidator.nsesss2024;

import java.util.List;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2024.pravidla02.kod00_09.Pravidlo1;

/**
 * Kontroluje kódování SIP souboru.
 */
public class K02_ZnakoveSady
        extends KontrolaBase<KontrolaNsessContext> {

    static final public String NAME = "znakové sady";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K02_ZnakoveSady() {
        super(TypUrovenKontroly.ZNAKOVE_SADY);
    }

    @Override
    public void validateImpl() {
        this.provedKontrolu(ctx, createRules());
    }

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }

	public List<? extends Rule<KontrolaNsessContext>> createRules() {
		return createRules(ruleClasses);
	}
}
