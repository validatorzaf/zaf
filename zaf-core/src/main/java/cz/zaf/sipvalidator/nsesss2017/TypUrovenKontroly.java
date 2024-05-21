package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.ValidationLayerInfo;

/**
 * Urovne kontroly
 * 
 *
 */
public enum TypUrovenKontroly
        implements ValidationLayerInfo {
    SKODLIVY_KOD(K00_SkodlivehoKodu.NAME),
    DATOVE_STRUKTURY(K01_DatoveStruktury.NAME),
    ZNAKOVE_SADY(K02_ZnakoveSady.NAME),
    SPRAVNOSTI(K03_Spravnosti.NAME),
    JMENNE_PROSTORY_XML(K04_JmennychProstoruXML.NAME),
    PROTI_SCHEMATU(K05_ProtiSchematu.NAME),
    OBSAHOVA(K06_Obsahova.NAME),
    KOMPONENT(K07_Komponent.NAME);

    private final String description;
	
    private TypUrovenKontroly(final String description) {
        this.description = description;
	}

    @Override
    public String getDescription() {
        return description;
    }
}
