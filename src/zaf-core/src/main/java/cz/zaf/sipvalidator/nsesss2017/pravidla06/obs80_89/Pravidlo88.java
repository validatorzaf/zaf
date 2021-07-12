package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo88 extends K06PravidloBase {

    static final public String OBS88 = "obs88";

    public Pravidlo88() {
        super(OBS88,
                "Pokud element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:Vyrizeni> obsahuje dětský element <nsesss:DatumOdeslani>, pak element <nsesss:Vyrizeni> obsahuje element <nsesss:OdeslaneMnozstvi> s neprázdnou hodnotou.",
                "Chybí množství odeslaného dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 1476.");
    }

    //OBSAHOVÁ č.88 Pokud element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, 
    // <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:Vyrizeni> 
    // obsahuje dětský element <nsesss:DatumOdeslani>, pak element <nsesss:Vyrizeni> obsahuje element 
    // <nsesss:OdeslaneMnozstvi> s neprázdnou hodnotou."
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> dokumenty = predpokladDokumenty();
        if (CollectionUtils.isEmpty(dokumenty)) {
            return false;
        }

        for (int i = 0; i < dokumenty.size(); i++) {
            Node dokument = dokumenty.get(i);
            Node analog = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace",
                                                 "nsesss:AnalogovyDokument");
            if (analog != null) {
                String hodnota = analog.getTextContent();
                if (hodnota.equals("ano")) {
                    Node datumOdeslani = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", JmenaElementu.VYRIZENI,
                                                                "nsesss:DatumOdeslani");
                    if (datumOdeslani != null) {
                        Node node = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", JmenaElementu.VYRIZENI,
                                                           "nsesss:OdeslaneMnozstvi");
                        if (node == null) {
                            return nastavChybu("Nenalezen element <nsesss:OdeslaneMnozstvi>. "
                                    + getJmenoIdentifikator(dokument), getMistoChyby(dokument));
                        }
                        if (!HelperString.hasContent(node.getTextContent())) {
                            return nastavChybu("Element <nsesss:OdeslaneMnozstvi> obsahuje prázdnou hodnotu. "
                                    + getJmenoIdentifikator(dokument), getMistoChyby(node));
                        }
                    }
                }
            }
        }
        return true;
    }

}
