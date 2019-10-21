/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.xml_creator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import cz.zaf.sipvalid.helper.HelperTime;
import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_helper;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola;
import cz.zaf.sipvalid.sip.SIP_MAIN_seznam;
import cz.zaf.sipvalid.sip.TypUrovenKontroly;
import cz.zaf.sipvalid.validator.K00_SkodlivehoKodu;

/**
 *
 * @author standa
 */
public class Create_xml {

	private org.w3c.dom.Document xml_parsed;
	private Node kontrolaSip_root;
	public final File xml;
	
	/**
	 * Pouzity seznam pro obsahovou kontrolu
	 */
	private int[] seznamObsKontroly;
	private String programName;
	private String programVersion;

	public Create_xml(List<SIP_MAIN> seznamNahranychSouboru, String path_for_xml, String sip_name,
			String typ_kontroly, String id_kontroly_zadane, int[] seznamObsKontroly,
			String programName,
			String programVersion)
			throws IOException, ParserConfigurationException, TransformerException, SAXException {
		this.seznamObsKontroly = seznamObsKontroly;
		this.programName = programName;
		this.programVersion = programVersion;
		if (path_for_xml==null||path_for_xml.equals("undefined") || path_for_xml.equals("")) {
			xml = create_xml(new File(".").getCanonicalPath() + File.separator + sip_name + ".xml");

		} else {
			if (path_for_xml.endsWith(File.separator)) {
				xml = create_xml(path_for_xml + sip_name + ".xml");
			} else {
				xml = create_xml(path_for_xml + File.separator + sip_name + ".xml");
			}

		}
//        String path = new File(".").getCanonicalPath();
		Parse(xml);
		set_root(id_kontroly_zadane, HelperTime.get_utc(), typ_kontroly);
		for (int i = 0; i < seznamNahranychSouboru.size(); i++) {
			Node node_sip = add_node_sip(seznamNahranychSouboru.get(i));
//            add_node_zakladniEntity(sip, seznamNahranychSouboru.get(i));
			add_kontrola("Kontrola škodlivého kódu", node_sip, seznamNahranychSouboru.get(i));
			add_kontrola("Kontrola datové struktury", node_sip, seznamNahranychSouboru.get(i));
			add_kontrola("Kontrola znakové sady", node_sip, seznamNahranychSouboru.get(i));
			add_kontrola("Kontrola správnosti XML", node_sip, seznamNahranychSouboru.get(i));
			add_kontrola("Kontrola jmenných prostorů XML", node_sip, seznamNahranychSouboru.get(i));
			add_kontrola("Kontrola proti schématu XSD", node_sip, seznamNahranychSouboru.get(i));
			add_kontrola("Kontrola obsahu", node_sip, seznamNahranychSouboru.get(i));
//            System.out.print(JFmain.main_pocitadlo + " ");
//            if(JFmain.main_pocitadlo % 1000 == 0){
//                System.out.println("");
//            }
//            JFmain.main_pocitadlo++;

		}
		save();
//        Transform_xml tx  = new Transform_xml(xml);
	}

