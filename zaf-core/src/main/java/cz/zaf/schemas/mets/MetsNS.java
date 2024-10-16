package cz.zaf.schemas.mets;

/**
 * METS namespace definitions and constants
 */
public class MetsNS {

	public static final String  NS_METS = "http://www.loc.gov/METS/";
	public static final String  NS_METS_LOCATION = "http://www.loc.gov/standards/mets/mets.xsd";

	static final public String SCHEMA_RESOURCE_V_1_11 = "/schema/mets/v1.11/mets.xsd";
	static final public String SCHEMA_RESOURCE_V_1_12_1 = "/schema/mets/v1.12.1/mets.xsd";
	
	static final public String AGENT_TYPE_OTHER = "OTHER";
	static final public String AGENT_TYPE_ORGANIZATION = "ORGANIZATION";
	static final public String AGENT_TYPE_PERSON = "PERSON";
	public static final String AGENT_ROLE_CREATOR = "CREATOR";
	public static final String STATUS_CURRENT = "CURRENT";
	public static final String STATUS_SUPERSEDED = "SUPERSEDED";
	public static final String LOCTYPE_URL = "URL";
	public static final String MDTYPE_EAD = "EAD";
	public static final String MDTYPE_PREMIS = "PREMIS";
	public static final String CHECKSUMTYPE_SHA_512 = "SHA-512";
}
