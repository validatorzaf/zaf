/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.tika.Tika;

/**
 *
 * @author m000xz006159
 */
public class FileMyme_Checker {
    public static String mimetype = "";
    
    public static String[] getMimeType(File file){
        
        String tika = null, fileUt = null, mimeTypes = null;
        try {
            tika = new Tika().detect(file.toPath());
        } catch (IOException ex) {
            System.out.println("chyba");
        }
        try {
            fileUt = Files.probeContentType(file.toPath());
        } catch (IOException ex) {
            System.out.println("chyba");
        }
        try {
            mimeTypes  = file.toURL().openConnection().getContentType();
        } catch (IOException ex) {
            System.out.println("chyba");
        }
            // mime: content/unknown
            
            //rtf tika: aplic/rtf, fileut ap/msword meme ap/rtf droid text/rtf
            //xml aplic i text ukazuje tika ap
            String[] results = {tika, fileUt, mimeTypes};
            return results;
    }
    
    public static boolean getCompareType(File file, String href){
        String[] results = getMimeType(file);
        boolean vysledek = false;
        for(String res: results){
            if(res != null){
                if(!res.equals(href)){
                    String text = res.replaceFirst("application", "text");
                    if(text.equals(href)){
                        vysledek = true;
                    }
                }
                else{
                    vysledek = true;
                }
            }
        }
        if(!vysledek){
            mimetype = results[0];
        }
        return vysledek;
    }
    
    
}
