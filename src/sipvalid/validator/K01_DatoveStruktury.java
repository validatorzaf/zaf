/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;


import sipvalid.sip.SIP_MAIN;
import sipvalid.sip.SIP_MAIN_helper;
import sipvalid.sip.SIP_MAIN_kontrola;
import sipvalid.sip.SIP_MAIN_kontrola_pravidlo;


/**
 *
 * @author standa
 */
public class K01_DatoveStruktury {
    
    public K01_DatoveStruktury(SIP_MAIN file, boolean proved) {
        SIP_MAIN_kontrola k = new SIP_MAIN_kontrola("kontrola datové struktury", proved);
        if(proved){
            pravidlo1(k, file);
            pravidlo2(k, file);
            pravidlo3(k, file);
        }
        file.getSeznamKontrol().add(k);
    }
    
    // Datový balíček SIP je soubor v datovém formátu ZIP (jeho MIME Content-type je detekován jako application/zip) nebo složka.
    private void pravidlo1(SIP_MAIN_kontrola k, SIP_MAIN file){
        int loadtype = file.getLoadType(); // load type: 0 dir, 1 xml, 2 zip, -1 nepovolený formát
        switch(loadtype){
            case -1:
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "data1", false, "Datový balíček SIP není soubor v povoleném datovém formátu.", 4, "");
                k.add(p);
                k.setStav(false);
            break;    
            case 1:
                SIP_MAIN_kontrola_pravidlo p1 = new SIP_MAIN_kontrola_pravidlo(0, "data1", false, "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu xml.", 4, "");
                k.add(p1);
                k.setStav(false);
            break;
            case 0:
                SIP_MAIN_kontrola_pravidlo p2 = new SIP_MAIN_kontrola_pravidlo(0, "data1", true, "SIP balíček byl nahrán jako složka.", 0, "");
                k.add(p2);
            break;
            case 2:
                if(!file.getLoadGood()){
                    SIP_MAIN_kontrola_pravidlo p3 = new SIP_MAIN_kontrola_pravidlo(0, "data1", false, "Datový balíček SIP není soubor v datovém formátu ZIP (jeho MIME Content-type není application/zip).", 4, "");
                    k.add(p3);
                    k.setStav(false);
                }
                else{
                    SIP_MAIN_kontrola_pravidlo p4 = new SIP_MAIN_kontrola_pravidlo(0, "data1", true, "SIP balíček nahrán ze souboru typu zip.", 0, "");
                    k.add(p4);
                }
            break;    
        }
        
    }
    
    // Pokud je datový balíček SIP komprimován do souboru v datovém formátu ZIP, 
    // po rozbalení obsahuje právě jednu složku. Ta má stejný název jako je název souboru v datovém formátu ZIP.
    private void pravidlo2(SIP_MAIN_kontrola k, SIP_MAIN file){
        int t = file.getLoadType();
        String typ = SIP_MAIN_helper.get_SIP_file_type(t);
        if(t == 2){ //byl to zip
            if(file.getName().equals(file.getNameZip().replaceAll(".zip", ""))){
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(1, "data2", true, "Nahraný soubor typu zip obsahoval očekávaný SIP balíček.", 0, "");
                k.add(p);
            }
            else{
                SIP_MAIN_kontrola_pravidlo p1 = new SIP_MAIN_kontrola_pravidlo(1, "data2", false, "Nahraný soubor typu zip neobsahoval očekávaný SIP balíček.", 5, "");
                k.setStav(false);
                k.add(p1);
            }
        }
        else{
            SIP_MAIN_kontrola_pravidlo p2 = new SIP_MAIN_kontrola_pravidlo(1, "data2", true, "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu " + typ + ".", 0, "");
            k.add(p2); 
        }
    }
    
    // Složka obsahuje právě jeden soubor pojmenovaný mets.xml nebo právě jeden soubor pojmenovaný mets.xml a složku pojmenovanou komponenty.
    private void pravidlo3(SIP_MAIN_kontrola k, SIP_MAIN file){
        if(SIP_MAIN_helper.ma_pouze_povolene_soubory(file)){
            if(SIP_MAIN_helper.ma_metsxml(file)){
                String s = "SIP balíček obsahuje právě jeden soubor \"mets.xml\"";
                if(SIP_MAIN_helper.maSlozku_komponenty(file)) s += " a složku komponenty."; else s += ".";
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(2, "data3", true, s, 0, "");
                k.add(p);
            }
            else{
                SIP_MAIN_kontrola_pravidlo p1 = new SIP_MAIN_kontrola_pravidlo(2, "data3", false, "SIP balíček neobsahuje právě jeden soubor \"mets.xml\".", 9, "");
                k.setStav(false);
                k.add(p1);
            }
        }
        else{
            SIP_MAIN_kontrola_pravidlo p2 = new SIP_MAIN_kontrola_pravidlo(2, "data3", false, "SIP balíček obsahuje nepovolené soubory.", 9, "");
            k.setStav(false);
            k.add(p2);
        }
    }
    
}
