/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.helper;

/**
 *
 * @author m000xz006159
 */
public class HelperCesty {
    public static String getCestaNastaveni(){
       String s = System.getProperty("user.dir");
       String cesta = s + "\\Nastaveni.ini";
       return cesta;
    }
}
