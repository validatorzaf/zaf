package cz.zaf.sipvalidator.sip;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.sipvalidator.nsesss2017.K00_SkodlivehoKodu;
import cz.zaf.sipvalidator.nsesss2017.K01_DatoveStruktury;
import cz.zaf.sipvalidator.nsesss2017.K02_ZnakoveSady;
import cz.zaf.sipvalidator.nsesss2017.K03_Spravnosti;
import cz.zaf.sipvalidator.nsesss2017.K04_JmennychProstoruXML;
import cz.zaf.sipvalidator.nsesss2017.K05_ProtiSchematu;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;

public class SipValidator {
	
    public static int[] seznamStandaPrace = {1,2,3,4,9,10,11,12,13,14,15,16,17,18,19,20,22,23,24,25,26,27,28,29,30,31,33,34,35,36,37,38,39,40,44,45,46,47,48,49,50,51,52,53,54,41,55,56,57,58,59,60,61,42,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,32,94,95,96,97,98};
    // 1
    public static int[] seznam_Prazdny = {1,2,4,9,10,11,12,13,14,15,16,17,18,19,20,22,23,24,25,26,27,28,29,30,31,33,34,35,36,37,38,39,54,41,57,58,59,61,42,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,81,82,83,84,85,86,87,88,89,90,91,92,93,32,94,95,96,97,98};
    // 2
    public static int[] seznam_Plny = {1,2,4,9,10,11,12,13,14,15,16,17,18,19,20,22,23,24,25,26,27,28,29,30,31,33,34,35,36,37,38,39,40,44,45,46,47,48,49,50,51,52,53,54,41,55,56,57,58,59,60,61,42,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,81,82,83,84,85,86,87,88,89,90,91,92,93,32,94,95,96,97,98};
    // 3
    public static int[] seznam_Prejimka = {1,3,4,9,10,11,12,13,14,15,16,17,18,19,20,22,23,24,25,26,27,28,29,30,31,33,34,35,36,37,38,39,40,44,45,46,47,48,49,50,51,52,53,54,41,55,56,57,58,59,60,61,42,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,32,94,95,96,97,98};
	
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
