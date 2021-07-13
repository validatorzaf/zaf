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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cz.zaf.common.xml.DFDocumentWalker;
import cz.zaf.common.xml.NodeAggregator;
import cz.zaf.sipvalidator.mets.MetsElements;
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
    public static final String PEVNY_KRIZOVY_ODKAZ = "nsesss:KrizovyOdkaz[pevny=ano]";

    /**
     * Parsed document
     */
    protected Document document;

    protected Node metsMets, metsDmdSec, metsMdWrap, xmlData, metsHdr;
    public ArrayList<Node> plneurcenySpisovyZnak;
    
    /**
     * Seznam zakladnich entit pod elementem xmldata.
     */
    protected List<Node> zakladniEntity;
    
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

        // priprava seznamů uzlu
        DFDocumentWalker dw = new DFDocumentWalker();
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.IDENTIFIKATOR, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.SPISOVY_PLAN, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.VECNA_SKUPINA, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.TYPOVY_SPIS, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.DIL, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.SPIS, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.DOKUMENT, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.KOMPONENTA, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.SOUCAST, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.CAS_OVERENI, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.CAS_POUZITI, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.POSUZOVANY_OKAMZIK, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.PLATNOST, nodeQueryCache));        
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.NAZEV, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.VYRIZENI, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.VYRIZENI_UZAVRENI, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.SKARTACNI_RIZENI, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.JAZYK, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.MANIPULACE, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(JmenaElementu.URCENE_CASOVE_OBDOBI, nodeQueryCache));
        
        dw.addAggregator(new NamedNodeAggregator(MetsElements.AGENT, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.AMD_SEC, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.DIGIPROV_MD, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.DIV, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FILE, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FILE_GRP, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FLOCAT, nodeQueryCache));
        dw.addAggregator(new NamedNodeAggregator(MetsElements.FPTR, nodeQueryCache));        
        // pevne krizove odkazy
        dw.addAggregator(new NodeAggregator() {
            List<Node> nodes = new ArrayList<>();

            @Override
            public void visitNode(Node node) {
                if (JmenaElementu.KRIZOVY_ODKAZ.equals(node.getNodeName())) {
                    if (ValuesGetter.hasAttributValue(node, "pevny", "ano")) {
                        nodes.add(node);
                    }
                }

            }

            @Override
            public void onFinished() {
                nodeQueryCache.put(PEVNY_KRIZOVY_ODKAZ, nodes);                
            }
            
        });
        dw.walk(document);
                
        plneurcenySpisovyZnak = ValuesGetter.getAllAnywhereList("nsesss:PlneUrcenySpisovyZnak", document);

        if (zakladniEntity != null && zakladniEntity.isEmpty())
            zakladniEntity = null; // kvůli pravidlům
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
    
    public List<Node> getNodes(String nodeName) {
        List<Node> nodes = this.nodeQueryCache.get(nodeName);
        Validate.notNull(nodes, "Nodes not in the cache, nodeName: %s", nodeName);
        return nodes;
        
    }

    public List<Node> getDokumenty() {
        return getNodes(JmenaElementu.DOKUMENT);
    }

    public List<Node> getNazvy() {
        return getNodes(JmenaElementu.NAZEV);
    }

    public List<Node> getIdentifikatory() {
        return getNodes(JmenaElementu.IDENTIFIKATOR);
    }

    public List<Node> getPlneurcenySpisovyZnak() {
        return plneurcenySpisovyZnak;
    }

    public List<Node> getKrizoveOdkazyPevnyAno() {
        return getNodes(PEVNY_KRIZOVY_ODKAZ);
    }

    public NodeList getElementsByTagName(String name) {
        return document.getElementsByTagName(name);
    }
}
