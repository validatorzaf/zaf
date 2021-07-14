/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.helper;

import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

/**
 *
 * @author m000xz006159
 */
public class Helper {
    
    public static boolean obsahujePoleHodnotu(String[] pole, String hodnota){
        for(String hodnotaPole : pole){
            if(hodnota.equals(hodnotaPole)) return true;
        }
        return false;
    }

    public static String getActualDateString(){
        Date date = new Date();
        com.ibm.icu.text.SimpleDateFormat dt1 = new com.ibm.icu.text.SimpleDateFormat("yyyyMMdd_HHmmss");       
        String format = dt1.format(date); 
        return format;
    }

}
