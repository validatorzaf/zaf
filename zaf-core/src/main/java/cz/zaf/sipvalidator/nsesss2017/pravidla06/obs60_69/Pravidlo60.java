package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo60 extends K06PravidloBaseOld {

    static final public String OBS60 = "obs60";

    public Pravidlo60() {
        super(OBS60,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <nsesss:Dokument> dětský element <nsesss:Komponenty>.",
                "Chybí popis komponenty (počítačového souboru) dokumentu v digitální podobě.",
                "Příloha č. 2 NSESSS, ř. 45.");
    }

    //OBSAHOVÁ č.60 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <nsesss:Dokument> dětský element <nsesss:Komponenty>.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            return false;
        }
        for (int i = 0; i < dokumenty.size(); i++) {
            Node dokument = dokumenty.get(i);
            Node ad = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Manipulace",
                                             "nsesss:AnalogovyDokument");
            if (ad == null) {
                return nastavChybu("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu "
                        + kontrola.getIdentifikatory(dokument) + ".", dokument);
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if (hodnotaAnalogovyDokument.equals("ne")) {
                if (ValuesGetter.getXChild(dokument, "nsesss:Komponenty") == null) {
                    return nastavChybu("Nenalezen povinný element <nsesss:Komponenty>. Dokumentu "
                            + kontrola.getIdentifikatory(dokument) + ".", dokument);
                }
            }
        }
        return true;
    }
}
