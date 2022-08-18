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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


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
    public static Element getXChild(Element parent, String... children) {
      if (parent == null) {
          return null;
      }
      int pos = 0;
      Element nextParent = parent;
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
    
    public static Element findFirstChild(Element parent, String childName) {
        Validate.notNull(parent);

        NodeList childNodes = parent.getChildNodes();
        if (childNodes == null) {
            return null;
        }

        int childCount = childNodes.getLength();
        for (int i = 0; i < childCount; i++) {
            Node nodeOnIndex = childNodes.item(i);
            short nodeType = nodeOnIndex.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                Element elem = (Element) nodeOnIndex;
                String nodeName = nodeOnIndex.getNodeName();
                if (nodeName.equals(childName)) {
                    return elem;
                }

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
    
    public static boolean hasChildWithName(Element node, String childName) {
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

        public static List<Element> getChildNodes(Element parentElement){
        if (parentElement == null) {
            return Collections.emptyList();
        }
        List<Element> list = new ArrayList<>();
        NodeList childNodes = parentElement.getChildNodes();
        int pocetDeti = childNodes.getLength();
        for (int i = 0; i < pocetDeti; i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                list.add((Element) childNode);
            }
        }
        return list;
    }
    
    /**
     * Vrati seznam uzlu s danym jmenem
     * 
     * @param node
     *            Rodicovsky uzel
     * @param name
     *            Nazev uzlu
     * @return Seznam uzlu, vraci vzdy seznam
     */
    public static List<Element> getChildNodes(Element node, String name) {

        if (node == null) {
            return Collections.emptyList();
        }
        List<Element> list = new ArrayList<>();

        NodeList childNodes = node.getChildNodes();
        int pocetDeti = childNodes.getLength();
        for (int i = 0; i < pocetDeti; i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            String childName = childNode.getNodeName();
            if (name.equals(childName)) {
                list.add((Element) childNode);
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
    public static Element getParent(Element node, String parentName) {
        if (node == null) {
            return null;
        }
        Node parent = node.getParentNode();
        if (parent == null) {
            return null;
        }
        String pName = parent.getNodeName();
        if(pName.equals(parentName)) {
            return (Element) parent;
        }
        return null;
    }
    
    public static Element getXParent(Element node, String... parentNames) {
        if (node == null) {
            return null;
        }
        Element xParent = getParent(node, parentNames[0]);

        int s = parentNames.length;
        if (s == 1) {
            return xParent;
        }
        for(int i = 1; i < s; i++){
            Element nextParent = getParent(xParent, parentNames[i]);
            xParent = nextParent;
        }
        return xParent; 
    }
    
    public static boolean isXParent(Element node, String... parentNames) {
        if (node == null) {
            return false;
        }
        Element xParent = getParent(node, parentNames[0]);
        if (xParent == null) {
            return false;
        }
        String p_name = xParent.getNodeName();
        int s = parentNames.length; 
        if (s == 1) {
            return p_name.equals(parentNames[0]);
        }
        
        for(int i = 1; i < s; i++){
            Element nextParent = getParent(xParent, parentNames[i]);
            if (nextParent == null) {
                return false;
            }
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
            
    public static Element getSourozencePrvnihoSeJmenem(Element node, String nazevSourozence) {
        Node parentNode = node.getParentNode();
        if (parentNode == null) {
            return null;
        }
        Element parent = (Element) parentNode;
        return getXChild(parent, nazevSourozence);
    }
    
    public static List<Element> getAllChildNodes(Element parent, List<Element> nodes) {
        List<Element> childNodes = new ArrayList<>();
        for (Element node : nodes) {
            if(isXParent(parent, node)) {
                childNodes.add(node);
            }
        }
        return childNodes;
    }
        
    public static boolean getObsahujeRodicElementSHodnotou(Element rodic, String elementName, String elementValue) {
        Element node = getXChild(rodic, elementName);
        if (node == null) {
            return false;
        }
        String textContent = node.getTextContent();
        return Objects.equals(textContent, elementValue);
    }
    
    public static Element getElementByValueOfAtributFromSpecificList(List<Element> list, String atributName,
                                                               String atributValue) {
        for(int i = 0; i < list.size(); i++){
            Element e = list.get(i);
            boolean find = hasAttributValue(e, atributName, atributValue);
            if(find){
                return e;
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
