package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;

/**
 * Obsahové pravidlo
 * 
 * Obsahové pravidlo může být používáno jen v rámci jednoho vlákna. 
 * V průběhu kontroly má uložen svůj vnitřní stav.
 * 
 */
public interface ObsahovePravidlo extends Rule {
	
	/**
	 * Metoda pro spusteni kontroly pravidla
	 * 
	 * @param kontrola Objekt obsahove kontroly
	 */
	void kontrolaPravidla(final K06_Obsahova kontrola);
}
