/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.analysis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextArea;
import static cz.zaf.sipvalidui.panels.JFmain.seznamNahranychSouboru;

/**
 *
 * @author m000xz006159
 */
public class Analys {
    String k_id, k_typ, k_doba = "00:00:00";
    long k_start, k_end;
    Analys_main analysa;
    
    
    public Analys(JTextArea jtextarea, String k_id, String k_typ, long k_start, long k_end) {
        this.k_id = k_id;
        this.k_typ = k_typ;
        this.k_start = k_start;
        this.k_end = k_end;
        k_doba = get_doba();
        
        analysa = new Analys_main();
        write(jtextarea, seznamNahranychSouboru.size());
        
    }
    
    private String get_doba(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date((0 - (60*60*1000)));
        date.setTime(date.getTime() + (k_end - k_start));
        return df.format(date);
    }
    
    private int get_percentage(int suma, int part){
        if(suma == 0) return -1;
        return 100 * part / suma;
    }
    
    private String get_seznam(ArrayList<Integer> list, int pocet, String veta){
        String seznam = "P.č.   ";
        if(list.isEmpty()){
            if(!veta.equals("")) return veta + ".";
            else return "Žádný balíček sip není validní.";
        }
        if(list.size() == pocet){
            if(!veta.equals("")) return veta + ".";
            else return "Všechny balíčky sip jsou nevalidní.";
        }
        for(int i = 0; i < list.size(); i++){
            if(i != list.size()-1){
                seznam += list.get(i)+1 + ", ";
            }
            else{
               seznam += list.get(i)+1 + "."; 
            }
        }
        return seznam;
    }
    
    private String vypis_kontrolu(Analys_main_control kontrol){
        String vypis = kontrol.nazev_kontroly.toUpperCase() + "\n";
        vypis += " provedena u " + kontrol.pocet_probehlych + " balíčků sip (" + get_percentage(kontrol.pocet_celkem, kontrol.pocet_probehlych) + "%)." + "\n";
        if(kontrol.pocet_neprobehlych != 0){
            vypis += "  neprovedena u " + kontrol.pocet_neprobehlych + " balíčků sip (" + get_percentage(kontrol.pocet_celkem, kontrol.pocet_neprobehlych) + "%). " + get_seznam(kontrol.list_neprobehlych, kontrol.pocet_celkem, "Kontrola neproběhla u všech balíčků sip") + "\n";
        }
        
        if(kontrol.pocet_probehlych != 0){
            if(kontrol.pocet_chybnych == 0){
                vypis +=  "   všechny kontrolované balíčky sip jsou v pořádku.";
            }
            else{
                vypis += "   chybné balíčky sip: " + kontrol.pocet_chybnych + " (" + get_percentage(kontrol.pocet_probehlych, kontrol.pocet_chybnych) + "%). " + get_seznam(kontrol.list_chybnych, kontrol.pocet_probehlych, "Všechny balíčky sip jsou chybné");
            }
        }
        
        
        return vypis + "\n";
    }
    
    private String vypis_pravidlaObsahove(Analys_main_control kontrol){
        String vypis = "  " + "ČETNOST CHYB V PRAVIDLECH" + "\n";
        for(Analys_rule anrule : Analys_main.analys_obsahova){
            String ids = "  ";
            if(anrule.id.length() == 1) ids += "  ";if(anrule.id.length() == 2) ids += " ";
            vypis += ("  " + "pr." + anrule.id + "." + ids + anrule.pocet_chybnych + "\t" + "(" + get_percentage(kontrol.pocet_probehlych, anrule.pocet_chybnych) + "%).    " + get_seznam(anrule.seznam_chybnych, 0, "") + "\n");
        }
        return vypis;
    }
    
    
    private void write(JTextArea area, int pocet){
        area.setText(
        "KONTROLA ID: " + k_id + " * TYP: " + k_typ + " * DOBA: " + k_doba + "\n" + "\n" +
        
        "BALÍČKŮ SIP: " + pocet + " * VALIDNÍCH: " + analysa.list_validnich.size() + " (" + get_percentage(pocet, analysa.list_validnich.size()) + "%)" + ". CHYBNÝCH: " + analysa.list_nevalidnich.size() + " (" + get_percentage(pocet, analysa.list_nevalidnich.size()) + "%)." + "\n" +
        "   validní: " + get_seznam(analysa.list_validnich, pocet, "") + "\n" +
        "    chybné: " + get_seznam(analysa.list_nevalidnich, pocet, "") + "\n" + "\n" +
                
        vypis_kontrolu(analysa.skodlivehokodu) + "\n" + 
        vypis_kontrolu(analysa.datovestruktury) + "\n" + 
        vypis_kontrolu(analysa.znakovesady) + "\n" + 
        vypis_kontrolu(analysa.spravnostixml) + "\n" +
        vypis_kontrolu(analysa.jmennychprostoru) + "\n" + 
        vypis_kontrolu(analysa.protischematu) + "\n" + 
        vypis_kontrolu(analysa.obsahu) + "\n" +
        vypis_pravidlaObsahove(analysa.obsahu)

        );
    }
    
}
