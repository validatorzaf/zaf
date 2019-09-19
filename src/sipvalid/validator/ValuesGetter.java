/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import static org.w3c.dom.Node.ELEMENT_NODE;
import org.w3c.dom.NodeList;
import sipvalid.helper.Helper;


public class ValuesGetter {
    
    public static Node getEntityFromIdentifikator(Node identifikator){
        Node parent = identifikator.getParentNode();
        String name = parent.getNodeName();
        if(name.equals("nsesss:BezpecnostniKategorie")){
            Node node = ValuesGetter.getXParent(parent, "nsesss:Pristupnost", "nsesss:EvidencniUdaje").getParentNode();
            return node;
        }
        if(name.equals("nsesss:SpisovyPlan") || name.equals("nsesss:SkartacniRezim")){
            return parent;
        }
        
        Node entita = ValuesGetter.getXParent(parent, "nsesss:EvidencniUdaje").getParentNode();
        return entita;      
    }
    
    public static Node getNodeWithID(String id, String nodeName, Document dom){
        NodeList nodeList = ValuesGetter.getAllAnywhere(nodeName, dom);
        ArrayList<Node> founded = new ArrayList<>();
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(hasAttribut(node, "ID")){
                boolean b = hasAttributValue(node, "ID", id);
                if(b) founded.add(node);
            }
            
        }
        if(!founded.isEmpty()) return founded.get(0);
        
