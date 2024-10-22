/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.common.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import cz.zaf.common.validation.ValidationLayerType;

/**
 * Výsledek kontroly
 * 
 * SIP prochazi sadou kontrol. Kazda kontrola
 * je tvorena sadou kontrolnich pravidel.
 * 
 * Trida umoznuje ulozit vysledek jedne kontroly.
 * 
 */
public class ValidationLayerResult {

    /**
     * Typ validace / kontroly
     */
    final ValidationLayerType validationType;
	
    /**
     * Nazev kontroly
     */
    final String validationName;
    
    /**
     * Optional inner file name
     */
    final String innerFileName;

    List<RuleValidationError> ruleErrors = new ArrayList<>();
    
    /**
     * Stav kontroly
     */
    ValidationStatus validationStatus = ValidationStatus.NOT_EXCECUTED;

    public ValidationLayerResult(final ValidationLayerType validationType, 
								 final String innerFileNamefinal ) {
        Objects.requireNonNull(validationType);

        this.validationType = validationType;
        this.validationName = validationType.getDescription();
        this.innerFileName = innerFileNamefinal;
    }

    public String getValidationName(){
        return validationName;        
    }
    
    public ValidationStatus getValidationStatus(){
        return validationStatus;
    }
    
    public void setStav(final ValidationStatus stavKontroly){
    	// Lze nastavovat jen koncove (vysledek) a nikoliv pocatecni stavy
    	Validate.isTrue(stavKontroly!=ValidationStatus.NOT_EXCECUTED);
    	
        this.validationStatus = stavKontroly;
    }

	public void add(RuleValidationError p) {
        ruleErrors.add(p);
        // nastaveni stavu
        if (validationStatus != ValidationStatus.ERROR) {
			// doslo k selhani -> error
			validationStatus = ValidationStatus.ERROR;
		}
	}

	public int size() {
        return ruleErrors.size();
	}

	public boolean isEmpty() {
        return ruleErrors.isEmpty();
	}

    public ValidationLayerType getValidationType() {
		return validationType;
	}

	public boolean isFailed() {		
		// pokud je stav false -> je selhana
        return CollectionUtils.isNotEmpty(ruleErrors);
	}

	public boolean isProvedena() {
		return validationStatus!=ValidationStatus.NOT_EXCECUTED;
	}

	public boolean isOk() {
		return validationStatus==ValidationStatus.OK;
	}

    public List<RuleValidationError> getPravidla() {
        return ruleErrors;
    }

    public RuleValidationError getPravidlo(String kodPravidla) {
        for (RuleValidationError pravidlo : ruleErrors) {
            if (pravidlo.getId().equals(kodPravidla)) {
                return pravidlo;
            }
        }
        return null;
    }

	public String getInnerFileName() {
		return innerFileName;
	}
    
}
