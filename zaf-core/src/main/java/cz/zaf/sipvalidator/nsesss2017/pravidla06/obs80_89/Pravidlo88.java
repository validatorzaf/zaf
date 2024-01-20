package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import org.apache.commons.lang.StringUtils;

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
    protected void kontrola() {
        List<Element> dokumenty = predpokladDokumenty();
        if (CollectionUtils.isEmpty(dokumenty)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        }

        for (int i = 0; i < dokumenty.size(); i++) {
            Element dokument = dokumenty.get(i);

            Element evidUdaje = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE);
            if (evidUdaje == null) {
                continue;
            }
            Element analog = ValuesGetter.getXChild(evidUdaje, NsessV3.MANIPULACE, NsessV3.ANALOGOVY_DOKUMENT);
            if (analog != null) {
                String hodnota = analog.getTextContent();
                if (hodnota.equals("ano")) {
                    Element datumOdeslani = ValuesGetter.getXChild(evidUdaje, NsessV3.VYRIZENI,
                            NsessV3.DATUM_ODESLANI);
                    if (datumOdeslani != null) {
                        Element elOdeslaneMnozstvi = ValuesGetter.getXChild(evidUdaje, NsessV3.VYRIZENI,
                                NsessV3.ODESLANE_MNOZSTVI);
                        if (elOdeslaneMnozstvi == null) {
                            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:OdeslaneMnozstvi>. "
                                    + getJmenoIdentifikator(dokument), getMistoChyby(dokument), kontrola.getEntityId(dokument));
                        } else {
                            String hodnotaElOdeslaneMnozstvi = elOdeslaneMnozstvi.getTextContent();
                            if (StringUtils.isBlank(hodnotaElOdeslaneMnozstvi)) {
                                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:OdeslaneMnozstvi> obsahuje prázdnou hodnotu. "
                                        + getJmenoIdentifikator(dokument), getMistoChyby(elOdeslaneMnozstvi), kontrola.getEntityId(dokument));
                            }
                        }
                    }
                }
            }
        }
    }

}
