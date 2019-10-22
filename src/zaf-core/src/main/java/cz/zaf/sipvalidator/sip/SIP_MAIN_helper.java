/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.sip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN_helper {
    
	// TODO: Nutno prepracovat
    public static long get_sip_lenght(Path sipPath){
        long g = 0;
        try{
            if(Files.exists(sipPath)){
                if(Files.isDirectory(sipPath)){
                    g = FileUtils.sizeOfDirectory(sipPath.toFile());
                }
                else{
                    g = Files.size(sipPath);
                }
            }
            else{
                g = -20;
            }
            return g;                   
        }
        catch(IllegalArgumentException e){
            return -1;
        } catch (IOException e) {
        	return -1;
		}        
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
    
    public static void set_SIP_type(String nodename, SipInfo sip){
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
    
        
    public static String getCesta_komponenty(SipInfo sip){
        return sip.getCesta() + File.separator + "komponenty";  
    }
        
    public static boolean maSlozku_komponenty(SipInfo sip){
        File k = new File(getCesta_komponenty(sip));
        return k.exists();
    }
    
    public static boolean ma_metsxml(SipInfo sip){
        File m = sip.getCesta_mets().toFile();
        return m.exists();
    }
    
    public static boolean ma_pouze_povolene_soubory(SipInfo sip){
        
        File[] f = sip.getCesta().toFile().listFiles();
        if(f != null){
            for(File s : f){
                if(!(s.getName().toLowerCase().equals("komponenty") || s.getName().toLowerCase().equals(SipInfo.METS_XML))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public static String get_vypisChyby_obsahova(SipInfo sip, int indexPravidla){
    	PravidloKontroly pravidlo = sip.getSeznamKontrol().get(6).getPravidlo(indexPravidla);
    	if(pravidlo!=null) {
    		return pravidlo.getVypis_chyby();
    	}
        return "";
    }
    
    public static String get_mistoChyby_obsahova(SipInfo sip, int indexPravidla){
    	PravidloKontroly pravidlo = sip.getSeznamKontrol().get(6).getPravidlo(indexPravidla);
    	if(pravidlo!=null) {
    		return pravidlo.getMisto_chyby();
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
}
