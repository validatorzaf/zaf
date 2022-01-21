package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// OBSAHOVÁ č.64
//
// Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii
// dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>,
// <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace>
// hodnotu, která je rovna nebo větší součtu hodnoty elementu
// <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta>
// uvedeného v rodičovském elementu <nsesss:SkartacniRezim>.
//
public class Pravidlo64 extends K06PravidloBase {

    static final public String OBS64 = "obs64";

    public Pravidlo64() {
        super(OBS64,
                "Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je rovna nebo větší součtu hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim>.",
                "Uveden je chybně rok skartační operace u dokumentu (počítá se jako rok spouštěcí události + 1 + skartační lhůta).",
                "§ 15 odst. 4 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }
        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals("nsesss:Dokument")) {
                Node vyrazovani = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI);
                if (vyrazovani == null) {
                    return nastavChybu("Nenalezen element <nsesss:Vyrazovani>. "
                            + getJmenoIdentifikator(zakladnientita),
                                       zakladnientita);
                }
                Node skartacniRezim = ValuesGetter.getXChild(vyrazovani, "nsesss:SkartacniRezim");
                if (skartacniRezim == null) {
                    return nastavChybu("Nenalezen element <nsesss:SkartacniRezim>. "
                            + getJmenoIdentifikator(zakladnientita),
                                       zakladnientita);
                }
                Node skartacniLhuta_node = ValuesGetter.getXChild(skartacniRezim, "nsesss:SkartacniLhuta");
                if (skartacniLhuta_node == null) {
                    return nastavChybu("Nenalezen element <nsesss:SkartacniLhuta>. "
                            + getJmenoIdentifikator(zakladnientita),
                                       skartacniRezim);
                }
                String skartacniLhuta = skartacniLhuta_node.getTextContent();
                Node rso = ValuesGetter.getXChild(vyrazovani, "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
                Node rsu = ValuesGetter.getXChild(vyrazovani, "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if (rso == null) {
                    return nastavChybu("Nenalezen element <nsesss:RokSkartacniOperace>. "
                            + getJmenoIdentifikator(zakladnientita),
                                       zakladnientita);
                }
                if (rsu == null) {
                    return nastavChybu("Nenalezen element <nsesss:RokSpousteciUdalosti>. "
                            + getJmenoIdentifikator(zakladnientita),
                                       zakladnientita);
                }
                String rokSkartacniOperace = rso.getTextContent();
                String rokSpousteciUdalosti = rsu.getTextContent();

                try {
                    int hodnotaOperace = Integer.parseInt(rokSkartacniOperace);
                    int hodnotaLhuta = Integer.parseInt(skartacniLhuta);
                    int hodnotaUdalosti = Integer.parseInt(rokSpousteciUdalosti);
                    if (hodnotaOperace < (hodnotaLhuta + hodnotaUdalosti + 1)) {
                        return nastavChybu("Rok skartační operace: " + hodnotaOperace + ", lhůta: " + hodnotaLhuta
                                + ", událost: " + hodnotaUdalosti + ". " + getJmenoIdentifikator(zakladnientita),
                                           zakladnientita);
                    }
                } catch (NumberFormatException e) {
                    return nastavChybu("Zápis roku je uveden ve špatném formátu. "
                            + getJmenoIdentifikator(zakladnientita),
                                       zakladnientita);
                }

            }
        }
        return true;
    }
}
