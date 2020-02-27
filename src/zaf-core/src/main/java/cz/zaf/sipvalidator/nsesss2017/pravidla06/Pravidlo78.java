package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo78 extends K06PravidloBase {

    public Pravidlo78(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS78,
                "Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
                "Chybí informace o skartačním řízení.",
                "Příloha č. 2 NSESSS, ř. 1228.");
    }

    //OBSAHOVÁ č.78 Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            ArrayList<Node> skartacniRizeni = ValuesGetter.getAllInNode(zakladnientita, "nsesss:SkartacniRizeni",
                                                                        metsParser.getDocument());
            if (skartacniRizeni == null || skartacniRizeni.isEmpty()) {
                return nastavChybu("Nenalezen element <nsesss:SkartacniRizeni>. " + getJmenoIdentifikator(
                                                                                                         zakladnientita),
                                   getMistoChyby(zakladnientita));
            }
            if (skartacniRizeni.size() != 1) {
                return nastavChybu("Element <nsesss:SkartacniRizeni> je v základní entitě uveden vícekrát. "
                        + getJmenoIdentifikator(zakladnientita), getMistoChyby(zakladnientita));
            }
            Node node = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                               "nsesss:SkartacniRizeni");
            if (node == null) {
                return nastavChybu("Element <nsesss:SkartacniRizeni> není správně zatříděn. " + getJmenoIdentifikator(
                                                                                                                     zakladnientita),
                                   getMistoChyby(skartacniRizeni.get(0)));
            }
        }
        return true;
    }

}
