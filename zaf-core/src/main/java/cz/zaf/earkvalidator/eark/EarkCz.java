package cz.zaf.earkvalidator.eark;

/**
 * Doplňující konstanty pro český profil EARK
 */
public class EarkCz {
	public static final String CONTENT_TYPE_NSESSS = "NSESSS";
	public static final String CONTENT_TYPE_VOLNE_SOUBORY = "Volné soubory";
	
	public static final String GROUPID_INHERENT = "INHERENT";
	public static final String GROUPID_CONTEXTUAL = "CONTEXTUAL";
	public static final String GROUPID_PRESERVATION = "PRESERVATION";
	
	public static final String STRUCTMAP_TYPE_LOGICAL = "LOGICAL";
	
	public static final String PACKAGE_INFO_FILENAME = "PACKAGE-INFO.xml";
	
	// Odkaz na aktuální balíček. Používá se ve spojení s lokálním identifikátorem (local).
	public static final String OBJECT_IDENTIFIER_ITSELF = "_THIS";
}
