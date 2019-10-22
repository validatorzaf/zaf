/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import static cz.zaf.sipvalidator.nsesss2017.RozparsovaniSipSouboru.parsedSAX_Sipsoubor;

import java.util.ArrayList;
import org.w3c.dom.Node;



/**
 *
 * @author m000xz006159
 */
public class ObjMetsDiv {
    String dmdid, admid, type;
    boolean maRodice = true, maSpravnehoHodice = true;
    boolean maSpravnyAmdSec = false;
    String chybova_hlaska;
    Node metsDiv;
    ObjMetsDiv rodicMetsDiv;
            
    public ObjMetsDiv(String dmdid, String admid, String type, Node metsDiv) {
        this.dmdid = dmdid;
        this.admid = admid;
        this.type = type;  
        this.metsDiv = metsDiv;
        if(!type.equals("spisový plán")){
            setRodice();
            setMaSpravnehoRodice();
        }
        
        if(maRodice && maSpravnehoHodice) overAmdSec();
        
        
    }
    
    public String getJeChyba(){
        return chybova_hlaska;
    }
    
    public boolean getJeOk(){
        return maSpravnyAmdSec;
    }
    
    public String getType(){
        return type;
    }
    
    public String getDmdid(){
        return dmdid;
    }
    
    public String getAdmid(){
        return admid;
    }
    
    public void maRodice(boolean bol){
        maRodice = bol;
    }
    
    public boolean getMaRodice(){
        return maRodice;
    }
    
    public boolean getMaSpravnehoRodice(){
        return maSpravnehoHodice;
    }
    
    private void setRodice(){
        Node rodic = ValuesGetter.getXParent(metsDiv, "mets:div");
        rodicMetsDiv = new ObjMetsDiv(ValuesGetter.getValueOfAttribut(rodic, "DMDID"), ValuesGetter.getValueOfAttribut(rodic, "ADMID"), ValuesGetter.getValueOfAttribut(rodic, "TYPE"), rodic); 
    }
    
    public String getDmdidRodice(){
        return rodicMetsDiv.getDmdid();
    }
    
    public String getAdmidRodice(){
        return rodicMetsDiv.getAdmid();
    }
    
    public String getTypeRodice(){
        return rodicMetsDiv.getType();
    }
    
    private void setMaSpravnehoRodice(){
        boolean podminka = (getTypeRodice().equals("věcná skupina") || getTypeRodice().equals("spisový plán") || getTypeRodice().equals("typový spis") || getTypeRodice().equals("součást") || getTypeRodice().equals("díl") || getTypeRodice().equals("spis") || getTypeRodice().equals("dokument") || getTypeRodice().equals("komponenta"));
        
        if(podminka){
            switch(type){
                case "spisový plán":
                    break;
                case "věcná skupina":
                    if(getTypeRodice().equals("věcná skupina")) maSpravnehoHodice = overRodice("nsesss:VecnaSkupina");
                    if(getTypeRodice().equals("spisový plán"))maSpravnehoHodice = overRodice("nsesss:SpisovyPlan");
                    break;

                case "typový spis":
                    if(getTypeRodice().equals("věcná skupina")) maSpravnehoHodice = overRodice("nsesss:VecnaSkupina");
                    break;

                case "součást":
                    if(getTypeRodice().equals("typový spis")) maSpravnehoHodice = overRodice("nsesss:TypovySpis");
                    break;  

                case "spis":
                    if(getTypeRodice().equals("věcná skupina")) maSpravnehoHodice = overRodice("nsesss:VecnaSkupina");
                    break;  

                case "díl":
                    if(getTypeRodice().equals("věcná skupina")) maSpravnehoHodice = overRodice("nsesss:Soucast");
                    break; 

                case "dokument":
                    if(getTypeRodice().equals("věcná skupina")) maSpravnehoHodice = overRodice("nsesss:VecnaSkupina");
                    if(getTypeRodice().equals("spis")) maSpravnehoHodice = overRodice("nsesss:Spis");
                    break; 

                case "komponenta":
                    maSpravnehoHodice = overRodice("nsesss:Dokument");
                    break;    

            }
        } 
        else{
            maSpravnehoHodice = false;
        }
    }
    
