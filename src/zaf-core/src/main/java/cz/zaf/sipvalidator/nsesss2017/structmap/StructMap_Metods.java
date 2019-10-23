/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017.structmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 *
 * @author m000xz006159
 */
public class StructMap_Metods {
    
    public static StructMap_Obj_return_bol_AL_Obj_amdSec get_amdSec_list(NodeList node_list){
        ArrayList<StructMap_Obj_amdSec> list = new ArrayList<>();
        ArrayList<StructMap_Obj_amdSec> error_list = new ArrayList<>();
        boolean bol = true;
        
        if(node_list == null) return null;
        
        for(int i = 0; i < node_list.getLength(); i++){
            Node node = node_list.item(i);
            boolean catcher = true;
            String id = "Nenalezeno";
            if(ValuesGetter.hasAttribut(node, "ID")){
                id = ValuesGetter.getValueOfAttribut(node, "ID");
            } else catcher = false;

            // <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID>
            Node node_identifikator = ValuesGetter.getXChild(node, "mets:digiprovMD", "mets:mdWrap", "mets:xmlData", "tp:TransakcniLogObjektu", "tp:TransLogInfo", "tp:Objekt", "tp:Identifikator", "tns:HodnotaID");
            String identifikator = "Nenalezeno";
            if(node_identifikator != null){
                identifikator = node_identifikator.getTextContent();
            } else catcher = false;
            
            Node node_zdroj = ValuesGetter.getXChild(node, "mets:digiprovMD", "mets:mdWrap", "mets:xmlData", "tp:TransakcniLogObjektu", "tp:TransLogInfo", "tp:Objekt", "tp:Identifikator", "tns:ZdrojID");
            String zdroj = "Nenalezeno";
            if(node_zdroj != null){
                zdroj = node_zdroj.getTextContent();
            } else catcher = false;
            
            // jestliže nastala nějaká chyba
            if(!catcher){
                bol = false;
                error_list.add(new StructMap_Obj_amdSec(id, identifikator, zdroj, node));
            }
            else{
                StructMap_Obj_amdSec amdSec = new StructMap_Obj_amdSec(id, identifikator, zdroj, node);
                list.add(amdSec);
            }
        }
        
        // nekončí u první chyby nahraje všechny chybné amdseC
        if(!bol){
            return new StructMap_Obj_return_bol_AL_Obj_amdSec(bol, error_list);
        }
        else{
            return new StructMap_Obj_return_bol_AL_Obj_amdSec(bol, list);
        }

    }
    
    public static StructMap_Obj_return_bol_AL_node test_amdSec_uniqueness(List<StructMap_Obj_amdSec> list) {
        for(int i = 0; i < list.size(); i++){
            
            StructMap_Obj_amdSec amd_main = list.get(i);
            
            for(int j = 0; j < list.size(); j++){
                
                if(j != i){
                   StructMap_Obj_amdSec amd_second = list.get(j);
                   if(amd_main.id.equals(amd_second.id) || amd_main.id.equals(amd_second.identifikator) || amd_main.id.equals(amd_second.zdroj)){
                       
                       ArrayList<Node> node_list = new ArrayList<>();
                       node_list.add(amd_main.node);
                       node_list.add(amd_second.node);
                       
                       return new StructMap_Obj_return_bol_AL_node(false, node_list);
                   }
                }
            }  
        }
        return new StructMap_Obj_return_bol_AL_node(true, null);
    }
    
    public static StructMap_Obj_return_bol_AL_Obj_metsdiv get_metsdiv_list(NodeList node_list){
        if(node_list == null) return null;
        ArrayList<StructMap_Obj_metsdiv> list = new ArrayList<>();
        ArrayList<StructMap_Obj_metsdiv> error_list = new ArrayList<>();
        for(int i = 0; i < node_list.getLength(); i++){
            String dmdid = "ERR", admid = "ERR", type = "ERR";
            if(ValuesGetter.hasAttribut(node_list.item(i), "ADMID")) dmdid = ValuesGetter.getValueOfAttribut(node_list.item(i), "DMDID");
            if(ValuesGetter.hasAttribut(node_list.item(i), "DMDID")) admid = ValuesGetter.getValueOfAttribut(node_list.item(i), "ADMID");
            if(ValuesGetter.hasAttribut(node_list.item(i), "TYPE")) type = ValuesGetter.getValueOfAttribut(node_list.item(i), "TYPE");
            StructMap_Obj_metsdiv metsdiv = new StructMap_Obj_metsdiv(node_list.item(i), dmdid, admid, type, true);
            if(dmdid.equals("ERR") || admid.equals("ERR") || type.equals("ERR")){
                error_list.add(metsdiv);
            }
            else{
                list.add(metsdiv);
            }
        }
        if(!error_list.isEmpty()){
            return new StructMap_Obj_return_bol_AL_Obj_metsdiv(false, error_list);
        }
        else{
            return new StructMap_Obj_return_bol_AL_Obj_metsdiv(true, list);
        }

    }
    
