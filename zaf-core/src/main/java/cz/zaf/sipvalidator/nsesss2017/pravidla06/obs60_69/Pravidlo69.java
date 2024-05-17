package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo69 extends K06PravidloBase {
    
    static final public String OBS69 = "obs69";
    
    public Pravidlo69() {
        super(OBS69,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom její element <nsesss:EvidencniUdaje> obsahuje dětský element <nsesss:Vyrizeni>.",
                "Chybí vyřízení dokumentu.",
                "Příloha č. 2 NSESSS, ř. 421.");
    }

    //OBSAHOVÁ č.69 Pokud je základní entitou dokument (<nsesss:Dokument>), potom její element <nsesss:EvidencniUdaje> obsahuje dětský element <nsesss:Vyrizeni>.",
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        
        for (Element ze : zakladniEntity) {
            if (ze.getNodeName().equals(NsesssV3.DOKUMENT)) {
                Element elVyrizeni = ValuesGetter.getXChild(ze, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.VYRIZENI);
                if (elVyrizeni == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Vyrizeni>. " + getJmenoIdentifikator(ze),
                            ze, kontrola.getEntityId(ze));
                }
            }
        }
    }
}
