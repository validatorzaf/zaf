/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017.structmap;

import java.util.List;

import org.w3c.dom.Node;

/**
 *
 * @author m000xz006159
 */
public class StructMap_Obj_return_bol_AL_node {
    boolean bol;
    List<Node> node_list;

    public StructMap_Obj_return_bol_AL_node(boolean bol, List<Node> node_list) {
        this.bol = bol;
        this.node_list = node_list;
    }

    public boolean getBol() {
        return bol;
    }

    public List<Node> getList() {
        return node_list;
    }
    
}