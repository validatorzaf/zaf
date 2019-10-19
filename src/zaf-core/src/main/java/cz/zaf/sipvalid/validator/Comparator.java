/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;

import java.util.ArrayList;

/**
 *
 * @author m000xz006159
 */
public class Comparator {
   
    
    public static ArrayList<Obj_Node_String> obj_Node_String_howmany_in_list(Obj_Node_String obj_node, ArrayList<Obj_Node_String> list){
        ArrayList<Obj_Node_String> seznam = new ArrayList<>();
        String node_string = obj_node.get_string();
        for(int i = 0; i < list.size(); i++){
            if(node_string.equals(list.get(i).get_string())){
                seznam.add(list.get(i));
            }
        }
        return seznam;
    }
}
