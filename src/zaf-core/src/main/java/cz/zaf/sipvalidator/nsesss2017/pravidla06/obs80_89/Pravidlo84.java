package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo84 extends K06PravidloBase {

    static final public String OBS84 = "obs84";

    public Pravidlo84(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo84.OBS84,
                "Pokud existuje jakýkoli element <nsesss:Vyrizeni> a obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:ObsahVyrizeni> s neprázdnou hodnotou.",
                "Chybí obsah vyřízení jiným způsobem.",
                "Příloha č. 2 NSESSS, ř. 1265.");
    }

    //OBSAHOVÁ č.84 Pokud existuje jakýkoli element <nsesss:Vyrizeni> a obsahuje element <nsesss:Zpusob> 
    // s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský 
    // element <nsesss:ObsahVyrizeni> s neprázdnou hodnotou.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList vyrizenis = ValuesGetter.getAllAnywhere("nsesss:Vyrizeni", metsParser.getDocument());
        if (vyrizenis == null)
            return true;
        int size = vyrizenis.getLength();
        for (int i = 0; i < size; i++) {
            Node n = vyrizenis.item(i);
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob",
                                                                                      "jiný způsob");
            if (maZpusobSHodnotou) {
                Node obs_vyr = ValuesGetter.getXChild(n, "nsesss:ObsahVyrizeni");
                if (obs_vyr == null) {
                    return nastavChybu("Nenalezen element <nsesss:ObsahVyrizeni>. " + getJmenoIdentifikator(n),
                                      getMistoChyby(n));
                }
                if (!HelperString.hasContent(obs_vyr.getTextContent())) {
                    return nastavChybu("Element <nsesss:ObsahVyrizeni> obsahuje prázdnou hodnotu. "
                            + getJmenoIdentifikator(n),
                                       getMistoChyby(obs_vyr));
                }
            }
        }

        return true;
    }

}
