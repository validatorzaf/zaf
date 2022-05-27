package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// OBSAHOVÁ č.64
//
// Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii
// dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>,
// <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace>
// hodnotu, která je rovna nebo větší součtu hodnoty elementu
// <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta>
// uvedeného v rodičovském elementu <nsesss:SkartacniRezim>.
//
public class Pravidlo64 extends K06PravidloBase {

    static final public String OBS64 = "obs64";

    public Pravidlo64() {
        super(OBS64,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je rovna nebo větší součtu hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim>.",
                "Uveden je chybně rok skartační operace u dokumentu (počítá se jako rok spouštěcí události + 1 + skartační lhůta).",
                "§ 15 odst. 4 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntita = zakladniEntity.get(i);
            if (elZakladniEntita.getNodeName().equals(NsessV3.DOKUMENT)) {
                Element elvyrazovani = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI);
                if (elvyrazovani == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Vyrazovani>. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }
                Element elSkartacniRezim = ValuesGetter.getXChild(elvyrazovani, NsessV3.SKARTACNI_REZIM);
                if (elSkartacniRezim == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }
                Element elSkartacniLhuta = ValuesGetter.getXChild(elSkartacniRezim, NsessV3.SKARTACNI_LHUTA);
                if (elSkartacniLhuta == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(elZakladniEntita),
                            elSkartacniRezim, kontrola.getEntityId(elZakladniEntita));
                }
                String hodnotaElSkartacniLhuta = elSkartacniLhuta.getTextContent();
                Element elRokSkartOperace = ValuesGetter.getXChild(elvyrazovani, NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SKARTACNI_OPERACE);
                Element elRokSousteciUdalosti = ValuesGetter.getXChild(elvyrazovani, NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SPOUSTECI_UDALOSTI);
                if (elRokSkartOperace == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }
                if (elRokSousteciUdalosti == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }

                String rokSkartacniOperace = elRokSkartOperace.getTextContent();
                String rokSpousteciUdalosti = elRokSousteciUdalosti.getTextContent();

                try {
                    int hodnotaOperace = Integer.parseInt(rokSkartacniOperace);
                    int hodnotaLhuta = Integer.parseInt(hodnotaElSkartacniLhuta);
                    int hodnotaUdalosti = Integer.parseInt(rokSpousteciUdalosti);
                    if (hodnotaOperace < (hodnotaLhuta + hodnotaUdalosti + 1)) {
                        nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Rok skartační operace: " + hodnotaOperace
                                + ", lhůta: " + hodnotaLhuta
                                + ", událost: " + hodnotaUdalosti + ". " + getJmenoIdentifikator(elZakladniEntita),
                                elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                    }
                } catch (NumberFormatException e) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Zápis roku je uveden ve špatném formátu. " + getJmenoIdentifikator(elZakladniEntita),
                            elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
                }

            }
        }
    }
}
