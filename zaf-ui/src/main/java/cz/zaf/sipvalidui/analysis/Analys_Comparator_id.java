/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.analysis;

import java.util.Comparator;

/**
 *
 * @author m000xz006159
 */
public class Analys_Comparator_id implements Comparator<Analys_rule>{

    @Override
    public int compare(Analys_rule t, Analys_rule t1) {
        if(get_cislo(t.id) < get_cislo(t1.id)){
            return -1;
        }
        if(get_cislo(t.id) >= get_cislo(t1.id)){
            return 1;
        }
        
        return 0;
    }
    
    
    private int get_cislo(String ida){
        int i;
        try{
           i = Integer.parseInt(ida);
        }
        catch(NumberFormatException ex){
            String cislo = "";
            for(int j = 0; j < ida.length(); j++){
                if(Character.isDigit(ida.charAt(j))){
                    cislo += ida.charAt(j);
                }
            } 
            try{
                i = Integer.parseInt(cislo);
            }
            catch(NumberFormatException e){
                return -1;
            }
        }
        return i;
    }
    
}
