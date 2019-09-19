/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.frames.panels;

/**
 *
 * @author m000xz006159
 */
public class EventListenerJFmainTableSeSipSoubory_object {
    int index;
    String id;
    boolean splneno;

    public EventListenerJFmainTableSeSipSoubory_object(int index, String id, boolean splneno) {
        this.index = index;
        this.id = id;
        this.splneno = splneno;
    }
    
    
    @Override
    public String toString() {
        String pravidlo, okNo;
        if(splneno) okNo = "OK";
        else okNo = "NO";
        
        pravidlo = " " + okNo + " " + id + ". " + "PRAVIDLO";

        return pravidlo;
    }
}
