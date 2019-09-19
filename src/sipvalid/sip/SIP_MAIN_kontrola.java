/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.sip;

import java.util.ArrayList;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN_kontrola extends ArrayList<SIP_MAIN_kontrola_pravidlo>{
    String kontrola_nazev;
    boolean kontrola_provedena = false, kontrola_stav;

    public SIP_MAIN_kontrola(String kontrola_nazev, boolean provedena){
        kontrola_provedena = provedena;
        kontrola_stav = provedena;
        this.kontrola_nazev = kontrola_nazev;
    }

    public String getKontrola_nazev(){
        if(kontrola_nazev == null){
            return "Nezadán název kontroly";
        }
        return kontrola_nazev;
        
    }

    public boolean getProvedena(){
        return kontrola_provedena;
    }
    
    public boolean getStav(){
        return kontrola_stav;
    }
    
    public void setProvedena(boolean bol){
        kontrola_provedena = bol;
    }
    
    public void setStav(boolean bol){
        kontrola_stav = bol;
    }
    
}
