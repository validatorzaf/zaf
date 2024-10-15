package cz.zaf.common;

public final class Properties {

	/**
	 * Property to set ZIP encoding.
	 * 
	 * If property is not set default encoding is UTF-8.
	 */
	public static final String ZAF_ZIP_ENCODING = "zaf.zip.encoding";

	/**
	 * Maximal allowed size of AIP.
	 * 
	 * If not set, default limit is 10 GB.
	 */
	public static final String ZAF_AIP_MAX_SIZE = "zaf.aip.max.size";

	/**
	 * Maximal number of files
	 * 
	 * If not set, default limit is 50 000.
	 */
	public static final String ZAF_AIP_MAX_FILECOUNT = "zaf.aip.max.filecount";

	/**
	 * Maximal allowed size of METS.xml.
	 * 
	 * If not set, default limit is 10 MB.
	 */
	public static final String ZAF_METS_MAX_SIZE = "zaf.mets.max.size";
}
