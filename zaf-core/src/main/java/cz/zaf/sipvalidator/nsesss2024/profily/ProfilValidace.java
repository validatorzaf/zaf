package cz.zaf.sipvalidator.nsesss2024.profily;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.sipvalidator.nsesss2024.KontrolaNsessContext;

/**
 * Rozhraní pro validační profily
 *
 */
public interface ProfilValidace extends ValidationSubprofile {

    /**
     * Vrátí seznam připravených obsahových kontrol
     *
     * Obsahová pravidla mohou být používána
     * jen v rámci jednoho vlákna. Mají svůj vnitřní stav
     * při běhu kontroly.
     *
     * @return Pole pravidel
     */
    public Rule<KontrolaNsessContext>[] createObsahovaPravidla();

    /**
     * Vrátí seznam tříd formátových kontrol
     *
     *
     * @return Seznam tříd pravidel
     */    
    public List<Class<? extends BaseRule<KontrolaNsessContext>>> getComponentRuleClasses();
}
