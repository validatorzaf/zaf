package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.Obj_Node_int;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import java.util.Arrays;

//
// OBSAHOVÁ č.65
//
// Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom
// v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>,
// <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace>
// hodnotu, která je rovna nebo větší vyšší hodnotě, přičemž jednou hodnotou je
// součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu
// <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu
// <nsesss:SkartacniRezim> a druhou hodnotou nejvyšší hodnota součtu hodnoty
// elementu <nsesss:DatumDoruceni> nebo <nsesss:DatumVytvoreni> (v závislosti na
// tom, zda jde o doručený nebo vlastní dokument), 1 a hodnoty elementu
// <nsesss:SkartacniLhuta> jakékoli dětské entity dokument (nsesss:Dokument>).
//
public class Pravidlo65 extends K06PravidloBase {

    static final public String OBS65 = "obs65";

    public Pravidlo65() {
        super(OBS65,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je rovna nebo větší vyšší hodnotě, přičemž jednou hodnotou je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> a druhou hodnotou nejvyšší hodnota součtu hodnoty elementu <nsesss:DatumDoruceni> nebo <nsesss:DatumVytvoreni> (v závislosti na tom, zda jde o doručený nebo vlastní dokument), 1 a hodnoty elementu <nsesss:SkartacniLhuta> jakékoli dětské entity dokument (nsesss:Dokument>).",
                "Uveden je chybně rok skartační operace u dílu nebo spisu (počítá se jak podle roku spouštěcí události + 1 + skartační lhůta, tak podle roku skartační operace u dokumentů - záleží na tom, co je vyšší).",
                "§ 15 odst. 5 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        List<Element> dokumenty = metsParser.getDokumenty();
        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntita = zakladniEntity.get(i);
            String nazevZakladniEntity = elZakladniEntita.getNodeName();
            if (nazevZakladniEntity.equals(NsessV3.SPIS) || nazevZakladniEntity.equals(NsessV3.DIL)) {
                Element elRokSkartacniOperace = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                        NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SKARTACNI_OPERACE);
                if (elRokSkartacniOperace == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }
                String hodnotaElRokSkartacniOperace = elRokSkartacniOperace.getTextContent();
                // HODNOTA 1
                int hodnota_rokSkartacniOperace_spis = 0;
                try {
                    hodnota_rokSkartacniOperace_spis = Integer.parseInt(hodnotaElRokSkartacniOperace);
                } catch (NumberFormatException e) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota roku elementu <nsesss:RokSkartacniOperace> je uvedena ve špatném formátu: "
                            + hodnotaElRokSkartacniOperace + "." + getJmenoIdentifikator(elZakladniEntita),
                            elRokSkartacniOperace, kontrola.getEntityId(elZakladniEntita));
                }