	private void Parse(File xml) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		try {
			xml_parsed = builder.parse(xml);

		} catch (SAXException | IOException ex) {
			Logger.getLogger(Create_xml.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void set_root(String kontrolaID, String datumKontroly, String typ) throws TransformerException {
		kontrolaSip_root = xml_parsed.getElementsByTagName("kontrolaSIP").item(0);
		((Element) kontrolaSip_root).setAttribute("kontrolaID", kontrolaID);
		((Element) kontrolaSip_root).setAttribute("datumKontroly", datumKontroly);
		((Element) kontrolaSip_root).setAttribute("pouzitiKontroly", typ);
		if(programName!=null) {
			((Element) kontrolaSip_root).setAttribute("nazevAplikace", programName);
		}
		if(programVersion!=null) {
			((Element) kontrolaSip_root).setAttribute("verzeAplikace", programVersion);
		}
		((Element) kontrolaSip_root).setAttribute("xsi:schemaLocation",
				"http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v1 http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v1/kontrolasip.xsd");
		((Element) kontrolaSip_root).setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		((Element) kontrolaSip_root).setAttribute("xmlns", "http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v1");
	}

	private void save() throws TransformerConfigurationException, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Result output = new StreamResult(xml);

		Source input = new DOMSource(xml_parsed);
		xml_parsed.setXmlStandalone(true); // když je false tak se s tím pak nedá hýbat a je to default (standalone s
											// eezobrazí vždy).
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("http://www.oracle.com/xml/is-standalone", "yes"); // dává novou řádku po
																							// deklaraci - takže root je
																							// na nové řádce
//        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

		// odsazení
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(input, output);
	}

	private void add_kontrola(String nazev, Node sip, SIP_MAIN sipPack) {
		switch (nazev) {
		case "Kontrola škodlivého kódu":
			add_ko_skodliveho_kodu(sip, sipPack);
			break;
		case "Kontrola datové struktury":
			add_ko_dat_struktury(sip, sipPack);
			break;
		case "Kontrola znakové sady":
			add_ko_zn_sady(sip, sipPack);
			break;
		case "Kontrola správnosti XML":
			add_ko_spravnosti_xml(sip, sipPack);
			break;
		case "Kontrola jmenných prostorů XML":
			add_ko_jmen_prostoru(sip, sipPack);
			break;
		case "Kontrola proti schématu XSD":
			add_ko_schema_xsd(sip, sipPack);
			break;
		case "Kontrola obsahu":
			add_ko_obsahu(sip, sipPack);
			break;
		}
	}

	private void add_ko_skodliveho_kodu(Node sip, SIP_MAIN sipPack) {
		String zdroj = "§ 21 odst. 6 vyhlášky č. 259/2012 Sb.";
		SIP_MAIN_kontrola kontrola = sipPack.getUrovenKontroly(TypUrovenKontroly.SKODLIVY_KOD);
		boolean provedena = kontrola.isProvedena();
		boolean stav = kontrola.getStav();
		Node typ = add_node_typ("Kontrola škodlivého kódu", provedena, stav, sip);
		if (provedena) {
			// id, znení pravidla, chybový stav, popis chyby, misto chyby, zdroj, stav
			if (stav) {
				add_node_pravidlo(typ, K00_SkodlivehoKodu.VIR1, "Datový balíček SIP neobsahuje hrozbu.", "", "", "",
						zdroj, stav);
			} else {
				add_node_pravidlo(typ, K00_SkodlivehoKodu.VIR1, "Datový balíček SIP obsahuje hrozbu.",
						kontrola.get(0).getVypis_chyby(), "Datový balíček SIP obsahuje hrozbu.", "", zdroj, stav);
			}
		}

	}

	private void add_ko_dat_struktury(Node sip, SIP_MAIN sipPack) {
		SIP_MAIN_kontrola kontrola = sipPack.getUrovenKontroly(TypUrovenKontroly.DATOVE_STRUKTURY);
		boolean provedena = kontrola.isProvedena();
		boolean stav = kontrola.getStav();
		Node typ = add_node_typ("Kontrola datové struktury", provedena, stav, sip);
		if (provedena) {
			for (int i = 0; i < 3; i++) {
				boolean stav_pravidla = kontrola.get(i).getStav();
				String vypis_chyby = "";
				if (!stav_pravidla)
					vypis_chyby = kontrola.get(i).getVypis_chyby(); // uložená hodnota nechce vidět v xml ale pro
																	// aplikaci dobrá
				add_node_pravidlo(typ, kontrola.get(i).getId(), // id
						SIP_MAIN_seznam.get_DatovaStruktura(i + 1), // text
						vypis_chyby, // vypis chyby
						SIP_MAIN_seznam.get_DatovaStruktura(kontrola.get(i).getPopisChybyIndex()), // popis chyby -
																									// index 0 prázdná
																									// hodnota
						"", // místo chyby
						SIP_MAIN_seznam.get_DatovaStruktura(i + 6), // zdroj
						stav_pravidla); // stav
			}
		} else {
			for (int i = 0; i < 4; i++) {
				add_node_pravidlo(typ, "data" + (i + 1), // id
						SIP_MAIN_seznam.get_DatovaStruktura(i + 1), // text
						"", // vypis chyby
						"", // popis chyby - index 0 prázdná hodnota
						"", // místo chyby
						SIP_MAIN_seznam.get_DatovaStruktura(i + 6), // zdroj
						false); // stav
			}
		}
	}

	private void add_ko_zn_sady(Node sip, SIP_MAIN sipPack) {
		SIP_MAIN_kontrola kontrola = sipPack.getUrovenKontroly(TypUrovenKontroly.ZNAKOVE_SADY);
		boolean provedena = kontrola.isProvedena();
		boolean stav = kontrola.getStav();
		Node typ = add_node_typ("Kontrola znakové sady", provedena, stav, sip);
		if (provedena) {
			if (!stav) {
				add_node_pravidlo(typ, kontrola.get(0).getId(), // id
						"Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).", // text
						kontrola.get(0).getVypis_chyby(), // vypis chyby
						"Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).", // popis
																															// chyby
						"", // místo chyby
						"Požadavek 11.2.7 NSESSS.", // zdroj
						stav); // stav
			} else {
				add_node_pravidlo(typ, kontrola.get(0).getId(), // id
						"Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).", // text
						"", // vypis chyby
						"", // popis chyby
						"", // místo chyby
						"Požadavek 11.2.7 NSESSS.", // zdroj
						stav); // stav
			}
		} else {
			add_node_pravidlo(typ, "kod1", // id
					"Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).", // text
					"", // vypis chyby
					"", // popis chyby
					"", // místo chyby
					"Požadavek 11.2.7 NSESSS.", // zdroj
					false); // stav
		}
	}

	private void add_ko_spravnosti_xml(Node sip, SIP_MAIN sipPack) {
		SIP_MAIN_kontrola kontrola = sipPack.getUrovenKontroly(TypUrovenKontroly.SPRAVNOSTI);
		boolean provedena = kontrola.isProvedena();
		boolean stav = kontrola.getStav();
		Node typ = add_node_typ("Kontrola správnosti XML", provedena, stav, sip);
		if (provedena) {
			if (!stav) {
				add_node_pravidlo(typ, kontrola.get(0).getId(), // id
						"Soubor je well-formed.", // text
						kontrola.get(0).getVypis_chyby(), // vypis chyby
						"Datový balíček SIP nedodržuje syntaxi jazyka XML.", // popis chyby
						"", // místo chyby
						"Požadavek 11.2.2 NSESSS.", // zdroj
						stav); // stav
			}
			// kontrola proběhla v pořádku
			else {
				add_node_pravidlo(typ, kontrola.get(0).getId(), // id
						"Soubor je well-formed.", // text
						"", // vypis chyby
						"", // popis chyby
						"", // místo chyby
						"Požadavek 11.2.2 NSESSS.", // zdroj
						stav); // stav
			}
		} else {
			add_node_pravidlo(typ, "wf1", // id
					"Soubor je well-formed.", // text
					"", // vypis chyby
					"", // popis chyby
					"", // místo chyby
					"Požadavek 11.2.2 NSESSS.", // zdroj
					false); // stav
		}
	}

	private void add_ko_jmen_prostoru(Node sip, SIP_MAIN sipPack) {
		SIP_MAIN_kontrola kontrola = sipPack.getUrovenKontroly(TypUrovenKontroly.JMENNE_PROSTORY_XML);
		boolean provedena = kontrola.isProvedena();
		boolean stav = kontrola.getStav();
		String ggg = kontrola.getKontrola_nazev();
		Node typ = add_node_typ("Kontrola jmenných prostorů XML", provedena, stav, sip);
		if (provedena) {
			String vypis = "";
			String popis = "";
			if (!kontrola.get(0).getStav()) {
				vypis = kontrola.get(0).getVypis_chyby();
				popis = "Chybí kořenový element datového balíčku SIP.";
			}
			add_node_pravidlo(typ, kontrola.get(0).getId(), // id
					SIP_MAIN_seznam.get_text_JmProstory(1), // text
					vypis, // vypis chyby
					popis, // popis chyby
					"", // místo chyby
					"Bod 2.1. přílohy č. 3 NSESSS.", // zdroj
					kontrola.get(0).getStav()); // stav

			String vypis1 = "";
			String popis1 = "";
			if (!kontrola.get(1).getStav()) {
				vypis1 = kontrola.get(1).getVypis_chyby();
				popis1 = "Popsáno je chybně umístění příslušných schémat XML.";
			}
			add_node_pravidlo(typ, kontrola.get(1).getId(), // id
					SIP_MAIN_seznam.get_text_JmProstory(2), // text
					vypis1, // vypis chyby
					popis1, // popis chyby
					"", // místo chyby
					"Bod 2.1. přílohy č. 3 NSESSS.", // zdroj
					kontrola.get(1).getStav()); // stav
		} else {
			add_node_pravidlo(typ, "ns1", // id
					SIP_MAIN_seznam.get_text_JmProstory(1), // text
					"", // vypis chyby
					"", // popis chyby
					"", // místo chyby
					"Bod 2.1. přílohy č. 3 NSESSS.", // zdroj
					false); // stav

			add_node_pravidlo(typ, "ns2", // id
					SIP_MAIN_seznam.get_text_JmProstory(1), // text
					"", // vypis chyby
					"", // popis chyby
					"", // místo chyby
					"Bod 2.1. přílohy č. 3 NSESSS.", // zdroj
					false); // stav
		}
	}

	private void add_ko_schema_xsd(Node sip, SIP_MAIN sipPack) {
		SIP_MAIN_kontrola kontrola = sipPack.getUrovenKontroly(TypUrovenKontroly.PROTI_SCHEMATU);
		boolean provedena = kontrola.isProvedena();
		boolean stav = kontrola.getStav();
		Node typ = add_node_typ("Kontrola proti schématu XSD", provedena, stav, sip);
		if (provedena) {
			String vypis = "";
			String popis = "";
			String misto = "";
			if (!stav) {
				vypis = kontrola.get(0).getVypis_chyby();
				popis = "Datový balíček SIP není validní proti schématům http://www.loc.gov/standards/mets/mets.xsd, http://www.loc.gov/standards/mets/xlink.xsd, http://www.mvcr.cz/nsesss/v3/nsesss.xsd, http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd, http://www.mvcr.cz/nsesss/v3/ess_ns.xsd a http://www.mvcr.cz/nsesss/v3/dmBaseTypes.xsd.";
				misto = kontrola.get(0).getMisto_chyby();
			}

			add_node_pravidlo(typ, "val1", // id
					"Soubor je validní proti schématům mets.xsd (v1.11), xlink.xsd (v2), nsesss.xsd (v3), nsesss-TrP.xsd, ess_ns.xsd a dmBaseTypes.xsd (v2.1).", // text
					vypis, // vypis chyby
					popis, // popis chyby
					misto, // místo chyby
					"Požadavek 11.2.5 NSESSS, bod 2.1. přílohy č. 3 NSESSS.", // zdroj
					stav); // stav
		} else {
			add_node_pravidlo(typ, "val1", // id
					"Soubor je validní proti schématům mets.xsd (v1.11), xlink.xsd (v2), nsesss.xsd (v3), nsesss-TrP.xsd, ess_ns.xsd a dmBaseTypes.xsd (v2.1).", // text
					"", // vypis chyby
					"", // popis chyby
					"", // místo chyby
					"Požadavek 11.2.5 NSESSS, bod 2.1. přílohy č. 3 NSESSS.", // zdroj
					false); // stav
		}
	}

	private void add_ko_obsahu(Node sip, SIP_MAIN sipPack) {
		SIP_MAIN_kontrola kontrola = sipPack.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);
		boolean provedena = kontrola.isProvedena();
		boolean stav = kontrola.getStav();
		Node typ = add_node_typ("Kontrola obsahu", provedena, stav, sip);
		int[] seznamIndexuPravidel = seznamObsKontroly;
		if (provedena) {
//            for(int i = 0; i < sipPack.getSeznamKontrol().get(6).size(); i++){
//                add_node_pravidlo(typ, 
//                sipPack.getSeznamKontrol().get(6).get(i).getId(), //id
//                SIP_MAIN_seznam.get_text_Obsahova(sipPack.getSeznamKontrol().get(6).get(i).getIndex()), // text
//                sipPack.getSeznamKontrol().get(6).get(i).getVypis_chyby(), //vypis chyby
//                SIP_MAIN_seznam.get_popis_Obsahova(sipPack.getSeznamKontrol().get(6).get(i).getIndex()), //popis chyby
//                sipPack.getSeznamKontrol().get(6).get(i).getMisto_chyby(), //místo chyby
//                SIP_MAIN_seznam.get_zdroje_Obsahova(sipPack.getSeznamKontrol().get(6).get(i).getIndex()), // zdroj
//                stav); // stav
//            }
//        }

//        int siz = sip.getSeznamKontrol().get(6).size();
			int t = 0;
			int size = kontrola.size() - 1;
			for (int i = 0; i < seznamIndexuPravidel.length; i++) {
				int j = seznamIndexuPravidel[i];
				if (!kontrola.isEmpty()) {
					if (kontrola.get(t).getIndex() == j) {
						add_node_pravidlo(typ, kontrola.get(t).getId(), // id
								SIP_MAIN_seznam.get_text_Obsahova(j), // text
								kontrola.get(t).getVypis_chyby(), // vypis chyby
								SIP_MAIN_seznam.get_popis_Obsahova(j), // popis chyby
								kontrola.get(t).getMisto_chyby(), // místo chyby
								SIP_MAIN_seznam.get_zdroje_Obsahova(j), // zdroj
								kontrola.get(t).getStav()); // stav
						if (t < size)
							t++;
					} else {
						String id = SIP_MAIN_helper.get_special_ida(j);
						add_node_pravidlo(typ, id, // id
								SIP_MAIN_seznam.get_text_Obsahova(j), // text
								"", // vypis chyby
								"", // popis chyby
								"", // místo chyby
								SIP_MAIN_seznam.get_zdroje_Obsahova(j), // zdroj
								true); // stav
					}
				} else {
					String id = SIP_MAIN_helper.get_special_ida(j);
					add_node_pravidlo(typ, id, // id
							SIP_MAIN_seznam.get_text_Obsahova(j), // text
							"", // vypis chyby
							"", // popis chyby
							"", // místo chyby
							SIP_MAIN_seznam.get_zdroje_Obsahova(j), // zdroj
							true); // stav
				}
			}
		} else {
			for (int i = 0; i < seznamIndexuPravidel.length; i++) {
				int j = seznamIndexuPravidel[i];
				String id = SIP_MAIN_helper.get_special_ida(j);
				add_node_pravidlo(typ, id, // id
						SIP_MAIN_seznam.get_text_Obsahova(j), // text
						"", // vypis chyby
						"", // popis chyby
						"", // místo chyby
						SIP_MAIN_seznam.get_zdroje_Obsahova(j), // zdroj
						false); // stav

			}
		}
	}

