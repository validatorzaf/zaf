/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author m000xz006159
 */
public class HelperTime {
    
    public static String getUtc() {
//        Date currentTime = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssXXX");
//        sdf.setTimeZone(timeZone);
        
        return sdf.format(calendar.getTime());
//        return sdf.format(currentTime);
    }
    
    public static String getHodiny(long lDate){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(lDate);
        return localDateFormat.format(date); 
    }
    
    public static String getCasRozmezi(long zacatek, long konec){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(konec - zacatek);
        return localDateFormat.format(date);
    }
    
    public static String getDatum(long cas){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(cas);
        return format.format(date);
    }
    
    public static String getActualDateString(){
        Date date = new Date();
        com.ibm.icu.text.SimpleDateFormat dt1 = new com.ibm.icu.text.SimpleDateFormat("yyyyMMdd_HHmmss");       
        String format = dt1.format(date); 
        return format;
    }
}
