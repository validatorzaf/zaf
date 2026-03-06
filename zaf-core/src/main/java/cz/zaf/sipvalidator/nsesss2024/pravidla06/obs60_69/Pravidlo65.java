package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;

//
// OBSAHOVÁ č.65
//
// Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů 
// <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> je součet hodnoty elementu 
// <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> 
// nebo hodnota elementu <nsesss:RokVyrazeni> uvedeného v rodičovském elementu <nsesss:SkartacniRezim>  menší nebo rovna aktuálnímu roku. 
// Pro všechny dětské entity spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>) dále platí, že součet hodnoty elementu <nsesss:DatumDoruceni> 
// nebo <nsesss:DatumVytvoreni> (v závislosti na tom, zda jde o doručený nebo vlastní dokument), 1 a hodnoty elementu <nsesss:SkartacniLhuta> 
// nebo hodnota elementu <nsesss:RokVyrazeni> je menší nebo rovna aktuálnímu roku.
//
public class Pravidlo65 extends K06PravidloBase {

    static final public String OBS65 = "obs65";

    public Pravidlo65() {
        super(OBS65,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> nebo hodnota elementu <nsesss:RokVyrazeni> uvedeného v rodičovském elementu <nsesss:SkartacniRezim>  menší nebo rovna aktuálnímu roku. Pro všechny dětské entity spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>) dále platí, že součet hodnoty elementu <nsesss:DatumDoruceni> nebo <nsesss:DatumVytvoreni> (v závislosti na tom, zda jde o doručený nebo vlastní dokument), 1 a hodnoty elementu <nsesss:SkartacniLhuta> nebo hodnota elementu <nsesss:RokVyrazeni> je menší nebo rovna aktuálnímu roku.",
                "Uveden je chybně rok skartační operace u dílu nebo spisu (počítá se jak podle roku spouštěcí události + 1 + skartační lhůta, tak podle roku skartační operace u dokumentů nebo roku vyřazení - záleží na tom, co je vyšší) nebo rok vyřazení .",
                "§ 15 odst. 3 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        List<Element> dokumenty = metsParser.getDokumenty();

        // dokumenty 
        if (dokumenty == null || dokumenty.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntita = zakladniEntity.get(i);
            String nazevZakladniEntity = elZakladniEntita.getNodeName();
            if (nazevZakladniEntity.equals(NsesssV4.SPIS) || nazevZakladniEntity.equals(NsesssV4.DIL)) {
                kontrolaSpisuDilu(elZakladniEntita, dokumenty);
            }
        }
    }

    private void kontrolaSpisuDilu(Element elZakladniEntita, List<Element> dokumenty) {
        int rokSkartacniOperace = this.kontrola.getContext().getLocalDate().getYear();

        Element elRokSpousteciUdalosti = ValuesGetter.getXChild(elZakladniEntita, NsesssV4.EVIDENCNI_UDAJE,
                NsesssV4.VYRAZOVANI,
                NsesssV4.DATACE_VYRAZENI,
                NsesssV4.ROK_SPOUSTECI_UDALOSTI);
        if (elRokSpousteciUdalosti == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSpousteciUdalosti>."
                    + getJmenoIdentifikator(elZakladniEntita),
                    elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
        }
        int rokSpousteciUdalostiSpis = 0;
        try {
            rokSpousteciUdalostiSpis = Integer.parseInt(elRokSpousteciUdalosti.getTextContent());
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Hodnota roku elementu <nsesss:RokSpousteciUdalosti> je uvedena ve špatném formátu: "
                    + elRokSpousteciUdalosti.getTextContent() + ". "
                    + getJmenoIdentifikator(elZakladniEntita),
                    elRokSpousteciUdalosti, kontrola.getEntityId(elZakladniEntita));
        }

        Element elSkLhutaRokVyrazeni = rokLhutyNeboVyrazeni(elZakladniEntita);

