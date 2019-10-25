/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Kontrola oproti schematu
 * 
 */
public class K05_ProtiSchematu
        extends KontrolaBase {
    static final public String NAME = "kontrola proti schématu XSD";

    static final public String VAL1 = "val1";

    public K05_ProtiSchematu() {
    }

    private void validaceVResource(ErrorHandlerValidaceXSD handler, String resource, SipInfo file) throws SAXException,
            IOException {
        URL schemaFile = K05_ProtiSchematu.class.getResource(resource); // tady musí být šablona (např sip.xsd)
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator nasValidator = schema.newValidator();
        //URL schemaFile = new File("d:\\5.xsd").toURI().toURL(); //cesta natvrdo 
        try (InputStream is = Files.newInputStream(file.getCestaMets())) {
            Source xmlFile = new StreamSource(is);

            nasValidator.setErrorHandler(handler);
            nasValidator.validate(xmlFile);
        }
    }

    @Override
    public void provedKontrolu() {

        boolean stav = false;
        String detailChyby = null;
        boolean addPravidlo = true;

        SipInfo file = ctx.getSip();
        try {
            ErrorHandlerValidaceXSD handler = new ErrorHandlerValidaceXSD(vysledekKontroly);
            validaceVResource(handler, "/schema/sip2017.xsd", file);
            if (!handler.getNalezenaChyba()) {
                stav = true;
            } else {
                // chyby ve validaci -> jiz jsou nahlaseny
                addPravidlo = false;
            }
        }
        // po jednom souboru
        catch (IOException e) {
            detailChyby = "CHYBA! " + "IO " + e.getLocalizedMessage();
        } catch (SAXException e) {
            detailChyby = "CHYBA! " + "SAX " + e.getLocalizedMessage();
        }

        String obecnyPopisChyby = null;
        if (!stav) {
            obecnyPopisChyby = "Datový balíček SIP není validní proti schématům http://www.loc.gov/standards/mets/mets.xsd, http://www.loc.gov/standards/mets/xlink.xsd, http://www.mvcr.cz/nsesss/v3/nsesss.xsd, http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd, http://www.mvcr.cz/nsesss/v3/ess_ns.xsd a http://www.mvcr.cz/nsesss/v3/dmBaseTypes.xsd.";
        }

        if (addPravidlo) {
            PravidloKontroly p = new PravidloKontroly(VAL1, stav,
                    "Soubor je validní proti schématům mets.xsd (v1.11), xlink.xsd (v2), nsesss.xsd (v3), nsesss-TrP.xsd, ess_ns.xsd a dmBaseTypes.xsd (v2.1).", // text
                    detailChyby,
                    obecnyPopisChyby,
                    null,
                    "Požadavek 11.2.5 NSESSS, bod 2.1. přílohy č. 3 NSESSS." // zdroj
            );
            vysledekKontroly.add(p);
        }

    }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.PROTI_SCHEMATU;
    }
}
