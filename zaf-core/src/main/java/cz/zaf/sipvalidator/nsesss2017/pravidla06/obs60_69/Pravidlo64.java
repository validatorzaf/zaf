package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//
// OBSAHOVÁ č.64
//
// Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii
// dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>,
// <nsesss:DataceVyrazeni> je součet hodnoty elementu
// <nsesss:RokSpousteciUdalosti>,
// 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu
// <nsesss:SkartacniRezim> menší nebo roven aktuálnímu roku.
//
public class Pravidlo64 extends K06PravidloBase {

    static final public String OBS64 = "obs64";

    public Pravidlo64() {
        super(OBS64,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> menší nebo roven aktuálnímu roku.",
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
                kontrolaDokumentu(elZakladniEntita);
            }
        }
    }

    private void kontrolaDokumentu(Element elDokument) {
        Element elvyrazovani = ValuesGetter.getXChild(elDokument, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI);
        if (elvyrazovani == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Vyrazovani>. "
                    + getJmenoIdentifikator(elDokument), elDokument, kontrola.getEntityId(elDokument));
        }

        // ziskani skartacni lhuty
        Element elSkartacniRezim = ValuesGetter.getXChild(elvyrazovani, NsessV3.SKARTACNI_REZIM);
        if (elSkartacniRezim == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRezim>. "
                    + getJmenoIdentifikator(elDokument), elDokument, kontrola.getEntityId(elDokument));
        }
        Element elSkartacniLhuta = ValuesGetter.getXChild(elSkartacniRezim, NsessV3.SKARTACNI_LHUTA);
        if (elSkartacniLhuta == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta>. "
                    + getJmenoIdentifikator(elDokument), elSkartacniRezim, kontrola.getEntityId(elDokument));
        }
        String hodnotaElSkartacniLhuta = elSkartacniLhuta.getTextContent();

        // zjisteni roku spousteci udalosti
        Element elRokSousteciUdalosti = ValuesGetter.getXChild(elvyrazovani, NsessV3.DATACE_VYRAZENI,
                                                               NsessV3.ROK_SPOUSTECI_UDALOSTI);
        if (elRokSousteciUdalosti == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>. "
                    + getJmenoIdentifikator(elDokument),
                        elDokument, kontrola.getEntityId(elDokument));
        }

        String rokSpousteciUdalosti = elRokSousteciUdalosti.getTextContent();

        try {
            int hodnotaOperace = context.getLocalDate().getYear();
            int hodnotaLhuta = Integer.parseInt(hodnotaElSkartacniLhuta);
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
}
