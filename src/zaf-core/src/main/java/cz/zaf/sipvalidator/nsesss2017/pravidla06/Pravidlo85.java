package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo85 extends K06PravidloBase {

    public Pravidlo85(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS85,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:UkladaciJednotka> s neprázdnou hodnotou.",
                "Chybí ukládací jednotka dokumentu v analogově podobě.",
                "Příloha č. 2 NSESSS, ř. 1352.");
    }

    //OBSAHOVÁ č.85 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů 
    // <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, 
    // potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:UkladaciJednotka> s neprázdnou hodnotou."
    @Override
    public boolean kontrolaPravidla() {
        List<Node> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            return false;
        }

        for (int i = 0; i < dokumenty.size(); i++) {
            Node dokument = dokumenty.get(i);
            Node analog = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace",
                                                 "nsesss:AnalogovyDokument");
            if (analog != null) {
                String hodnota = analog.getTextContent();
                if (hodnota.equals("ano")) {
                    Node uklalaciJednotka = ValuesGetter.getSourozencePrvnihoSeJmenem(analog,
                                                                                      "nsesss:UkladaciJednotka");
                    if (uklalaciJednotka == null) {
                        return nastavChybu("Nenalezen element <nsesss:UkladaciJednotka>. " + getJmenoIdentifikator(
                                                                                                                  dokument),
                                           getMistoChyby(analog));
                    }
                    if (StringUtils.isBlank(uklalaciJednotka.getTextContent())) {
                        return nastavChybu("Element <nsesss:UkladaciJednotka> obsahuje prázdnou hodnotu. "
                                + getJmenoIdentifikator(dokument), getMistoChyby(uklalaciJednotka));
                    }
                }
            }
        }
        return true;
    }

}
