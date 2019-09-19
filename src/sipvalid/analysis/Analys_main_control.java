/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.analysis;

import java.util.ArrayList;

/**
 *
 * @author m000xz006159
 */
public class Analys_main_control {
    String nazev_kontroly;
    int id_kontroly;
    ArrayList<Integer> list_chybnych, list_neprobehlych, list_probehlych, list_bezchybnych;
    int pocet_celkem, pocet_chybnych, pocet_probehlych, pocet_neprobehlych, pocet_bezchybnych;

    public Analys_main_control(String nazev_kontroly, int id_kontroly, int pocet_celkem) {
        this.nazev_kontroly = nazev_kontroly;
        this.id_kontroly = id_kontroly;
        this.pocet_celkem = pocet_celkem;
        list_chybnych = new ArrayList<>();
        list_bezchybnych = new ArrayList<>();
        list_probehlych = new ArrayList<>();
        list_neprobehlych = new ArrayList<>();
    }

    public ArrayList<Integer> get_sip_chybny(int index){
        return list_chybnych;
    }
    
    public ArrayList<Integer> get_sip_bezchybny(int index){
        return list_bezchybnych;
    }
    
    public ArrayList<Integer> get_sip_probehly(int index){
        return list_probehlych;
    }
    
    public ArrayList<Integer> get_sip_neprobehly(int index){
        return list_neprobehlych;
    }
    
    public void vyhodnot_kontrolu(){
        pocet_chybnych = list_chybnych.size();
        pocet_probehlych = list_probehlych.size();
        pocet_neprobehlych = list_neprobehlych.size();
        pocet_bezchybnych = list_bezchybnych.size();
        
    }
    
    
}
