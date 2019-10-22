/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.sip;

import java.util.ArrayList;

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
	
	ArrayList<SIP_MAIN_kontrola_pravidlo> pravidla = new ArrayList<>(); 
	
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

	public SIP_MAIN_kontrola_pravidlo getPravidlo(int indexPravidla) {
		for(SIP_MAIN_kontrola_pravidlo p: pravidla) {
			if(p.getIndex()==indexPravidla) {
				return p;
			}
		}
		return null;
	}

	public void add(SIP_MAIN_kontrola_pravidlo p) {
		pravidla.add(p);
		// nastaveni stavu dle vysledku pravidla
		if(!p.getStav()) {
			// doslo k selhani -> error
			stavKontroly = StavKontroly.CHYBA;
		} else {
			// overime predchozi stav
			if(stavKontroly==StavKontroly.NESPUSTENA) {
				// nebyla spustena, nastavime na ok
				stavKontroly = StavKontroly.OK;
			}
		}
	}

	public int size() {
		return pravidla.size();
	}

	public SIP_MAIN_kontrola_pravidlo get(int i) {
		return pravidla.get(i);
	}

	public boolean isEmpty() {
		return pravidla.isEmpty();
	}

	public TypUrovenKontroly getTypUrovneKontroly() {
		return urovenKontroly;
	}

	public boolean isFailed() {		
		// pokud je stav false -> je selhana
		return stavKontroly==StavKontroly.CHYBA;
	}

	public boolean isProvedena() {
		return stavKontroly!=StavKontroly.NESPUSTENA;
	}

	public boolean isOk() {
		return stavKontroly==StavKontroly.OK;
	}
    
}
