package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo68 extends K06PravidloBase {

    static final public String OBS68 = "obs68";

    public Pravidlo68(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo68.OBS68,
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

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals("nsesss:Dokument") || zakladnientita.getNodeName().equals(
                                                                                                              "nsesss:Spis")) {
                Node vecnaskupina = ValuesGetter.getFirstInNode(zakladnientita, "nsesss:VecnaSkupina", metsParser
                        .getDocument());
                if (vecnaskupina == null) {
                    return nastavChybu("Nenalezena rodičovská entita věcná skupina základní entity. "
                            + getJmenoIdentifikator(zakladnientita), zakladnientita);
                }
                Node sr = ValuesGetter.getXChild(vecnaskupina, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                                 "nsesss:SkartacniRezim");
                if (sr == null) {
                    return nastavChybu("Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(
                                                                                                            vecnaskupina),
                                       vecnaskupina);
                }
            }
        }
        return true;
    }

}
