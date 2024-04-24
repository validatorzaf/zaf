package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo78 extends K06PravidloBase {

    static final public String OBS78 = "obs78";

    public Pravidlo78() {
        super(OBS78,
                "Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
                "Chybí informace o skartačním řízení.",
                "Příloha č. 2 NSESSS, ř. 1228.");
    }

    //OBSAHOVÁ č.78 Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        List<Element> skartacniRizeni = metsParser.getNodes(NsesssV3.SKARTACNI_RIZENI);
        Set<Element> skartacniRizeniSet = new HashSet<>(skartacniRizeni);

        for (Element zakladnientita : zakladniEntity) {
            Element node = ValuesGetter.getXChild(zakladnientita, NsesssV3.EVIDENCNI_UDAJE,
                    NsesssV3.VYRAZOVANI, NsesssV3.SKARTACNI_RIZENI);
            if (node == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <nsesss:SkartacniRizeni> není správně zatříděn. " + getJmenoIdentifikator(zakladnientita),
                        zakladnientita, kontrola.getEntityId(zakladnientita));
            }
            if (!skartacniRizeniSet.remove(node)) {
                nastavChybu(BaseCode.CHYBNY_ELEMENT, "Element <nsesss:SkartacniRizeni> není správně zatříděn. " + getJmenoIdentifikator(zakladnientita),
                        zakladnientita, kontrola.getEntityId(zakladnientita));
            }
        }

        if (!skartacniRizeniSet.isEmpty()) {
            nastavChybu(BaseCode.CHYBNY_ELEMENT, "Nalezen chybně použitý element <nsesss:SkartacniRizeni>.",
                    new ArrayList<>(skartacniRizeniSet));
        }
    }

}
