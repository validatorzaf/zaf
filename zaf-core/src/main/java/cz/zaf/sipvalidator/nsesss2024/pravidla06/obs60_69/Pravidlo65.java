package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import org.apache.commons.collections4.CollectionUtils;

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

        int aktualniRok = this.kontrola.getLocalDate().getYear();
        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntita = zakladniEntity.get(i);
            String nazevZakladniEntity = elZakladniEntita.getNodeName();
            kontrolaZakladniEntity(elZakladniEntita, aktualniRok);
            if (nazevZakladniEntity.equals(NsesssV4.DIL)) {
                Element elSPisy = ValuesGetter.getXChild(elZakladniEntita, NsesssV4.SPISY);
                if (elSPisy != null) {
                    List<Element> listSpisy = ValuesGetter.getChildNodes(elSPisy, NsesssV4.SPIS);
                    if (!CollectionUtils.isEmpty(listSpisy)) {
                        for (Element elSpis : listSpisy) {
                            kontrolaEntitiy(elSpis, aktualniRok);
                            zkontrolujDokumenty(elSpis, aktualniRok);
                        }
                    }
                }
            }
            zkontrolujDokumenty(elZakladniEntita, aktualniRok);
        }
    }

    private void zkontrolujDokumenty(Element elEntita, int aktualniRok) {
        Element elDokumenty = ValuesGetter.getXChild(elEntita, NsesssV4.DOKUMENTY);
        List<Element> listDokumenty = ValuesGetter.getChildNodes(elDokumenty, NsesssV4.DOKUMENT);
        if (!CollectionUtils.isEmpty(listDokumenty)) {
            for (Element elDokument : listDokumenty) {
                kontrolaEntitiy(elDokument, aktualniRok);
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

    private Element getRokLhutyNeboVyrazeni(Element elEntita, String elementName) {
        Element elSkartacniRezim = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRAZOVANI, NsesssV4.SKARTACNI_REZIM);
        if (elSkartacniRezim == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRezim>. "
                    + getJmenoIdentifikator(elEntita), elEntita, getEntityId(elEntita));
        }

        Element element = ValuesGetter.getXChild(elSkartacniRezim, elementName);
        return element;
    }

    private int getDatumDoruceniVytvoreni(Element elEntita) {
        Element elDatum = null;
        String entitaName = elEntita.getNodeName();
        if (entitaName.equals(NsesssV4.SPIS)) {
            elDatum = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.PUVOD, NsesssV4.DATUM_VYTVORENI);
            if (elDatum == null) {
                elDatum = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.PUVOD, NsesssV4.DATUM_DORUCENI);
            }
            if (elDatum == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <" + NsesssV4.DATUM_VYTVORENI + "> ani <" + NsesssV4.DATUM_DORUCENI + ">.",
                        getMistoChyby(elEntita), getEntityId(elEntita));
            }
        }
        if (entitaName.equals(NsesssV4.DOKUMENT)) {
            elDatum = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.PUVOD, NsesssV4.VLASTNI_DOKUMENT, NsesssV4.DATUM_VYTVORENI);
            if (elDatum == null) {
                elDatum = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.PUVOD, NsesssV4.DORUCENY_DOKUMENT, NsesssV4.DATUM_DORUCENI);
            }
        }
        if (elDatum == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <" + NsesssV4.DATUM_VYTVORENI + "> ani <" + NsesssV4.DATUM_DORUCENI + ">.",
                    getMistoChyby(elEntita), getEntityId(elEntita));
        }

        String strDatum = elDatum.getTextContent();
        if (strDatum.length() > 4) {
            // extrakce jen roku
            strDatum = strDatum.substring(0, 4);
        }
        int intDatum = 0;
        try {
            intDatum = Integer.parseInt(strDatum);
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <" + elDatum.getNodeName()
                    + "> obsahuje hodnotu roku ve špatném formátu." + getJmenoIdentifikator(elEntita),
                    getMistoChyby(elDatum), getEntityId(elEntita));
        }
        return intDatum;
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

    private void kontrolaEntitiy(Element elEntita, int aktualniRok) {
        int datumDoruceniVytvoreni = getDatumDoruceniVytvoreni(elEntita);
        Element elSkartacniLhuta = getRokLhutyNeboVyrazeni(elEntita, NsesssV4.SKARTACNI_LHUTA);
        if (elSkartacniLhuta != null) {
            int skartacniLhuta = getRokSkLhutaRokVyrazeni(elEntita, elSkartacniLhuta);
            if (!(datumDoruceniVytvoreni + 1 + skartacniLhuta <= aktualniRok)) {
                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Součet je vyšší než aktuální rok. Rok vytvoření/doručení: " + datumDoruceniVytvoreni
                        + ", skartační lhůta: " + skartacniLhuta
                        + ", aktuální rok: " + aktualniRok + ".",
                        getMistoChyby(elEntita), getEntityId(elEntita));
            }
        } else {
            Element elRokVyrazeni = getRokLhutyNeboVyrazeni(elEntita, NsesssV4.ROK_VYRAZENI);
            if (elRokVyrazeni == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <" + NsesssV4.SKARTACNI_LHUTA + ">, ani element <" + NsesssV4.ROK_VYRAZENI + ">.",
                        getMistoChyby(elEntita),
                        getEntityId(elEntita));
            } else {
                int rokVyrazeni = getRokSkLhutaRokVyrazeni(elEntita, elRokVyrazeni);
                if (!(rokVyrazeni <= aktualniRok)) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Rok vyřazení: " + rokVyrazeni + ", je větší než aktuální rok: " + aktualniRok + ".",
                            getMistoChyby(elEntita), getEntityId(elEntita));
                }
            }
        }
    }

}
