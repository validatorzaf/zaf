package cz.zaf.sipvalidator.nsesss2024;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace;

/**
 * Kontrola komponent
 */
public class K07_Komponent extends KontrolaBase<KontrolaNsessContext> {

    static final public String NAME = "kontrola komponent";

	private List<Class<? extends BaseRule<KontrolaNsessContext>>> ruleClasses;

    public K07_Komponent(final ProfilValidace profilValidace) {
        super(TypUrovenKontroly.KOMPONENT);
        
        ruleClasses = profilValidace.getComponentRuleClasses();        
    }

    @Override
    protected void validateImpl() {
        provedKontrolu(ctx, createRules());
    }

	public List<? extends Rule<KontrolaNsessContext>> createRules() {
		return createRules(ruleClasses);
	}
}
