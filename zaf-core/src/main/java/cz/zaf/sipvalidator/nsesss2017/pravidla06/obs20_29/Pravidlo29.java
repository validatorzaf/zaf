package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import java.util.ArrayList;

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
    protected void kontrola() {
        List<Element> pevneKrizoveOdkazy = metsParser.getKrizoveOdkazyPevnyAno();
        if (!pevneKrizoveOdkazy.isEmpty()) {
            List<Element> zaklEntityChybne = metsParser.getZakladniEntityChybne();
            if (CollectionUtils.isNotEmpty(zaklEntityChybne)) {
                nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                        "Element <mets:xmlData> obsahuje nepovolené dětské elementy.", zaklEntityChybne);
            }
            List<Element> zaklEntity = metsParser.getZakladniEntity();
            if (CollectionUtils.isEmpty(zaklEntity)) {
                Node xmlData = metsParser.getMetsXmlData();
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:xmlData> neobsahuje žádné dětské elementy.", xmlData);
            }

            for (int i = 0; i < pevneKrizoveOdkazy.size(); i++) {
                Element krizovyOdkaz = pevneKrizoveOdkazy.get(i);
                Element materska_zakl_entita_eu = ValuesGetter.getXParent(krizovyOdkaz, "nsesss:Souvislosti",
                        NsesssV3.EVIDENCNI_UDAJE);

                if (materska_zakl_entita_eu == null) {
                    nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                            "Element <nsesss:KrizovyOdkaz> je špatně zatříděn. Nenalezeny elementy <nsesss:Souvislosti> a <nsesss:EvidencniUdaje>.",
                            krizovyOdkaz);
                }
                Node za_ent = materska_zakl_entita_eu.getParentNode();
                Node identifikator_me = ValuesGetter.getXChild(materska_zakl_entita_eu, "nsesss:Identifikace",
                        "nsesss:Identifikator");
                if (identifikator_me == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT,
                            "Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí element <nsesss:Identifikator>.",
                            za_ent);
                }
                if (!ValuesGetter.hasAttribut(identifikator_me, "zdroj")) {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT,
                            "Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí atribut zdroj u elementu <nsesss:Identifikator>.",
                            identifikator_me);
                }
                String zdroj_me = ValuesGetter.getValueOfAttribut(identifikator_me, "zdroj");
                String ident_me = identifikator_me.getTextContent();
                Node identifikator_ko = ValuesGetter.getXChild(krizovyOdkaz, "nsesss:Identifikator");
                if (identifikator_ko == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT,
                            "Element <nsesss:KrizovyOdkaz> nemá dětský element <nsesss:Identifikator>.",
                            krizovyOdkaz);
                }
                if (!ValuesGetter.hasAttribut(identifikator_ko, "zdroj")) {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT,
                            "Dětský element <nsesss:Identifikator> elementu <nsesss:KrizovyOdkaz> nemá atribut zdroj.",
                            identifikator_ko);
                }
                String zdroj_ko = ValuesGetter.getValueOfAttribut(identifikator_ko, "zdroj");
                String ident_ko = identifikator_ko.getTextContent();

                if (zdroj_me.equals(zdroj_ko) && ident_me.equals(ident_ko)) {
                    nastavChybu(BaseCode.CHYBA_KRIZOVEHO_ODKAZU, "Element <nsesss:KrizovyOdkaz> odkazuje na vlastní základní entitu.",
                            krizovyOdkaz);
                }

                int pocitadlo = 0;
                List<Element> listElChybnych = new ArrayList<>();
                for (Element zaklEntita : zaklEntity) {
                    Node id_ze = ValuesGetter.getXChild(zaklEntita, NsesssV3.EVIDENCNI_UDAJE,
                            "nsesss:Identifikace",
                            "nsesss:Identifikator");
                    if (id_ze == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Identifikator> základní entity.",
                                zaklEntita);
                    }
                    if (!ValuesGetter.hasAttribut(id_ze, "zdroj")) {
                        nastavChybu(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut zdroj elementu <nsesss:Identifikator>.",
                                id_ze);
                    }
                    String hodnotaZdrojMatEnt = ValuesGetter.getValueOfAttribut(id_ze, "zdroj");
                    String hodnotaIdentMatEnt = id_ze.getTextContent();
                    if (zdroj_ko.equals(hodnotaZdrojMatEnt) && ident_ko.equals(hodnotaIdentMatEnt)) {
                        pocitadlo++;
                        listElChybnych.add(zaklEntita);
                    }
                }
                if (pocitadlo == 0) {
                    nastavChybu(BaseCode.CHYBA_KRIZOVEHO_ODKAZU,
                            "Nenalezena žádná základní entita, na kterou odkazuje element <nsesss:KrizovyOdkaz> s id: "
                            + ident_ko + " a zdrojem: " + zdroj_ko + ".",
                            krizovyOdkaz);
                }
                if (pocitadlo > 1) {
                    nastavChybu(BaseCode.CHYBA_KRIZOVEHO_ODKAZU,
                            "Element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko + " odkazuje na více základních entit.",
                            listElChybnych);
                }
            }
        }
    }
}
