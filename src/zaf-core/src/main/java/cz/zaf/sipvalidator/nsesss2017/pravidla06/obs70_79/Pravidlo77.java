package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo77 extends K06PravidloBase {

    static final public String OBS77 = "obs77";

    public Pravidlo77() {
        super(OBS77,
                "Pokud základní entita obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom obsahuje v hierarchii dětských elementů <nsesss:Vyrazovani> a <nsesss:SkartacniRizeni> element <nsesss:Mnozstvi> s neprázdnou hodnotou.",
                "Chybí množství dílu, spisu nebo dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 1006.");
    }

    //OBSAHOVÁ č.77 Pokud základní entita obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, 
    // <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom obsahuje v hierarchii 
    // dětských elementů <nsesss:Vyrazovani> a <nsesss:SkartacniRizeni> element <nsesss:Mnozstvi> s neprázdnou hodnotou.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            Node analogovy = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Manipulace",
                                                    "nsesss:AnalogovyDokument");
            if (analogovy != null) {
                String analogovyZakladni = analogovy.getTextContent();
                if (analogovyZakladni.equals("ano")) {
                    Node mnozstvi = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                                           "nsesss:SkartacniRizeni", "nsesss:Mnozstvi");
                    if (mnozstvi == null) {
                        return nastavChybu("Nenalezen element <nsesss:Mnozstvi> základní entity. "
                                + getJmenoIdentifikator(zakladnientita), getMistoChyby(zakladnientita));
                    }
                    if (!HelperString.hasContent(mnozstvi.getTextContent())) {
                        return nastavChybu("Element <nsesss:Mnozstvi> obsahuje prázdnou hodnotu. "
                                + getJmenoIdentifikator(zakladnientita), getMistoChyby(mnozstvi));
                    }
                }
            }
        }
        return true;
    }

}
