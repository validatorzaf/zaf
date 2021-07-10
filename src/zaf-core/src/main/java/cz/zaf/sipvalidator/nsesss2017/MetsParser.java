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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.SipType;
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
    public ArrayList<Node> identifikatory, nazvy, dokumenty, manipulace, urceneCasoveObdobi,
            plneurcenySpisovyZnak;
    
    /**
     * Seznam zakladnich entit pod elementem xmldata.
     */
    public List<Node> zakladniEntity;
    
    /**
     * Seznam chybnych zakladnich entit
     * 
     * Obsahuje seznam uzlu s nerozponanou zakladni entitou
     */
    private List<Node> zakladniEntityChybne;

    /**
     * Chyba vzniklá při parsování
     */
    public String parserError;
    
    /**
     * Mapa dotazu na uzly
     */
    Map<String, List<Node> > nodeQueryCache = new HashMap<>();    

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
            metsHdr = ValuesGetter.findFirstChild(metsMets, "mets:metsHdr");
            
            metsDmdSec = ValuesGetter.findFirstChild(metsMets, "mets:dmdSec");
            if(metsDmdSec!=null) {
                metsMdWrap = ValuesGetter.findFirstChild(metsDmdSec, "mets:mdWrap");
                if(metsMdWrap!=null) {
                    xmlData = ValuesGetter.findFirstChild(metsMdWrap, "mets:xmlData");
                    if(xmlData!=null) {
                        readNsesssData(sipInfo);                        
                    }
                }
            }        
        }
        // konec        
    }

    private void readNsesssData(SipInfo sipInfo) {
        zakladniEntity = new ArrayList<>();
        zakladniEntityChybne = new ArrayList<>();
        NodeList zakladniEntityNodes = xmlData.getChildNodes();
        SipType sipType = null;
        for(int i = 0; i<zakladniEntityNodes.getLength(); i++) {
            Node node = zakladniEntityNodes.item(i);
            if(node.getNodeType()!=Node.ELEMENT_NODE) {
                continue;
            }
            switch(node.getNodeName()) {
            case "nsesss:Dokument":
                zakladniEntity.add(node);
                if(sipType==null) {
                    sipType = SipType.DOKUMENT;
                }
                break;
            case "nsesss:Spis":
                zakladniEntity.add(node);
                if(sipType==null) {
                    sipType = SipType.SPIS;
                }
                break;
            case "nsesss:Dil":
                zakladniEntity.add(node);
                if(sipType==SipType.DIL) {
                    sipType = SipType.DIL;
                }
                break;
            default:
                zakladniEntityChybne.add(node);
            }
        }
        sipInfo.setType(sipType);

        //velké seznamy u velkých souborů
        identifikatory = ValuesGetter.getAllAnywhereList("nsesss:Identifikator", document);
        nazvy = ValuesGetter.getAllAnywhereList("nsesss:Nazev", document);
        dokumenty = ValuesGetter.getAllAnywhereList("nsesss:Dokument", document);
        manipulace = ValuesGetter.getAllAnywhereList("nsesss:Manipulace", document);
        urceneCasoveObdobi = ValuesGetter.getAllAnywhereList("nsesss:UrceneCasoveObdobi", document);
        plneurcenySpisovyZnak = ValuesGetter.getAllAnywhereList("nsesss:PlneUrcenySpisovyZnak", document);

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
        
        zakladniSipInfo(sipInfo);
    }

    public void zakladniSipInfo(SipInfo sip) {
        if (CollectionUtils.isEmpty(zakladniEntity)) {
            // chybi zakladni entita...
            return;
        }
        // info se nastavi dle prvni zakladni entity
        Node zaklEntita = zakladniEntity.get(0);
        Node skZnakMaterskeEntity = ValuesGetter.getXChild(zaklEntita,
                                                           "nsesss:EvidencniUdaje",
                                                           "nsesss:Vyrazovani", "nsesss:SkartacniRezim",
                                                           "nsesss:SkartacniZnak");
        if (skZnakMaterskeEntity != null)
            sip.setSKznak(skZnakMaterskeEntity.getTextContent());
        Node skLhutaMaterskeEntity = ValuesGetter.getXChild(zaklEntita, "nsesss:EvidencniUdaje",
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

    public List<Node> getZakladniEntityChybne() {
        return zakladniEntityChybne;
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

    public List<Node> getKrizoveOdkazyPevnyAno() {
        List<Node> list = nodeQueryCache.get("nsesss:KrizovyOdkaz[pevny=ano]");
        if(list==null) {
            NodeList krizoveOdkazy = document.getElementsByTagName("nsesss:KrizovyOdkaz");
            if(krizoveOdkazy.getLength()==0) {
                list = Collections.emptyList();
            } else {
                list = new ArrayList<>();
                for(int i = 0; i < krizoveOdkazy.getLength(); i++){
                   if(ValuesGetter.hasAttributValue(krizoveOdkazy.item(i), "pevny", "ano")){
                       list.add(krizoveOdkazy.item(i));
                    }
                }                
            }
            nodeQueryCache.put("nsesss:KrizovyOdkaz[pevny=ano]", list);
        }
        return list;
    }

    public NodeList getElementsByTagName(String name) {
        return document.getElementsByTagName(name);
    }
}
