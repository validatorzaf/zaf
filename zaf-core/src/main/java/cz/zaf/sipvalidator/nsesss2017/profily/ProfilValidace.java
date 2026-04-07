package cz.zaf.sipvalidator.nsesss2017.profily;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.sipvalidator.nsesss2017.KontrolaNsessContext;

/**
 * Rozhraní pro validační profily
 *
 */
public interface ProfilValidace extends ValidationSubprofile {

    /**
     * Vrátí seznam tříd obsahových kontrol
     *
     * Obsahová pravidla mohou být používána
     * jen v rámci jednoho vlákna. Mají svůj vnitřní stav
     * při běhu kontroly.
     *
     * @return Seznam tříd pravidel
     */
    public List<Class<? extends BaseRule<KontrolaNsessContext>>> getContentRuleClasses();

    /**
     * Vrátí seznam tříd formátových kontrol
     *
     * Obsahová pravidla mohou být používána
     * jen v rámci jednoho vlákna. Mají svůj vnitřní stav
     * při běhu kontroly.
     *
     * @return Seznam tříd pravidel
     */
    public List<Class<? extends BaseRule<KontrolaNsessContext>>> getComponentRuleClasses();
}
