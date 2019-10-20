/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.sip;

import java.util.ArrayList;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN_kontrola {
	
	final TypUrovenKontroly urovenKontroly;
	
	ArrayList<SIP_MAIN_kontrola_pravidlo> pravidla = new ArrayList<>(); 
	
    String kontrolaNazev;
    
    /**
     * Priznak, zda kontrola byla dokoncena
     * true - dokoncena
     * false - nedokoncena
     */
    boolean kontrolaProvedena = false;
    
    /**
     * Stav provedeni kontroly
     * 
     * Standardne je kontrola oznacena jako ok
     */
    boolean kontrolaStav = true;

    public SIP_MAIN_kontrola(final TypUrovenKontroly urovenKontroly, 
    		final String kontrolaNazev){
    	this.urovenKontroly = urovenKontroly;
        this.kontrolaNazev = kontrolaNazev;
    }

    public String getKontrola_nazev(){
        if(kontrolaNazev == null){
            return "Nezadán název kontroly";
        }
        return kontrolaNazev;
        
    }

    public boolean isProvedena(){
        return kontrolaProvedena;
    }
    
    public void setProvedena(boolean provedena) {
    	this.kontrolaProvedena = provedena;
    }
    
    public boolean getStav(){
        return kontrolaStav;
    }
    
    public void setStav(boolean kontrolaOk){
        kontrolaStav = kontrolaOk;
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
		return kontrolaStav==false;
	}
    
}
