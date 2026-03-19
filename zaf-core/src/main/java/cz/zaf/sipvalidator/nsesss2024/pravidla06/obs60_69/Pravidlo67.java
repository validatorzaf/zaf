package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.67 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů 
// <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, 
// která je rovna nejvyššímu skartačnímu znaku dětské entity spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>), 
// přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.
public class Pravidlo67 extends K06PravidloBase {

    static final public String OBS67 = "obs67";

    public Pravidlo67() {
        super(OBS67,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
                "Uveden je chybně skartační znak u dílu nebo spisu (stanovuje se podle nejvyššího skartačního znaku dokumentu).",
                "Požadavek 6.1.8 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = metsParser.getZakladniEntity();
        for (Element zakladniEntita : zakladniEntity) {
            String zeName = zakladniEntita.getNodeName();
            if (zeName.equals(NsesssV4.DIL)) {
                String skartacniZnak = getSkartacniZnak(zakladniEntita);
                Element elSpisy = ValuesGetter.getXChild(zakladniEntita, NsesssV4.SPISY);
                if (elSpisy != null) {
                    List<Element> listSpisy = ValuesGetter.getChildNodes(elSpisy, NsesssV4.SPIS);
                    for (Element elSpis : listSpisy) {
                        String skZnakSpis = getSkartacniZnak(elSpis);
                        if (!compareSkartacniZnaky(skartacniZnak, skZnakSpis)) {
                            List<Element> all = new ArrayList<>();
                            all.add(zakladniEntita);
                            all.add(elSpis);
                            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                                    "Element <nsesss:Dil> obsahuje skartační znak: " + skartacniZnak + ". V něm zatříděný element <nsesss:Spis> obsahuje skartační znak: " + skZnakSpis + ".",
                                    getMistoChyby(zakladniEntita) + " " + getMistoChyby(elSpis), kontrola.getEntityId(all));
                        }
                        checkDokumenty(elSpis, skZnakSpis);
                    }
                }
                checkDokumenty(zakladniEntita, skartacniZnak);
            }
            if (zeName.equals(NsesssV4.SPIS)) {
                String skartacniZnak = getSkartacniZnak(zakladniEntita);
                checkDokumenty(zakladniEntita, skartacniZnak);
            }
        }
    }

    private void checkDokumenty(Element elParent, String skartacniZnakParent) {
        String parentName = elParent.getNodeName();
        Element elDokumenty = ValuesGetter.getXChild(elParent, NsesssV4.DOKUMENTY);
        if (elDokumenty != null) {
            List<Element> listDokumenty = ValuesGetter.getChildNodes(elDokumenty, NsesssV4.DOKUMENT);
            for (Element elDokument : listDokumenty) {
                String skZnakDokument = getSkartacniZnak(elDokument);
                if (!compareSkartacniZnaky(skartacniZnakParent, skZnakDokument)) {
                    List<Element> all = new ArrayList<>();
                    all.add(elParent);
                    all.add(elDokument);
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                            "Element <" + parentName + "> obsahuje skartační znak: " + skartacniZnakParent + ". V něm zatříděný element <nsesss:Dokument> obsahuje skartační znak: " + skZnakDokument + ".",
                            getMistoChyby(elParent) + " " + getMistoChyby(elDokument), kontrola.getEntityId(all));
                }
            }
        }
    }

    private String getSkartacniZnak(Element elEntita) {
        Element elSkartacniZnak = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRAZOVANI, NsesssV4.SKARTACNI_REZIM, NsesssV4.SKARTACNI_ZNAK);
        if (elSkartacniZnak == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen dětský element <nsesss:SkartacniZnak> elementu <" + elEntita.getNodeName() + ">.", getMistoChyby(elEntita), kontrola.getEntityId(elEntita));
        }
        return elSkartacniZnak.getTextContent();
    }

    private boolean compareSkartacniZnaky(String nadrazeny, String podrizeny) {
        if (nadrazeny.equals("S") && (podrizeny.equals("A") || podrizeny.equals("V"))) {
            return false;
        }
        return !(nadrazeny.equals("V") && (podrizeny.equals("A")));
    }
}
