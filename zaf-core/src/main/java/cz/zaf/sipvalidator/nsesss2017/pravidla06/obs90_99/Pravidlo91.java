package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo91 extends K06PravidloBase {

    static final public String OBS91 = "obs91";

    public Pravidlo91() {
        super(OBS91,
                "Pokud je základní entitou díl (<nsesss:Dil>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Uzavreni>.",
                "Není v souladu rok spouštěcí události a datum uzavření u dílu.",
                null);
    }

    //OBSAHOVÁ č.91 Pokud je základní entitou díl (<nsesss:Dil>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Uzavreni>.",
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        } else {
            for (int i = 0; i < zakladniEntity.size(); i++) {
                Element dil = zakladniEntity.get(i);
                if (dil.getNodeName().equals(NsessV3.DIL)) {
                    Element elRokSpoustUdalosti = ValuesGetter.getXChild(dil, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                            NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SPOUSTECI_UDALOSTI);
                    Element elDatum = ValuesGetter.getXChild(dil, NsessV3.EVIDENCNI_UDAJE, NsessV3.UZAVRENI, NsessV3.DATUM);
                    if (elRokSpoustUdalosti == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(dil),
                                getMistoChyby(elRokSpoustUdalosti), kontrola.getEntityId(dil));
                    }
                    if (elDatum == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(dil),
                                getMistoChyby(elDatum), kontrola.getEntityId(dil));
                    }

                    Integer rokSpousteci = vratRok(elRokSpoustUdalosti);
                    Integer rokDatum = vratRok(elDatum);
                    if (!(rokSpousteci >= rokDatum)) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. Událost: " + rokSpousteci + ". Datum: " + rokDatum
                                + ". " + getJmenoIdentifikator(dil),
                                getMistoChyby(elRokSpoustUdalosti) + " " + getMistoChyby(elDatum),
                                kontrola.getEntityId(dil));
                    }
                }
            }
        }
    }

}