	private Node add_node_typ(String nazev, boolean provedena, boolean stav, Node sip) {
		Node typ = sip.appendChild(xml_parsed.createElement("typ"));
		((Element) typ).setAttribute("nazev", nazev);
		((Element) typ).setAttribute("provedena", String.valueOf(provedena));
		((Element) typ).setAttribute("stav", String.valueOf(stav));
		return typ;
	}

	private void add_node_pravidlo(Node parent, String id, String text, String chyba, String popisChyby,
			String mistoChyby, String zdroj, boolean stav) {
		Node pravidlo = parent.appendChild(xml_parsed.createElement("pravidlo"));
		((Element) pravidlo).setAttribute("id",
				id.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		((Element) pravidlo).setAttribute("text",
				text.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		((Element) pravidlo).setAttribute("vypisChyby",
				chyba.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		((Element) pravidlo).setAttribute("popisChyby",
				popisChyby.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		((Element) pravidlo).setAttribute("mistoChyby",
				mistoChyby.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		((Element) pravidlo).setAttribute("zdroj",
				zdroj.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		((Element) pravidlo).setAttribute("stav",
				String.valueOf(stav).replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));

	}

	private void add_node_info() {
		Node info = kontrolaSip_root.appendChild(xml_parsed.createElement("info"));
		((Element) info).setAttribute("pocetChyb", "0");
		((Element) info).setAttribute("dobaKontroly", "hh:mm:ss");
		((Element) info).setAttribute("dalsi", "další podobné informace");
	}

	private Node add_node_sip(SIP_MAIN sip) {
		Element elemNodeSip = xml_parsed.createElement("sip");
		String metsObjId = sip.getMetsObjId();
		if(StringUtils.isNotEmpty(metsObjId)) {
			elemNodeSip.setAttribute("sipID", metsObjId);
		}

		String g = sip.getNameZip();
		if (g == null) {
			g = sip.getName();
		}
		elemNodeSip.setAttribute("nazevSouboru", g);
		
		Node nodeSIP = kontrolaSip_root.appendChild(elemNodeSip);		
		nodeSIP.setTextContent("");
		return nodeSIP;
	}

	private void add_node_zakladniEntity(Node sip, SIP_MAIN sipPackage) {
		Node zaEn = sip.appendChild(xml_parsed.createElement("zakladniEntity"));
		((Element) zaEn).setAttribute("pocet", "0");
		// cyklus
		add_node_zakladni_entita(zaEn);

	}

	private void add_node_zakladni_entita(Node parent) {
		Node entita = parent.appendChild(xml_parsed.createElement("zakladniEntita"));
		((Element) entita).setAttribute("typ", "spis");
		((Element) entita).setAttribute("zdroj", "erms");
		((Element) entita).setAttribute("identifikator", "MSLSSSSS02");

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

}
