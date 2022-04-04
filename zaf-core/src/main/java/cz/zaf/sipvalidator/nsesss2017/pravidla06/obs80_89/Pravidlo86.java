package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
                    Element elMaterskeEntity = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                            NsessV3.MATERSKE_ENTITY);
                    if (elMaterskeEntity == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:MaterskeEntity>. " + getJmenoIdentifikator(zakladnientita),
                                getMistoChyby(zakladnientita), kontrola.getEntityId(zakladnientita));
                    }
                }
            }
        }
    }

}
