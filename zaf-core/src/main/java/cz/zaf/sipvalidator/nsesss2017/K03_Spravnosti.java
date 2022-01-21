/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import cz.zaf.sipvalidator.sip.VysledekPravidla;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 *
 * @author m000xz006159
 */
public class K03_Spravnosti
        extends KontrolaBase {

    static final public String NAME = "kontrola správnosti XML";

    static final public String WF1 = "wf1";

    public K03_Spravnosti() {
    }

    @Override
    public void provedKontrolu() {

        SipInfo file = ctx.getSip();

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
        }
        catch (SAXException e) {
            detailChyby = e.getLocalizedMessage();
        } catch (IOException e) {
            detailChyby = e.getLocalizedMessage();
        } catch (ParserConfigurationException e) {
            detailChyby = e.getLocalizedMessage();
        }
        
        String popisChybyObecny = null;
        if(!stav) {
            popisChybyObecny = "Datový balíček SIP nedodržuje syntaxi jazyka XML."; // popis chyby
        }

        VysledekPravidla p = new VysledekPravidla(WF1, stav,
                "Soubor je well-formed.",
                detailChyby,
                popisChybyObecny,
                null,
                "Požadavek 11.2.2 NSESSS." // zdroj
                );
        vysledekKontroly.add(p);
    }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.SPRAVNOSTI;
    }

}
