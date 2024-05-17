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
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Kontrola souladu se schématem XSD
 * 
 */
public class K05_ProtiSchematu
        extends KontrolaBase {
    static final public String NAME = "proti schématu";

    static final public String VAL1 = "val1";
    static final public String VAL1_TEXT = "Soubor je validní proti schématům mets.xsd (v1.11), xlink.xsd (v2), nsesss.xsd (v3), nsesss-TrP.xsd, ess_ns.xsd a dmBaseTypes.xsd (v2.1).";
    static final public String VAL1_ZDROJ = "Požadavek 11.2.5 NSESSS, bod 2.1. přílohy č. 3 NSESSS.";
    static final public String VAL1_POPIS_CHYBY = "Datový balíček SIP není validní proti schématům http://www.loc.gov/standards/mets/mets.xsd, http://www.loc.gov/standards/mets/xlink.xsd, http://www.mvcr.cz/nsesss/v3/nsesss.xsd, http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd, http://www.mvcr.cz/nsesss/v3/ess_ns.xsd a http://www.mvcr.cz/nsesss/v3/dmBaseTypes.xsd.";

    static Map<String, Schema> schemaCache = new HashMap<>();

    public K05_ProtiSchematu() {
    }

    private void validaceVResource(ErrorHandlerValidaceXSD handler, String resource, SipInfo file)
            throws SAXException, IOException {
        Schema schema = schemaCache.get(resource);
        if (schema == null) {
            //URL schemaFile = new File("d:\\5.xsd").toURI().toURL(); //cesta natvrdo 
            URL schemaFile = K05_ProtiSchematu.class.getResource(resource); // tady musí být šablona (např sip.xsd)
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(schemaFile);

            schemaCache.put(resource, schema);
        }

        validaceVResource(handler, schema, file);
    }

    private void validaceVResource(ErrorHandlerValidaceXSD handler, Schema schema, SipInfo file) throws SAXException,
            IOException {
        Validator nasValidator = schema.newValidator();
        try (InputStream is = Files.newInputStream(file.getCestaMets())) {
            Source xmlFile = new StreamSource(is);

            nasValidator.setErrorHandler(handler);
            nasValidator.validate(xmlFile);
        }
    }

    @Override
    public void provedKontrolu() {

        String detailChyby;
        SipInfo file = ctx.getSip();
        try {
            ErrorHandlerValidaceXSD handler = new ErrorHandlerValidaceXSD(vysledekKontroly);
            validaceVResource(handler, "/schema/sip2017.xsd", file);
            if (!handler.getNalezenaChyba()) {
                // vse ok
                return;
            } else {
                // chyby ve validaci -> jiz jsou nahlaseny
                return;
            }
        }
        // po jednom souboru
        catch (IOException e) {
            detailChyby = "CHYBA! " + "IO " + e.getLocalizedMessage();
        } catch (SAXException e) {
            detailChyby = "CHYBA! " + "SAX " + e.getLocalizedMessage();
        }

        String obecnyPopisChyby = VAL1_POPIS_CHYBY;

        ChybaPravidla p = new ChybaPravidlaNsesss2017(VAL1,
                VAL1_TEXT,
                detailChyby,
                obecnyPopisChyby,
                null,
                VAL1_ZDROJ,
                BaseCode.CHYBA,
                null);
        vysledekKontroly.add(p);

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
