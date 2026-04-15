package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

//
// OBSAHOVÁ č.64
//
// Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> 
// je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu 
// <nsesss:SkartacniRezim> nebo hodnota elementu <nsesss:RokVyrazeni> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> menší nebo rovna aktuálnímu roku.
//
public class Pravidlo64 extends K06PravidloBase {

    static final public String OBS64 = "obs64";

    public Pravidlo64() {
        super(OBS64,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> nebo hodnota elementu <nsesss:RokVyrazeni> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> menší nebo rovna aktuálnímu roku.",
                "Uveden je chybně rok skartační operace u dokumentu (počítá se jako rok spouštěcí události + 1 + skartační lhůta) nebo jako rok vyřazení.",
                "§ 15 odst. 3 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        int aktualniRok = this.kontrola.getLocalDate().getYear();
        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntita = zakladniEntity.get(i);
            if (elZakladniEntita.getNodeName().equals(NsesssV4.DOKUMENT)) {
                kontrolaZakladniEntity(elZakladniEntita, aktualniRok);
            }
        }
    }

    private void kontrolaZakladniEntity(Element elZakladniEntita, int aktualniRok) {
        int rokSpousteciUdalosti = getRokSpousteciUdalosti(elZakladniEntita);
        Element elSkartacniLhuta = getRokLhutyNeboVyrazeni(elZakladniEntita, NsesssV4.SKARTACNI_LHUTA);
        if (elSkartacniLhuta != null) {
            int skartacniLhuta = getRokSkLhutaRokVyrazeni(elZakladniEntita, elSkartacniLhuta);
            if (!(rokSpousteciUdalosti + 1 + skartacniLhuta <= aktualniRok)) {
                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Součet je vyšší než aktuální rok. Rok spouštěcí události: " + rokSpousteciUdalosti
                        + ", skartační lhůta: " + skartacniLhuta
                        + ", aktuální rok: " + aktualniRok + ".",
                        getMistoChyby(elZakladniEntita), getEntityId(elZakladniEntita));
            }
        } else {
            Element elRokVyrazeni = getRokLhutyNeboVyrazeni(elZakladniEntita, NsesssV4.ROK_VYRAZENI);
            if (elRokVyrazeni == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <" + NsesssV4.SKARTACNI_LHUTA + ">, ani element <" + NsesssV4.ROK_VYRAZENI + ">.",
                        getMistoChyby(elZakladniEntita),
                        getEntityId(elZakladniEntita));
            } else {
                int rokVyrazeni = getRokSkLhutaRokVyrazeni(elZakladniEntita, elRokVyrazeni);
                if (!(rokVyrazeni <= aktualniRok)) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Rok vyřazení: " + rokVyrazeni + ", je větší než aktuální rok: " + aktualniRok + ".",
                            getMistoChyby(elZakladniEntita), getEntityId(elZakladniEntita));
                }
            }
        }
    }

    private int getRokSpousteciUdalosti(Element entita) {
        Element elRokSpousteciUdalosti = ValuesGetter.getXChild(entita, NsesssV4.EVIDENCNI_UDAJE,
                NsesssV4.VYRAZOVANI,
                NsesssV4.DATACE_VYRAZENI,
                NsesssV4.ROK_SPOUSTECI_UDALOSTI);
        if (elRokSpousteciUdalosti == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>."
                    + getJmenoIdentifikator(entita),
                    entita, getEntityId(entita));
        }
        int rokSpousteciUdalostiEntita = 0;
        try {
            rokSpousteciUdalostiEntita = Integer.parseInt(elRokSpousteciUdalosti.getTextContent());
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Hodnota roku elementu <nsesss:RokSpousteciUdalosti> je uvedena ve špatném formátu: "
                    + elRokSpousteciUdalosti.getTextContent() + ". "
                    + getJmenoIdentifikator(entita),
                    elRokSpousteciUdalosti, getEntityId(entita));
        }
        return rokSpousteciUdalostiEntita;
    }

    private Element getRokLhutyNeboVyrazeni(Element elEntita, String elementName) {
        Element elSkartacniRezim = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRAZOVANI, NsesssV4.SKARTACNI_REZIM);
        if (elSkartacniRezim == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRezim>. "
                    + getJmenoIdentifikator(elEntita), elEntita, getEntityId(elEntita));
        }

        Element element = ValuesGetter.getXChild(elSkartacniRezim, elementName);
        return element;
    }

    private int getRokSkLhutaRokVyrazeni(Element nadrazenaEntita, Element elSkLhutaRokVyrazeni) {
        if (elSkLhutaRokVyrazeni == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:" + NsesssV4.SKARTACNI_LHUTA + ">.", getMistoChyby(nadrazenaEntita),
                    getEntityId(nadrazenaEntita));
        }
        int skartacniLhutaEntita = 0;
        try {
            skartacniLhutaEntita = Integer.parseInt(elSkLhutaRokVyrazeni.getTextContent());
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Hodnota roku elementu <" + elSkLhutaRokVyrazeni.getNodeName() + "> je uvedena ve špatném formátu: "
                    + elSkLhutaRokVyrazeni.getTextContent() + ".", getMistoChyby(elSkLhutaRokVyrazeni), getEntityId(nadrazenaEntita));
        }
        return skartacniLhutaEntita;
    }

}
