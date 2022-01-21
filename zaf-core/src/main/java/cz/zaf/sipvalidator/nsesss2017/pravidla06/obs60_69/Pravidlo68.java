package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo68 extends K06PravidloBase {

    static final public String OBS68 = "obs68";

    public Pravidlo68() {
        super(OBS68,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>), která je rodičovskou entitou spisu (<nsesss:Spis>) nebo dokumentu (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> element <nsesss:SkartacniRezim>.",
                "Chybí skartační režim věcné skupiny.",
                "§ 15 odst. 2 vyhlášky č. 259/2012 Sb.; příloha č. 2 NSESSS, ř. 1250.");
    }

    //OBSAHOVÁ č.68 Každá entita věcná skupina (<nsesss:VecnaSkupina>), která je rodičovskou entitou spisu (<nsesss:Spis>) nebo dokumentu (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> element <nsesss:SkartacniRezim>.
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (Node zakladnientita: zakladniEntity) {
            Node vecnaskupina;
            if (zakladnientita.getNodeName().equals("nsesss:Dokument") ) {
                vecnaskupina = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE,
                                                      "nsesss:Trideni", "nsesss:MaterskeEntity",
                                                      NsessV3.VECNA_SKUPINA);
            } else 
            if (zakladnientita.getNodeName().equals("nsesss:Spis")) {
                vecnaskupina = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE,
                                                      "nsesss:Trideni", "nsesss:MaterskaEntita",
                                                      NsessV3.VECNA_SKUPINA);
            } else {
                return true;
            }
            if (vecnaskupina == null) {
                return nastavChybu("Nenalezena rodičovská entita věcná skupina základní entity. "
                            + getJmenoIdentifikator(zakladnientita), zakladnientita);
            }
            Node sr = ValuesGetter.getXChild(vecnaskupina, NsessV3.EVIDENCNI_UDAJE, "nsesss:Vyrazovani",
                                             "nsesss:SkartacniRezim");
            if (sr == null) {
                return nastavChybu("Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(vecnaskupina),
                                       vecnaskupina);
            }
        }
        return true;
    }

}
