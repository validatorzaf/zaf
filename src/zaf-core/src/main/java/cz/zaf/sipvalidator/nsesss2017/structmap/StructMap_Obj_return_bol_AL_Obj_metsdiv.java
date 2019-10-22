/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017.structmap;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author m000xz006159
 */
public class StructMap_Obj_return_bol_AL_Obj_metsdiv {
    boolean bol;
    ArrayList<StructMap_Obj_metsdiv> list;

    public StructMap_Obj_return_bol_AL_Obj_metsdiv(boolean bol, ArrayList<StructMap_Obj_metsdiv> list) {
        this.bol = bol;
        this.list = list;
    }
    
    public List<StructMap_Obj_metsdiv> getList() {
        return list;
    }

    public boolean getBol() {
        return bol;
    }
}
