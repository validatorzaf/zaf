package cz.zaf.earkvalidator;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.ValidationLayerType;

public enum ValidationLayers implements ValidationLayerType {
	
	DATA("Datová správnost"),
    ENCODING("Kódování"),
    WELL_FORMED("Správnost formátu"),
	NAMESPACE("Jmenné prostory"),
	VALIDATION("Soulad se schématem"),
	OBSAH("Obsahové kontroly"),
	FILES("Kontroly souborů");
	
	private final String description;
	
	private final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses;
	
	private ValidationLayers(final String description) {
		this.description = description;
		this.ruleClasses = null;
	}

	private ValidationLayers(final String description, final List<Class<? extends BaseRule<AipValidationContext>>> ruleClasses) {
		this.description = description;
		this.ruleClasses = ruleClasses;
	}

	@Override
	public String getDescription() {
		return description;
	}

	List<Class<? extends BaseRule<AipValidationContext>>> getRuleClasses() {		
		return ruleClasses;
	}

}
