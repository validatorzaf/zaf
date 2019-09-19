/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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
public class K05_ProtiSchematu {
    SIP_MAIN_kontrola k;
    
    public K05_ProtiSchematu(SIP_MAIN file, boolean proved) throws MalformedURLException, SAXException {
        k = new SIP_MAIN_kontrola("kontrola proti schématu XSD", proved);
        if(proved){
            if(!SIP_MAIN_helper.ma_metsxml(file)){
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "val1", false, "SIP balíček neobsahoval kontrolovatelný soubor.", 0, "");
                k.setStav(false);
                k.add(p);
            }
            else{
                try{
                    ValidaceVResource("sip2017.xsd", file); 
                    if(!ErrorHandlerValidaceXSD.nalezenaChyba){
                        SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "val1", true, "", 0, "");
                        k.add(p);
                    }
                } 
                catch (SAXParseException se) {
    //                String chyba = "CHYBA! " + "SAX " + "Řádek: " + Integer.toString(se.getLineNumber()) + " Sloupec: " + Integer.toString(se.getColumnNumber()) + " " + se.getLocalizedMessage();
    //                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "val", false, chyba, 0, "");
    //                k.setStav(false);
    //                k.add(p);
                } 
                // po jednom souboru
                catch(IOException e) {
                    String chyba = "CHYBA! " + "IO " + e.getLocalizedMessage();
                    SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "val1", false, chyba, 0, "");
                    k.setStav(false);
                    k.add(p);
                }
            }
        }
        file.getSeznamKontrol().add(k);
    }
    
    private void ValidaceVResource(String resource, SIP_MAIN file) throws MalformedURLException, SAXException, IOException {
        URL schemaFile = K05_ProtiSchematu.class.getResource(resource); // tady musí být šablona (např sip.xsd)
        //URL schemaFile = new File("d:\\5.xsd").toURI().toURL(); //cesta natvrdo 
        File f = new File(SIP_MAIN_helper.getCesta_mets(file));
        Source xmlFile = new StreamSource(f);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator nasValidator = schema.newValidator();

        ErrorHandlerValidaceXSD handler = new ErrorHandlerValidaceXSD(k);
        nasValidator.setErrorHandler(handler);
        nasValidator.validate(xmlFile);          
    }
  
}
