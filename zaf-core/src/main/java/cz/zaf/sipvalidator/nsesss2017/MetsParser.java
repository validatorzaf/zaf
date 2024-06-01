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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cz.zaf.common.xml.DFDocumentWalker;
import cz.zaf.common.xml.NodeAggregator;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.SipType;

/**
 * METS parser
 * 
 * provede nacteni METS.xml a pripravi jeho zakladni
 * prehled
 */
public class MetsParser {
    public static final String PEVNY_KRIZOVY_ODKAZ = "nsesss:KrizovyOdkaz[pevny=ano]";

    /**
     * Parsed document
     */
    protected Document document;

    protected Element metsMets, metsDmdSec, metsMdWrap, xmlData, metsHdr;
    
    /**
     * Seznam zakladnich entit pod elementem xmldata.
     */
    protected List<Element> zakladniEntity;
    
    /**
     * Seznam chybnych zakladnich entit
     * 
     * Obsahuje seznam uzlu s nerozponanou zakladni entitou
     */
    private List<Element> zakladniEntityChybne;

    /**
     * Chyba vzniklá při parsování
     */
    public String parserError;
    
    /**
     * Mapa dotazu na uzly
     */
    Map<String, List<Element>> nodeQueryCache = new HashMap<>();

    public MetsParser() {
    }

    /*
    public MetsParser(SipInfo sip) throws ParserConfigurationException, SAXException {
    
    }
    */

    public void parse(SipInfo sipInfo) {
        Path cestaMets = sipInfo.getCestaMets();
        if(cestaMets==null) {
            parserError = "Není definována cesta k mets.xml";
            return;
        }
        try (InputStream is = Files.newInputStream(cestaMets)) {
            PositionalXMLReader xmlReader = new PositionalXMLReader();
            document = xmlReader.readXML(is);

            // vlastni nacteni dat
            readDocument(sipInfo);
        } catch (SAXException e) {
            parserError = e.toString();
        } catch (IOException e) {
            parserError = e.toString();
        }

    }

    /**
     * Nacteni zakladnich elementu z metsu
     * 
     * @param sipInfo
     */
    private void readDocument(SipInfo sipInfo) {
        Element rootElement = document.getDocumentElement();
        if (rootElement == null || !"mets:mets".equals(rootElement.getNodeName())) {
            return;
        }
        metsMets = rootElement;

        String objid = ValuesGetter.getValueOfAttribut(metsMets, "OBJID");
        if (objid != null) {
            // nastaveni id SIP
            sipInfo.setMetsObjId(objid);
        }
        metsHdr = ValuesGetter.findFirstChild(metsMets, "mets:metsHdr");

        metsDmdSec = ValuesGetter.findFirstChild(metsMets, "mets:dmdSec");
        if (metsDmdSec != null) {
            metsMdWrap = ValuesGetter.findFirstChild(metsDmdSec, "mets:mdWrap");
            if (metsMdWrap != null) {
                xmlData = ValuesGetter.findFirstChild(metsMdWrap, "mets:xmlData");
            }
        }

        readNsesssData(sipInfo);                        
        // konec        
    }

