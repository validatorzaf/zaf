package cz.zaf.earkvalidator.eark;

public final class PremisConstants {
	public static final String IDENT_TYPE_LOCAL = "local";
	
	public static final String IDENT_TYPE_CAM = "CAM_ID";
	public static final String IDENT_TYPE_INSTITUTION = "INSTITUTION_ID";
	
	
	public static final String AGENT_TYPE_SOFTWARE = "sof";	
	public static final String AGENT_TYPE_PERSON = "per";
	public static final String AGENT_TYPE_ORGANIZATION = "org";
	public static final String AGENT_TYPE_OTHER_ORGANIZATION = "organization";
	
	// CZDAX-PKG0501 - vznik
	// CZDAX-PMP0203 - vznik / digitalizace
	// CZDAX-PMP0303 - vznik	
	final public static String EVENT_TYPE_CRE = "cre";
	// CZDAX-PKG0601 - Vložení do digitálního archivu
	final public static String EVENT_TYPE_ING = "ing";
	// CZDAX-PKG0701 - přesun
	final public static String EVENT_TYPE_TRA = "tra";
	// CZDAX-PKG0801 - export
	final public static String EVENT_TYPE_EXP = "exp";
	// CZDAX-PKG0901 - vznik balicku 
	final public static String EVENT_TYPE_IPC = "ipc";
	// CZDAX-PMP0205 - smazání
	final public static String EVENT_TYPE_DEL = "del";
	// PMP0207 - migrace
	final public static String EVENT_TYPE_MIG = "mig";
	//  PMP0212 - zabalení
	final public static String EVENT_TYPE_PAC = "pac";
	// PMP0216 - rozbalení
	final public static String EVENT_TYPE_UNP = "unp";
	// CZDAX-PMP0305 - Kontrola neporušenosti obsahu
	final public static String EVENT_TYPE_FIX = "fix";
	// CZDAX-PMP0307 - Antivirová kontrola
	final public static String EVENT_TYPE_VIR = "vir";
	// CZDAX-PMP0311 - Identifikace formátů
	final public static String EVENT_TYPE_FOR = "for";
	// CZDAX-PMP0314 - Validace
	final public static String EVENT_TYPE_VAL = "val";
	
	// CZDAX-PKG0606 - vložení do DA
	public static final String ROLE_OUT = "out";
	// CZDAX-PKG0606 - vložení do DA
	public static final String ROLE_SOU = "sou";
	// CZDAX-PKG0503
	public static final String ROLE_ORIGINATOR = "ORIGINATOR";
	// CZDAX-PKG0603
	public static final String ROLE_SUBMITTER = "SUBMITTER";
	// CZDAX-PKG0604
	public static final String ROLE_CURATOR = "CURATOR";
	
}
