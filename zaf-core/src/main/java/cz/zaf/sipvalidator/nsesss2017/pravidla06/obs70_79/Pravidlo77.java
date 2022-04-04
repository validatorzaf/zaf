package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import org.apache.commons.lang.StringUtils;

public class Pravidlo77 extends K06PravidloBase {

    static final public String OBS77 = "obs77";

    public Pravidlo77() {
        super(OBS77,
                "Pokud základní entita obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom obsahuje v hierarchii dětských elementů <nsesss:Vyrazovani> a <nsesss:SkartacniRizeni> element <nsesss:Mnozstvi> s neprázdnou hodnotou.",
                "Chybí množství dílu, spisu nebo dokumentu v analogové podobě.",
                "Příloha č. 2 NSESSS, ř. 1006.");
    }

    //OBSAHOVÁ č.77 Pokud základní entita obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, 
    // <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom obsahuje v hierarchii 
    // dětských elementů <nsesss:Vyrazovani> a <nsesss:SkartacniRizeni> element <nsesss:Mnozstvi> s neprázdnou hodnotou.",
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element zakladnientita = zakladniEntity.get(i);
            Element analogovy = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, NsessV3.MANIPULACE,
                    NsessV3.ANALOGOVY_DOKUMENT);
            if (analogovy != null) {
                String analogovyZakladni = analogovy.getTextContent();
                if (analogovyZakladni.equals("ano")) {
                    Element mnozstvi = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                            NsessV3.SKARTACNI_RIZENI, NsessV3.MNOZSTVI);
                    if (mnozstvi == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Mnozstvi> základní entity. "
                                + getJmenoIdentifikator(zakladnientita),
                                zakladnientita, kontrola.getEntityId(zakladnientita));
                    } else {
                        String hodnotaMnozstvi = mnozstvi.getTextContent();
                        if (StringUtils.isBlank(hodnotaMnozstvi)) {
                            nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:Mnozstvi> obsahuje prázdnou hodnotu. "
                                    + getJmenoIdentifikator(zakladnientita),
                                    mnozstvi, kontrola.getEntityId(zakladnientita));
                        }
                    }
                }
            }
        }
    }

}
