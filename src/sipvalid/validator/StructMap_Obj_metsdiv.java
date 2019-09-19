/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import org.w3c.dom.Node;

/**
 *
 * @author m000xz006159
 */
public class StructMap_Obj_metsdiv {
    Node metsdiv;
    String dmdid, admid, type;
    StructMap_Obj_metsdiv rodic;
    boolean parent_right;

    public StructMap_Obj_metsdiv(Node metsdiv, String dmdid, String admid, String type, boolean getRodice) {
        this.metsdiv = metsdiv;
        this.dmdid = dmdid;
        this.admid = admid;
        this.type = type;
        if(getRodice){
            rodic = set_parent(); // set also parent_right
        }    
    }

    private StructMap_Obj_metsdiv set_parent(){
        Node parent = metsdiv.getParentNode();
        if(parent == null){
            parent_right = false;
            return new StructMap_Obj_metsdiv(parent, "ERR", "ERR", "ERR", false);
        }
        if(type.toLowerCase().equals("spisový plán")){
            if(parent.getNodeName().equals("mets:structMap")){
                parent_right = true;
                return new StructMap_Obj_metsdiv(parent, "MAP", "MAP", "MAP", false);
            }  
            else{
                parent_right = false;
                return new StructMap_Obj_metsdiv(parent, "ERR", "ERR", "ERR", false); 
            }
        }
        else{
            String admid2 = "ERR", dmdid2 = "ERR", type2 = "ERR";
            if(parent.getNodeName().equals("mets:div")){
                parent_right = true;
                if(ValuesGetter.hasAttribut(parent, "ADMID")) admid2 = ValuesGetter.getValueOfAttribut(parent, "ADMID");
                if(ValuesGetter.hasAttribut(parent, "DMDID")) dmdid2 = ValuesGetter.getValueOfAttribut(parent, "DMDID");
                if(ValuesGetter.hasAttribut(parent, "TYPE")) type2 = ValuesGetter.getValueOfAttribut(parent, "TYPE");
                return new StructMap_Obj_metsdiv(parent, dmdid2, admid2, type2, false);
            } 
            else{
                parent_right = false;
                return new StructMap_Obj_metsdiv(parent, "ERR", "ERR", "ERR", false);  
            }
        }
    }
    
    
    
}
