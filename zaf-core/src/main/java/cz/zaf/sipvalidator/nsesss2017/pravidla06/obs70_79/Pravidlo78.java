package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo78 extends K06PravidloBaseOld {

    static final public String OBS78 = "obs78";

    public Pravidlo78() {
        super(OBS78,
                "Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
                "Chybí informace o skartačním řízení.",
                "Příloha č. 2 NSESSS, ř. 1228.");
    }

    //OBSAHOVÁ č.78 Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }
        List<Node> skartacniRizeni = metsParser.getNodes(NsessV3.SKARTACNI_RIZENI);
        Set<Node> skartacniRizeniSet = new HashSet<>(skartacniRizeni);

        for (Node zakladnientita: zakladniEntity) {
            Node node = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, 
                                               "nsesss:Vyrazovani", NsessV3.SKARTACNI_RIZENI);
            if (node == null) {
                return nastavChybu("Element <nsesss:SkartacniRizeni> není správně zatříděn. " + getJmenoIdentifikator(zakladnientita),
                                   zakladnientita);
            }
            if(!skartacniRizeniSet.remove(node)) {
                return nastavChybu("Element <nsesss:SkartacniRizeni> není správně zatříděn. " + getJmenoIdentifikator(zakladnientita),
                                   zakladnientita);                
            }
        }

        if (!skartacniRizeniSet.isEmpty()) {
            return nastavChybu("Nalezen chybně použitý element <nsesss:SkartacniRizeni>.",
                               new ArrayList<>(skartacniRizeniSet));
        }
        
        return true;
    }

}
