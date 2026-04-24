package cz.zaf.sipvalidator.nsesss2024;

import java.util.List;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2024.pravidla04.ns00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2024.pravidla04.ns00_09.Pravidlo2;

/**
 * Kontrola jmenných prostorů
 */
public class K04_JmennychProstoruXML
        extends KontrolaBase<KontrolaNsessContext> {

    static final public String NAME = "jmenných prostorů";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class,
        Pravidlo2.class
    };

    public K04_JmennychProstoruXML() {
        super(TypUrovenKontroly.JMENNE_PROSTORY_XML);
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
