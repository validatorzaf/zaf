/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;

/**
 * METS parser
 * 
 * provede nacteni METS.xml a pripravi jeho zakladni
 * prehled
 */
public class MetsParser {

    /**
     * Parsed document
     */
    public Document document;

    public Node metsMets, metsDmdSec, metsMdWrap, xmlData, metsHdr;
    public ArrayList<Node> zakladniEntity, identifikatory, nazvy, dokumenty, manipulace, urceneCasoveObdobi,
            plneurcenySpisovyZnak;

    /**
     * Chyba vzniklá při parsování
     */
    public String parserError;

    public MetsParser() {
    }

    /*
    public MetsParser(SipInfo sip) throws ParserConfigurationException, SAXException {
    
    }
    */

    public void parse(SipLoader sipLoader) {
        Path cestaMets = sipLoader.getSip().getCestaMets();
        if(cestaMets==null) {
            parserError = "Není definována cesta k mets.xml";
            return;
        }
        try (InputStream is = Files.newInputStream(cestaMets)) {
            document = PositionalXMLReader.readXML(is);

            // vlastni nacteni dat
            readDocument(sipLoader.getSip());
        } catch (SAXException e) {
            parserError = e.toString();
        } catch (IOException e) {
            parserError = e.toString();
        }

    }

    private void readDocument(SipInfo sipInfo) {
        metsMets = document.getDocumentElement();
        if (!metsMets.getNodeName().equals("mets:mets")) {
            // kontrola pojmenovani korene
            metsMets = null;
        }
        if (metsMets != null) {
            if (ValuesGetter.hasAttribut(metsMets, "OBJID")) {
                // nastaveni id SIP
                sipInfo.setMetsObjId(ValuesGetter.getValueOfAttribut(metsMets, "OBJID"));
            }
        }
        // konec
        metsDmdSec = ValuesGetter.getXChild(metsMets, "mets:dmdSec");
        metsMdWrap = ValuesGetter.getXChild(metsMets, "mets:dmdSec", "mets:mdWrap");
        xmlData = ValuesGetter.getXChild(metsMets, "mets:dmdSec", "mets:mdWrap", "mets:xmlData");
        zakladniEntity = ValuesGetter.getChildList(xmlData, "nsesss:Dokument", "nsesss:Spis", "nsesss:Dil");

        //velké seznamy u velkých souborů
        identifikatory = ValuesGetter.getAllAnywhereArrayList("nsesss:Identifikator", document);
        nazvy = ValuesGetter.getAllAnywhereArrayList("nsesss:Nazev", document);
        dokumenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", document);
        manipulace = ValuesGetter.getAllAnywhereArrayList("nsesss:Manipulace", document);
        urceneCasoveObdobi = ValuesGetter.getAllAnywhereArrayList("nsesss:UrceneCasoveObdobi", document);
        plneurcenySpisovyZnak = ValuesGetter.getAllAnywhereArrayList("nsesss:PlneUrcenySpisovyZnak", document);

        if (zakladniEntity != null && zakladniEntity.isEmpty())
            zakladniEntity = null; // kvůli pravidlům
        if (identifikatory != null && identifikatory.isEmpty())
            identifikatory = null;
        if (nazvy != null && nazvy.isEmpty())
            nazvy = null;
        if (dokumenty != null && dokumenty.isEmpty())
            dokumenty = null;
        if (manipulace != null && manipulace.isEmpty())
            manipulace = null;
        if (urceneCasoveObdobi != null && urceneCasoveObdobi.isEmpty())
            urceneCasoveObdobi = null;
        if (plneurcenySpisovyZnak != null && plneurcenySpisovyZnak.isEmpty())
            plneurcenySpisovyZnak = null;

        metsHdr = ValuesGetter.getXChild(metsMets, "mets:metsHdr");

        zakladniSipInfo(sipInfo);
    }

    public void zakladniSipInfo(SipInfo sip) {
        if (zakladniEntity == null) {
            // chybi zakladni entita...
            return;
        }
        // info se nastavi dle prvni zakladni entity
        Node zaklEntita = zakladniEntity.get(0);
        // 0 dok, 1 spi 2 dil
        String nodeName = zaklEntita.getNodeName();
        switch (nodeName) {
        case "nsesss:Dokument":
            sip.setType(0);
            break;
        case "nsesss:Spis":
            sip.setType(1);
            break;
        case "nsesss:Dil":
            sip.setType(2);
            break;
        }
        Node skZnakMaterskeEntity = ValuesGetter.getXChild(zakladniEntity.get(0),
                                                           "nsesss:EvidencniUdaje",
                                                           "nsesss:Vyrazovani", "nsesss:SkartacniRezim",
                                                           "nsesss:SkartacniZnak");
        if (skZnakMaterskeEntity != null)
            sip.setSKznak(skZnakMaterskeEntity.getTextContent());
        Node skLhutaMaterskeEntity = ValuesGetter.getXChild(zakladniEntity.get(0), "nsesss:EvidencniUdaje",
                                                            "nsesss:Vyrazovani", "nsesss:SkartacniRezim",
                                                            "nsesss:SkartacniLhuta");
        if (skLhutaMaterskeEntity != null)
            sip.setSKLhuta(skLhutaMaterskeEntity.getTextContent());
    }

    public Document getDocument() {
        return document;
    }

    /**
     * Vraci korenovy element metsu
     * 
     * @return
     */
    public Node getMetsRootNode() {
        return metsMets;
    }

    public Node getMetsHdr() {
        return metsHdr;
    }

    public Node getMetsMdWrap() {
        return metsMdWrap;
    }

    public Node getMetsDmdSec() {
        return metsDmdSec;
    }

    public Node getMetsXmlData() {
        return xmlData;
    }

    public List<Node> getZakladniEntity() {
        return zakladniEntity;
    }

    public List<Node> getDokumenty() {
        return dokumenty;
    }

    public List<Node> getNazvy() {
        return nazvy;
    }

    public List<Node> getManipulace() {
        return manipulace;
    }
    public List<Node> getIdentifikatory() {
        return identifikatory;
    }

    public List<Node> getUrceneCasoveObdobi() {
        return urceneCasoveObdobi;
    }

    public List<Node> getPlneurcenySpisovyZnak() {
        return plneurcenySpisovyZnak;
    }
}
