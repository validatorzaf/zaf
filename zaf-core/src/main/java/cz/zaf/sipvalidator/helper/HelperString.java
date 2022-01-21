/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.helper;

import java.io.File;

/**
 *
 */
public class HelperString {
    
    /**
     * Kontrola, zda retezec obsahuje realna data
     * 
     * Kontrola, zda retezec nejsou jen whitespace
     * 
     * @param str
     * @return
     */
    public static boolean hasContent(String str) {
        if (str == null) {
            return false;
        }
        for (int pos = 0; pos < str.length(); pos++) {
            char ch = str.charAt(pos);
            if (Character.isAlphabetic(ch)) {
                return true;
            }
            if (Character.isDigit(ch)) {
                return true;
            }
        }
        return false;
    }
    
    // upraví / a \ v cestě vždy na path separator.
    public static String replaceSeparators(String path){
        String s = path;
        if(path.contains("\\")){
            s = path.replace("\\", File.separator);
        }
        if(path.contains("/")){
            s = path.replace("/", File.separator);
        }
        
        return s;
    }
}
