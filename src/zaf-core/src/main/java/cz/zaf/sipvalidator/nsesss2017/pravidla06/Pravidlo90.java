package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo90 extends K06PravidloBase {

    public Pravidlo90(K06_Obsahova kontrola) {
        super(kontrola,
                K06_Obsahova.OBS90,
                "Pokud je základní entitou spis (<nsesss:Spis>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:VyrizeniUzavreni>.",
                "Není v souladu rok spouštěcí události a datum vyřízení nebo datum uzavření u spisu.",
                null);
    }

    //OBSAHOVÁ č.90 Pokud je základní entitou dokument (<nsesss:Spis>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:VyrizeniUzavreni>.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node spis = zakladniEntity.get(i);
            if (spis.getNodeName().equals("nsesss:Spis")) {
                Node node = ValuesGetter.getXChild(spis, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                                   "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                Node node2 = ValuesGetter.getXChild(spis, "nsesss:EvidencniUdaje", "nsesss:VyrizeniUzavreni",
                                                    "nsesss:Datum");
                if (node == null)
                    return nastavChybu("Nenalezen element <nsesss:RokSpousteciUdalosti>. "
                            + getJmenoIdentifikator(spis),
                                       getMistoChyby(spis));
                if (node2 == null)
                    return nastavChybu("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(spis),
                                       getMistoChyby(spis));
                String rok1 = node.getTextContent().substring(0, 4);
                String rok2 = node2.getTextContent().substring(0, 4);
                boolean b = (ValuesGetter.overSpravnostRetezceProInt(rok1) && ValuesGetter.overSpravnostRetezceProInt(
                                                                                                                      rok1));
                if (!b)
                    return nastavChybu("Hodnoty dat jsou zaznamenány v nepovoleném formátu. " + getJmenoIdentifikator(
                                                                                                                      spis),
                                       getMistoChyby(node) + " " + getMistoChyby(node2));
                int rokSpousteci = Integer.parseInt(rok1);
                int rokDatum = Integer.parseInt(rok2);
                if (!(rokSpousteci >= rokDatum)) {
                    return nastavChybu("Nesplněna podmínka pravidla. Událost: " + rokSpousteci + ". Datum: " + rokDatum
                            + ". " + getJmenoIdentifikator(spis), getMistoChyby(node) + " " + getMistoChyby(
                                                                                                                   node2));
                }
            }
        }
        return true;
    }

}