    private void readNsesssData(SipInfo sipInfo) {
        zakladniEntity = new ArrayList<>();
        zakladniEntityChybne = new ArrayList<>();

        // priprava seznamů uzlu
        DFDocumentWalker dw = new DFDocumentWalker();

        dw.addAggregator(new NamedNodeAggregator(MetsElements.AGENT, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.AMD_SEC, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.DIGIPROV_MD, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.DIV, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FILE, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FILE_GRP, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FLOCAT, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FPTR, nodeQueryCache));        
        
        if(xmlData!=null) {
            NodeList zakladniEntityNodes = xmlData.getChildNodes();
            SipType sipType = null;
            for (int i = 0; i < zakladniEntityNodes.getLength(); i++) {
                Node node = zakladniEntityNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element elem = (Element) node;
                switch (node.getNodeName()) {
                case NsesssV3.DOKUMENT:
                    zakladniEntity.add(elem);
                    if (sipType == null) {
                        sipType = SipType.DOKUMENT;
                    }
                    break;
                case NsesssV3.SPIS:
                    zakladniEntity.add(elem);
                    if (sipType == null) {
                        sipType = SipType.SPIS;
                    }
                    break;
                case NsesssV3.DIL:
                    zakladniEntity.add(elem);
                    if (sipType == SipType.DIL) {
                        sipType = SipType.DIL;
                    }
                    break;
                default:
                    zakladniEntityChybne.add(elem);
                }
            }
            sipInfo.setType(sipType);

            dw.addAggregator(new NamedNodeAggregator(NsesssV3.IDENTIFIKATOR, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.SPISOVY_PLAN, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.VECNA_SKUPINA, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.TYPOVY_SPIS, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.DIL, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.SPIS, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.DOKUMENT, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.KOMPONENTA, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.SOUCAST, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.CAS_OVERENI, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.CAS_POUZITI, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.POSUZOVANY_OKAMZIK, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.PLATNOST, nodeQueryCache));        
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.NAZEV, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.VYRIZENI, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.VYRIZENI_UZAVRENI, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.SKARTACNI_RIZENI, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.JAZYK, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.MANIPULACE, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.URCENE_CASOVE_OBDOBI, nodeQueryCache));
            dw.addAggregator(new NamedNodeAggregator(NsesssV3.PLNE_URCENY_SPISOVY_ZNAK, nodeQueryCache));
            // pevne krizove odkazy
            dw.addAggregator(new NodeAggregator() {
                List<Element> nodes = new ArrayList<>();

                @Override
                public void visitNode(Node node) {
                    if (node.getNodeType() != Node.ELEMENT_NODE) {
                        return;
                    }
                    Element elem = (Element) node;
                    if (NsesssV3.KRIZOVY_ODKAZ.equals(elem.getNodeName())) {
                        if (ValuesGetter.hasAttributValue(node, "pevny", "ano")) {
                            nodes.add(elem);
                        }
                    }

                }

                @Override
                public void onFinished() {
                    nodeQueryCache.put(PEVNY_KRIZOVY_ODKAZ, nodes);                
                }
                
            });
        }

        dw.walk(document);
                
        if (zakladniEntity != null && zakladniEntity.isEmpty())
            zakladniEntity = null; // kvůli pravidlům
    }

    public Document getDocument() {
        return document;
    }

    /**
     * Vraci korenovy element metsu
     * 
     * Po kontrole schématu vrací platný uzel.
     * 
     * @return kořenový uzel
     */
    public Element getMetsRootNode() {
        return metsMets;
    }

    public Element getMetsHdr() {
        return metsHdr;
    }

    public Element getMetsMdWrap() {
        return metsMdWrap;
    }

    public Element getMetsDmdSec() {
        return metsDmdSec;
    }

    public Element getMetsXmlData() {
        return xmlData;
    }

    public List<Element> getZakladniEntity() {
        return zakladniEntity;
    }

    public List<Element> getZakladniEntityChybne() {
        return zakladniEntityChybne;
    }
    
    public List<Element> getNodes(String nodeName) {
        List<Element> nodes = this.nodeQueryCache.get(nodeName);
        if(nodes==null) {
            throw new RuntimeException("Element pro vyhodnocení pravidla není načten z důvodu jiné chyby, název elementu: "+nodeName);
        }
        return nodes;
        
    }

    public List<Element> getDokumenty() {
        return getNodes(NsesssV3.DOKUMENT);
    }

    public List<Element> getNazvy() {
        return getNodes(NsesssV3.NAZEV);
    }

    public List<Element> getIdentifikatory() {
        return getNodes(NsesssV3.IDENTIFIKATOR);
    }

    public List<Element> getKrizoveOdkazyPevnyAno() {
        return getNodes(PEVNY_KRIZOVY_ODKAZ);
    }

    public NodeList getElementsByTagName(String name) {
        return document.getElementsByTagName(name);
    }
}
