/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author standa
 */
public class UrlJazykyParser {
    
    String url;
    ArrayList<String> jazyky;
    
    public UrlJazykyParser(){
        this.url = "jazyky.txt";
        jazyky = new ArrayList<>();
    }
    
    public void NactiJazykyZUrl() throws IOException{
       
        InputStream res = getClass().getResourceAsStream("/sipvalid/validator/jazyky.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(res))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String radka = line;
                int indexPrvnihoSvislitka = radka.indexOf('|');
                String prvniJazykNaRadce = radka.substring(0, indexPrvnihoSvislitka);
                
                jazyky.add(prvniJazykNaRadce);  
            }
        }

    }
    
    public boolean jeObsazenJazyk(String jazyk)
    {
        boolean jeObsazeno = jazyky.contains(jazyk);        
        return jeObsazeno;
    }
}
