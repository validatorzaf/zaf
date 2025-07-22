package cz.zaf.sipvalidator.nsesss2024;

import java.util.List;

import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerType;

/**
 * Urovne kontroly
 * 
 *
 */
public enum TypUrovenKontroly
        implements ValidationLayerType {
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
    
    List<Rule<? extends RuleEvaluationContext>> getRules() {
		return null;
	}
}
