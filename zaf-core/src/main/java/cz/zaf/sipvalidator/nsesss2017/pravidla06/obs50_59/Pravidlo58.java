package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo58 extends K06PravidloBase {

    static final public String OBS58 = "obs58";

    public Pravidlo58() {
        super(OBS58,
                "Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
                "Není uveden zdroj identifikátoru.",
                "Příloha č. 2 NSESSS, ř. 288.");
    }

    //OBSAHOVÁ č.58 Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
    @Override
    protected void kontrola() {
        List<Element> identifikatory = metsParser.getIdentifikatory();
        if (CollectionUtils.isEmpty(identifikatory)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Identifikator>.");
        }
        for (Element identifikator : identifikatory) {
            if (!ValuesGetter.hasAttribut(identifikator, "zdroj")) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <nsesss:Identifikator> neobsahuje atribut zdroj.", identifikator);
            }
            String str = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
            if (!HelperString.hasContent(str)) {
                nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Atribut zdroj elementu <nsesss:Identifikator> má prázdnou hodnotu. "
                        + getJmenoIdentifikator(identifikator), identifikator);
            }
        }
    }

}
