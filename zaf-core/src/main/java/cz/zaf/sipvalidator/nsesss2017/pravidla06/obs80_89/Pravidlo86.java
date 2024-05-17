package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo86 extends K06PravidloBase {

    static final public String OBS86 = "obs86";

    public Pravidlo86() {
        super(OBS86,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:MaterskeEntity>.",
                "Chybí zatřídění dokumentu.",
                "Příloha č. 2 NSESSS, ř. 1397.");
    }

    //OBSAHOVÁ č.86 Pokud je základní entitou dokument (<nsesss:Dokument>), 
    // obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> 
    // element <nsesss:MaterskeEntity>."
    @Override
    protected void kontrola() {

        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        } else {
            for (int i = 0; i < zakladniEntity.size(); i++) {
                Element zakladnientita = zakladniEntity.get(i);
                if (zakladnientita.getNodeName().equals("nsesss:Dokument")) {
                    Element elMaterskeEntity = ValuesGetter.getXChild(zakladnientita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                            NsesssV3.MATERSKE_ENTITY);
                    if (elMaterskeEntity == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:MaterskeEntity>. " + getJmenoIdentifikator(zakladnientita),
                                getMistoChyby(zakladnientita), kontrola.getEntityId(zakladnientita));
                    }
                }
            }
        }
    }

}
