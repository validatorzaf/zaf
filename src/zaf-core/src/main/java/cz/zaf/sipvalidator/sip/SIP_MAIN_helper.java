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
        
    public static String getCesta_komponenty(SipInfo sip){
        return sip.getCesta() + File.separator + "komponenty";  
    }
        
    public static boolean maSlozku_komponenty(SipInfo sip){
        File k = new File(getCesta_komponenty(sip));
        return k.exists();
    }
}
