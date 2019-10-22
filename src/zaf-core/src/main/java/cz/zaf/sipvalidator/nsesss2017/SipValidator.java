package cz.zaf.sipvalidator.nsesss2017;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.sipvalidator.sip.UrovenKontroly;

public class SipValidator {

    /**
     * Pripravi seznam kontrol
     * @param seznamObsKontrol 
     * @param skodlivyKodError 
     * @param skodlivyKodOk 
     * @return
     */
    public static List<UrovenKontroly> pripravKontroly(boolean skodlivyKodOk, String skodlivyKodError, int[] seznamObsKontrol) {
    	ArrayList<UrovenKontroly> kontroly = new ArrayList<>(6);
    	
        K00_SkodlivehoKodu ksk = new K00_SkodlivehoKodu(skodlivyKodOk, skodlivyKodError);
        kontroly.add(ksk);
        
        K01_DatoveStruktury kds = new K01_DatoveStruktury();
        kontroly.add(kds);
        
        K02_ZnakoveSady kko = new K02_ZnakoveSady();
        kontroly.add(kko);        

        K03_Spravnosti kwf = new K03_Spravnosti();
        kontroly.add(kwf);

        K04_JmennychProstoruXML kjp = new K04_JmennychProstoruXML();
        kontroly.add(kjp);

        K05_ProtiSchematu vxml = new K05_ProtiSchematu();
        kontroly.add(vxml);

        K06_Obsahova oks = new K06_Obsahova(seznamObsKontrol);
        kontroly.add(oks);
        
    	return kontroly;
    }
    

}
