package cz.zaf.sipvalidator.nsesss2024.pravidla03.wf00_09;

import java.io.File;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;
import cz.zaf.sipvalidator.sip.SipInfo;

// Soubor obsahuje právě jeden kořenový element <mets:mets>.
public class Pravidlo1 extends PravidloBase {

    static final public String KOD = "wf1";

    public Pravidlo1() {
        super(KOD,
        		"Soubor je well-formed.",
                "Datový balíček SIP nedodržuje syntaxi jazyka XML.",
                "Požadavek 9.2.5 NSESSS.");
    }

    @Override
    protected void kontrola() {
        SipInfo file = ctx.getContext().getSip();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        // the "parse" method also validates XML, will throw an exception if
        // misformatted
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File f = file.getCestaMets().toFile(); // kvůli diakritice aby pak použil file a ne

            // testovaci rozparsovani
            builder.parse(f);

        } catch (MalformedURLException ex) {
            // Logger.getLogger(K03_Spravnosti.class.getName()).log(Level.SEVERE, null, ex);
            String detailChyby = "Soubor nezpracován. " + ex.getLocalizedMessage()
                + ". Nepovolené znaky v názvu souboru, nebo na cestě k souboru.";
            throw new ZafException(BaseCode.CHYBA, detailChyby);
        } catch (Exception e) // SAXParseException, SAXException, IOException, ParserConfiguration 
        { 
            throw new ZafException(BaseCode.CHYBA, e.getLocalizedMessage());
        }
    }

}
