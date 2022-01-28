/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import static org.w3c.dom.Node.ELEMENT_NODE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.Helper;


public class ValuesGetter {
                
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
    
    /**
     * Find first child in given path
     * 
     * @param parent
     *            výchozí uzel (rodič)
     * @param children
     *            jména dětských uzlů, alespoň jeden
     * @return první nalezený dětský uzel
     */
    public static Node getXChild(Node parent, String... children){
      if (parent == null) {
          return null;
      }
      int pos = 0;
      Node nextParent = parent;
      for(; pos<(children.length-1); pos++) {
          nextParent = findFirstChild(nextParent, children[pos]);
          if(nextParent==null) {
              return null;
          }
      }      
      return findFirstChild(nextParent, children[pos]);
    }

    public static List<Node> getChildNodes(Node node){
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
    
    public static Node findFirstChild(Node parent, String childName) {
        Validate.notNull(parent);

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
                
    public static String getValueOfAttribut(Node node, String attributName) {
        Node valueNode = getAttribut(node, attributName);
        if (valueNode == null) {
            return null;
        }
        return valueNode.getNodeValue();
    }
    
    public static Integer getAttribute(Node node, String attrName) {
        Node valueNode = getAttribut(node, attrName);
        if (valueNode == null) {
            return null;
        }
        String str = valueNode.getNodeValue();
        return Integer.valueOf(str);
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
        if (node == null) {
            return null;
        }

        NamedNodeMap atributy = node.getAttributes();
        if (atributy == null) {
            return null;
        }

        Node atribut = atributy.getNamedItem(attributName);

        return atribut;
    }

    public static boolean hasAttributValue(Node node, String attributName, String value) {
        String hodnota = getValueOfAttribut(node, attributName);
        if (hodnota == null) {
            return false;
        }
        return value.equals(hodnota);
    }
    
    public static boolean hasChildWithName(Node node, String childName) {
        Node n = getXChild(node, childName);
        return n != null;
    
    }
    
    public static boolean hasOnlyOneChild_ElementNode(Node node, String childName) {
        if (node == null) {
            return false;
        }
        NodeList list = node.getChildNodes();
        if (list == null) {
            return false;
        }
        int size = list.getLength();
        int count = 0;
        for(int i = 0; i < size; i++){
            Node n = list.item(i);
            if(n.getNodeType() == ELEMENT_NODE){
                if(n.getNodeName().equals(childName)){
                    count++;
                    if (count > 1) {
                        return false;
                    }
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

    /**
     * Return parent with given name
     * 
     * @param node
     *            uzel
     * @param parentName
     *            jméno rodičovského uzlu
     * @return Return null if parent does not exists or has different name.
     */
    public static Node getParent(Node node, String parentName){
        if (node == null) {
            return null;
        }
        Node parent = node.getParentNode();
        if (parent == null) {
            return null;
        }
        String pName = parent.getNodeName();
        if(pName.equals(parentName)) {
            return parent; 
        }
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
        if (node == null) {
            return false;
        }
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
        
    public static List<Node> getChildList(Node parent, String... childNames) {
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
    
    public static List<Node> getAllChildNodes(Node parent, List<Node> nodes) {
        List<Node> childNodes = new ArrayList<>();
        for(Node node: nodes) {
            if(isXParent(parent, node)) {
                childNodes.add(node);
            }
        }
        return childNodes;
    }
        
    public static boolean getObsahujeRodicElementSHodnotou(Node rodic, String elementName, String elementValue){
        Node node = getXChild(rodic, elementName);
        if(node != null){
            boolean jeHodnota = node.getTextContent().equals(elementValue);
            return jeHodnota;   
        }
        return false;
    }
    
    public static Node getNodeByValueOfAtributFromSpecificList(List<Node> nodeList, String atributName,
                                                               String atributValue) {
        for(int i = 0; i < nodeList.size(); i++){
            Node n = nodeList.get(i);
            boolean find = hasAttributValue(n, atributName, atributValue);
            if(find){
                return n;
            } 
        }
        return null;
    }
                
    /**
     * Prevod nazvu urovne na nazev elementu dle NSESSS
     * 
     * @param type
     *            název úrovně
     * @return jméno elementu včetně namespace
     */
    public static String prevodTypeToNodeName(String type){
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
        return null;
    }

}
