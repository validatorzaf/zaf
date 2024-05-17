package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo60 extends K06PravidloBase {

    static final public String OBS60 = "obs60";

    public Pravidlo60() {
        super(OBS60,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <nsesss:Dokument> dětský element <nsesss:Komponenty>.",
                "Chybí popis komponenty (počítačového souboru) dokumentu v digitální podobě.",
                "Příloha č. 2 NSESSS, ř. 45.");
    }

    //OBSAHOVÁ č.60 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <nsesss:Dokument> dětský element <nsesss:Komponenty>.",
    @Override
    protected void kontrola() {
        List<Element> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        }
        for (int i = 0; i < dokumenty.size(); i++) {
            Element dokument = dokumenty.get(i);
            Element ad = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.MANIPULACE,
                    NsesssV3.ANALOGOVY_DOKUMENT);
            if (ad == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu "
                        + K06_Obsahova.getIdentifikatory(dokument) + ".", dokument, K06_Obsahova.getEntityId(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if ("ne".equals(hodnotaAnalogovyDokument)) {
                if (ValuesGetter.getXChild(dokument, "nsesss:Komponenty") == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen povinný element <nsesss:Komponenty>. Dokumentu "
                            + K06_Obsahova.getIdentifikatory(dokument) + ".", dokument, K06_Obsahova.getEntityId(
                                                                                                                 dokument));
                }
            }
        }
    }
}
