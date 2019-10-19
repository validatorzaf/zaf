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
public class StructMap_Obj_amdSec {
    String id, identifikator, zdroj;
    Node node;

    public StructMap_Obj_amdSec(String id, String identifikator, String zdroj, Node node) {
        this.id = id;
        this.identifikator = identifikator;
        this.zdroj = zdroj;
        this.node = node;
    }
}