                Element elRokSpousteciUdalosti = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                        NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SPOUSTECI_UDALOSTI);
                if (elRokSpousteciUdalosti == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>." + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }
                String hodnotaRokSpousteciUdalosti_spis = elRokSpousteciUdalosti.getTextContent();
                int rokSpousteciUdalosti_spis = 0;
                try {
                    rokSpousteciUdalosti_spis = Integer.parseInt(hodnotaRokSpousteciUdalosti_spis);
                } catch (NumberFormatException e) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota roku elementu <nsesss:RokSpousteciUdalosti> je uvedena ve špatném formátu: "
                            + hodnotaRokSpousteciUdalosti_spis + ". " + getJmenoIdentifikator(elZakladniEntita),
                            elRokSpousteciUdalosti, kontrola.getEntityId(elZakladniEntita));
                }

                Element elSkartacniLhuta = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                        NsessV3.SKARTACNI_REZIM, NsessV3.SKARTACNI_LHUTA);
                if (elSkartacniLhuta == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }
                String str_skartacniLhuta_spis = elSkartacniLhuta.getTextContent();
                int skartacniLhuta_spis = 0;
                try {
                    skartacniLhuta_spis = Integer.parseInt(str_skartacniLhuta_spis);
                } catch (NumberFormatException e) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota roku elementu <nsesss:SkartacniLhuta> je uvedena ve špatném formátu: "
                            + str_skartacniLhuta_spis + ". " + getJmenoIdentifikator(elZakladniEntita),
                            elSkartacniLhuta, kontrola.getEntityId(elZakladniEntita));
                }

                //HODNOTA 2
                int hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta = rokSpousteciUdalosti_spis + 1 + skartacniLhuta_spis;

                // dokumenty 
                if (dokumenty == null || dokumenty.isEmpty()) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }

                Obj_Node_int dokument = new Obj_Node_int(null, 0);
                for (int j = 0; j < dokumenty.size(); j++) {
                    Element dokumentze = dokumenty.get(j);
                    int int_finalhodnota_dok;
                    Element elSkartacniLhutaDokumentu = ValuesGetter.getXChild(dokumentze, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                            NsessV3.SKARTACNI_REZIM, NsessV3.SKARTACNI_LHUTA);
                    if (elSkartacniLhutaDokumentu == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(dokumentze),
                                dokumentze, kontrola.getEntityId(dokumentze));
                    }
                    String d_lhuta = elSkartacniLhutaDokumentu.getTextContent();

                    Element datum_dok = ValuesGetter.getXChild(dokumentze, NsessV3.EVIDENCNI_UDAJE, NsessV3.PUVOD,
                            NsessV3.VLASTNI_DOKUMENT, NsessV3.DATUM_VYTVORENI);
                    if (datum_dok == null) {
                        datum_dok = ValuesGetter.getXChild(dokumentze, NsessV3.EVIDENCNI_UDAJE, NsessV3.PUVOD,
                                NsessV3.DORUCENY_DOKUMENT, NsessV3.DATUM_DORUCENI);
                    }
                    if (datum_dok == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(dokumentze),
                                dokumentze, kontrola.getEntityId(dokumentze));
                    }
                    String d_vytvDor = datum_dok.getTextContent();
                    if (d_vytvDor.length() > 4) {
                        String sub = d_vytvDor.substring(0, 4);
                        d_vytvDor = sub;
                    }
                    int int_dok_lhuta = 0, int_dok_rok_vytvDor = 0;
                    try {
                        int_dok_lhuta = Integer.parseInt(d_lhuta);
                    } catch (NumberFormatException e) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:SkartacniLhuta> obsahuje hodnotu roku ve špatném formátu. "
                                + getJmenoIdentifikator(dokumentze), elSkartacniLhutaDokumentu, kontrola.getEntityId(dokumentze));
                    }
                    try {
                        int_dok_rok_vytvDor = Integer.parseInt(d_vytvDor);
                    } catch (NumberFormatException e) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <" + datum_dok.getNodeName()
                                + "> obsahuje hodnotu roku ve špatném formátu." + getJmenoIdentifikator(dokumentze),
                                datum_dok, kontrola.getEntityId(dokumentze));
                    }

                    int_finalhodnota_dok = int_dok_lhuta + 1 + int_dok_rok_vytvDor;

                    if ((int_finalhodnota_dok) > dokument.get_int()) {
                        dokument = new Obj_Node_int(dokumentze, (int_finalhodnota_dok));
                    }
                }

                //HODNOTA 3 
                int hodnota_rokSkartacniOperace_dokument = dokument.get_int();

                //PODMÍNKA
                int hodnota_maximalni = Math.max(hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta,
                        hodnota_rokSkartacniOperace_dokument);
                if (hodnota_rokSkartacniOperace_spis < hodnota_maximalni) {
                    List<Element> listElementu = Arrays.asList(elZakladniEntita, dokument.get_node());
                    if (hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta > hodnota_rokSkartacniOperace_dokument) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Rok uvedený v elementu <nsesss:RokSkartacniOperace>: "
                                + hodnota_rokSkartacniOperace_spis
                                + ", se nerovná nejvyšší hodnotě. Buď nejvyšší hodnotě z dětských elementů <nsesss:Dokument>: "
                                + hodnota_rokSkartacniOperace_dokument
                                + ", nebo součtu hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: "
                                + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = "
                                + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta + ". " + getJmenoIdentifikator(elZakladniEntita)
                                + " " + getJmenoIdentifikator(dokument.get_node()),
                                getMistoChyby(elZakladniEntita) + " " + getMistoChyby(dokument.get_node()),
                                kontrola.getEntityId(listElementu));
                    } else {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Součet hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: "
                                + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = "
                                + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta
                                + ", je roven nejvyšší hodnotě elementu <nsesss:RokSkartacniOperace> elementu <nsesss:Dokument>: "
                                + hodnota_rokSkartacniOperace_dokument
                                + ". Nerovná se však hodnotě elementu <nsesss:RokSkartacniOperace> základní entity: "
                                + hodnota_rokSkartacniOperace_spis + ". " + getJmenoIdentifikator(elZakladniEntita) + " "
                                + getJmenoIdentifikator(dokument.get_node()),
                                getMistoChyby(elZakladniEntita) + " " + getMistoChyby(dokument.get_node()),
                                kontrola.getEntityId(listElementu));
                    }
                }
            }
        }
    }
}
