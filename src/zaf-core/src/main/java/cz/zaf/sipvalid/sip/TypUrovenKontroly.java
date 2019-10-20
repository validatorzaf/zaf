package cz.zaf.sipvalid.sip;

/**
 * Urovne kontroly
 * 
 *
 */
public enum TypUrovenKontroly {
	SKODLIVY_KOD(0),
	DATOVE_STRUKTURY(1),
	ZNAKOVE_SADY(2),
	SPRAVNOSTI(3),
	JMENNE_PROSTORY_XML(4),
	PROTI_SCHEMATU(5),
	OBSAHOVA(6);
	
	/**
	 * Ciselny kod urovne kontroly
	 */
	private int level;

	private TypUrovenKontroly(int level) {
		this.level = level;
	}
	
	/**
	 * Vrati uroven kontroly
	 * @return
	 */
	public int getLevel() {
		return level;
	}
}
