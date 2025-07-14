package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.67 Pokud je základní entitou díl (<nsesss:Dil>) nebo 
// spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, 
// <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, 
// která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), 
// přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
public class Pravidlo67 extends K06PravidloBase {
    
    static final public String OBS67 = "obs67";
    
    public Pravidlo67() {
        super(OBS67,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
                "Uveden je chybně skartační znak u dílu nebo spisu (stanovuje se podle nejvyššího skartačního znaku dokumentu).",
                "§ 15 odst. 5 vyhlášky č. 259/2012 Sb.");
    }
    
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        
        Set<Element> zakladniDokumenty = new HashSet<>();
        List<Element> spisyDily = new ArrayList<>();
        for (Element zakladnientita : zakladniEntity) {
            String zeName = zakladnientita.getNodeName();
            if (NsesssV3.SPIS.equals(zeName) || NsesssV3.DIL.equals(zeName)) {
                spisyDily.add(zakladnientita);
            } else if (NsesssV3.DOKUMENT.equals(zeName)) {
                zakladniDokumenty.add(zakladnientita);
            }
        }
        if (!spisyDily.isEmpty()) {
            List<Element> dokumenty = metsParser.getDokumenty();
            if (dokumenty == null || dokumenty.isEmpty()) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný dětský element <nsesss:Dokument>.",
                        spisyDily, kontrola.getEntityId(spisyDily));
            }
            // priprava sk. znaku dle zakladnich entit
            Map<Element, Set<String>> skZnaky = new HashMap<>();
            for (Element dokument : dokumenty) {
                // zakladni dokumenty se nezahrnuji
                if (zakladniDokumenty.contains(dokument)) {
                    continue;
                }

                // nalezeni rodicovske zakl. entity
                Element dokumentyNode = ValuesGetter.getXParent(dokument, NsesssV3.DOKUMENTY);
                if (dokumentyNode == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen rodičovský element <nsesss:Dokumenty> elementu <nsesss:Dokument>. "
                            + getJmenoIdentifikator(dokument),
                            dokument, kontrola.getEntityId(dokument));
                }
                Element rodicNode = (Element) dokumentyNode.getParentNode();
                if (rodicNode == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen rodičovský element elementu <nsesss:Dokumenty>. "
                            + getJmenoIdentifikator(dokument),
                            dokument, kontrola.getEntityId(dokument));
                }
                Set<String> skZnakyDokumentu = skZnaky.computeIfAbsent(rodicNode, n -> new HashSet<>());
                
                Element elSkartacniZnak = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.VYRAZOVANI,
                        NsesssV3.SKARTACNI_REZIM, NsesssV3.SKARTACNI_ZNAK);
                if (elSkartacniZnak == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen dětský element <nsesss:SkartacniZnak> elementu <nsesss:Dokument>. "
                            + getJmenoIdentifikator(dokument),
                            dokument, kontrola.getEntityId(rodicNode));
                }
                String znak = elSkartacniZnak.getTextContent();
                skZnakyDokumentu.add(znak);
            }
            
            for (Element spisDil : spisyDily) {
                Set<String> skZnakyDokumentu = skZnaky.get(spisDil);
                if (skZnakyDokumentu == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:SkartacniZnak> dětských elementů <nsesss:Dokument> základní entity."
                            + getJmenoIdentifikator(spisDil),
                            spisDil, kontrola.getEntityId(spisDil));
                }
                kontrola(spisDil, skZnakyDokumentu);
            }
        }
    }
    
    private void kontrola(Element zakladnientita, Set<String> skZnakyDokumentu) {
        Element n = ValuesGetter.getXChild(zakladnientita, NsesssV3.EVIDENCNI_UDAJE,
                NsesssV3.VYRAZOVANI,
                NsesssV3.SKARTACNI_REZIM, NsesssV3.SKARTACNI_ZNAK);
        if (n == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen dětský element <nsesss:SkartacniZnak> základní entity. "
                    + getJmenoIdentifikator(zakladnientita),
                    zakladnientita, kontrola.getEntityId(zakladnientita));
        }
        String skZnakME = n.getTextContent();
        
        switch (skZnakME) {
            case "A":
                if (!skZnakyDokumentu.contains("A")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Spis se skartačním znakem A neobsahuje žádný dokument se skartačním znakem A. "
                            + getJmenoIdentifikator(zakladnientita),
                            n, kontrola.getEntityId(zakladnientita));
                }
                break;
            case "V":
                if (skZnakyDokumentu.contains("A")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Spis se skartačním znakem V obsahuje dokument se skartačním znakem A. "
                            + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(zakladnientita),
                            zakladnientita, kontrola.getEntityId(zakladnientita));
                }
                if (!skZnakyDokumentu.contains("V")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Spis se skartačním znakem V neobsahuje žádný dokument se skartačním znakem V. "
                            + getJmenoIdentifikator(zakladnientita), n, kontrola.getEntityId(zakladnientita));
                }
                break;
            case "S":
                if (skZnakyDokumentu.contains("A") || skZnakyDokumentu.contains("V")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Spis se skartačním znakem S obsahuje dokument se skartačním znakem A nebo V. "
                            + getJmenoIdentifikator(zakladnientita), zakladnientita, kontrola.getEntityId(zakladnientita));
                    
                }
                break;
        }
    }
}
