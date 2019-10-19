/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;

import java.util.ArrayList;

/**
 *
 * @author standa
 */
public class Helper_Obj_Node {
    
    
    public static Obj_Node_String has_any_skartacni_znak(ArrayList<Obj_Node_String> list, String znak){
        if(list == null) return null;
        for(int i = 0; i < list.size(); i++){
            String zn_dok = list.get(i).get_string();
            if(zn_dok.equals(znak)){
                return list.get(i);
            }
        }
        return null;
    }
    
    public static ArrayList<Obj_Node_String> all_with_skartacni_znak(ArrayList<Obj_Node_String> list, String znak1, String znak2){
        if(list == null) return null;
        ArrayList<Obj_Node_String> seznam = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            String zn_dok = list.get(i).get_string();
            if(zn_dok.equals(znak1) || zn_dok.equals(znak2)){
                seznam.add(list.get(i));
            }
        }
        return seznam;
    }
}
