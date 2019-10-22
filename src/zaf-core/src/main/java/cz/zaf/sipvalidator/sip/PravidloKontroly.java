package cz.zaf.sipvalidator.sip;

/**
 * Vysledek kontroly jednoho pravidla
 * 
 */
public class PravidloKontroly {
	/**
	 * Stav kontroly
	 * true - probehla ok
	 * false - doslo k chybe
	 */
    final boolean stav;
    final String id;    
    final int index;
    final int popis_chyby_index;
    final String vypis_chyby, misto_chyby;

    public PravidloKontroly(int index, String id, boolean stav, String vypis_chyby, int popis_chyby_index, String misto_chyby) {
        this.index = index;
        this.id = id;
        this.stav = stav;
        this.vypis_chyby = vypis_chyby;
        this.popis_chyby_index = popis_chyby_index;
        this.misto_chyby = misto_chyby;
    }
    
    public boolean getStav(){
        return stav;
    }
    
    public String getId(){
        return id;
    }
    
    public int getIndex(){
        return index;
    }
    
    public int getPopisChybyIndex(){
        return popis_chyby_index;
    }
    
    public String getVypis_chyby(){
        return vypis_chyby;
    }
    
    public String getMisto_chyby(){
        return misto_chyby;
    }
    
    
}
