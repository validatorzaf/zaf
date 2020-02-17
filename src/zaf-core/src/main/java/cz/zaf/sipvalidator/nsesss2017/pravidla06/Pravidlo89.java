package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo89 extends K06PravidloBase {

    public Pravidlo89(K06_Obsahova kontrola) {
        super(kontrola,
                K06_Obsahova.OBS89,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Vyrizeni>.",
                "Není v souladu rok spouštěcí události a datum vyřízení u dokumentu.",
                null);
    }

    //OBSAHOVÁ č.89 Pokud je základní entitou (<nsesss:Dokument>), 
    // potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> 
    // obsahuje element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené 
    // v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Vyrizeni>."
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node entita = zakladniEntity.get(i);
            if (entita.getNodeName().equals("nsesss:Dokument")) {
                Node node = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                                   "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if (node == null) {
                    return nastavChybu("Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(
                                                                                                                   entita),
                                       getMistoChyby(entita));
                }
                String s = node.getTextContent().substring(0, 4);
                boolean b = ValuesGetter.overSpravnostRetezceProInt(s);
                if (!b) {
                    return nastavChybu("Hodnota elementu <nsesss:RokSpousteciUdalosti> uvedena v nepovoleném formátu. "
                            + getJmenoIdentifikator(entita), getMistoChyby(node));
                }
                int rokUdalosti = Integer.parseInt(s);

                Node datum = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Vyrizeni", "nsesss:Datum");
                if (datum == null) {
                    return nastavChybu("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(entita),
                                       getMistoChyby(entita));
                }
                String d = datum.getTextContent().substring(0, 4);
                boolean bo = ValuesGetter.overSpravnostRetezceProInt(d);
                if (!bo) {
                    return nastavChybu("Hodnota elementu <nsesss:Datum> uvedena v nepovoleném formátu. "
                            + getJmenoIdentifikator(entita), getMistoChyby(datum));
                }
                int dat = Integer.parseInt(d);
                if (!(rokUdalosti >= dat)) {
                    return nastavChybu("Nesplněna podmínka pravidla. Událost: " + rokUdalosti + ". Datum: " + dat + ". "
                            + getJmenoIdentifikator(entita),
                                       getMistoChyby(node) + " " + getMistoChyby(datum));
                }
            }
        }
        return true;
    }

}
