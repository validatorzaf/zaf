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
 */
public class CompareNodes {
    
    /**
     * Compare two nodes and its subnodes
     * @param leftNode
     * @param rightNode
     * @return Return null if nodes are same. 
     *         Return description of difference if nodes are different.
     */
    public static String compare(Node leftNode, Node rightNode){
        //JEDNÁ SE O TEN SAMÝ UZEL
        if(leftNode.isSameNode(rightNode)) {
            return null;
        }
        
        //KDYŽ RYCHLÉ POROVNÁNÍ NEPROJDE MUSÍM PO ŘÁDCÍCH - ABYCH MOHL VYPSAT KONKRÉTNÍ CHYBU
        //NODE NAME
        String leftName = leftNode.getNodeName();
        String rightName = rightNode.getNodeName();
        if (!Objects.equals(leftName, rightName)) {
            return "Jména uzlů se neshodují: " + leftName + " - " + rightName + ".";
        }
        //NODE TYPE
        short leftType = leftNode.getNodeType();
        short rightType = rightNode.getNodeType();
        if(leftType != rightType) {
            return "Různé typy uzlů. " + leftName + " (" + leftType + ") a " + rightName + " (" + rightType + ").";
        }
        //TEXT CONTENT
        if(!hasChildNodes(leftNode) && !hasChildNodes(rightNode)){
            String leftText = leftNode.getTextContent().trim();
            String rightText = rightNode.getTextContent().trim();
            if(!Objects.equals(leftText, rightText)) {
                return "Hodnoty uzlů se neshodují: " + leftName + ": " + leftText + " - " + rightName + ": " + rightText + ".";
            }
        }
        //ATRIBUTY
        String result = compareAttributes(leftNode, rightNode);
        if(result==null) {
            //CHILDREN
            result = checkChildren(leftNode, rightNode);
        }
        
        return result;
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
        return null;
    }
    
    private static class ElementIterator {
        final NodeList nodes;
        int position = 0;
        
        ElementIterator(final NodeList nodes) {
            this.nodes = nodes;
        }
        
        Node next() {
            while(position<nodes.getLength()) {
                Node node = nodes.item(position);
                position++;
                if(node.getNodeType() == ELEMENT_NODE) {
                    return node;
                }
            }
            return null;
        }
    };
    
    // musím vynechat komentáře != ELEMENT_NODE
    private static String checkChildren(Node leftNode, Node rightNode){
        NodeList leftNodes = leftNode.getChildNodes();
        NodeList rightNodes = rightNode.getChildNodes();
        
        ElementIterator leftIter = new ElementIterator(leftNodes);
        ElementIterator rightIter = new ElementIterator(rightNodes);
        
        Node l = leftIter.next(),
                r = rightIter.next();
        
        while (l != null && r != null) {
            String result = compare(l, r);
            if(result!=null) {
                return result;
            }
            
            l = leftIter.next();
            r = rightIter.next();
        }

        if(l != null || r != null) {
            return "Chybí element: "+ ((l!=null)?l.getNodeName():r.getNodeName());
        }
        return null;
    }
        
    private static boolean hasChildNodes(Node node){
        if(node == null) {
            return false;
        }
        if(node.hasChildNodes()){
            NodeList list = node.getChildNodes();
            for(int i = 0; i < list.getLength(); i++){
                if(list.item(i).getNodeType() == ELEMENT_NODE) {
                    return true;
                }
            }
        }
        return false;
    }
}
