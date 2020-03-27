package cz.zaf.sipvalidator.sip;

import org.apache.commons.lang3.Validate;

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

    /**
     * Identifikator kontroly
     * Pouziva se v report XML pro jeji oznaceni
     * 
     * Obvykly format Prefix<cislo>, napr. vir1
     */
    final String id;
    /**
     * Popisny text pravidla
     */
    final String textPravidla;

    /**
     * Technický popis chyby
     * 
     * Jedná se o popis konkrétní chyb,
     * může obsahovat kontextovou informaci apod.
     */
    final String vypis_chyby;

    /**
     * Obecný popis chyby
     * 
     * Obvykle se jedná o připravený popis typu chyby
     */
    final String popisChybyObecny;

    /**
     * Identifikace místa vzniku chyby
     */
    final String misto_chyby;

    /**
     * Legislativni zdroj pro pravidlo
     */
    final String zdroj;

    public PravidloKontroly(
                            final String id,
                            final boolean stav,
                            final String textPravidla,
                            final String vypis_chyby,
                            final String popisChybyObecny,
                            final String misto_chyby,
                            final String zdroj) {
        Validate.notEmpty(id);
        Validate.notEmpty(textPravidla);

        this.id = id;
        this.stav = stav;
        this.textPravidla = textPravidla;
        this.vypis_chyby = vypis_chyby;
        this.popisChybyObecny = popisChybyObecny;
        this.misto_chyby = misto_chyby;
        this.zdroj = zdroj;
    }
    
    public boolean getStav(){
        return stav;
    }
    
    public String getId(){
        return id;
    }

    public String getVypis_chyby(){
        return vypis_chyby;
    }
    
    public String getMisto_chyby(){
        return misto_chyby;
    }
    
    public String getTextPravidla() {
        return textPravidla;
    }

    public String getZdroj() {
        return zdroj;
    }

    public String getPopisChybyObecny() {
        return popisChybyObecny;
    }
}
