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
        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntita = zakladniEntity.get(i);
            if (elZakladniEntita.getNodeName().equals(NsesssV4.DOKUMENT)) {
                kontrolaDokumentu(elZakladniEntita);
            }
        }
    }

    private void kontrolaDokumentu(Element elDokument) {
        Element elvyrazovani = ValuesGetter.getXChild(elDokument, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRAZOVANI);
        if (elvyrazovani == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Vyrazovani>. "
                    + getJmenoIdentifikator(elDokument), elDokument, kontrola.getEntityId(elDokument));
        }

        // ziskani skartacni lhuty
        Element elSkartacniRezim = ValuesGetter.getXChild(elvyrazovani, NsesssV4.SKARTACNI_REZIM);
        if (elSkartacniRezim == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRezim>. "
                    + getJmenoIdentifikator(elDokument), elDokument, kontrola.getEntityId(elDokument));
        }
        Element elSkLhutaRokVyrazeni = rokLhutyNeboVyrazeni(elSkartacniRezim);
        if (elSkLhutaRokVyrazeni == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta> nebo <nsesss:RokVyrazeni>. "
                    + getJmenoIdentifikator(elDokument), elSkartacniRezim, kontrola.getEntityId(elDokument));
        }
        String hodnotaSkLhutaRokVyrazeni = elSkLhutaRokVyrazeni.getTextContent();

        // zjisteni roku spousteci udalosti
        Element elRokSousteciUdalosti = ValuesGetter.getXChild(elvyrazovani, NsesssV4.DATACE_VYRAZENI,
                                                               NsesssV4.ROK_SPOUSTECI_UDALOSTI);
        if (elRokSousteciUdalosti == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>. "
                    + getJmenoIdentifikator(elDokument),
                        elDokument, kontrola.getEntityId(elDokument));
        }

        String rokSpousteciUdalosti = elRokSousteciUdalosti.getTextContent();

        try {
            int hodnotaOperace = kontrola.getContext().getLocalDate().getYear();
            int hodnotaLhuta = Integer.parseInt(hodnotaSkLhutaRokVyrazeni);
            int hodnotaUdalosti = Integer.parseInt(rokSpousteciUdalosti);
            if (hodnotaOperace < (hodnotaLhuta + hodnotaUdalosti + 1)) {
                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Rok skartační operace: " + hodnotaOperace
                        + ", lhůta: " + hodnotaLhuta
                        + ", událost: " + hodnotaUdalosti + ". " + getJmenoIdentifikator(elDokument),
                            elDokument, kontrola.getEntityId(elDokument));
            }
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Zápis roku je uveden ve špatném formátu. "
                    + getJmenoIdentifikator(elDokument),
                        elDokument, kontrola.getEntityId(elDokument));
        }
    }
    
    private Element rokLhutyNeboVyrazeni(Element elSkartacniRezim){
        Element element = ValuesGetter.getXChild(elSkartacniRezim, NsesssV4.SKARTACNI_LHUTA);
        if (element == null) {
            element = ValuesGetter.getXChild(elSkartacniRezim, NsesssV4.ROK_VYRAZENI);
        }
        return element;
    }
}
