/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.w3c.dom.Element;

/**
 *
 */
public class Obj_Node_int {
    private final Element node;
    private final int integer;
    
    public Obj_Node_int(Element node, int integer) {
        this.node = node;
        this.integer = integer;
    }
    
    public Element get_node() {
        return node;
    }
    
    public int get_int(){
        return integer;
    }
    
}
