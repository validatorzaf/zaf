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

    public static String replace_format_tags(String string){
        string = string.replaceAll("\n", "");
        string = string.replaceAll("\t", ""); 
        return string;
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

    public static boolean has_string_more_separators(String string){
        int count = 0;
        for(int i = 0; i < string.length()-1; i++){
            char c = string.charAt(i);
            if(c == ' ' || c == '-' || c == '_' || c == '\\' || c == '/' || c == '.'){
                count++;
                if(count < 1) return true;
            }
        }
        return false;
    }
    
    public static boolean has_string_two_separators_abreast(String string){
        for(int i = 1; i < string.length(); i++){
            char c = string.charAt(i);
            char before_c = string.charAt(i-1);
            if(c == ' ' || c == '-' || c == '_' || c == '\\' || c == '/' || c == '.'){
                if(before_c == ' ' || before_c == '-' || before_c == '_' || before_c == '\\' || before_c == '/' || before_c == '.'){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean is_char_separator(char c){
        boolean b = (c == ' ' || c == '-' || c == '_' || c == '\\' || c == '/' || c == '.');
        return b;
    }
    
}
