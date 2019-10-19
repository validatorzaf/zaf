/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.helper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;


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
    
    public static Date getTimeRightNow(){
        long g = System.currentTimeMillis();
        Date d = new Time(g);
        return d;
    }
    
    public static Date getDuration(Date start, Date end){
        long hodina = (60*60*1000);
        return new Time((end.getTime() - start.getTime()) - hodina);
    }
    
    public static Date getDuration(long start, long end){
        return new Time((end - start) - 60*60*1000);
    }
    
    public static boolean isStringNoEmpty(String string){
        int pocitadlo = 0;
        if(!string.isEmpty() && string.length() >= 1){
            for(int i = 0; i < string.length(); i++){
                if(Character.isLetterOrDigit(string.charAt(i))){
                    pocitadlo++;
                    if(pocitadlo > 0) return true;
                }
            }
        }
        boolean bol = pocitadlo > 0;

        return bol;
    }
    // číslo o osmi číslicích, jejichž vážený součet je dělitelný jedenácti beze zbytku
        public static boolean icoCounter(String string){
        if(string.length() != 8) return false;
        int posledniCislice;
        try{
            int cis = Integer.parseInt(string);
            posledniCislice = Character.getNumericValue(string.charAt(string.length()-1));
            
        }
        catch(NumberFormatException e){
            return false;
        }
        int soucetVsech = 0;
        int vaha = 8;
        int vazenySoucetIco = 0;
        // https://phpfashion.com/jak-overit-platne-ic-a-rodne-cislo
        for(int i = 0; i < string.length(); i++){
            int cislo = Character.getNumericValue(string.charAt(i));
            soucetVsech += vaha*cislo;
            if(i == string.length()-2) vazenySoucetIco = soucetVsech;
            vaha--;  
        }
        int zbytek = soucetVsech%11;

        boolean bol = (zbytek == 0 || zbytek == 1 || posledniCislice == 11-zbytek);
        return bol;
    }
        
    
//// Get elapsed time in milliseconds
//long elapsedTimeMillis = System.currentTimeMillis()-start;
//
//// Get elapsed time in seconds
//float elapsedTimeSec = elapsedTimeMillis/1000F;
//
//// Get elapsed time in minutes
//float elapsedTimeMin = elapsedTimeMillis/(60*1000F);
//
//// Get elapsed time in hours
//float elapsedTimeHour = elapsedTimeMillis/(60*60*1000F);
//
//// Get elapsed time in days
//float elapsedTimeDay = elapsedTimeMillis/(24*60*60*1000F);
    public static int sectiDelkyPoli(int pocetPoli, String[]... pole){
        int soucet = 0;
        for(int i = 0; i < pocetPoli; i++){
            int p = pole[i].length;
            soucet += p;
        }
        return soucet;
    }
    
    public static String getPoleJakoString(String[] pole){
        String retezec = "";
        for (String pole1 : pole) {
            retezec += pole1 + "\n";
        }
        return retezec;
    }
    
    public static ArrayList<String> indexyOpacne(ArrayList<String> list){
        ArrayList<String> pomocnyList = new ArrayList<>();
        for(int i = list.size()-1; i < 0; i--){
            String str = list.get(i);
            pomocnyList.add(str);
        }
        list = pomocnyList;
        return list;
    }
}
