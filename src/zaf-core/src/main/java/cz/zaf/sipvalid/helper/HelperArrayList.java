/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.helper;

import java.util.ArrayList;

/**
 *
 * @author m000xz006159
 */
public class HelperArrayList {
    public static boolean equalsString(ArrayList<String> seznam, String string){
        if(!seznam.isEmpty()){
            for(int i = 0; i < seznam.size(); i++){
            if(seznam.get(i).equals(string)) return true;
            }
        }
        return false;
    }
}
