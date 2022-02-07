package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo69 extends K06PravidloBaseOld {

    static final public String OBS69 = "obs69";

    public Pravidlo69() {
        super(OBS69,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom její element <nsesss:EvidencniUdaje> obsahuje dětský element <nsesss:Vyrizeni>.",
                "Chybí vyřízení dokumentu.",
                "Příloha č. 2 NSESSS, ř. 421.");
    }

    //OBSAHOVÁ č.69 Pokud je základní entitou dokument (<nsesss:Dokument>), potom její element <nsesss:EvidencniUdaje> obsahuje dětský element <nsesss:Vyrizeni>.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if(zakladniEntity==null) {
            return false;
        }
        
        for (Element ze : zakladniEntity) {
            if (ze.getNodeName().equals("nsesss:Dokument")) {
                Node node = ValuesGetter.getXChild(ze, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRIZENI);
                if (node == null) {
                    return nastavChybu("Nenalezen element <nsesss:Vyrizeni>. " + getJmenoIdentifikator(ze),
                                       ze);
                }
            }
        }
        return true;
    }
}
