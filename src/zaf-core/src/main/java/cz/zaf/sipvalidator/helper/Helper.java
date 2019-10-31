/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.helper;

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

}
