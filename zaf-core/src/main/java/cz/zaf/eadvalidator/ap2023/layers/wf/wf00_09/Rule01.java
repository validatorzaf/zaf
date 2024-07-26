package cz.zaf.eadvalidator.ap2023.layers.wf.wf00_09;

import java.net.MalformedURLException;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;

public class Rule01 extends EadRule {

    static final public String CODE = "wf1";

    public Rule01() {
        super(CODE,
                "Soubor je well-formed.",
                "Kontrolovaný soubor nedodržuje syntaxi jazyka XML.",
                "Část 1.1.3 profilu EAD3 MV ČR");
    }

    @Override
    protected void evalImpl() {
        Path eadPath = ctx.getLoader().getFilePath();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        // the "parse" method also validates XML, will throw an exception if
        // misformatted
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            // testovaci rozparsovani
            builder.parse(eadPath.toFile());

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
