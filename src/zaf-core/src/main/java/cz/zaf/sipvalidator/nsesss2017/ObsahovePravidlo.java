package cz.zaf.sipvalidator.nsesss2017;

public interface ObsahovePravidlo {
	
	/**
	 * Metoda pro spusteni kontroly pravidla
	 * 
	 * @param kontrola Objekt obsahove kontroly
	 */
	void kontrolaPravidla(final K06_Obsahova kontrola);
}
