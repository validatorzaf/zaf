package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo63 extends K06PravidloBaseOld {

    static final public String OBS63 = "obs63";

    public Pravidlo63() {
        super(OBS63,
                "Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
                "Chybí odůvodnění vyřízení jiným způsobem.",
                "Příloha č. 2 NSESSS, ř. 1270.");
    }

    //OBSAHOVÁ č.63 Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, 
    // potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> vyrizenis = metsParser.getNodes(NsessV3.VYRIZENI);
        for (Node n : vyrizenis) {
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob",
                                                                                      "jiný způsob");
            if (maZpusobSHodnotou) {
                Node oduvodneni = ValuesGetter.getXChild(n, "nsesss:Oduvodneni");
                if (oduvodneni == null) {
                    return nastavChybu("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n),
                                       n);
                }
                if (!HelperString.hasContent(oduvodneni.getTextContent())) {
                    return nastavChybu("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                   n),
                                       oduvodneni);
                }
            }
        }
        List<Node> vyrizeniUzavrenis = metsParser.getNodes(NsessV3.VYRIZENI_UZAVRENI);
        for (Node n : vyrizeniUzavrenis) {
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob",
                                                                                      "jiný způsob");
            if (maZpusobSHodnotou) {
                Node oduvodneni = ValuesGetter.getXChild(n, "nsesss:Oduvodneni");
                if (oduvodneni == null) {
                    return nastavChybu("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n),
                                       n);
                }
                if (!HelperString.hasContent(oduvodneni.getTextContent())) {
                    return nastavChybu("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                   n),
                                       oduvodneni);
                }
            }
        }
        return true;
    }

}
