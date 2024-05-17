/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.sip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.result.ValidationStatus;
import cz.zaf.common.validation.ValidationType;

/**
 * Výsledek kontroly Sipu
 * 
 * SIP prochazi sadou kontrol. Kazda kontrola
 * je tvorena sadou kontrolnich pravidel.
 * 
 * Trida umoznuje ulozit vysledek jedne kontroly.
 * 
 */
public class VysledekKontroly {
	
	final TypUrovenKontroly urovenKontroly;
	
    List<RuleValidationError> chyby = new ArrayList<>();
	
    String kontrolaNazev;
    
    /**
     * Stav kontroly
     */
    ValidationStatus stavKontroly = ValidationStatus.NOT_EXCECUTED;

    public VysledekKontroly(final TypUrovenKontroly urovenKontroly, 
    		final String kontrolaNazev){
        Objects.requireNonNull(urovenKontroly);
    	Validate.notEmpty(kontrolaNazev);
    	
    	this.urovenKontroly = urovenKontroly;
        this.kontrolaNazev = kontrolaNazev;
    }

    public String getKontrola_nazev(){
        return kontrolaNazev;        
    }
    
    public ValidationStatus getStavKontroly(){
        return stavKontroly;
    }
    
    public void setStav(final ValidationStatus stavKontroly){
    	// Lze nastavovat jen koncove (vysledek) a nikoliv pocatecni stavy
    	Validate.isTrue(stavKontroly!=ValidationStatus.NOT_EXCECUTED);
    	
        this.stavKontroly = stavKontroly;
    }

	public void add(RuleValidationError p) {
        chyby.add(p);
        // nastaveni stavu
        if (stavKontroly != ValidationStatus.ERROR) {
			// doslo k selhani -> error
			stavKontroly = ValidationStatus.ERROR;
		}
	}

	public int size() {
        return chyby.size();
	}

	public RuleValidationError get(int i) {
        return chyby.get(i);
	}

	public boolean isEmpty() {
        return chyby.isEmpty();
	}

    public ValidationType getTypUrovneKontroly() {
		return urovenKontroly;
	}

	public boolean isFailed() {		
		// pokud je stav false -> je selhana
        return CollectionUtils.isNotEmpty(chyby);
	}

	public boolean isProvedena() {
		return stavKontroly!=ValidationStatus.NOT_EXCECUTED;
	}

	public boolean isOk() {
		return stavKontroly==ValidationStatus.OK;
	}

    public List<RuleValidationError> getPravidla() {
        return chyby;
    }

    public RuleValidationError getPravidlo(String kodPravidla) {
        for (RuleValidationError pravidlo : chyby) {
            if (pravidlo.getId().equals(kodPravidla)) {
                return pravidlo;
            }
        }
        return null;
    }
    
}
