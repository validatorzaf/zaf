package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo61 extends K06PravidloBase {

    static final public String OBS61 = "obs61";

    public Pravidlo61(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo61.OBS61,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
                "Chybí doručené množství dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 365.");
    }

    //OBSAHOVÁ č.61 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            return false;
        }
        for (int i = 0; i < dokumenty.size(); i++) {
            Node dokument = dokumenty.get(i);
            Node ad = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace",
                                             "nsesss:AnalogovyDokument");
            if (ad == null) {
                return nastavChybu("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + kontrola
                        .getIdentifikatory(
                                                                                                                 dokument)
                        + ".", dokument);
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if (hodnotaAnalogovyDokument.equals("ano") && ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje",
                                                                                 "nsesss:Puvod",
                                                                                 "nsesss:DorucenyDokument") != null) {
                if (ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument",
                                           "nsesss:DoruceneMnozstvi") == null) {
                    return nastavChybu("Nenalezen povinný element <nsesss:DoruceneMnozstvi>. Dokumentu "
                            + kontrola.getIdentifikatory(dokument) + ".", dokument);
                }
                if (ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument",
                                           "nsesss:DoruceneMnozstvi") != null) {
                    String s = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod",
                                                      "nsesss:DorucenyDokument", "nsesss:DoruceneMnozstvi")
                            .getTextContent();
                    if (!HelperString.hasContent(s)) {
                        return nastavChybu("Element <nsesss:DoruceneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu "
                                + kontrola.getIdentifikatory(dokument) + ".", dokument);
                    }
                }
            }
        }
        return true;
    }
}