package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import org.apache.commons.lang3.StringUtils;

public class Pravidlo61 extends K06PravidloBase {

    static final public String OBS61 = "obs61";

    public Pravidlo61() {
        super(OBS61,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
                "Chybí doručené množství dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 365.");
    }

    //OBSAHOVÁ č.61 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
    @Override
    protected void kontrola() {
        List<Element> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        }
        for (int i = 0; i < dokumenty.size(); i++) {
            Element dokument = dokumenty.get(i);
            Element ad = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, NsessV3.MANIPULACE,
                    NsessV3.ANALOGOVY_DOKUMENT);
            if (ad == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + kontrola.getIdentifikatory(dokument) + ".",
                        dokument, kontrola.getEntityId(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            Element elDorucenyDokument = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, NsessV3.PUVOD, NsessV3.DORUCENY_DOKUMENT);
            if ("ano".equals(hodnotaAnalogovyDokument) && elDorucenyDokument != null) {
                Element elDoruceneMnozstvi = ValuesGetter.getXChild(elDorucenyDokument, NsessV3.DORUCENE_MNOZSTVI);
                if (elDoruceneMnozstvi == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen povinný element <nsesss:DoruceneMnozstvi>. Dokumentu "
                            + kontrola.getIdentifikatory(dokument) + ".", dokument, kontrola.getEntityId(dokument));
                }

                String hodnotaElDoruceneMnozstvi = elDoruceneMnozstvi.getTextContent();
                if (StringUtils.isBlank(hodnotaElDoruceneMnozstvi)) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:DoruceneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu "
                            + kontrola.getIdentifikatory(dokument) + ".", dokument, kontrola.getEntityId(dokument));
                }
            }
        }
    }
}
