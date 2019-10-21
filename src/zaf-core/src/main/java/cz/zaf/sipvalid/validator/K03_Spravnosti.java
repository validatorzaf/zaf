/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

import cz.zaf.sipvalid.sip.KontrolaContext;
import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_helper;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola_pravidlo;
import cz.zaf.sipvalid.sip.TypUrovenKontroly;
import cz.zaf.sipvalid.sip.UrovenKontroly;

/**
 *
 * @author m000xz006159
 */
public class K03_Spravnosti
	implements UrovenKontroly
{
	
	static final public String NAME = "kontrola správnosti xml"; 

    public K03_Spravnosti() {
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

	@Override
	public void provedKontrolu(KontrolaContext ctx) throws Exception {
		boolean isFailed = ctx.isFailed();
        SIP_MAIN_kontrola k = new SIP_MAIN_kontrola(
        		TypUrovenKontroly.SPRAVNOSTI,
        		NAME);
        ctx.pridejKontrolu(k);
		if(isFailed) {
			return;
		}

		SIP_MAIN file = ctx.getSip();
		if (!SIP_MAIN_helper.ma_metsxml(file)) {
			SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "wf1", false,
					"SIP balíček neobsahoval soubor mets.xml.", 0, "");
			k.setStav(false);
			k.add(p);
		} else {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			// the "parse" method also validates XML, will throw an exception if
			// misformatted
			try {
				File f = file.getCesta_mets().toFile(); // kvůli diakritice aby pak použil file a ne
																		// string
				org.w3c.dom.Document document = builder.parse(f);
				SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "wf1", true, "", 0, "");
				k.add(p);
				document = null;

			} catch (MalformedURLException ex) {
				// Logger.getLogger(K03_Spravnosti.class.getName()).log(Level.SEVERE, null, ex);
				chybaMalFormedURL(ex, k);
			} catch (SAXParseException exception) {
				chybaSaxParse(exception, k);
			}

			catch (MalformedByteSequenceException exception) {
				chybaMalFormedByteSequence(exception, k);
			}
		}
		k.setProvedena(true);
	}

	@Override
	public String getNazev() {
		return NAME;
	}
    
   
    
}
