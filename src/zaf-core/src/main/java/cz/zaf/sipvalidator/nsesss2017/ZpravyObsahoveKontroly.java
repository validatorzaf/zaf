/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

/**
 *
 * @author m000xz006159
 */
public class ZpravyObsahoveKontroly {
    static String[] list_text_obsahova = {
    "Pravidlo neexistuje.",
    //1. - 4.
            "",
            "",
            "",
            "",
    };
    
    public static String get_text_Obsahova(int index){
        if(index >= list_text_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_text_obsahova[index];
    }
    
    static String[] list_zdroje_obsahova = {
    "0 volný index",
    //1. - 4.
            "",
            "",
            "",
            "",
    };
    
    public static String get_zdroje_Obsahova(int index){
        if(index >= list_zdroje_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_zdroje_obsahova[index];
    }
    
    static String[] list_popis_obsahova = {
    "0 volný index",
    //1. - 4.
            "",
            "",
            "",
            "",
    };
    
    public static String get_popis_Obsahova(int index){
        if(index >= list_popis_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_popis_obsahova[index];
    }
    
}
