package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo84 extends K06PravidloBase {

    static final public String OBS84 = "obs84";

    public Pravidlo84() {
        super(OBS84,
                "Pokud existuje jakýkoli element <nsesss:Vyrizeni> a obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:ObsahVyrizeni> s neprázdnou hodnotou.",
                "Chybí obsah vyřízení jiným způsobem.",
                "Příloha č. 2 NSESSS, ř. 1265.");
    }

    //OBSAHOVÁ č.84 Pokud existuje jakýkoli element <nsesss:Vyrizeni> a obsahuje element <nsesss:Zpusob> 
    // s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský 
    // element <nsesss:ObsahVyrizeni> s neprázdnou hodnotou.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> vyrizenis = metsParser.getNodes(JmenaElementu.VYRIZENI);
        for (Node n : vyrizenis) {
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob",
                                                                                      "jiný způsob");
            if (maZpusobSHodnotou) {
                Node obs_vyr = ValuesGetter.getXChild(n, "nsesss:ObsahVyrizeni");
                if (obs_vyr == null) {
                    return nastavChybu("Nenalezen element <nsesss:ObsahVyrizeni>. " + getJmenoIdentifikator(n),
                                       n);
                }
                if (!HelperString.hasContent(obs_vyr.getTextContent())) {
                    return nastavChybu("Element <nsesss:ObsahVyrizeni> obsahuje prázdnou hodnotu. "
                            + getJmenoIdentifikator(n),
                                       obs_vyr);
                }
            }
        }

        return true;
    }

}