    public static StructMap_Obj_return_bol_AL_node test_metsdiv_uniqueness(List<StructMap_Obj_metsdiv> list) {
        for(int i = 0; i < list.size(); i++){
            
            StructMap_Obj_metsdiv div_main = list.get(i);
            for(int j = 0; j < list.size(); j++){
                if(j != i){
                   StructMap_Obj_metsdiv div_second = list.get(j);
                   if(div_main.admid.equals(div_second.admid) || div_main.dmdid.equals(div_second.dmdid)){
                       ArrayList<Node> node_list = new ArrayList<>();
                       node_list.add(div_main.metsdiv);
                       node_list.add(div_second.metsdiv);
                       return new StructMap_Obj_return_bol_AL_node(false, node_list);
                   }
                }
            }  
        }
        return new StructMap_Obj_return_bol_AL_node(true, null);
    }
    
    public static StructMap_Obj_return_bol_AL_Obj_dmdSec get_dmdsec_list(Document document) {
        ArrayList<StructMap_Obj_dmdSec> all_dmdSec = new ArrayList<>();
        ArrayList<StructMap_Obj_dmdSec> error_all_dmdSec = new ArrayList<>();
        NodeList plany_nodelist = ValuesGetter.getAllAnywhere("nsesss:SpisovyPlan", document);
        NodeList skupiny_nodelist = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", document);
        NodeList soucasti_nodelist = ValuesGetter.getAllAnywhere("nsesss:Soucast", document);
        NodeList typoveSpisy_nodelist = ValuesGetter.getAllAnywhere("nsesss:TypovySpis", document);
        NodeList spisy_nodelist = ValuesGetter.getAllAnywhere("nsesss:Spis", document);
        NodeList dily_nodelist = ValuesGetter.getAllAnywhere("nsesss:Dil", document);
        NodeList dokumenty_nodelist = ValuesGetter.getAllAnywhere("nsesss:Dokument", document);
        NodeList komponenty_nodelist = ValuesGetter.getAllAnywhere("nsesss:Komponenta", document);
        NodeList skartacnirezim_nodelist = ValuesGetter.getAllAnywhere("nsesss:SkartacniRezim", document);
        NodeList typdokumentu_nodelist = ValuesGetter.getAllAnywhere("nsesss:TypDokumentu", document);
        if(skartacnirezim_nodelist != null){
            work_values("skartační režim", plany_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(typdokumentu_nodelist != null){
            work_values("typ dokumentu", plany_nodelist, all_dmdSec, error_all_dmdSec);
        }
        
        if(plany_nodelist != null){
            work_values("spisový plán", plany_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(skupiny_nodelist != null){
            work_values("věcná skupina", skupiny_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(soucasti_nodelist != null){
            work_values("součást", soucasti_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(typoveSpisy_nodelist != null){
            work_values("typový spis", typoveSpisy_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(spisy_nodelist != null){
            work_values("spis", spisy_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(dily_nodelist != null){
            work_values("díl", dily_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(dokumenty_nodelist != null){
            work_values("dokument", dokumenty_nodelist, all_dmdSec, error_all_dmdSec);
        }
        if(komponenty_nodelist != null){
            work_values("komponenta", komponenty_nodelist, all_dmdSec, error_all_dmdSec);
        }
       
        if(!error_all_dmdSec.isEmpty()){
            return new StructMap_Obj_return_bol_AL_Obj_dmdSec(false, error_all_dmdSec);
        }
        else{
           return new StructMap_Obj_return_bol_AL_Obj_dmdSec(true, all_dmdSec); 
        }
    }
    
    public static StructMap_Obj_return_bol_AL_Obj_dmdSec get_dmdsec_specialObject_list(Document document) {
        ArrayList<StructMap_Obj_dmdSec> all_dmdSec = new ArrayList<>();
        ArrayList<StructMap_Obj_dmdSec> error_all_dmdSec = new ArrayList<>();
        NodeList bezpecnostni_kategorie_list = ValuesGetter.getAllAnywhere("nsesss:BezpecnostniKategorie", document);
        NodeList skartacni_rezim_list = ValuesGetter.getAllAnywhere("nsesss:SkartacniRezim", document);
        NodeList typ_dokumentu_list = ValuesGetter.getAllAnywhere("nsesss:TypDokumentu", document);
        if(bezpecnostni_kategorie_list != null){
            work_values_special("bezpečnostní kategorie", bezpecnostni_kategorie_list, all_dmdSec, error_all_dmdSec);
        }
        if(skartacni_rezim_list != null){
            work_values_special("skartační režim", skartacni_rezim_list, all_dmdSec, error_all_dmdSec);
        }
        if(typ_dokumentu_list != null){
            work_values_special("typ dokumentu", typ_dokumentu_list, all_dmdSec, error_all_dmdSec);
        }
        
        if(!error_all_dmdSec.isEmpty()){
            return new StructMap_Obj_return_bol_AL_Obj_dmdSec(false, error_all_dmdSec);
        }
        else{
           return new StructMap_Obj_return_bol_AL_Obj_dmdSec(true, all_dmdSec); 
        }
    }
    
    static void work_values(String type, NodeList node_list, ArrayList<StructMap_Obj_dmdSec> all_dmdSec, ArrayList<StructMap_Obj_dmdSec> error_all_dmdSec){
        for(int i = 0; i < node_list.getLength(); i++){
            String[] values = get_nodeValues(node_list.item(i));
            if(values[0].equals("ERR") || values[1].equals("ERR") || values[2].equals("ERR")){
                error_all_dmdSec.add(new StructMap_Obj_dmdSec(type, values[0], values[1], values[2], node_list.item(i), true));
            }
            else{
                all_dmdSec.add(new StructMap_Obj_dmdSec(type, values[0], values[1], values[2], node_list.item(i), true));
            }
        }
    }
    
    static void work_values_special(String type, NodeList node_list, ArrayList<StructMap_Obj_dmdSec> all_dmdSec, ArrayList<StructMap_Obj_dmdSec> error_all_dmdSec){
        for(int i = 0; i < node_list.getLength(); i++){
            String[] values = get_nodeValues_special(node_list.item(i));
            if(values[1].equals("ERR") || values[2].equals("ERR")){
                error_all_dmdSec.add(new StructMap_Obj_dmdSec(type, "nemáID", values[1], values[2], node_list.item(i), false));
            }
            else{
                all_dmdSec.add(new StructMap_Obj_dmdSec(type, "nemáID", values[1], values[2], node_list.item(i), false));
            }
        }
    }
    
    static String[] get_nodeValues(Node node){

            String id = "ERR", identifikator = "ERR", zdroj = "ERR";
            if(node.getNodeName().equals("nsesss:SpisovyPlan") || node.getNodeName().equals("nsesss:Skartacnirezim") || node.getNodeName().equals("nsesss:TypDokumentu")){
                Node n = ValuesGetter.getXChild(node, "nsesss:Identifikator");
                if(ValuesGetter.hasAttribut(node, "ID")){
                        id = ValuesGetter.getValueOfAttribut(node, "ID");
                    }
                if(n != null){
                    if(ValuesGetter.hasAttribut(n, "zdroj")){
                        zdroj = ValuesGetter.getValueOfAttribut(n, "zdroj");
                    }
                    identifikator = n.getTextContent(); 
                }
            }
            else{
                Node n = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
                if(ValuesGetter.hasAttribut(node, "ID")){
                        id = ValuesGetter.getValueOfAttribut(node, "ID");
                    }
                if(n != null){
                    if(ValuesGetter.hasAttribut(n, "zdroj")){
                        zdroj = ValuesGetter.getValueOfAttribut(n, "zdroj");
                    }
                    identifikator = n.getTextContent(); 
                }
            }
            
        String[] values = {id, identifikator, zdroj};
        return values;
    }
    
    static String[] get_nodeValues_special(Node node){

        String identifikator = "ERR", zdroj = "ERR";
        Node n = ValuesGetter.getXChild(node, "nsesss:Identifikator");
        if(n != null){
            if(ValuesGetter.hasAttribut(n, "zdroj")){
                zdroj = ValuesGetter.getValueOfAttribut(n, "zdroj");
            }
            identifikator = n.getTextContent(); 
        }

        String[] values = {"nemáID", identifikator, zdroj};
        return values;
    }
        
    // na prvním místě v seznamu je amdSec a na dalších ty metsy který se dublovaly
    public static StructMap_Obj_return_bol_AL_node compare_amdSec_dmdSec(List<StructMap_Obj_amdSec> amdSec_list,
                                                                  List<StructMap_Obj_dmdSec> dmdSec_list) {
            for(int i = 0; i < amdSec_list.size(); i++){
                StructMap_Obj_amdSec amd = amdSec_list.get(i);
                String amd_identifikator = amd.identifikator;
                String amd_zdroj = amd.zdroj;
                int counter = 0;
                ArrayList<StructMap_Obj_dmdSec> novy_seznam = new ArrayList<>();
                for(int j = 0; j < dmdSec_list.size(); j++){
                    String dmd_identifikator = dmdSec_list.get(j).identifikator;
                    String dmd_zdroj = dmdSec_list.get(j).zdroj;
                    if((amd_identifikator.equals(dmd_identifikator)) && (amd_zdroj.equals(dmd_zdroj))){
                        counter++;
                    }
                    else{
                        novy_seznam.add(dmdSec_list.get(j));
                    }
                }
                if(counter == 0){
                    ArrayList<Node> l = new ArrayList<>();
                    l.add(amd.node);
                    return new StructMap_Obj_return_bol_AL_node(false, l);
                }
                dmdSec_list = novy_seznam;
            }
            // lestli nějaký zbyly v dmd sec
            if(!dmdSec_list.isEmpty()){
                ArrayList<Node> l = new ArrayList<>();
                for(int i = 0; i < dmdSec_list.size(); i++){
                    l.add(dmdSec_list.get(i).node);
                }
                return new StructMap_Obj_return_bol_AL_node(false, l);
            }

        return new StructMap_Obj_return_bol_AL_node(true, null);
    }
    
    public static StructMap_Obj_return_bol_AL_node compare_amdSec_with_metsDiv(List<StructMap_Obj_amdSec> amdSec_list,
                                                                        List<StructMap_Obj_metsdiv> metsdiv_list) {
        ArrayList<Node> seznam_chbnych_node = new ArrayList<>();
        
        for(int i = 0; i < amdSec_list.size(); i++){
            StructMap_Obj_amdSec amd =  amdSec_list.get(i);
            ArrayList<Node> chybne_div = new ArrayList<>();
            ArrayList<StructMap_Obj_metsdiv> novy_seznam = new ArrayList<>();
            for(int j = 0; j < metsdiv_list.size(); j++){
                StructMap_Obj_metsdiv div = metsdiv_list.get(j);
                if(amd.id.equals(div.admid)){
                    chybne_div.add(div.metsdiv);
                }
                else{
                  novy_seznam.add(div);  
                }
                
            }
            // 0 - element amdsec neodkazuje na žádný element div
            // vrácený seznam má size 1
            if(chybne_div.isEmpty()){
                ArrayList<Node> l = new ArrayList<>();
                l.add(amd.node);
                return new StructMap_Obj_return_bol_AL_node(false, l);
            }
            // 1 - true
            // 2+ - na element amd sec odkazuje vice elementů mets div
            // do seznamu nahraju amd + ty chybný mets na který odkazuje a vrátim velkej seznam
            // čili chyba bud že amd sec odkazuje na více div
            if(chybne_div.size() > 1){
                seznam_chbnych_node.add(amd.node);
                seznam_chbnych_node.addAll(chybne_div);
            }
            metsdiv_list = novy_seznam;
        }
        if(!seznam_chbnych_node.isEmpty()){
            return new StructMap_Obj_return_bol_AL_node(false, seznam_chbnych_node);
        }
        
        return new StructMap_Obj_return_bol_AL_node(true, null);
    }
    
    public static StructMap_Obj_return_bol_AL_node compare_metsDiv_with_amdSec(List<StructMap_Obj_amdSec> amdSec_list,
                                                                        List<StructMap_Obj_metsdiv> metsdiv_list) {
        ArrayList<Node> seznam_chbnych_node = new ArrayList<>();
        
        for(int i = 0; i < metsdiv_list.size(); i++){
            StructMap_Obj_metsdiv div =  metsdiv_list.get(i);
            ArrayList<Node> chybne_amd = new ArrayList<>();
            ArrayList<StructMap_Obj_amdSec> novy_seznam = new ArrayList<>();
            for(int j = 0; j < amdSec_list.size(); j++){
                StructMap_Obj_amdSec amd = amdSec_list.get(j);
                if(div.admid.equals(amd.id)){
                    chybne_amd.add(amd.node);
                }
                else{
                   novy_seznam.add(amd); 
                }
                
            }
            // 0 - element amdsec neodkazuje na žádný element div
            // vrácený seznam má size 1
            if(chybne_amd.isEmpty()){
                return new StructMap_Obj_return_bol_AL_node(false, Collections.singletonList(div.metsdiv));
            }
            // 1 - true
            // 2+ - na element amd sec odkazuje vice elementů mets div
            // do seznamu nahraju amd + ty chybný mets na který odkazuje a vrátim velkej seznam
            // čili chyba bud že amd sec odkazuje na více div
            if(chybne_amd.size() > 1){
                seznam_chbnych_node.add(div.metsdiv);
                seznam_chbnych_node.addAll(chybne_amd);
            }
            amdSec_list = novy_seznam;
        }
        if(!seznam_chbnych_node.isEmpty()){
            return new StructMap_Obj_return_bol_AL_node(false, seznam_chbnych_node);
        }
        
        return new StructMap_Obj_return_bol_AL_node(true, null);
    }

    static StructMap_Obj_return_bol_AL_node compare_metsDiv_with_dmdSec(ArrayList<StructMap_Obj_dmdSec> dmdSec_list, ArrayList<StructMap_Obj_metsdiv> metsdiv_list){
        
        for(int i = 0; i < metsdiv_list.size(); i++){
            StructMap_Obj_metsdiv div =  metsdiv_list.get(i);
            int counter = 0;
            ArrayList<StructMap_Obj_dmdSec> novy_seznam = new ArrayList<>();
            for(int j = 0; j < dmdSec_list.size(); j++){
                StructMap_Obj_dmdSec dmd = dmdSec_list.get(j);

                if((div.dmdid.equals(dmd.id)) && (div.type.equals(dmd.type))){
                    counter ++;
                }
                else{
                    novy_seznam.add(dmd);
                }
                
            }
            if(counter == 0){
                ArrayList<Node> l = new ArrayList<>();
                l.add(div.metsdiv);
                return new StructMap_Obj_return_bol_AL_node(false, l);
            }
            dmdSec_list = novy_seznam;
        }
        
        if(!dmdSec_list.isEmpty()){
            ArrayList<Node> l = new ArrayList<>();
            for(int i = 0; i < dmdSec_list.size(); i++){
                l.add(dmdSec_list.get(i).node);
            }
            return new StructMap_Obj_return_bol_AL_node(false, l);
        }
        
        return new StructMap_Obj_return_bol_AL_node(true, null);
    }    

    public static StructMap_Obj_return_bol_AL_node compare_metsDiv_with_dmdSec_structure(List<StructMap_Obj_metsdiv> metsdiv_list,
                                                                                  List<StructMap_Obj_dmdSec> dmdSec_list,
                                                                                         Document document) {
        for(int i = 0; i < metsdiv_list.size(); i++){
            StructMap_Obj_metsdiv div =  metsdiv_list.get(i);
            if(!div.parent_right){
                ArrayList<Node> l = new ArrayList<>();
                l.add(div.metsdiv);
                return new StructMap_Obj_return_bol_AL_node(false, l);
            }
            
            for(int j = 0; j < dmdSec_list.size(); j++){
                StructMap_Obj_dmdSec dmd = dmdSec_list.get(j);
                //nalezl v dmd obraz (vždy najde už aspon jeden)
                
                if(dmd.id.equals(div.dmdid) && dmd.type.equals(div.type)){
                    // zkontroluj rodice (id nemůžu kvůli duplicitě)
                    // if(!dmd.rodic_zatrideni.id.equals(div.rodic.dmdid) || !dmd.rodic_zatrideni.type.equals(div.rodic.type)){
                    if(!dmd.rodic_zatrideni.id.equals(div.rodic.dmdid) || !dmd.rodic_zatrideni.type.equals(div.rodic.type)){
                        if(!div.type.equals("spisový plán")){
                            Node rodic_z_div_dmd = ValuesGetter.getNodeWithID(div.rodic.dmdid, ValuesGetter.prevod_type_to_nodeName(div.rodic.type), document);
//                            Node rodic_z_div_dmd = parsedSAX_Sipsoubor.getElementById(div.rodic.dmdid);
                            String[] rodic_z_div_dmd_udaje = ValuesGetter.get_node_identifikator_zdroj(rodic_z_div_dmd);
                            if(!rodic_z_div_dmd_udaje[1].equals(dmd.rodic_zatrideni.identifikator) || !rodic_z_div_dmd_udaje[2].equals(dmd.rodic_zatrideni.zdroj)){
                                ArrayList<Node> l = new ArrayList<>();
                                l.add(div.metsdiv);
                                l.add(div.rodic.metsdiv);
                                l.add(dmd.node);
                                l.add(dmd.rodic_zatrideni.node);
                                return new StructMap_Obj_return_bol_AL_node(false, l);
                            }
                        }
                    }
                }
            }
            
        } 
        return new StructMap_Obj_return_bol_AL_node(true, null);
    }
}
