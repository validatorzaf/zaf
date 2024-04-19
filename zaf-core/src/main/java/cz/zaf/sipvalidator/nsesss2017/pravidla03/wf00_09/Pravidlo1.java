package cz.zaf.sipvalidator.nsesss2017.pravidla03.wf00_09;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla03.WfCheckRuleBase;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.SipInfo;

// Soubor obsahuje právě jeden kořenový element <mets:mets>.
public class Pravidlo1 extends WfCheckRuleBase {

    static final public String KOD = "wf1";

    public Pravidlo1() {
        super(KOD,
        		"Soubor je well-formed.",
                "Datový balíček SIP nedodržuje syntaxi jazyka XML.",
                "Požadavek 11.2.2 NSESSS.");
    }

    @Override
    protected void kontrola() {
        SipInfo file = ctx.getContext().getSip();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        // the "parse" method also validates XML, will throw an exception if
        // misformatted
        boolean stav = false;
        String detailChyby = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File f = file.getCestaMets().toFile(); // kvůli diakritice aby pak použil file a ne

            // testovaci rozparsovani
            builder.parse(f);

            stav = true;
        } catch (MalformedURLException ex) {
            // Logger.getLogger(K03_Spravnosti.class.getName()).log(Level.SEVERE, null, ex);
            detailChyby = "Soubor nezpracován. " + ex.getLocalizedMessage()
                + ". Nepovolené znaky v názvu souboru, nebo na cestě k souboru.";
        } catch (SAXParseException exception) {
            detailChyby = exception.getLocalizedMessage();
        } catch (SAXException e) {
            detailChyby = e.getLocalizedMessage();
        } catch (IOException e) {
            detailChyby = e.getLocalizedMessage();
        } catch (ParserConfigurationException e) {
            detailChyby = e.getLocalizedMessage();
        }
        
        if(!stav) {
        	throw new ZafException(BaseCode.CHYBA, detailChyby);
        }
    }

}
