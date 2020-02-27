package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo86 extends K06PravidloBase {

    public Pravidlo86(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS86,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:MaterskeEntity>.",
                "Chybí zatřídění dokumentu.",
                "Příloha č. 2 NSESSS, ř. 1397.");
    }

    //OBSAHOVÁ č.86 Pokud je základní entitou dokument (<nsesss:Dokument>), 
    // obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> 
    // element <nsesss:MaterskeEntity>."
    @Override
    protected boolean kontrolaPravidla() {

        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals("nsesss:Dokument")) {
                Node node = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                   "nsesss:MaterskeEntity");
                if (node == null) {
                    return nastavChybu("Nenalezen element <nsesss:MaterskeEntity>. " + getJmenoIdentifikator(
                                                                                                            zakladnientita),
                                       getMistoChyby(zakladnientita));
                }
            }
        }
        return true;
    }

}
