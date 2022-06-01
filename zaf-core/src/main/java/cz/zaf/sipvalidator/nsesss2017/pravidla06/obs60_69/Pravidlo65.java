package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// OBSAHOVÁ č.65
//
// Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom
// v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>,
// <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace>
// hodnotu, která je rovna nebo větší vyšší hodnotě, přičemž jednou hodnotou je
// součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu
// <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu
// <nsesss:SkartacniRezim> a druhou hodnotou nejvyšší hodnota součtu hodnoty
// elementu <nsesss:DatumDoruceni> nebo <nsesss:DatumVytvoreni> (v závislosti na
// tom, zda jde o doručený nebo vlastní dokument), 1 a hodnoty elementu
// <nsesss:SkartacniLhuta> jakékoli dětské entity dokument (nsesss:Dokument>).
//
public class Pravidlo65 extends K06PravidloBase {

    static final public String OBS65 = "obs65";

    public Pravidlo65() {
        super(OBS65,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je rovna nebo větší vyšší hodnotě, přičemž jednou hodnotou je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> a druhou hodnotou nejvyšší hodnota součtu hodnoty elementu <nsesss:DatumDoruceni> nebo <nsesss:DatumVytvoreni> (v závislosti na tom, zda jde o doručený nebo vlastní dokument), 1 a hodnoty elementu <nsesss:SkartacniLhuta> jakékoli dětské entity dokument (nsesss:Dokument>).",
                "Uveden je chybně rok skartační operace u dílu nebo spisu (počítá se jak podle roku spouštěcí události + 1 + skartační lhůta, tak podle roku skartační operace u dokumentů - záleží na tom, co je vyšší).",
                "§ 15 odst. 5 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        // zjistit ze spisu / dilu (vse povine)
        // - nsesss:EvidencniUdaje a nsesss:VyrizeniUzavreni
        // - nsesss:RokSpousteciUdalosti
        // - nsesss:DatumVytvoreni

        // Priklad: 
        // - zachovani do 2025 - rok spousteci udalosti
        // - 

        // TODO: kontrola vsech dokumentu na skartacni lhutu
        //  soucasne overeni, ze obsahují element nsesss:Vyrizeni (volitelne), hledame datum 
        // Kontrola: zda vyrizeni < vytvoreni rodice - detekce umeleho rodice     

        // základní entita obsahuje nsesss:DatumVytvoreni s vyšší hodnotou než je je nejvyšší hodnota elementu 
        // nsesss:Vyrizeni dětských entit dokument a 
        // současně obsahuje základní entita nsesss:RokSpousteciUdalosti se stejnou hodnotou jako je hodnota 
        // roku uvedená v elementu nsesss:Datum v hierarchii elementů  
        // zakl entity.
        List<Element> dokumenty = metsParser.getDokumenty();

        // dokumenty 
        if (dokumenty == null || dokumenty.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntita = zakladniEntity.get(i);
            String nazevZakladniEntity = elZakladniEntita.getNodeName();
            if (nazevZakladniEntity.equals(NsessV3.SPIS) || nazevZakladniEntity.equals(NsessV3.DIL)) {
                kontrolaSpisuDilu(elZakladniEntita, dokumenty);
            }
        }
    }

    private void kontrolaSpisuDilu(Element elZakladniEntita, List<Element> dokumenty) {
        int rokSkartacniOperace = this.context.getLocalDate().getYear();

        Element elRokSpousteciUdalosti = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE,
                                                                NsessV3.VYRAZOVANI,
                                                                NsessV3.DATACE_VYRAZENI,
                                                                NsessV3.ROK_SPOUSTECI_UDALOSTI);
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

        Element skartacniLhutaNode = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                                                          NsessV3.SKARTACNI_REZIM, NsessV3.SKARTACNI_LHUTA);
        if (skartacniLhutaNode == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta>. "
                    + getJmenoIdentifikator(elZakladniEntita), elZakladniEntita,
                        kontrola.getEntityId(elZakladniEntita));
        }
        int skartacniLhutaSpis = 0;
        try {
            skartacniLhutaSpis = Integer.parseInt(skartacniLhutaNode.getTextContent());
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Hodnota roku elementu <nsesss:SkartacniLhuta> je uvedena ve špatném formátu: "
                                + skartacniLhutaNode.getTextContent() + ". " + getJmenoIdentifikator(elZakladniEntita),
                        skartacniLhutaNode, kontrola.getEntityId(elZakladniEntita));
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
        Element elSkartacniLhutaDokumentu = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE,
                                                                   NsessV3.VYRAZOVANI,
                                                                   NsessV3.SKARTACNI_REZIM,
                                                                   NsessV3.SKARTACNI_LHUTA);
        if (elSkartacniLhutaDokumentu == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniLhuta>. "
                    + getJmenoIdentifikator(dokument),
                        dokument, kontrola.getEntityId(dokument));
        }
        int lhutaDokumentu = 0;
        try {
            lhutaDokumentu = Integer.parseInt(elSkartacniLhutaDokumentu.getTextContent());
        } catch (NumberFormatException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Element <nsesss:SkartacniLhuta> obsahuje hodnotu roku ve špatném formátu. "
                                + getJmenoIdentifikator(dokument), elSkartacniLhutaDokumentu, kontrola
                                        .getEntityId(dokument));
        }

        Element datumVznikuDokumentuNode = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, NsessV3.PUVOD,
                                                                  NsessV3.VLASTNI_DOKUMENT, NsessV3.DATUM_VYTVORENI);
        if (datumVznikuDokumentuNode == null) {
            datumVznikuDokumentuNode = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, NsessV3.PUVOD,
                                                              NsessV3.DORUCENY_DOKUMENT, NsessV3.DATUM_DORUCENI);
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
                                + ", je roven nejvyšší hodnotě elementu <nsesss:RokSkartacniOperace> elementu <nsesss:Dokument>: "
                                + minLhutaDokumentu
                                + ". Nerovná se však hodnotě elementu <nsesss:RokSkartacniOperace> základní entity: "
                                + rokSkartacniOperace + ". " + getJmenoIdentifikator(zakladniEntita)
                                + " "
                                + getJmenoIdentifikator(dokument),
                        getMistoChyby(zakladniEntita) + " " + getMistoChyby(dokument),
                        kontrola.getEntityId(listElementu));
        }
    }
}
