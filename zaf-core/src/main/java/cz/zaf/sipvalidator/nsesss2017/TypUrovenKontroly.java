package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.ValidationType;

/**
 * Urovne kontroly
 * 
 *
 */
public enum TypUrovenKontroly
        implements ValidationType {
    SKODLIVY_KOD,
    DATOVE_STRUKTURY,
    ZNAKOVE_SADY,
    SPRAVNOSTI,
    JMENNE_PROSTORY_XML,
    PROTI_SCHEMATU,
    OBSAHOVA,
    KOMPONENT;
	
    private TypUrovenKontroly() {
	}
}
