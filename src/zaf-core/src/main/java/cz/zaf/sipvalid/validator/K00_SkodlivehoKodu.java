/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;
import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola_pravidlo;
/**
 *
 * @author standa
 */
public class K00_SkodlivehoKodu {
    
    public K00_SkodlivehoKodu(SIP_MAIN sip_xml, boolean stav, String error) {
        set_boolen(stav, error, sip_xml);
    }
    
    private void set_boolen(boolean stav, String error, SIP_MAIN sip_xml){
        SIP_MAIN_kontrola k = new SIP_MAIN_kontrola("kontrola škodlivého kódu", true);
            if(stav){
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "vir1", true, "Datový balíček SIP neobsahuje hrozbu.", 0, "");
                k.setStav(stav);
                k.add(p);
                sip_xml.getSeznamKontrol().add(k);
            }
            else{
                if(error == null) error = "Chybové hlášení nebylo předáno.";
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "vir1", false, error, 0, "");
                k.setStav(stav);
                k.add(p);
                sip_xml.getSeznamKontrol().add(k);
                k.setStav(false);
            }
    }
    
}
