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
public class Obj_Node_int {
    private final Node node;
    private final int integer;
    
    public Obj_Node_int(Node node, int integer) {
        this.node = node;
        this.integer = integer;
    }
    
    public Node get_node(){
        return node;
    }
    
    public int get_int(){
        return integer;
    }
    
}
