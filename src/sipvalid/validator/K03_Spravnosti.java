/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import sipvalid.sip.SIP_MAIN;
import sipvalid.sip.SIP_MAIN_helper;
import sipvalid.sip.SIP_MAIN_kontrola;
import sipvalid.sip.SIP_MAIN_kontrola_pravidlo;

/**
 *
 * @author m000xz006159
 */
public class K03_Spravnosti {

    public K03_Spravnosti(SIP_MAIN file, boolean proved) throws IOException, ParserConfigurationException, SAXException {
        SIP_MAIN_kontrola k = new SIP_MAIN_kontrola("kontrola správnosti xml", proved);
        if(proved){
            if(!SIP_MAIN_helper.ma_metsxml(file)){
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "wf1", false, "SIP balíček neobsahoval soubor mets.xml.", 0, "");
                k.setStav(false);
                k.add(p);
            }
            else{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false);
                factory.setNamespaceAware(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                // the "parse" method also validates XML, will throw an exception if misformatted
                try { 
                    File f = new File(SIP_MAIN_helper.getCesta_mets(file)); // kvůli diakritice aby pak použil file a ne string
                    org.w3c.dom.Document document = builder.parse(f);
                    SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "wf1", true, "", 0, "");
                    k.add(p);
                    document = null;

                } catch (MalformedURLException ex) {
    //                    Logger.getLogger(K03_Spravnosti.class.getName()).log(Level.SEVERE, null, ex);
                    chybaMalFormedURL(ex, k);
                }
                catch(SAXParseException exception){
                    chybaSaxParse(exception, k);
                }

                catch(MalformedByteSequenceException exception){
                    chybaMalFormedByteSequence(exception, k);
                }
            }
        }
        file.getSeznamKontrol().add(k);
    }
    
    private void chybaMalFormedURL(MalformedURLException exception, SIP_MAIN_kontrola k) throws MalformedURLException {
        String s1 = "Soubor nezpracován. " + exception.getLocalizedMessage() + ". Nepovolené znaky v názvu souboru, nebo na cestě k souboru.";
        SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "wf1", false, s1, 0, "");
        k.add(p);
        k.setStav(false);
    }
    
    private void chybaSaxParse(SAXParseException exception, SIP_MAIN_kontrola k) throws SAXParseException {
        String s1 = exception.getLocalizedMessage();
        SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "wf1", false, s1, 0, "");
        k.add(p);
        k.setStav(false);
        
    }
    
    private void chybaMalFormedByteSequence(MalformedByteSequenceException exception, SIP_MAIN_kontrola k){
        String s1 = exception.getLocalizedMessage();
        SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "wf1", false, s1, 0, "");
        k.add(p);
        k.setStav(false);
    }
    
   
    
}