        if (elSkLhutaRokVyrazeni == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta> nebo <nsesss:RokVyrazeni>. "
                    + getJmenoIdentifikator(elZakladniEntita), elZakladniEntita,
                    kontrola.getEntityId(elZakladniEntita));
        }
        int skartacniLhutaSpis = 0;
        try {
            skartacniLhutaSpis = Integer.parseInt(elSkLhutaRokVyrazeni.getTextContent());
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Hodnota roku elementu <nsesss:SkartacniLhuta> je uvedena ve špatném formátu: "
                    + elSkLhutaRokVyrazeni.getTextContent() + ". " + getJmenoIdentifikator(elZakladniEntita),
                    elSkLhutaRokVyrazeni, kontrola.getEntityId(elZakladniEntita));
        }

        // zakladni kontrola spisu
        int minLhutaSpis = rokSpousteciUdalostiSpis + 1 + skartacniLhutaSpis;
        if (minLhutaSpis > rokSkartacniOperace) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Rok skartační operace: " + rokSkartacniOperace
                    + ", lhůta: " + skartacniLhutaSpis
                    + ", událost: " + rokSpousteciUdalostiSpis + ". " + getJmenoIdentifikator(elZakladniEntita),
                    elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
        }

        // kontrola jednotlivych dokumentu
        for (int j = 0; j < dokumenty.size(); j++) {
            Element dokument = dokumenty.get(j);
            kontrolaDokumentu(elZakladniEntita, rokSkartacniOperace, dokument,
                    minLhutaSpis, rokSpousteciUdalostiSpis, skartacniLhutaSpis);
        }

    }

    private void kontrolaDokumentu(Element zakladniEntita, int rokSkartacniOperace,
            Element dokument, int minLhutaSpis,
            int rokSpousteciUdalostiSpis, int skartacniLhutaSpis) {

        Element elSkLhutaRokVyrazeniDokumentu = rokLhutyNeboVyrazeni(dokument);
        if (elSkLhutaRokVyrazeniDokumentu == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta>. "
                    + getJmenoIdentifikator(dokument),
                    dokument, kontrola.getEntityId(dokument));
        }
        int lhutaDokumentu = 0;
        try {
            lhutaDokumentu = Integer.parseInt(elSkLhutaRokVyrazeniDokumentu.getTextContent());
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Element <nsesss:SkartacniLhuta> obsahuje hodnotu roku ve špatném formátu. "
                    + getJmenoIdentifikator(dokument), elSkLhutaRokVyrazeniDokumentu, kontrola
                    .getEntityId(dokument));
        }

        Element datumVznikuDokumentuNode = ValuesGetter.getXChild(dokument, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.PUVOD,
                NsesssV4.VLASTNI_DOKUMENT, NsesssV4.DATUM_VYTVORENI);
        if (datumVznikuDokumentuNode == null) {
            datumVznikuDokumentuNode = ValuesGetter.getXChild(dokument, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.PUVOD,
                    NsesssV4.DORUCENY_DOKUMENT, NsesssV4.DATUM_DORUCENI);
        }
        if (datumVznikuDokumentuNode == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element vytvoření nebo doručení dokumentu. "
                    + getJmenoIdentifikator(dokument), dokument, kontrola.getEntityId(dokument));
        }
        String rokVznikuDokumentuStr = datumVznikuDokumentuNode.getTextContent();
        if (rokVznikuDokumentuStr.length() > 4) {
            // extrakce jen roku
            rokVznikuDokumentuStr = rokVznikuDokumentuStr.substring(0, 4);
        }
        int rokVznikuDokumentu = 0;
        try {
            rokVznikuDokumentu = Integer.parseInt(rokVznikuDokumentuStr);
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <" + datumVznikuDokumentuNode.getNodeName()
                    + "> obsahuje hodnotu roku ve špatném formátu." + getJmenoIdentifikator(dokument),
                    datumVznikuDokumentuNode, kontrola.getEntityId(dokument));
        }

        int minLhutaDokumentu = lhutaDokumentu + 1 + rokVznikuDokumentu;
        if (minLhutaDokumentu > rokSkartacniOperace) {
            List<Element> listElementu = Arrays.asList(zakladniEntita, dokument);
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Součet hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: "
                    + rokSpousteciUdalostiSpis + " + 1 + " + skartacniLhutaSpis + " = "
                    + minLhutaSpis
                    + " je u základní entity menší než rok skartační operace. Lhůta dokumentu <nsesss:Dokument> je: "
                    + minLhutaDokumentu
                    + ". Ta je však vyšší než-li rok skartační operace: "
                    + rokSkartacniOperace + ".",
                    getMistoChyby(zakladniEntita) + " " + getMistoChyby(dokument),
                    kontrola.getEntityId(listElementu));
        }
    }

    private Element rokLhutyNeboVyrazeni(Element elEntita) {
        Element elSkartacniRezim = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRAZOVANI, NsesssV4.SKARTACNI_REZIM);
        if (elSkartacniRezim == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRezim>. "
                    + getJmenoIdentifikator(elEntita), elEntita, kontrola.getEntityId(elEntita));
        }

        Element element = ValuesGetter.getXChild(elSkartacniRezim, NsesssV4.SKARTACNI_LHUTA);
        if (element == null) {
            element = ValuesGetter.getXChild(elSkartacniRezim, NsesssV4.ROK_VYRAZENI);
        }
        return element;
    }
}
