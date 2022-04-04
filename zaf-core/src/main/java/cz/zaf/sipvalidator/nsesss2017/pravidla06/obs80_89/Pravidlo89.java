package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo89 extends K06PravidloBase {
    
    static final public String OBS89 = "obs89";
    
    public Pravidlo89() {
        super(OBS89,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Vyrizeni>.",
                "Není v souladu rok spouštěcí události a datum vyřízení u dokumentu.",
                null);
    }

    //OBSAHOVÁ č.89 Pokud je základní entitou (<nsesss:Dokument>), 
    // potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> 
    // obsahuje element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené 
    // v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Vyrizeni>.
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        } else {
            for (int i = 0; i < zakladniEntity.size(); i++) {
                Element entita = zakladniEntity.get(i);
                if (entita.getNodeName().equals(NsessV3.DOKUMENT)) {
                    Element elRokSpoustUdalosti = ValuesGetter.getXChild(entita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                            NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SPOUSTECI_UDALOSTI);
                    if (elRokSpoustUdalosti == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(entita),
                                getMistoChyby(entita), kontrola.getEntityId(entita));
                    }
                    
                    Integer rokUdalosti = vratRok(elRokSpoustUdalosti);
                    Element datum = ValuesGetter.getXChild(entita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRIZENI, "nsesss:Datum");
                    if (datum == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(entita),
                                getMistoChyby(entita), kontrola.getEntityId(entita));
                    }
                    
                    Integer dat = vratRok(datum);
                    if (!(rokUdalosti >= dat)) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. Událost: " + rokUdalosti + ". Datum: " + dat + ". "
                                + getJmenoIdentifikator(entita),
                                getMistoChyby(elRokSpoustUdalosti) + " " + getMistoChyby(datum),
                                kontrola.getEntityId(entita));
                    }
                }
            }
        }
    }
    
}
