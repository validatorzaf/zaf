package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.61a Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a 
// současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:VlastniDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:VytvoreneMnozstvi> s neprázdnou hodnotou.
public class Pravidlo61a extends K06PravidloBase {

    static final public String OBS61A = "obs61a";

    public Pravidlo61a() {
        super(OBS61A,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:VlastniDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:VytvoreneMnozstvi> s neprázdnou hodnotou.",
                "Chybí množství vlastního dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 1208.");
    }

    @Override
    protected void kontrola() {
        List<Element> dokuments = metsParser.getDokumenty();
        if (dokuments == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        }
        for (Element dokument : dokuments) {
            Element ad = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.MANIPULACE, NsesssV3.ANALOGOVY_DOKUMENT);
            if (ad == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + kontrola.getIdentifikatory(dokument) + ".",
                        dokument, kontrola.getEntityId(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            Element elVlastniDokument = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.PUVOD, NsesssV3.VLASTNI_DOKUMENT);
            if ("ano".equals(hodnotaAnalogovyDokument) && elVlastniDokument != null) {
                Element elMnozstvi = ValuesGetter.getXChild(elVlastniDokument, NsesssV3.VYTVORENE_MNOZSTVI);
                if (elMnozstvi == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen povinný element <nsesss:VytvoreneMnozstvi>. Dokumentu " + kontrola.getIdentifikatory(dokument) + ".",
                            dokument, kontrola.getEntityId(dokument));
                } else {
                    String hodnotaElMnozstvi = elMnozstvi.getTextContent();
                    if (StringUtils.isBlank(hodnotaElMnozstvi)) {
                        nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:VytvoreneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu " + kontrola.getIdentifikatory(dokument) + ".",
                                elMnozstvi, kontrola.getEntityId(dokument));
                    }
                }
            }
        }
    }

}
