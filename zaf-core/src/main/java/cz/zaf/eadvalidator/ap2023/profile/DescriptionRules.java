package cz.zaf.eadvalidator.ap2023.profile;

/**
 * Pravidla popisu s definicí podporovaných typů pomůcek
 */
public enum DescriptionRules {
	CZ_ZP1958("základní pravidla z roku 1958",
			new FindingAidType[] {FindingAidType.PROZ_INV_SEZNAM, FindingAidType.MANIP_SEZNAM, FindingAidType.INVENTAR, FindingAidType.KATALOG} ),
	CZ_ZP2013("základní pravidla od roku 2013", 
			new FindingAidType[] {FindingAidType.MANIP_SEZNAM, FindingAidType.INVENTAR, FindingAidType.KATALOG});
	
	private final String ruleName;
	
	/**
	 * Přípustné typy pomůcek
	 */
	private final FindingAidType findingAidTypes[];
	
	private DescriptionRules(final String ruleName,
			final FindingAidType findingAidTypes[]) {
		this.ruleName = ruleName;
		this.findingAidTypes = findingAidTypes;
	}

	public String getRuleName() {
		return ruleName;
	}
	
	/**
	 * Kontrola zda daný typ pomůcky lze použít
	 * @param findingAidType
	 * @return
	 */
	public boolean isApplicable(final FindingAidType findingAidType) {
		for(FindingAidType type : findingAidTypes) {
			if(type.equals(findingAidType)) {
				return true;
			}
		}
		return false;
	}
}
