package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo91 extends K06PravidloBaseOld {

    static final public String OBS91 = "obs91";

    public Pravidlo91() {
        super(OBS91,
                "Pokud je základní entitou díl (<nsesss:Dil>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Uzavreni>.",
                "Není v souladu rok spouštěcí události a datum uzavření u dílu.",
                null);
    }

    //OBSAHOVÁ č.91 Pokud je základní entitou díl (<nsesss:Dil>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Uzavreni>.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element dil = zakladniEntity.get(i);
            if (dil.getNodeName().equals("nsesss:Dil")) {
                Node node = ValuesGetter.getXChild(dil, NsessV3.EVIDENCNI_UDAJE, "nsesss:Vyrazovani",
                                                   "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                Node node2 = ValuesGetter.getXChild(dil, NsessV3.EVIDENCNI_UDAJE, "nsesss:Uzavreni", "nsesss:Datum");
                if (node == null)
                    return nastavChybu("Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(dil),
                                       getMistoChyby(node));
                if (node2 == null)
                    return nastavChybu("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(dil),
                                      getMistoChyby(node2));

                Integer rokSpousteci = vratRok(node);
                if (rokSpousteci == null) {
                    return false;
                }
                Integer rokDatum = vratRok(node2);
                if (rokDatum == null) {
                    return false;
                }
                if (!(rokSpousteci >= rokDatum))
                    return nastavChybu("Nesplněna podmínka pravidla. Událost: " + rokSpousteci + ". Datum: " + rokDatum
                            + ". " + getJmenoIdentifikator(dil),
                                       getMistoChyby(node) + " " + getMistoChyby(node2));
            }
        }
        return true;
    }

}