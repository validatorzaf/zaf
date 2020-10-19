package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo58 extends K06PravidloBase {

    static final public String OBS58 = "obs58";

    public Pravidlo58(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo58.OBS58,
                "Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
                "Není uveden zdroj identifikátoru.",
                "Příloha č. 2 NSESSS, ř. 288.");
    }

    //OBSAHOVÁ č.58 Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
    @Override
    protected boolean kontrolaPravidla() {
        //        NodeList identifikatory = ValuesGetter.getAllAnywhere("nsesss:Identifikator", metsParser.getDocument());
        if (metsParser.identifikatory == null) {
            return nastavChybu("Nenalezen žádný element <nsesss:Identifikator>.");
        }
        for (int i = 0; i < metsParser.identifikatory.size(); i++) {
            Node identifikator = metsParser.identifikatory.get(i);
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
