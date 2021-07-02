package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.29 Pokud existuje element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, potom pro každý jeho výskyt
// obsahuje element <mets:dmdSec> v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> dětský element <nsesss:Dil> nebo 
// <nsesss:Dokument> nebo <nsesss:Spis> se stejnou hodnotou v dětském elementu <nsesss:Identifikator> a v jeho atributu zdroj.",
public class Pravidlo29 extends K06PravidloBase {

	static final public String OBS29 = "obs29";

	public Pravidlo29() {
		super(OBS29,
				"Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, potom pro každý jeho výskyt obsahuje element <mets:dmdSec> v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> dětský element <nsesss:Dil> nebo <nsesss:Dokument> nebo <nsesss:Spis> se stejnou hodnotou v dětském elementu <nsesss:Identifikator> a v jeho atributu zdroj.",
				"Chybí popis dílu, spisu nebo dokumentu, který je v datovém balíčku SIP připojen pevným křížovým odkazem.",
				"Bod 2.7. přílohy č. 3 NSESSS; příloha č. 2 NSESSS, ř. 20.");
	}

	@Override
	protected boolean kontrolaPravidla() {
		List<Node> krizove_odkazy_pevny_ano = metsParser.getKrizoveOdkazyPevnyAno();
		if (krizove_odkazy_pevny_ano == null || krizove_odkazy_pevny_ano.isEmpty())
			return true;

		Node xmlData = this.metsParser.getMetsXmlData();

		ArrayList<Node> list = ValuesGetter.get_Node_Children(xmlData);
		if (!ValuesGetter.maRodicPouzeTytoPotomky(xmlData, "nsesss:Dil", "nsesss:Dokument", "nsesss:Spis")) {
			String ch = "";
			for (int i = 0; i < list.size(); i++) {
				Node node = list.get(i);
				String name = node.getNodeName();
				if (!name.equals("nsesss:Dokument") || !name.equals("nsesss:Spis") || !name.equals("nsesss:Dil")) {
					ch += getMistoChyby(node) + " ";
				}
			}
			return nastavChybu("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", ch);
		}

		for (int i = 0; i < krizove_odkazy_pevny_ano.size(); i++) {
			Node krizovyOdkaz = krizove_odkazy_pevny_ano.get(i);
			Node materska_zakl_entita_eu = ValuesGetter.getXParent(krizovyOdkaz, "nsesss:Souvislosti",
					"nsesss:EvidencniUdaje");

			if (materska_zakl_entita_eu == null) {
				return nastavChybu(
						"Element <nsesss:KrizovyOdkaz> je špatně zatříděn. Nenalezeny elementy <nsesss:Souvislosti> a <nsesss:EvidencniUdaje>.",
						getMistoChyby(krizovyOdkaz));
			}
			Node za_ent = materska_zakl_entita_eu.getParentNode();
			Node identifikator_me = ValuesGetter.getXChild(materska_zakl_entita_eu, "nsesss:Identifikace",
					"nsesss:Identifikator");
			if (identifikator_me == null) {
				return nastavChybu(
						"Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí element <nsesss:Identifikator>.",
						getMistoChyby(za_ent));
			}
			if (!ValuesGetter.hasAttribut(identifikator_me, "zdroj")) {
				return nastavChybu(
						"Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí atribut zdroj u elementu <nsesss:Identifikator>.",
						getMistoChyby(identifikator_me));
			}
			String zdroj_me = ValuesGetter.getValueOfAttribut(identifikator_me, "zdroj");
			String ident_me = identifikator_me.getTextContent();
			Node identifikator_ko = ValuesGetter.getXChild(krizovyOdkaz, "nsesss:Identifikator");
			if (identifikator_ko == null) {
				return nastavChybu("Element <nsesss:KrizovyOdkaz> nemá dětský element <nsesss:Identifikator>.",
						getMistoChyby(krizovyOdkaz));
			}
			if (!ValuesGetter.hasAttribut(identifikator_ko, "zdroj")) {
				return nastavChybu(
						"Dětský element <nsesss:Identifikator> elementu <nsesss:KrizovyOdkaz> nemá atribut zdroj.",
						getMistoChyby(identifikator_ko));
			}
			String zdroj_ko = ValuesGetter.getValueOfAttribut(identifikator_ko, "zdroj");
			String ident_ko = identifikator_ko.getTextContent();

			if (zdroj_me.equals(zdroj_ko) && ident_me.equals(ident_ko)) {
				return nastavChybu("Element <nsesss:KrizovyOdkaz> odkazuje na vlastní základní entitu.",
						getMistoChyby(krizovyOdkaz));
			}

			int pocitadlo = 0;
			String ch = "";
			List<Node> zakladniEntity = metsParser.getZakladniEntity();

			for (int k = 0; k < zakladniEntity.size(); k++) {
				Node zentita = zakladniEntity.get(k);
				Node id_ze = ValuesGetter.getXChild(zentita, "nsesss:EvidencniUdaje", "nsesss:Identifikace",
						"nsesss:Identifikator");
				if (id_ze == null) {
					return nastavChybu("Nenalezen element <nsesss:Identifikator> základní entity.",
							getMistoChyby(zentita));
				}
				if (!ValuesGetter.hasAttribut(id_ze, "zdroj")) {
					return nastavChybu("Nenalezen atribut zdroj elementu <nsesss:Identifikator>.",
							getMistoChyby(id_ze));
				}
				String hodnotaZdrojMatEnt = ValuesGetter.getValueOfAttribut(id_ze, "zdroj");
				String hodnotaIdentMatEnt = id_ze.getTextContent();
				if (zdroj_ko.equals(hodnotaZdrojMatEnt) && ident_ko.equals(hodnotaIdentMatEnt)) {
					pocitadlo++;
					ch += getMistoChyby(zentita) + " ";
				}
			}
			if (pocitadlo == 0) {
				return nastavChybu(
						"Nenalezena žádná základní entita, na kterou odkazuje element <nsesss:KrizovyOdkaz> s id: "
								+ ident_ko + " a zdrojem: " + zdroj_ko + ".",
						getMistoChyby(krizovyOdkaz));
			}
			if (pocitadlo > 1) {
				return nastavChybu("Element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko
						+ " odkazuje na více základních entit.", ch + getMistoChyby(krizovyOdkaz));
			}
		}

		return true;
	}
}