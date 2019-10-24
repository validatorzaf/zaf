/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.sip;

import java.io.File;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN_helper {

    
    public static String get_SIP_type_XXX(int index){

        if(index == 0){
            return "DOK";
        }
        if(index == 1){
            return "SPI";
        }
        if(index == 2){
            return "DÍL";
        }
        
        return " - ";
    }
        
    public static String getCesta_komponenty(SipInfo sip){
        return sip.getCesta() + File.separator + "komponenty";  
    }
        
    public static boolean maSlozku_komponenty(SipInfo sip){
        File k = new File(getCesta_komponenty(sip));
        return k.exists();
    }
    
    public static String get_special_ida(int index_pravidla){
        String special_id = Integer.toString(index_pravidla);
//        if(index_pravidla == 21) special_id = "59b";
        if(index_pravidla == 32) special_id = "93a";
        if(index_pravidla == 41) special_id = "54a";
        if(index_pravidla == 42) special_id = "61a";
        if(index_pravidla == 43) special_id = "94a";
        return special_id;
    }
}
