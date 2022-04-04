package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
            Element ad = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, NsessV3.MANIPULACE,
                    NsessV3.ANALOGOVY_DOKUMENT);
            if (ad == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu "
                        + kontrola.getIdentifikatory(dokument) + ".", dokument, kontrola.getEntityId(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if ("ne".equals(hodnotaAnalogovyDokument)) {
                if (ValuesGetter.getXChild(dokument, "nsesss:Komponenty") == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen povinný element <nsesss:Komponenty>. Dokumentu "
                            + kontrola.getIdentifikatory(dokument) + ".", dokument, kontrola.getEntityId(dokument));
                }
            }
        }
    }
}