    private boolean overRodice(String jmenoRodice){
        String g = getDmdidRodice();
//        Node rodic = ValuesGetter.getNodeWithID(getDmdidRodice(), jmenoRodice, parsedSAX_Sipsoubor);
        Node rodic = parsedSAX_Sipsoubor.getElementById(g);
        boolean ret = rodic != null;
        
        return ret;
    }

    private void overAmdSec(){
        ArrayList<Node> vybrane = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(ValuesGetter.getAllAnywhereArrayList("mets:amdSec", parsedSAX_Sipsoubor), "ID", admid);
        if(vybrane != null){
            if(vybrane.size() == 1){
                Node hodnotaId = ValuesGetter.getXChild(vybrane.get(0), "mets:digiprovMD", "mets:mdWrap", "mets:xmlData", "tp:TransakcniLogObjektu", "tp:TransLogInfo", "tp:Objekt", "tp:Identifikator", "tns:HodnotaID");
                Node zdrojID = ValuesGetter.getXChild(vybrane.get(0), "mets:digiprovMD", "mets:mdWrap", "mets:xmlData", "tp:TransakcniLogObjektu", "tp:TransLogInfo", "tp:Objekt", "tp:Identifikator", "tns:ZdrojID");
                if(hodnotaId != null && zdrojID != null){
                    String hodId = hodnotaId.getTextContent();
                    String zdrID = zdrojID.getTextContent();
                    ArrayList<Node> list;
                    switch(type){
                        case "spisový plán":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:SpisovyPlan", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list, zdrID, hodId);
                        }
                        break;
                    case "věcná skupina":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list, zdrID, hodId);
                        }
                        break;

                    case "typový spis":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:TypovySpis", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list.get(0), zdrID, hodId);
                        }
                        break;

                    case "součást":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:Soucast", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list.get(0), zdrID, hodId);
                        }
                        break;  

                    case "spis":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:Spis", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list.get(0), zdrID, hodId);
                        }
                        break;  

                    case "díl":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:Dil", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list.get(0), zdrID, hodId);
                        }
                        break; 

                    case "dokument":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list.get(0), zdrID, hodId);
                        }
                        break; 

                    case "komponenta":
                        list = ValuesGetter.getAllAnywhereArrayList("nsesss:Komponenta", parsedSAX_Sipsoubor);
                        list = ValuesGetter.getArrayListOfNodesByValueOfAtributFromSpecificArrayList(list, "ID", dmdid);
                        if(list.size() == 1){
                            overIdentifikator(list.get(0), zdrID, hodId);
                        }
                        break; 
                    }
                }
            }
        }
        else{
           maSpravnyAmdSec = false; 
        }
    }
    
    private void overIdentifikator(Node nodeFromList, String zdrID, String hodId){
        Node node;
        if(nodeFromList.getNodeName().equals("nsesss:SpisovyPlan")){
            node = ValuesGetter.getXChild(nodeFromList, "nsesss:Identifikator"); 
        }
        else{
            node = ValuesGetter.getXChild(nodeFromList, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
        }
        String nodeZdroj = ValuesGetter.getValueOfAttribut(node, "zdroj");
        String nodeHodnota = node.getTextContent();
        if(zdrID.equals(nodeZdroj) && hodId.equals(nodeHodnota)) maSpravnyAmdSec = true;
    }
    
    private void overIdentifikator(ArrayList<Node> list, String zdrID, String hodId){
        maSpravnyAmdSec = true;
        for(int i = 0; i < list.size(); i++){
            Node node;
            if(list.get(i).getNodeName().equals("nsesss:SpisovyPlan")){
                node = ValuesGetter.getXChild(list.get(i), "nsesss:Identifikator"); 
            }
            else{
                node = ValuesGetter.getXChild(list.get(i), "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
            }
            String nodeZdroj = ValuesGetter.getValueOfAttribut(node, "zdroj");
            String nodeHodnota = node.getTextContent();
             if(!zdrID.equals(nodeZdroj) || !hodId.equals(nodeHodnota)) maSpravnyAmdSec = false;
        }
    }
    
    
    
    
}
