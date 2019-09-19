/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.analysis;

import java.util.ArrayList;
import static sipvalid.frames.panels.JFmain.seznamNahranychSouboru;

/**
 *
 * @author m000xz006159
 */
public final class Analys_main {
    Analys_main_control skodlivehokodu, datovestruktury, znakovesady, spravnostixml, jmennychprostoru, protischematu, obsahu;
    ArrayList<Integer> list_validnich, list_nevalidnich;
    static ArrayList<Analys_rule> analys_obsahova;
    boolean pomocnybol = true;
    public Analys_main() {
        list_validnich = new ArrayList();
        list_nevalidnich = new ArrayList();
        analys_obsahova = new ArrayList();
        skodlivehokodu = new Analys_main_control("Kontrola škodlivého kódu", 0, seznamNahranychSouboru.size());
        datovestruktury = new Analys_main_control("Kontrola datové struktury", 1, seznamNahranychSouboru.size());
        znakovesady = new Analys_main_control("Kontrola znakové sady", 2, seznamNahranychSouboru.size());
        spravnostixml = new Analys_main_control("Kontrola správnosti xml", 3, seznamNahranychSouboru.size());
        jmennychprostoru = new Analys_main_control("Kontrola jmenných prostorů", 4, seznamNahranychSouboru.size());
        protischematu = new Analys_main_control("Kontrola proti schématu", 5, seznamNahranychSouboru.size());
        obsahu = new Analys_main_control("Kontrola obsahu", 6, seznamNahranychSouboru.size());

        for(int i = 0; i < seznamNahranychSouboru.size(); i++){
            zapis_kontrolu(i, seznamNahranychSouboru.get(i).getSeznamKontrol().get(0).getProvedena(), seznamNahranychSouboru.get(i).getSeznamKontrol().get(0).getStav(), skodlivehokodu);
            zapis_kontrolu(i, seznamNahranychSouboru.get(i).getSeznamKontrol().get(1).getProvedena(), seznamNahranychSouboru.get(i).getSeznamKontrol().get(1).getStav(), datovestruktury);
            zapis_kontrolu(i, seznamNahranychSouboru.get(i).getSeznamKontrol().get(2).getProvedena(), seznamNahranychSouboru.get(i).getSeznamKontrol().get(2).getStav(), znakovesady);
            zapis_kontrolu(i, seznamNahranychSouboru.get(i).getSeznamKontrol().get(3).getProvedena(), seznamNahranychSouboru.get(i).getSeznamKontrol().get(3).getStav(), spravnostixml);
            zapis_kontrolu(i, seznamNahranychSouboru.get(i).getSeznamKontrol().get(4).getProvedena(), seznamNahranychSouboru.get(i).getSeznamKontrol().get(4).getStav(), jmennychprostoru);
            zapis_kontrolu(i, seznamNahranychSouboru.get(i).getSeznamKontrol().get(5).getProvedena(), seznamNahranychSouboru.get(i).getSeznamKontrol().get(5).getStav(), protischematu);
            zapis_kontrolu(i, seznamNahranychSouboru.get(i).getSeznamKontrol().get(6).getProvedena(), seznamNahranychSouboru.get(i).getSeznamKontrol().get(6).getStav(), obsahu);
            analys_obsahova_set(i);
            
            if(pomocnybol) list_validnich.add(i);
            else list_nevalidnich.add(i);
            pomocnybol = true;
        }
        
        skodlivehokodu.vyhodnot_kontrolu();
        datovestruktury.vyhodnot_kontrolu();
        znakovesady.vyhodnot_kontrolu();
        spravnostixml.vyhodnot_kontrolu();
        jmennychprostoru.vyhodnot_kontrolu();
        protischematu.vyhodnot_kontrolu();
        obsahu.vyhodnot_kontrolu();
        analys_obsahova = Analys_helper.sort_reverse(analys_obsahova);
        
    }
    
    void zapis_kontrolu(int index_sip, boolean probehla, boolean stav, Analys_main_control kontrola){
        if(probehla){
                kontrola.list_probehlych.add(index_sip);
                if(stav) kontrola.list_bezchybnych.add(index_sip);
                else{
                    kontrola.list_chybnych.add(index_sip);
                    pomocnybol = false;
                }
            }
            else kontrola.list_neprobehlych.add(index_sip);
    }
    
    private void analys_obsahova_set(int index){
        for(int i = 0; i < seznamNahranychSouboru.get(index).getSeznamKontrol().get(6).size(); i++){
            int index_rule = seznamNahranychSouboru.get(index).getSeznamKontrol().get(6).get(i).getIndex();
            if(!Analys_helper.equals_rule_with_index(analys_obsahova, index_rule)){
                Analys_rule arule = new Analys_rule();
                arule.index = index_rule; 
                arule.id = seznamNahranychSouboru.get(index).getSeznamKontrol().get(6).get(i).getId();
                arule.pocet_chybnych = 1;
                arule.seznam_chybnych = new ArrayList<>();
                arule.seznam_chybnych.add(index);
                analys_obsahova.add(arule);
            }
            else{
                Analys_helper.get_rule_with_index(analys_obsahova, index_rule).pocet_chybnych++;
                Analys_helper.get_rule_with_index(analys_obsahova, index_rule).seznam_chybnych.add(index);
            }
        }
    }
    
    
    
}
