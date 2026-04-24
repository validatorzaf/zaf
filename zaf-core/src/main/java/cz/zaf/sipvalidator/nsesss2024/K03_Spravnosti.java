package cz.zaf.sipvalidator.nsesss2024;

import java.util.List;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2024.pravidla03.wf00_09.Pravidlo1;

/**
 * Kontrola správnosti XML
 */
public class K03_Spravnosti
        extends KontrolaBase<KontrolaNsessContext> {

    static final public String NAME = "správnosti XML";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K03_Spravnosti() {
        super(TypUrovenKontroly.SPRAVNOSTI);
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