        return null;
    }
    
    public static Node getEntityWithIdentifikator(Node identifikator){
        if(identifikator.getParentNode().getNodeName().equals("nsesss:Identifikace")){
            if(identifikator.getParentNode().getParentNode().getNodeName().equals("nsesss:EvidencniUdaje")){
                return identifikator.getParentNode().getParentNode().getParentNode();
            }
            else{
                return identifikator.getParentNode().getParentNode();
            }
        }
        else{
            return identifikator.getParentNode();
        }
    }
    
    public static String get_type_to_nsesss(String type){
        switch(type){
            case "spisový plán":
                return "nsesss:SpisovyPlan";
            case "věcná skupina":
                return "nsesss:VecnaSkupina";
            case "typový spis":
                return "nsesss:TypovySpis";
            case "součást":
                return "nsesss:Soucast";
            case "spis":
                return "nsesss:Spis";
            case "díl":
                return "nsesss:Dil";
            case "dokument":
                return "nsesss:Dokument";
            case "komponenta":
                return "nsesss:Komponenta";
        }
        return "Nenalezen";
    }
    
    public static boolean parentCheck(String node_type, String rodic_node_type){
        switch(node_type){
            case "nsesss:SpisovyPlan":
                return true;
            case "nsesss:VecnaSkupina":
                if(rodic_node_type.equals("nsesss:SpisovyPlan") || rodic_node_type.equals("nsesss:VecnaSkupina")) return true;
            case "nsesss:TypovySpis":
                if(rodic_node_type.equals("nsesss:VecnaSkupina")) return true;
            case "nsesss:Soucast":
                if(rodic_node_type.equals("nsesss:TypovySpis")) return true;
            case "nsesss:Spis":
                if(rodic_node_type.equals("nsesss:VecnaSkupina")) return true;
            case "nsesss:Dil":
                if(rodic_node_type.equals("nsesss:Soucast")) return true;
            case "nsesss:Dokument":
                if(rodic_node_type.equals("nsesss:Dil") || rodic_node_type.equals("nsesss:Spis") || rodic_node_type.equals("nsesss:VecnaSkupina")) return true;
            case "nsesss:Komponenta":
                if(rodic_node_type.equals("nsesss:Dokument")) return true;  
        }
        return false;
    }
    
    public static boolean checkEntity_IdentifikatorCompare(Node node){
        switch(node.getNodeName()){
            case "nsesss:SpisovyPlan":
                return true;
            case "nsesss:VecnaSkupina":
                return true;
            case "nsesss:TypovySpis":
                return true;
            case "nsesss:Soucast":
                return true;
            case "nsesss:Spis":
                return true;
            case "nsesss:Dil":
                return true;
            case "nsesss:Dokument":
                return true;
            case "nsesss:Komponenta":
                return true;  
            case "nsesss:BezpecnostniKategorie":
                return true;    
            case "nsesss:SkartacniRezim":
                return true;
            case "nsesss:TypDokumentu":
                return true;             
        }
        return false;
    }

    public static Node getXChild (Node parent, String... children){
      if (parent == null) return null;
      Node xChild = findChild(parent, children[0]);
      
      int s = children.length; if (s == 1) return xChild;
      
      for(int i = 1; i < s; i++){
        Node nextChild = findChild(xChild, children[i]);
        xChild = nextChild; 
       
      }  
 
      return xChild;
    }

    public static ArrayList<Node> get_Node_Children(Node node){
        NodeList list  = node.getChildNodes();
        ArrayList<Node> seznam = new ArrayList<>();
        if(list == null) return seznam;
        for(int i = 0; i < list.getLength(); i++){
            Node child = list.item(i);
            if(child.getNodeType() == ELEMENT_NODE){
                seznam.add(list.item(i));
            }
        }
        return seznam;
    }
    
    public static Node findChild(Node parent, String childName) {
        if (parent == null) {
            return null;
        }
        NodeList childNodes = parent.getChildNodes();
        if (childNodes == null) {
            return null;
        }

        int childCount = childNodes.getLength();
        

        for (int i = 0; i < childCount; i++) {
            Node nodeOnIndex = childNodes.item(i);
            String nodeName = nodeOnIndex.getNodeName();
            if (nodeName.equals(childName)) {
                return nodeOnIndex;
            }
        }

        return null;
    }
    
    public static NodeList getAllAnywhere(String nazev, Document dom){
        NodeList nodeList = dom.getElementsByTagName(nazev); 
        if(nodeList.getLength() == 0) return null;
        return nodeList;
    }
    
    public static ArrayList<Node> getAllAnywhereArrayList(String nazev, Document dom){
        NodeList nodeList = dom.getElementsByTagName(nazev); 
        if(nodeList.getLength() == 0) return null;
        int size = nodeList.getLength();
        ArrayList<Node>  list = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            Node node = nodeList.item(i);
            list.add(node);
        }
        return list;
    }
    
    public static Node getFirstAnywhere(String nazev, Document dom){
        NodeList nodeList = dom.getElementsByTagName(nazev);
        if(nodeList.getLength() == 0) return null;
        
        return nodeList.item(0);
    }
    
    public static String getValueOfAttribut(Node node, String attributName) {
        if(node == null) return null;
        NamedNodeMap map = node.getAttributes();
        if (map == null) return null;
        Node atribut = map.getNamedItem(attributName);
        if (atribut != null) {
            String value = atribut.getNodeValue();
            return value;
        }
        
        return null;

    }
    
    public static boolean hasAttribut(Node node, String attributName) {
        if(node == null) return false;
        Node attribut = getAttribut(node, attributName);
        boolean bol = attribut != null;

        return bol;
    }
    
    public static boolean hasAttribut(ArrayList<Node> list, String attributName) {
        for(int i = 0; i < list.size(); i++){
            Node n = list.get(i);
            Node atribut = getAttribut(n, attributName);
            if(atribut == null) return false;
        }
        
        return true;
    }
    
    public static Node getAttribut(Node node, String attributName) {
        if (node == null) return null;

        NamedNodeMap atributy = node.getAttributes();
        if (atributy == null) {
            return null;
        }

        Node atribut = atributy.getNamedItem(attributName);

        return atribut;
    }

    public static boolean hasAttributValue(Node node, String attributName, String value) {
        Node atribut = getAttribut(node, attributName);

        if (atribut == null) {
            return false;
        }

        String hodnota = atribut.getNodeValue();
        if(hodnota == null) return false;
        boolean vysledek = value.equals(hodnota);

        return vysledek;
    }
    
    public static boolean hasChildWithName(Node node, String childName) {
        Node n = getXChild(node, childName);
        return n != null;
    
    }
    
    public static boolean hasOnlyOneChild_ElementNode(Node node, String childName) {
        if(node == null) return false;
        NodeList list = node.getChildNodes();
        if(list == null) return false;
        int size = list.getLength();
        if(size == 0) return false;
        int count = 0;
        for(int i = 0; i < size; i++){
            Node n = list.item(i);
            if(n.getNodeType() == ELEMENT_NODE){
                if(n.getNodeName().equals(childName)){
                    count++;
                    if(count > 1) return false;
                }
            } 
        }
        return count == 1;
    
    }

    public static ArrayList<Node> getSpecificChildWithName(Node node, String name) {
        
        ArrayList<Node> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        NodeList childNodes = node.getChildNodes();
        int pocetDeti = childNodes.getLength();
        for (int i = 0; i < pocetDeti; i++) {
            Node childNode = childNodes.item(i);
            String childName = childNode.getNodeName();
            if (name.equals(childName)) {
                list.add(childNode);
            }
        }
        return list;
    }

    public static Node getParent(Node node, String parentName){
        if (node == null) return null;
        Node parent = node.getParentNode();
        if (parent == null) return null;
        String p_name = parent.getNodeName();
        if(p_name.equals(parentName)) return parent; 
        return null;
    }
    
    public static Node getXParent(Node node, String... parentNames){
        if (node == null) return null;
        Node xParent = getParent(node, parentNames[0]);
        int s = parentNames.length; if (s == 1) return xParent;
        for(int i = 1; i < s; i++){
        Node nextParent = getParent(xParent, parentNames[i]);
        xParent = nextParent;
        }
        return xParent; 
    }
    
    public static boolean isXParent(Node node, String... parentNames){
        if (node == null) return false;
        Node xParent = getParent(node, parentNames[0]);
        if(xParent == null) return false;
        String p_name = xParent.getNodeName();
        int s = parentNames.length; 
        if (s == 1 && p_name.equals(parentNames[0])) return true;
        if (s == 1 && !p_name.equals(parentNames[0])) return false;
        
        for(int i = 1; i < s; i++){
        Node nextParent = getParent(xParent, parentNames[i]);
        if(nextParent == null) return false;
        xParent = nextParent;
        p_name = xParent.getNodeName();
        }
        
        boolean bol = (p_name.equals(parentNames[s-1]));
        return bol;
    }
    
    public static boolean isXParent(Node foundedParent, Node xParent){
        Node nextParent = xParent.getParentNode();
        if(nextParent == null) return false;
        while(nextParent == foundedParent){
            return true;
        }
        return isXParent(foundedParent, nextParent);
    }
    
    public static boolean overSpravnostRetezceProInt (String hodnotaString){
            
        String nezadouciZnaky = "+/*-,)ú§ů¨-.,éíáýžřčšě+;qwertzuioplkjhgfdsayxcvbnm";
        for (char c : hodnotaString.toCharArray())
        {
        if (nezadouciZnaky.contains(String.valueOf(c))) return false;
        }
        return true;
    }
        
    public static Date vytvorDate(Node node, String... vzorDatumu) throws ParseException{
        
        if(node == null)
            return null;        
        
        String str = node.getTextContent();       
        Date ret = null;
        for (String vzorDatumu1 : vzorDatumu) {
            try {
                DateFormat formatter = new SimpleDateFormat(vzorDatumu1);
                ret = formatter.parse(str);
                if(ret != null) return ret;
            }catch(ParseException e){
//                return null;
            }
        }
        
        return ret;
    }
            
    public static Node getSourozencePrvnihoSeJmenem(Node node, String nazevSourozence){
        Node rodic = node.getParentNode();
        Node sourozenec = getXChild(rodic, nazevSourozence);
        return sourozenec;
    }  
    
    public static boolean maRodicPouzeTytoPotomky(Node rodic, String... childNames){
        NodeList nodeList = rodic.getChildNodes();
        ArrayList<String> nodeNames = new ArrayList<>();
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            String name = node.getNodeName();
            if(node.getNodeType() == ELEMENT_NODE){
                nodeNames.add(name);
            }
        }
        int pocitadlo = 0;
        for(String s : childNames){
            for(int j = 0; j < nodeNames.size(); j++){
                if(nodeNames.get(j).equals(s)){
                    pocitadlo ++;
                }
            }
        }
        boolean b = pocitadlo == nodeNames.size();
        
        
        return b;
    }
    
    public static ArrayList<Node> getChildList(Node parent, String... childNames){
        ArrayList<Node> list = new ArrayList<>();
        if(parent == null) return list;
        NodeList nodeList = parent.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            String name = node.getNodeName();
            if(Helper.obsahujePoleHodnotu(childNames, name)){
                list.add(node);
            }
        }
        return list;
    }
    
    public static ArrayList<Node> getAllInNode(Node node, String elementName, Document dom){
        ArrayList<Node> list = new ArrayList<>();
        NodeList vsechny = getAllAnywhere(elementName, dom);
        if(vsechny == null) return null;
        for(int i = 0; i < vsechny.getLength(); i++){
            Node n = vsechny.item(i);
            if(isXParent(node, n)) list.add(n);
        }
        if(list.isEmpty()) return null;
        return list;    
    }
    
    public static Node getFirstInNode(Node node, String elementName, Document dom){
        NodeList vsechny = getAllAnywhere(elementName, dom);
        if(vsechny == null) return null;
        for(int i = 0; i < vsechny.getLength(); i++){
            Node n = vsechny.item(i);
            if(isXParent(node, n)) return n;
        }
        
        return null;
    }
    
    public static boolean getObsahujeRodicElementSHodnotou(Node rodic, String elementName, String elementValue){
        Node node = getXChild(rodic, elementName);
        if(node != null){
            boolean jeHodnota = node.getTextContent().equals(elementValue);
            return jeHodnota;   
        }
        return false;
    }
    
    public static Node getNodeByValueOfAtributFromSpecificList(ArrayList<Node> nodeList, String atributName, String atributValue){
        for(int i = 0; i < nodeList.size(); i++){
            Node n = nodeList.get(i);
            boolean find = hasAttributValue(n, atributName, atributValue);
            if(find){
                return n;
            } 
        }
        return null;
    }
    
    public static Node getNodeByValueOfAtributFromSpecificList_andRemove(ArrayList<Node> nodeList, String atributName, String atributValue){
        for(int i = 0; i < nodeList.size(); i++){
            Node n = nodeList.get(i);
            boolean find = hasAttributValue(n, atributName, atributValue);
            if(find){
                nodeList.remove(i);
                return n;
            } 
        }
        return null;
    }
    
    public static ArrayList<Node> getArrayListOfNodesByValueOfAtributFromSpecificArrayList(ArrayList<Node> nodeList, String atributName, String atributValue){
        if(nodeList == null) return null;
        ArrayList<Node> list = new ArrayList<>();
        for(int i = 0; i < nodeList.size(); i++){
            Node n = nodeList.get(i);
            boolean find = hasAttributValue(n, atributName, atributValue);
            if(find){
                list.add(n);
            } 
        }
        return list;
    }   

    public static boolean compareSpisoveZnaky(String jednoduchy, String plneUrceny){
        if(jednoduchy.equals(plneUrceny)) return true;

        int lastIn = plneUrceny.lastIndexOf(jednoduchy);
        if(lastIn == -1) return false;
        String orez = plneUrceny.substring(0, lastIn);
        if(orez.equals("")) return false;
        orez = orez.substring(orez.length()-1);
        boolean bol = !orez.matches("[A-Za-z0-9]+");
        return bol;
    }
    
    public static String[] get_node_identifikator_zdroj(Node node){
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
    
    public static String prevod_type_to_nodeName(String type){
        switch(type.toLowerCase()){
            case "spisový plán": return "nsesss:SpisovyPlan";
            case "věcná skupina": return "nsesss:VecnaSkupina";
            case "součást": return "nsesss:Soucast";
            case "typový spis": return "nsesss:TypovySpis";
            case "spis": return "nsesss:Spis";
            case "díl": return "nsesss:Dil";
            case "dokument": return "nsesss:Dokument";
            case "komponenta": return "nsesss:Komponenta";
            
   
        }
        return "";
    }

}
