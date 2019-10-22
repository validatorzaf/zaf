/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.w3c.dom.Node;

/**
 *
 * @author m000xz006159
 */
public class Obj_Node_String {
    private final Node node;
    private final String string;
    
    public Obj_Node_String(Node node, String string) {
        this.node = node;
        this.string = string;
    }
    
    public Node get_node(){
        return node;
    }
    
    public String get_string(){
        return string;
    }
    
}
