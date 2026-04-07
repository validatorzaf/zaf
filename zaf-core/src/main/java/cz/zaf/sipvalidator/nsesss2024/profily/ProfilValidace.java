package cz.zaf.sipvalidator.nsesss2024.profily;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.sipvalidator.nsesss2024.KontrolaNsessContext;

/**
 * Rozhraní pro validační profily
 *
 */
public interface ProfilValidace extends ValidationSubprofile {

    /**
     * Vrátí seznam t5=říd obsahových kontrol
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
     *
     * @return Seznam tříd pravidel
     */    
    public List<Class<? extends BaseRule<KontrolaNsessContext>>> getComponentRuleClasses();
}
