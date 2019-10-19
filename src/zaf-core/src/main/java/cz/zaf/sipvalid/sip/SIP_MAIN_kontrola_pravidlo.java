/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.sip;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN_kontrola_pravidlo {
    boolean stav;
    String id;
    int index;
    int popis_chyby_index;
    String vypis_chyby, misto_chyby;

    public SIP_MAIN_kontrola_pravidlo(int index, String id, boolean stav, String vypis_chyby, int popis_chyby_index, String misto_chyby) {
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
