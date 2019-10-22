/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.sipvalidator.helper.HelperString;

/**
 *
 * @author m000xz006159
 */
public class SIP_Valid_Helepr {
    
    
    public static boolean over_plneUrcene_spisoveZnaky(String pus_znak, String nadrizeny, boolean can_be_same){
        if(HelperString.has_string_two_separators_abreast(nadrizeny)) return false;
        if(HelperString.has_string_two_separators_abreast(pus_znak)) return false;
        if(pus_znak.startsWith(nadrizeny)){
            if(can_be_same){
                if(nadrizeny.equals(pus_znak)){
                    return true;
                }
            }    
            else{
                if(nadrizeny.equals(pus_znak)){
                    return false;
                }
                //087.1. - 087. || 087.1 - 087 || 087.1 - 087. mám ignorovat oddělovač na posledním místě
                String rozsireni = pus_znak.replaceFirst(nadrizeny, "");
                // zbude 1 - .1 - .1. = v pořádku. Nesmí zbýt 1.1
                // když je separátor na 1.
                boolean vysledek = over_rozsireni_spisoveho_naku(rozsireni);
                return vysledek;
            }
        }
        return false;
    }
    
    public static boolean over_rozsireni_spisoveho_naku(String rozsireni){
        char s = rozsireni.charAt(0);
        boolean zacina_separatorem = HelperString.is_char_separator(s);
        if(zacina_separatorem) rozsireni = rozsireni.substring(1);
        for(int i = 0; i < rozsireni.length()-1; i++){
            char c = rozsireni.charAt(i);
            char after_c = rozsireni.charAt(i+1);
            if(HelperString.is_char_separator(c) && !HelperString.is_char_separator(after_c)){
                return false;
            }
        }
        return true;
    }
    
}
