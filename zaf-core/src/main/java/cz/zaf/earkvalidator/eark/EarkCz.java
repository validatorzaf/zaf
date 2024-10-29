package cz.zaf.earkvalidator.eark;

/**
 * Doplňující konstanty pro český profil EARK
 */
public class EarkCz {
	public static final String CONTENT_TYPE_NSESSS = "NSESSS";
	public static final String CONTENT_TYPE_VOLNE_SOUBORY = "Volné soubory";
	public static final String CONTENT_TYPE_CHANGE_REQUEST = "change_request_v1_0";
	
	public static final String GROUPID_INHERENT = "INHERENT";
	public static final String GROUPID_CONTEXTUAL = "CONTEXTUAL";
	public static final String GROUPID_PRESERVATION = "PRESERVATION";
	
	public static final String STRUCTMAP_TYPE_LOGICAL = "LOGICAL";
	public static final String STRUCTMAP_LOGICAL_NAME = "CZDAX-LOGICAL-STRUCTURE";	
	
	public static final String PACKAGE_INFO_FILENAME = "PACKAGE-INFO.xml";
	
	// Odkaz na aktuální balíček. Používá se ve spojení s lokálním identifikátorem (local).
	public static final String OBJECT_IDENTIFIER_ITSELF = "_THIS";
	
	// CZDAX-PMS0304
	public static final String EVENT_DATETIME_NA = "NA";
	
	public static final String NSESSS_LEVEL_SPISPLAN = "spisplan";
	public static final String NSESSS_LEVEL_VECNASKP = "vecnaskp";
	public static final String NSESSS_LEVEL_DOKUMENT = "dokument";
	public static final String NSESSS_LEVEL_SPIS = "spis";
	public static final String NSESSS_LEVEL_KOMPONENTA = "komponenta";
	public static final String NSESSS_LEVEL_VERKOMPONENTY = "verkomponenty";
	public static final String NSESSS_LEVEL_TYPSPIS = "typspis";
	public static final String NSESSS_LEVEL_DIL = "dil";
	
	public static final String VOLNE_SOUBORY_LEVEL_BALICEK = "balicek";
	public static final String VOLNE_SOUBORY_LEVEL_SLOZKA = "slozka";
	public static final String VOLNE_SOUBORY_LEVEL_KOMPONENTA = "komponenta";
}
