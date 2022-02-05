/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.sip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

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
	
    List<ChybaPravidla> chyby = new ArrayList<>();
	
    String kontrolaNazev;
    
    /**
     * Stav kontroly
     */
    StavKontroly stavKontroly = StavKontroly.NESPUSTENA;

    public VysledekKontroly(final TypUrovenKontroly urovenKontroly, 
    		final String kontrolaNazev){
    	Validate.notNull(urovenKontroly);
    	Validate.notEmpty(kontrolaNazev);
    	
    	this.urovenKontroly = urovenKontroly;
        this.kontrolaNazev = kontrolaNazev;
    }

    public String getKontrola_nazev(){
        return kontrolaNazev;        
    }
    
    public StavKontroly getStavKontroly(){
        return stavKontroly;
    }
    
    public void setStav(final StavKontroly stavKontroly){
    	// Lze nastavovat jen koncove (vysledek) a nikoliv pocatecni stavy
    	Validate.isTrue(stavKontroly!=StavKontroly.NESPUSTENA);
    	
        this.stavKontroly = stavKontroly;
    }

	public void add(ChybaPravidla p) {
        chyby.add(p);
        // nastaveni stavu
        if (stavKontroly != StavKontroly.CHYBA) {
			// doslo k selhani -> error
			stavKontroly = StavKontroly.CHYBA;
		}
	}

	public int size() {
        return chyby.size();
	}

	public ChybaPravidla get(int i) {
        return chyby.get(i);
	}

	public boolean isEmpty() {
        return chyby.isEmpty();
	}

	public TypUrovenKontroly getTypUrovneKontroly() {
		return urovenKontroly;
	}

	public boolean isFailed() {		
		// pokud je stav false -> je selhana
        return CollectionUtils.isNotEmpty(chyby);
	}

	public boolean isProvedena() {
		return stavKontroly!=StavKontroly.NESPUSTENA;
	}

	public boolean isOk() {
		return stavKontroly==StavKontroly.OK;
	}

    public List<ChybaPravidla> getPravidla() {
        return chyby;
    }

    public ChybaPravidla getPravidlo(String kodPravidla) {
        for (ChybaPravidla pravidlo : chyby) {
            if (pravidlo.getId().equals(kodPravidla)) {
                return pravidlo;
            }
        }
        return null;
    }
    
}
