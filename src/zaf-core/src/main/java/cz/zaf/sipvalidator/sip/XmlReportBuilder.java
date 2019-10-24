/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.sip;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import cz.zaf.sipvalidator.helper.HelperTime;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;

/**
 * Třída pro sestavení XML výstupu
 * 
 */
public class XmlReportBuilder {

    private org.w3c.dom.Document document;
    private Element rootElement;
    public File xml;
	
	/**
	 * Pouzity seznam pro obsahovou kontrolu
	 */
	private int[] seznamObsKontroly;
	private String programName;
	private String programVersion;
    private String kontrolaID;
    private String pouzitiKontroly;

    public XmlReportBuilder(String kontrolaID,
                            String programName,
                            String programVersion,
                            String pouzitiKontroly) throws ParserConfigurationException {
        this.kontrolaID = kontrolaID;
        this.programName = programName;
        this.programVersion = programVersion;
        this.pouzitiKontroly = pouzitiKontroly;

        createRoot();
    }

    private void createRoot() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        //
        //        // root elements
        document = docBuilder.newDocument();
        rootElement = document.createElement("kontrolaSIP");
        document.appendChild(rootElement);
        
        String datumKontroly = HelperTime.get_utc();
        if(kontrolaID!=null) {
            rootElement.setAttribute("kontrolaID", kontrolaID);
        }
        rootElement.setAttribute("datumKontroly", datumKontroly);
        if(pouzitiKontroly!=null) {
            rootElement.setAttribute("pouzitiKontroly", pouzitiKontroly);
        }
        if(programName!=null) {
            rootElement.setAttribute("nazevAplikace", programName);
        }
        if(programVersion!=null) {
            rootElement.setAttribute("verzeAplikace", programVersion);
        }
        rootElement.setAttribute("xsi:schemaLocation",
                "http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v1 http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v1/kontrolasip.xsd");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xmlns", "http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v1");
        
        
    }

    public XmlReportBuilder(List<SipInfo> seznamNahranychSouboru, String path_for_xml,
                            String sip_name,
                            ProfilValidace profilValidace,
                            String id_kontroly_zadane,
			String programName,
			String programVersion)
			throws IOException, ParserConfigurationException, TransformerException, SAXException {

		if (path_for_xml==null||path_for_xml.equals("undefined") || path_for_xml.equals("")) {
			xml = create_xml(new File(".").getCanonicalPath() + File.separator + sip_name + ".xml");

		} else {
			if (path_for_xml.endsWith(File.separator)) {
				xml = create_xml(path_for_xml + sip_name + ".xml");
			} else {
				xml = create_xml(path_for_xml + File.separator + sip_name + ".xml");
			}

		}

        Parse(xml);
		for (int i = 0; i < seznamNahranychSouboru.size(); i++) {
            Node node_sip = addSipNode(seznamNahranychSouboru.get(i));

		}
	}

	private void Parse(File xml) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		try {
            this.document = builder.parse(xml);

		} catch (SAXException | IOException ex) {
			Logger.getLogger(XmlReportBuilder.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
    public Element addKontrolaNode(VysledekKontroly kontrola, Node parent)
	{
        Element elem = document.createElement("typ");
		
		elem.setAttribute("nazev", kontrola.getKontrola_nazev());
		elem.setAttribute("stav", kontrola.getStavKontroly().toString());
		
		parent.appendChild(elem);
		return elem;
		
	}

    private Element addNodePravidlo(Node parent, String id, String text, String chyba, String popisChyby,
			String mistoChyby, String zdroj, boolean stav) {
        Element pravidloElem = document.createElement("pravidlo");

		pravidloElem.setAttribute("id", id.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		pravidloElem.setAttribute("text",
				text.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		if (StringUtils.isNotEmpty(chyba)) {
			pravidloElem.setAttribute("vypisChyby",
					chyba.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		}
		if (StringUtils.isNotEmpty(popisChyby)) {
			pravidloElem.setAttribute("popisChyby",
					popisChyby.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		}
		if (StringUtils.isNotEmpty(mistoChyby)) {
			pravidloElem.setAttribute("mistoChyby",
					mistoChyby.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		}
		if (StringUtils.isNotEmpty(zdroj)) {
			pravidloElem.setAttribute("zdroj",
					zdroj.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		}
		pravidloElem.setAttribute("stav",
				String.valueOf(stav).replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));

        parent.appendChild(pravidloElem);
        return pravidloElem;
	}

    /*
    private void add_node_info() {
        Node info = rootElement.appendChild(document.createElement("info"));
    	((Element) info).setAttribute("pocetChyb", "0");
    	((Element) info).setAttribute("dobaKontroly", "hh:mm:ss");
    	((Element) info).setAttribute("dalsi", "další podobné informace");
    }*/

    public Element addSipNode(SipInfo sip) {
        Element elemNodeSip = document.createElement("sip");
        writeSipInfo(elemNodeSip, sip);
        rootElement.appendChild(elemNodeSip);

        List<VysledekKontroly> kontroly = sip.getSeznamKontrol();
        for (VysledekKontroly vysl : kontroly) {
            addVysledek(elemNodeSip, vysl);
        }
        return elemNodeSip;
	}

    private Element addVysledek(Element elemNodeSip, VysledekKontroly vysl) {
        Element elemKontrola = addKontrolaNode(vysl, elemNodeSip);
        // pravidla se zapisi jen pokud kontrola byla provedena
        if (vysl.getStavKontroly() != StavKontroly.NESPUSTENA) {
            List<PravidloKontroly> pravidla = vysl.getPravidla();
            for (PravidloKontroly pravidlo : pravidla) {
                addPravidlo(elemKontrola, pravidlo);
            }
        }
        return elemKontrola;
    }

    private Element addPravidlo(Element elemKontrola, PravidloKontroly pravidlo) {
        String id = pravidlo.getId();
        String text = pravidlo.getTextPravidla();
        String chyba = pravidlo.getVypis_chyby();
        String popisChyby = pravidlo.getPopisChybyObecny();
        String mistoChyby = pravidlo.getMisto_chyby();
        String zdrojChyby = pravidlo.getZdroj();
        boolean stav = pravidlo.getStav();
        Validate.notEmpty(id);
        Validate.notEmpty(text);

        return addNodePravidlo(elemKontrola, id, text,
                                 chyba, popisChyby, mistoChyby, zdrojChyby, stav);

    }

    private void writeSipInfo(Element elemNodeSip, SipInfo sip) {
        String metsObjId = sip.getMetsObjId();
        if (StringUtils.isNotEmpty(metsObjId)) {
            elemNodeSip.setAttribute("sipID", metsObjId);
        }

        String g = sip.getNameZip();
        if (g == null) {
            g = sip.getName();
        }
        elemNodeSip.setAttribute("nazevSouboru", g);
    }

	private File create_xml(String cesta) {
		File file = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//
//            // root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("kontrolaSIP");
			doc.appendChild(rootElement);
//            rootElement.appendChild(doc.createElement("x"));
//            rootElement.setTextContent("");

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file = new File(cesta));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException pce) {
		}
		return file;
	}

    public void save(Path outputPath) throws Exception {
        if (outputPath == null) {
            Result output = new StreamResult(System.out);
            saveTransformed(output);

        } else {
            try (OutputStream os = Files.newOutputStream(outputPath)) {
                Result output = new StreamResult(os);
                saveTransformed(output);
            }
        }
        
    }

    private void saveTransformed(Result output) throws TransformerException, TransformerFactoryConfigurationError {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        Source input = new DOMSource(document);
        document.setXmlStandalone(true); // když je false tak se s tím pak nedá hýbat a je to default (standalone s
                                          // eezobrazí vždy).
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("http://www.oracle.com/xml/is-standalone", "yes"); // dává novou řádku po
                                                                                        // deklaraci - takže root je
                                                                                        // na nové řádce
                                                                                        //        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        // odsazení
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(input, output);

    }
}
