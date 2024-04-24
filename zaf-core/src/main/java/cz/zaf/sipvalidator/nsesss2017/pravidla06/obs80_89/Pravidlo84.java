package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import org.apache.commons.lang.StringUtils;

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
    protected void kontrola() {
        List<Element> vyrizenis = metsParser.getNodes(NsesssV3.VYRIZENI);
        for (Element elVyrizeni : vyrizenis) {
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(elVyrizeni, NsesssV3.ZPUSOB,
                    "jiný způsob");
            if (maZpusobSHodnotou) {
                Element entita = kontrola.getEntity(elVyrizeni);
                Element obs_vyr = ValuesGetter.getXChild(elVyrizeni, NsesssV3.OBSAH_VYRIZENI);
                if (obs_vyr == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:ObsahVyrizeni>. " + getJmenoIdentifikator(elVyrizeni),
                            elVyrizeni, kontrola.getEntityId(entita));
                } else {
                    String hodnotaElObsVyrizeni = obs_vyr.getTextContent();
                    if (StringUtils.isBlank(hodnotaElObsVyrizeni)) {
                        nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:ObsahVyrizeni> obsahuje prázdnou hodnotu. "
                                + getJmenoIdentifikator(elVyrizeni),
                                obs_vyr, kontrola.getEntityId(entita));
                    }
                }
            }
        }
    }

}
