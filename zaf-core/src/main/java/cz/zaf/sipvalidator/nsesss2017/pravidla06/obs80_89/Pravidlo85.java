package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo85 extends K06PravidloBase {
    
    static final public String OBS85 = "obs85";
    
    public Pravidlo85() {
        super(OBS85,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:UkladaciJednotka> s neprázdnou hodnotou.",
                "Chybí ukládací jednotka dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 1352.");
    }

    //OBSAHOVÁ č.85 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů 
    // <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, 
    // potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:UkladaciJednotka> s neprázdnou hodnotou."
    @Override
    public void kontrola() {
        List<Element> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        } else {
            for (int i = 0; i < dokumenty.size(); i++) {
                Element dokument = dokumenty.get(i);
                Element analog = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.MANIPULACE,
                        NsesssV3.ANALOGOVY_DOKUMENT);
                if (analog != null) {
                    String hodnota = analog.getTextContent();
                    if (hodnota.equals("ano")) {
                        Element uklalaciJednotka = ValuesGetter.getSourozencePrvnihoSeJmenem(analog,
                                NsesssV3.UKLADACI_JEDNOTKA);
                        if (uklalaciJednotka == null) {
                            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:UkladaciJednotka>. " + getJmenoIdentifikator(
                                    dokument),
                                    getMistoChyby(analog), kontrola.getEntityId(dokument));
                        } else {
                            String hodnotaElUkladJednotky = uklalaciJednotka.getTextContent();
                            if (StringUtils.isBlank(hodnotaElUkladJednotky)) {
                                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:UkladaciJednotka> obsahuje prázdnou hodnotu. "
                                        + getJmenoIdentifikator(dokument), getMistoChyby(uklalaciJednotka),
                                        kontrola.getEntityId(dokument));
                            }
                        }
                    }
                }
            }
        }
    }
    
}
