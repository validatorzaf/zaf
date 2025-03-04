package cz.zaf.eadvalidator.ap2023.profile;

/**
 * Přípustné typy archivních pomůcek
 */
public enum FindingAidType {
	PROZ_INV_SEZNAM("prozatimní inventární seznam"),
	MANIP_SEZNAM("manipulační seznam"),
	INVENTAR("inventář"),
	KATALOG("katalog");
	
	final String findingAidName;
	
	private FindingAidType(final String findingAidName) {
		this.findingAidName = findingAidName;
	}

	public String getFindingAidName() {
		return findingAidName;
	}
}
