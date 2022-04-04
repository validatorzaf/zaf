package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo90 extends K06PravidloBase {
    
    static final public String OBS90 = "obs90";
    
    public Pravidlo90() {
        super(OBS90,
                "Pokud je základní entitou spis (<nsesss:Spis>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:VyrizeniUzavreni>.",
                "Není v souladu rok spouštěcí události a datum vyřízení nebo datum uzavření u spisu.",
                null);
    }

    //OBSAHOVÁ č.90 Pokud je základní entitou spis (<nsesss:Spis>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:VyrizeniUzavreni>.",
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        } else {
            for (int i = 0; i < zakladniEntity.size(); i++) {
                Element spis = zakladniEntity.get(i);
                if (spis.getNodeName().equals(NsessV3.SPIS)) {
                    Element elRokSpoustUdalosti = ValuesGetter.getXChild(spis, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                            NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SPOUSTECI_UDALOSTI);
                    if (elRokSpoustUdalosti == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>. "
                                + getJmenoIdentifikator(spis),
                                spis, kontrola.getEntityId(spis));
                    }
                    
                    Element elDatum = ValuesGetter.getXChild(spis, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRIZENI_UZAVRENI,
                            NsessV3.DATUM);
                    if (elDatum == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(spis),
                                spis, kontrola.getEntityId(spis));
                    }
                    
                    Integer rokSpousteci = vratRok(elRokSpoustUdalosti);
                    Integer rokDatum = vratRok(elDatum);
                    if (!(rokSpousteci >= rokDatum)) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. Událost: " + rokSpousteci + ". Datum: " + rokDatum
                                + ". " + getJmenoIdentifikator(spis),
                                getMistoChyby(elRokSpoustUdalosti) + " " + getMistoChyby(elDatum),
                                kontrola.getEntityId(spis));
                    }
                }
            }
        }
    }
    
}
