/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

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

import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;

/**
 *
 * @author m000xz006159
 */
public class K05_ProtiSchematu
	implements UrovenKontroly
{
	static final public String NAME = "kontrola proti schématu XSD"; 
			
	VysledekKontroly k;
    
    public K05_ProtiSchematu() {
    }
    
    private void ValidaceVResource(String resource, SipInfo file) throws MalformedURLException, SAXException, IOException {
        URL schemaFile = K05_ProtiSchematu.class.getResource(resource); // tady musí být šablona (např sip.xsd)
        //URL schemaFile = new File("d:\\5.xsd").toURI().toURL(); //cesta natvrdo 
        File f = file.getCesta_mets().toFile();
        Source xmlFile = new StreamSource(f);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator nasValidator = schema.newValidator();

        ErrorHandlerValidaceXSD handler = new ErrorHandlerValidaceXSD(k);
        nasValidator.setErrorHandler(handler);
        nasValidator.validate(xmlFile);          
    }

	@Override
	public void provedKontrolu(KontrolaContext ctx) throws Exception {
		boolean isFailed = ctx.isFailed();		

        k = new VysledekKontroly(
        		TypUrovenKontroly.PROTI_SCHEMATU,
        		NAME);
        ctx.pridejKontrolu(k);
        if(isFailed) {
        	return;
        }

		SipInfo file = ctx.getSip();
		if (!SIP_MAIN_helper.ma_metsxml(file)) {
			PravidloKontroly p = new PravidloKontroly(0, "val1", false,
					"SIP balíček neobsahoval kontrolovatelný soubor.", 0, "");
			k.add(p);
		} else {
			try {
				ValidaceVResource("/schema/sip2017.xsd", file);
				if (!ErrorHandlerValidaceXSD.nalezenaChyba) {
					PravidloKontroly p = new PravidloKontroly(0, "val1", true, "", 0, "");
					k.add(p);
				}
			} catch (SAXParseException se) {
				// String chyba = "CHYBA! " + "SAX " + "Řádek: " +
				// Integer.toString(se.getLineNumber()) + " Sloupec: " +
				// Integer.toString(se.getColumnNumber()) + " " + se.getLocalizedMessage();
				// SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "val",
				// false, chyba, 0, "");
				// k.setStav(false);
				// k.add(p);
			}
			// po jednom souboru
			catch (IOException e) {
				String chyba = "CHYBA! " + "IO " + e.getLocalizedMessage();
				PravidloKontroly p = new PravidloKontroly(0, "val1", false, chyba, 0, "");
				k.add(p);
			}
		}

	}

	@Override
	public String getNazev() {
		return NAME;
	}
  
}
