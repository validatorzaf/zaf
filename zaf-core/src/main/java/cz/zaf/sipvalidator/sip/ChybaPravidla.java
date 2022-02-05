package cz.zaf.sipvalidator.sip;

import org.apache.commons.lang3.Validate;

import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;

/**
 * Vysledek kontroly jednoho pravidla
 * 
 */
public class ChybaPravidla {
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
    final String vypisChyby;

    /**
     * Obecný popis chyby
     * 
     * Obvykle se jedná o připravený popis typu chyby
     */
    final String popisChybyObecny;

    /**
     * Identifikace místa vzniku chyby
     */
    final String mistoChyby;

    /**
     * Legislativni zdroj pro pravidlo
     */
    final String zdroj;

    /**
     * Kod chyby
     */
    final private ErrorCode errorCode;

    public ChybaPravidla(
                            final String id,
                            final String textPravidla,
                            final String vypisChyby,
                            final String popisChybyObecny,
                            final String mistoChyby,
                            final String zdroj,
                            final ErrorCode errorCode) {
        Validate.notEmpty(id);
        Validate.notEmpty(textPravidla);

        this.id = id;
        this.textPravidla = textPravidla;
        this.vypisChyby = vypisChyby;
        this.popisChybyObecny = popisChybyObecny;
        this.mistoChyby = mistoChyby;
        this.zdroj = zdroj;
        this.errorCode = errorCode;
    }

    public String getId(){
        return id;
    }

    public String getVypisChyby(){
        return vypisChyby;
    }
    
    public String getMistoChyby(){
        return mistoChyby;
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

    public ErrorCode getKodChyby() {
        return errorCode;
    }
}
