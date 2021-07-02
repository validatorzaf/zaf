package cz.zaf.sipvalidator.nsesss2017;

/**
 * Obsahové pravidlo
 * 
 * Obsahové pravidlo může být používáno jen v rámci jednoho vlákna. 
 * V průběhu kontroly má uložen svůj vnitřní stav.
 * 
 */
public interface ObsahovePravidlo {
	
	/**
	 * Metoda pro spusteni kontroly pravidla
	 * 
	 * @param kontrola Objekt obsahove kontroly
	 */
	void kontrolaPravidla(final K06_Obsahova kontrola);

	/**
	 * Vrati kod pravidla
	 * @return
	 */
	String getKodPravidla();
}
