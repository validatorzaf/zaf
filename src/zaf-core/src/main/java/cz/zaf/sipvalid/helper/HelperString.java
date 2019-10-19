/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.helper;

import java.io.File;

/**
 *
 * @author m000xz006159
 */
public class HelperString {
    
    public static int kolikObsahujeRetezecZnaku(String retezec, String znaky){
        int pocitadlo = 0;
        for(int i = 0; i < znaky.length(); i++){
            char znak = znaky.charAt(i);
            for(int j = 0; j < retezec.length(); j++){
                if(retezec.charAt(j) == znak) pocitadlo++;
            }
            
        }
        return pocitadlo;
    }
    
    public static String replace_format_tags(String string){
        string = string.replaceAll("\n", "");
        string = string.replaceAll("\t", ""); 
        return string;
    }
    
    // upraví / a \ v cestě vždy na path separator.
    public static String edit_path(String path){
        String s = path;
        if(path.contains("\\")){
            s = path.replace("\\", File.separator);
        }
        if(path.contains("/")){
            s = path.replace("/", File.separator);
        }
        
        return s;
    }
    
        // na konci oddělovač nehlídá
    public static boolean has_string_separator(String string){
        for(int i = 0; i < string.length()-1; i++){
            char c = string.charAt(i);
            if(c == ' ' || c == '-' || c == '_' || c == '\\' || c == '/' || c == '.'){
                return true;
            }
        }
        return false;
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
