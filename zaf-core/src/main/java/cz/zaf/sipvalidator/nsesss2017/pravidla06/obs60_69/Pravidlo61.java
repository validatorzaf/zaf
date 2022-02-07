package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo61 extends K06PravidloBaseOld {

    static final public String OBS61 = "obs61";

    public Pravidlo61() {
        super(OBS61,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
                "Chybí doručené množství dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 365.");
    }

    //OBSAHOVÁ č.61 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Element> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            return false;
        }
        for (int i = 0; i < dokumenty.size(); i++) {
            Element dokument = dokumenty.get(i);
            Element ad = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Manipulace",
                                             "nsesss:AnalogovyDokument");
            if (ad == null) {
                return nastavChybu("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + kontrola
                        .getIdentifikatory(
                                                                                                                 dokument)
                        + ".", dokument);
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if (hodnotaAnalogovyDokument.equals("ano") && ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE,
                                                                                 "nsesss:Puvod",
                                                                                 "nsesss:DorucenyDokument") != null) {
                if (ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Puvod", "nsesss:DorucenyDokument",
                                           "nsesss:DoruceneMnozstvi") == null) {
                    return nastavChybu("Nenalezen povinný element <nsesss:DoruceneMnozstvi>. Dokumentu "
                            + kontrola.getIdentifikatory(dokument) + ".", dokument);
                }
                if (ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Puvod", "nsesss:DorucenyDokument",
                                           "nsesss:DoruceneMnozstvi") != null) {
                    String s = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Puvod",
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
