package cz.zaf.sipvalidator.nsesss2017.pravidla07;

import cz.zaf.common.validation.Rule;

/**
 * Pravidlo pro formát komponent
 * 
 * Formátové pravidlo může být používáno jen v rámci jednoho vlákna.
 * V průběhu kontroly má uložen svůj vnitřní stav.
 * 
 */
public interface KomponentovePravidlo extends Rule {

    /**
     * Metoda pro spusteni kontroly pravidla
     * 
     * @param kontrola
     *            Kontext kontroly
     */
    void kontrolaPravidla(final K07KontrolaContext kontrola);
}
