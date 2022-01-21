/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.analysis;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author m000xz006159
 */
public class Analys_helper {
    
    // musí to výt od největšího po nejměnšího
    public static ArrayList<Analys_rule> sort_reverse(ArrayList<Analys_rule> analys_obsahova){
        ArrayList<Analys_rule> newlist = new ArrayList<>();
        // srovná podle množství - ale čísla pravidel jsou přeházená
        try{
            Collections.sort(analys_obsahova, new Analys_Comparator_pocet());
        }
        catch(IllegalArgumentException es){
            System.out.println("problém u analýzy");
        }
        ArrayList<Integer> pocty = new ArrayList<>();
        for(int i = 0; i < analys_obsahova.size(); i++){
            if(!pocty.contains(analys_obsahova.get(i).pocet_chybnych)){
                ArrayList<Analys_rule> helplist = new ArrayList<>();
                helplist.add(analys_obsahova.get(i));
                pocty.add(analys_obsahova.get(i).pocet_chybnych);
                for(int j = 0; j < analys_obsahova.size(); j++){
                    
                    if(i != j && analys_obsahova.get(j).pocet_chybnych == analys_obsahova.get(i).pocet_chybnych){
                        helplist.add(analys_obsahova.get(j));

                    }
                }
                Collections.sort(helplist, new Analys_Comparator_id());
                newlist.addAll(helplist);
            }
        }

        return newlist;
    }

}
