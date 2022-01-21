package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.Obj_Node_int;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }
        List<Node> dokumenty = metsParser.getDokumenty();

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladniEntita = zakladniEntity.get(i);
            String jmeno = zakladniEntita.getNodeName();
            if (jmeno.equals(NsessV3.SPIS) || jmeno.equals(NsessV3.DIL)) {
                Node node = ValuesGetter.getXChild(zakladniEntita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Vyrazovani",
                                                   "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
                if (node == null) {
                    return nastavChybu("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(
                                                                                                                 zakladniEntita),
                                       zakladniEntita);
                }
                String str_rokSkartacniOperace_spis = node.getTextContent();
                // HODNOTA 1
                int hodnota_rokSkartacniOperace_spis;
                try {
                    hodnota_rokSkartacniOperace_spis = Integer.parseInt(str_rokSkartacniOperace_spis);
                } catch (NumberFormatException e) {
                    return nastavChybu("Hodnota roku elementu <nsesss:RokSkartacniOperace> je uvedena ve špatném formátu: "
                            + str_rokSkartacniOperace_spis + "." + getJmenoIdentifikator(zakladniEntita),
                                       node);
                }

                node = ValuesGetter.getXChild(zakladniEntita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Vyrazovani",
                                              "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if (node == null) {
                    return nastavChybu("Nenalezen element <nsesss:RokSpousteciUdalosti>." + getJmenoIdentifikator(
                                                                                                                 zakladniEntita),
                                       zakladniEntita);
                }
                String str_rokSpousteciUdalosti_spis = node.getTextContent();
                int rokSpousteciUdalosti_spis;
                try {
                    rokSpousteciUdalosti_spis = Integer.parseInt(str_rokSpousteciUdalosti_spis);
                } catch (NumberFormatException e) {
                    return nastavChybu("Hodnota roku elementu <nsesss:RokSkartacniOperace> je uvedena ve špatném formátu: "
                            + str_rokSpousteciUdalosti_spis + ". " + getJmenoIdentifikator(zakladniEntita),
                                       node);
                }

                node = ValuesGetter.getXChild(zakladniEntita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Vyrazovani",
                                              "nsesss:SkartacniRezim", "nsesss:SkartacniLhuta");
                if (node == null) {
                    return nastavChybu("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(
                                                                                                            zakladniEntita),
                                       zakladniEntita);
                }
                String str_skartacniLhuta_spis = node.getTextContent();
                int skartacniLhuta_spis;
                try {
                    skartacniLhuta_spis = Integer.parseInt(str_skartacniLhuta_spis);
                } catch (NumberFormatException e) {
                    return nastavChybu("Hodnota roku elementu <nsesss:SkartacniLhuta> je uvedena ve špatném formátu: "
                            + str_skartacniLhuta_spis + ". " + getJmenoIdentifikator(zakladniEntita),
                                       node);
                }

                //HODNOTA 2
                int hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta = rokSpousteciUdalosti_spis + 1
                        + skartacniLhuta_spis;

                // dokumenty 
                if (dokumenty == null || dokumenty.isEmpty()) {
                    return nastavChybu("Nenalezen žádný element <nsesss:Dokument>. " + getJmenoIdentifikator(
                                                                                                            zakladniEntita),
                                       zakladniEntita);
                }
                Obj_Node_int dokument = new Obj_Node_int(null, 0);
                for (int j = 0; j < dokumenty.size(); j++) {
                    Node dokumentze = dokumenty.get(j);
                    int int_finalhodnota_dok;
                    Node dok_lhuta = ValuesGetter.getXChild(dokumentze, NsessV3.EVIDENCNI_UDAJE, "nsesss:Vyrazovani",
                                                            "nsesss:SkartacniRezim", "nsesss:SkartacniLhuta");
                    if (dok_lhuta == null) {
                        return nastavChybu("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(
                                                                                                                dokumentze),
                                           dokumentze);
                    }
                    String d_lhuta = dok_lhuta.getTextContent();

                    Node datum_dok = ValuesGetter.getXChild(dokumentze, NsessV3.EVIDENCNI_UDAJE, "nsesss:Puvod",
                                                            "nsesss:VlastniDokument", "nsesss:DatumVytvoreni");
                    if (datum_dok == null)
                        datum_dok = ValuesGetter.getXChild(dokumentze, NsessV3.EVIDENCNI_UDAJE, "nsesss:Puvod",
                                                           "nsesss:DorucenyDokument", "nsesss:DatumDoruceni");
                    if (datum_dok == null) {
                        return nastavChybu("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(
                                                                                                                     dokumentze),
                                           dokumentze);
                    }
                    String d_vytvDor = datum_dok.getTextContent().substring(0, 4);
                    int int_dok_lhuta, int_dok_rok_vytvDor;
                    try {
                        int_dok_lhuta = Integer.parseInt(d_lhuta);
                    } catch (NumberFormatException e) {
                        return nastavChybu("Element <nsesss:SkartacniLhuta> obsahuje hodnotu roku ve špatném formátu. "
                                + getJmenoIdentifikator(dokumentze), getMistoChyby(dok_lhuta));
                    }
                    try {
                        int_dok_rok_vytvDor = Integer.parseInt(d_vytvDor);
                    } catch (NumberFormatException e) {
                        return nastavChybu("Element <" + datum_dok.getNodeName()
                                + "> obsahuje hodnotu roku ve špatném formátu." + getJmenoIdentifikator(dokumentze),
                                           datum_dok);
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
                    if (hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta > hodnota_rokSkartacniOperace_dokument) {
                        return nastavChybu("Rok uvedený v elementu <nsesss:RokSkartacniOperace>: "
                                + hodnota_rokSkartacniOperace_spis
                                + ", se nerovná nejvyšší hodnotě. Buď nejvyšší hodnotě z dětských elementů <nsesss:Dokument>: "
                                + hodnota_rokSkartacniOperace_dokument
                                + ", nebo součtu hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: "
                                + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = "
                                + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta + ". " + getJmenoIdentifikator(
                                                                                                                  zakladniEntita)
                                + " " + getJmenoIdentifikator(dokument.get_node()), getMistoChyby(zakladniEntita)
                                        + " " + getMistoChyby(dokument.get_node()));
                    } else {
                        return nastavChybu("Součet hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: "
                                + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = "
                                + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta
                                + ", je roven nejvyšší hodnotě elementu <nsesss:RokSkartacniOperace> elementu <nsesss:Dokument>: "
                                + hodnota_rokSkartacniOperace_dokument
                                + ". Nerovná se však hodnotě elementu <nsesss:RokSkartacniOperace> základní entity: "
                                + hodnota_rokSkartacniOperace_spis + ". " + getJmenoIdentifikator(zakladniEntita) + " "
                                + getJmenoIdentifikator(dokument.get_node()),
                                           getMistoChyby(zakladniEntita) + " "
                                        + getMistoChyby(dokument.get_node()));
                    }
                }
            }
        }

        return true;
    }
}
