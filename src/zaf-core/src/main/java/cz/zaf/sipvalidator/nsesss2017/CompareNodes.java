/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import static org.w3c.dom.Node.ELEMENT_NODE;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Orkscream
 */
public class CompareNodes {
    private static int index; //kvůli vyhledávání elementů 
    
    public static String compare(Node node, Node node_compared){
        //JEDNÁ SE O TEN SAMÝ UZEL
        if(node.isSameNode(node_compared)) {
            return "OK";
        }
        
        //KDYŽ RYCHLÉ POROVNÁNÍ NEPROJDE MUSÍM PO ŘÁDCÍCH - ABYCH MOHL VYPSAT KONKRÉTNÍ CHYBU
        //NODE NAME
        String prvni_nodeName = node.getNodeName();
        String druhy_nodeName = node_compared.getNodeName();
        if (!prvni_nodeName.equals(druhy_nodeName)) {
            return "Jména uzlů se neshodují: " + prvni_nodeName + " - " + druhy_nodeName + ".";
        }
        //NODE TYPE
        short prvni_nodeType = node.getNodeType();
        short druhy_nodeType = node_compared.getNodeType();
        if(prvni_nodeType != druhy_nodeType) {
            return "Různé typy uzlů. " + node.getNodeName() + " - " + node.getNodeType() + ". " + node_compared.getNodeName() + " - " + node_compared.getNodeType() + ".";
        }
        //TEXT CONTENT
        if(!hasChildNodes(node) && !hasChildNodes(node_compared)){
            String prvni_nodeText = node.getTextContent().trim();
            String druhy_nodeText = node_compared.getTextContent().trim();
            if(prvni_nodeText == null ? druhy_nodeText == null : !prvni_nodeText.equals(druhy_nodeText)){
                return "Hodnoty uzlů se neshodují: " + node.getNodeName() + ": " + prvni_nodeText + " - " + node_compared.getNodeName() + ": " + druhy_nodeText + ".";
            }
        }
        //ATRIBUTY
        String atributy_vysledek = compareAttributes(node, node_compared);
        if(!atributy_vysledek.equals("OK")) {
            return atributy_vysledek;
        }
        //CHILDREN
        String children_vysledek = check_children(node, node_compared);
        if(!children_vysledek.equals("OK")) {
            return children_vysledek;
        }
        
        return "OK";
    }
    
    private static Map<String, String> prepareAttributeMap(Node node) {
        NamedNodeMap attrs = node.getAttributes();
        if(attrs==null||attrs.getLength()==0) {
            return Collections.emptyMap();
        }
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < attrs.getLength(); i++) {
            Attr attr = (Attr) attrs.item(i);
            String attrName = attr.getName();
            // TODO: zde by mela byt lepsi kontrola
            if (attrName.startsWith("xmlns") || attrName.equals("ID")) {
                continue;
            }
            result.put(attrName, attr.getValue());
        }
        return result;
    }
    
    private static String compareAttributes(Node left, Node right){
        Map<String, String> leftAttrsMap = prepareAttributeMap(left);
        Map<String, String> rightAttrsMap = prepareAttributeMap(right);
        if(leftAttrsMap.size()!=rightAttrsMap.size()) {
            return "Rozdílný počet atributů: " + left.getNodeName() + " " + right.getNodeName();
        }
        for(String leftKey: leftAttrsMap.keySet()) {
            String leftValue = leftAttrsMap.get(leftKey);
            String rightValue = rightAttrsMap.get(leftKey);
            if(Objects.equals(leftValue, rightValue)) {
                continue;
            }
            if(leftValue==null||rightValue==null) {
                return "Nenalezen atribut " + leftKey;
            }
            return "Atributy elementu " + left.getNodeName() + " se neshodují: " + leftValue + " " + rightValue;            
        }
        return "OK";
    }
    
    // musím vynechat komentáře != ELEMENT_NODE
    private static String check_children(Node vzor, Node porovnavany){
        NodeList vzor_Deti = vzor.getChildNodes();
        NodeList porovnavany_deti = porovnavany.getChildNodes();
        int t= vzor_Deti.getLength();
        int g = porovnavany_deti.getLength();
        if(vzor_Deti.getLength() >= porovnavany_deti.getLength()){
            int j = 0;
            for(int i = 0; i < vzor_Deti.getLength(); i++){
                Node prvni = getFirst_ElementNode(vzor_Deti, i);
                i = index+1;
                Node druhy = getFirst_ElementNode(porovnavany_deti, j);
                j = index+1;
                if(prvni == null && druhy == null) return "OK";
                String compare = compare(prvni, druhy);
                if(!compare.equals("OK")) return compare;
            }
        }
        else{
            int j = 0;
            for(int i = 0; i < porovnavany_deti.getLength(); i++){
                Node druhy = getFirst_ElementNode(porovnavany_deti, i);
                i = index+1;
                Node prvni = getFirst_ElementNode(vzor_Deti, j);
                j = index+1;
                if(prvni == null && druhy == null) return "OK";
                String compare = compare(prvni, druhy);
                if(!compare.equals("OK")) return compare;
            }
        }
        
        return "OK";
    }
    
    
//metody do value gettru    
    private static Node getFirst_ElementNode(NodeList nodeList, int startIndex){
        if(nodeList == null) return null;
        if(startIndex >= nodeList.getLength()) return null;
        for(int i = startIndex; i < nodeList.getLength(); i++){
            Node n = nodeList.item(i);
            if(n.getNodeType() == ELEMENT_NODE){
                String g = n.getNodeName();
                index = i;
                return n;
            }
            else{
                int f = 0;
            }
        }
        
        return null;
    }
    
    private static boolean hasChildNodes(Node node){
        if(node == null) return false;
        if(node.hasChildNodes()){
            NodeList list = node.getChildNodes();
            for(int i = 0; i < list.getLength(); i++){
                if(list.item(i).getNodeType() == ELEMENT_NODE) return true;
            }
        }
        return false;
    }
}
