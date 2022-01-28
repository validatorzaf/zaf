package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo58 extends K06PravidloBaseOld {

    static final public String OBS58 = "obs58";

    public Pravidlo58() {
        super(OBS58,
                "Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
                "Není uveden zdroj identifikátoru.",
                "Příloha č. 2 NSESSS, ř. 288.");
    }

    //OBSAHOVÁ č.58 Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> identifikatory = metsParser.getIdentifikatory();
        if (CollectionUtils.isEmpty(identifikatory)) {
            return nastavChybu("Nenalezen žádný element <nsesss:Identifikator>.");
        }
        for (Node identifikator: identifikatory) {
            if (!ValuesGetter.hasAttribut(identifikator, "zdroj")) {
                return nastavChybu("Element <nsesss:Identifikator> neobsahuje atribut zdroj.", identifikator);
            }
            String str = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
            if (!HelperString.hasContent(str)) {
                return nastavChybu("Atribut zdroj elementu <nsesss:Identifikator> má prázdnou hodnotu. "
                        + getJmenoIdentifikator(identifikator), identifikator);
            }
        }
        return true;
    }

}
