/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;


import org.w3c.dom.Node;

/**
 *
 * @author m000xz006159
 */
public class StructMap_Obj_dmdSec {
    String id, identifikator, zdroj, type;
    Node node;
    StructMap_Obj_dmdSec rodic_zatrideni;
    String rodic_type = "";
    

    public StructMap_Obj_dmdSec(String type, String id, String identifikator, String zdroj, Node node, boolean getRodice) {
        this.type = type;
        this.id = id;
        this.identifikator = identifikator;
        this.zdroj = zdroj;
        this.node = node;
        if(getRodice){
            get_rodice();
        }
        
    }
    
    private void get_rodice(){
        Node rodic = null;
        
        switch(type){
            case "věcná skupina":
               rodic = parent_vecna_skupina();
            break;
            case "součást":
               rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:TypovySpis");
               rodic_type = "součást";
            break;
            case "typový spis":
               rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
               rodic_type = "věcná skupina";
            break;
            case "spis":
               rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
               rodic_type = "věcná skupina";
            break;
            case "díl":
               rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:Soucast");
               rodic_type = "součást";
            break;
            case "dokument":
               rodic = parent_dokument();
            break;
            case "komponenta":
               rodic = node.getParentNode().getParentNode();
               rodic_type = "dokument";
            break;
        }
        String[] values = get_nodeValues(rodic);
        rodic_zatrideni = new StructMap_Obj_dmdSec(rodic_type, values[0], values[1], values[2], rodic, false);

    }
    
    private String[] get_nodeValues(Node node){
        if(node == null){
            String[] values = {"ERR", "ERR", "ERR"};
            return values;
        }
        String id2 = "ERR", identifikator2 = "ERR", zdroj2 = "ERR";
        if(node.getNodeName().equals("nsesss:SpisovyPlan")){
            Node n = ValuesGetter.getXChild(node, "nsesss:Identifikator");
            if(ValuesGetter.hasAttribut(node, "ID")){
                    id2 = ValuesGetter.getValueOfAttribut(node, "ID");
                }
            if(n != null){
                if(ValuesGetter.hasAttribut(n, "zdroj")){
                    zdroj2 = ValuesGetter.getValueOfAttribut(n, "zdroj");
                }
                identifikator2 = n.getTextContent(); 
            }
        }
        else{
            Node n = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
            if(ValuesGetter.hasAttribut(node, "ID")){
                    id2 = ValuesGetter.getValueOfAttribut(node, "ID");
                }
            if(n != null){
                if(ValuesGetter.hasAttribut(n, "zdroj")){
                    zdroj2 = ValuesGetter.getValueOfAttribut(n, "zdroj");
                }
                identifikator2 = n.getTextContent(); 
            }
        }

        String[] values = {id2, identifikator2, zdroj2};
        return values;
    }
    
    private Node parent_dokument(){
        Node p;
        if(node.getParentNode().getNodeName().equals("mets:xmlData")){
            p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
            rodic_type = "věcná skupina";
            if(p == null){
                p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskeEntity", "nsesss:VecnaSkupina");
            }
        }
        else{
            p = node.getParentNode().getParentNode();
            if(p.getNodeName().equals("nsesss:Spis")){
                rodic_type = "spis";
            }
            else{
                rodic_type = "díl";
            }
        }
        return p;
    }
    
    private Node parent_vecna_skupina(){
        Node p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
        rodic_type = "věcná skupina";
        if(p == null){
            p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskeEntity", "nsesss:VecnaSkupina");
            if(p == null){
                p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
                rodic_type = "spisový plán";
            }
        }
        return p;
    }
}
