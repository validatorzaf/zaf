/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import static org.w3c.dom.Node.ELEMENT_NODE;
import org.w3c.dom.NodeList;

/**
 *
 * @author Orkscream
 */
public class CompareNodes {
    private static int index; //kvůli vyhledávání elementů 
    
    public static String compare(Node node, Node node_compared){

        if(node == null || node_compared == null){
            if(node != null){
                return "Element " + node.getNodeName() + " - nebyl nalezen u druhé entity/objektu.";
            }
            if(node_compared != null){
                return "Element " + node_compared.getNodeName() + " - nebyl nalezen u první entity/objektu.";
            }
            return "Uzly nenalezeny.";
        }
        //JEDNÁ SE O TEN SAMÝ UZEL
        if(node.isSameNode(node_compared)) return "OK";
        //RYCHLÉ SROVNÁNÍ - KDYŽ BUDOU V POŘÁDKU
        if(node.isEqualNode(node_compared)) return "OK";
        
        //KDYŽ RYCHLÉ POROVNÁNÍ NEPROJDE MUSÍM PO ŘÁDCÍCH - ABYCH MOHL VYPSAT KONKRÉTNÍ CHYBU
            //NODE NAME
        String prvni_nodeName = node.getNodeName();
        String druhy_nodeName = node_compared.getNodeName();
        if (!prvni_nodeName.equals(druhy_nodeName)) return "Jména uzlů se neshodují: " + prvni_nodeName + " - " + druhy_nodeName + ".";
            //NODE TYPE
        short prvni_nodeType = node.getNodeType();
        short druhy_nodeType = node_compared.getNodeType();
        if(prvni_nodeType != druhy_nodeType) return "Různé typy uzlů. " + node.getNodeName() + " - " + node.getNodeType() + ". " + node_compared.getNodeName() + " - " + node_compared.getNodeType() + ".";
            //TEXT CONTENT
        if(!hasChildren_ElementNode(node) && !hasChildren_ElementNode(node_compared)){
            String prvni_nodeText = node.getTextContent().trim();
            String druhy_nodeText = node_compared.getTextContent().trim();
            if(prvni_nodeText == null ? druhy_nodeText == null : !prvni_nodeText.equals(druhy_nodeText)){
                return "Hodnoty uzlů se neshodují: " + node.getNodeName() + ": " + prvni_nodeText + " - " + node_compared.getNodeName() + ": " + druhy_nodeText + ".";
            }
        }    
            //ATRIBUTY
        String atributy_vysledek = check_attributes(node, node_compared);
        if(!atributy_vysledek.equals("OK")) return atributy_vysledek;
            //CHILDREN
        String children_vysledek = check_children(node, node_compared);
        if(!children_vysledek.equals("OK")) return children_vysledek;
        
        return "OK";
    }
    
    private static String check_attributes(Node vzor, Node porovnavany){
        NamedNodeMap expectedAttrs = vzor.getAttributes();
        NamedNodeMap actualAttrs = porovnavany.getAttributes();
        if (check_attributes_countNonNamespaceAttribures(expectedAttrs) != check_attributes_countNonNamespaceAttribures(actualAttrs)) return "Rozdílný počet atributů: " + vzor.getNodeName() + " " + porovnavany.getNodeName();
        if(expectedAttrs != null){
            for (int i = 0; i < expectedAttrs.getLength(); i++) {
                Attr expectedAttr = (Attr) expectedAttrs.item(i);
                if (expectedAttr.getName().startsWith("xmlns") || expectedAttr.getName().equals("ID")){
                  continue;
                }
                Attr actualAttr;
                if (expectedAttr.getNamespaceURI() == null) {
                    actualAttr = (Attr) actualAttrs.getNamedItem(expectedAttr.getName());
                } else {
                    actualAttr = (Attr) actualAttrs.getNamedItemNS(expectedAttr.getNamespaceURI(),
                    expectedAttr.getLocalName());
                }
                if(actualAttr == null) return "Nenalezen atribut " + expectedAttr + " elementu " + porovnavany.getNodeName(); 
                if(!actualAttr.getValue().equals(expectedAttr.getValue())) return "Atributy elementu " + porovnavany.getNodeName() + " se neshodují: " + expectedAttr.getValue() + " " + actualAttr.getValue();
            }
        }
        return "OK";
    }
    
    private static int check_attributes_countNonNamespaceAttribures(NamedNodeMap attrs) {
        int n = 0;
        if(attrs == null) return 0;
        for (int i = 0; i < attrs.getLength(); i++) {
          Attr attr = (Attr) attrs.item(i);
          if (!attr.getName().startsWith("xmlns")) {
            n++;
          }
        }
        return n;
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
    
    private static boolean hasChildren_ElementNode(Node node){
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
