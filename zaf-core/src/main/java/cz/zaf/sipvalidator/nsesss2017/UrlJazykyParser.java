/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author standa
 */
public class UrlJazykyParser {
    
    static final String URL = "/jazyky.txt";
    List<String> jazyky = new ArrayList<>();
    
    public UrlJazykyParser(){
    }
    
    public void nactiJazykyZUrl() throws IOException {
       
        InputStream res = getClass().getResourceAsStream(URL);
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
