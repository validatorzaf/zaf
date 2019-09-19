/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.sip;

import java.io.File;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN_helper {
    
    public static long get_sip_lenght(File sip){
        long g = 0;
        try{
            if(sip.exists()){
                if(sip.isDirectory()){
                    g = FileUtils.sizeOfDirectory(sip);
                }
                else{
                    g = sip.length();
                }
            }
            else{
                g = -20;
            }

                   
        }
        catch(IllegalArgumentException e){
            g = -1;
        }
        return g;
    }
    
    public static SIP_MAIN_kontrola get_Kontrola(SIP_MAIN sip, String nazev, int ocekavany_index_kontroly){
        String s = sip.getSeznamKontrol().get(ocekavany_index_kontroly).getKontrola_nazev();
        if(s.toLowerCase().equals(nazev)){
            return sip.getSeznamKontrol().get(ocekavany_index_kontroly);
        }
        else{
            for(int i = 0; i < sip.getSeznamKontrol().size(); i++){
                if(i != ocekavany_index_kontroly){
                    if(sip.getSeznamKontrol().get(i).getKontrola_nazev().toLowerCase().equals(nazev)){
                        return sip.getSeznamKontrol().get(i);
                    }
                }
            } 
        }
        return null;
    }
    
    public static String get_SIP_type(int index){

        if(index == 0){
            return "Dokument";
        }
        if(index == 1){
            return "Spis";
        }
        if(index == 2){
            return "Díl";
        }
        
        return " - ";
    }

    public static String get_SIP_type(String nodename){

        if(nodename.equals("nsesss:Dokument")){
            return "Dokument";
        }
        if(nodename.equals("nsesss:Spis")){
            return "Spis";
        }
        if(nodename.equals("nsesss:Dil")){
            return "Díl";
        }
        
        return " - ";
    }
    
    public static String get_SIP_type_XXX(int index){

        if(index == 0){
            return "DOK";
        }
        if(index == 1){
            return "SPI";
        }
        if(index == 2){
            return "DÍL";
        }
        
        return " - ";
    }
    
    public static String get_SIP_type_XXX(String nodename){

        if(nodename.equals("nsesss:Dokument")){
            return "DOK";
        }
        if(nodename.equals("nsesss:Spis")){
            return "SPI";
        }
        if(nodename.equals("nsesss:Dil")){
            return "DÍL";
        }
        
        return " - ";
    }
    
    public static void set_SIP_type(String nodename, SIP_MAIN sip){
        switch(nodename){
            case "nsesss:Dokument": 
            sip.setType(0);
            break;
            case "nsesss:Spis": 
            sip.setType(1);
            break;
            case "nsesss:Dil": 
            sip.setType(2);
            break;
        }
    }
    
    
    // load type: 0 dir, 1 xml, 2 zip, -1 nepovolený formát
    public static String get_SIP_file_type(int index){

        if(index == -1){
            return "nepovolený formát";
        }
        
        if(index == 0){
            return "složka";
        }
        if(index == 1){
            return "xml";
        }
        if(index == 2){
            return "zip";
        }
        
        return " - ";
    }
    
    public static String getCesta_komponenty(SIP_MAIN sip){
        return sip.getCesta() + File.separator + "komponenty";  
    }
    
    public static String getCesta_mets(SIP_MAIN sip){
        return sip.getCesta() + File.separator + "mets.xml";
    }
    
    public static boolean maSlozku_komponenty(SIP_MAIN sip){
        File k = new File(getCesta_komponenty(sip));
        return k.exists();
    }
    
    public static boolean ma_metsxml(SIP_MAIN sip){
        File m = new File(getCesta_mets(sip));
        return m.exists();
    }
    
    public static boolean ma_pouze_povolene_soubory(SIP_MAIN sip){
        
        File[] f = new File(sip.getCesta()).listFiles();
        if(f != null){
            for(File s : f){
                if(!(s.getName().toLowerCase().equals("komponenty") || s.getName().toLowerCase().equals("mets.xml"))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public static String get_vypisChyby_obsahova(SIP_MAIN sip, int index_pravidla){
        for(int i = 0; i < sip.getSeznamKontrol().get(6).size(); i++){
            if(index_pravidla == sip.getSeznamKontrol().get(6).get(i).getIndex()){
                return sip.getSeznamKontrol().get(6).get(i).getVypis_chyby();
            }
        }
        return "";
    }
    
    public static String get_mistoChyby_obsahova(SIP_MAIN sip, int index_pravidla){
        for(int i = 0; i < sip.getSeznamKontrol().get(6).size(); i++){
            if(index_pravidla == sip.getSeznamKontrol().get(6).get(i).getIndex()){
                return sip.getSeznamKontrol().get(6).get(i).getMisto_chyby();
            }
        }
        return "";
    }
    
    public static String get_special_ida(int index_pravidla){
        String special_id = Integer.toString(index_pravidla);
//        if(index_pravidla == 21) special_id = "59b";
        if(index_pravidla == 32) special_id = "93a";
        if(index_pravidla == 41) special_id = "54a";
        if(index_pravidla == 42) special_id = "61a";
        if(index_pravidla == 43) special_id = "94a";
        return special_id;
    }
    
   
    public static boolean get_pokracuj_v_kontrole(SIP_MAIN sip, int indexKontroly){
        try{
            if(sip.getSeznamKontrol().isEmpty()) return false;
            boolean b = sip.getSeznamKontrol().get(indexKontroly).getStav();
            return b;
        }
        catch(NullPointerException e){
            return false;
        }
    }
}
