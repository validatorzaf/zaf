package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.Calendar;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo66 extends K06PravidloBase {

    static final public String OBS66 = "obs66";

    public Pravidlo66(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo66.OBS66,
                "Pokud je základní entitou díl (<nsesss:Dil>), spis (<nsesss:Spis> nebo dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je menší nebo rovna aktuálnímu roku.",
                "U dílu, spisu nebo dokumentu nelze provést skartační řízení, protože ještě nenadešel uváděný rok skartační operace.",
                "§ 20 odst. 1 vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.66 Pokud je základní entitou díl (<nsesss:Dil>), spis (<nsesss:Spis> nebo dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je menší nebo rovna aktuálnímu roku.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node ze = zakladniEntity.get(i);
            Node node = ValuesGetter.getXChild(ze, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                               "nsesss:DataceVyrazeni", NsessV3.ROK_SKARTACNI_OPERACE);
            Integer rokSkartacniOperace = vratRok(node);
            if (rokSkartacniOperace == null) {
                return false;
            }
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (rokSkartacniOperace > year) {
                return nastavChybu("Hodnota roku elementu <nsesss:RokSkartacniOperace> je větší, než aktuální rok. Hodnota: "
                        + rokSkartacniOperace + ". " + getJmenoIdentifikator(ze), node);
            }
        }

        return true;
    }

}
