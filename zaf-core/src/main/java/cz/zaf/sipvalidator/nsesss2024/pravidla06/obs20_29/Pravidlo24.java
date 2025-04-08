package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs20_29;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.24 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
public class Pravidlo24 extends K06PravidloBase {

    static final public String OBS24 = "obs24";

    public Pravidlo24() {
        super(OBS24,
                "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
                "Uveden je chybně popis schématu XML.",
                "Bod 1.7 přílohy č. 2 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Node metsMdWrap = metsParser.getMetsMdWrap();
        if (metsMdWrap == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element <mets:mdWrap>.");
        }
        if (!ValuesGetter.hasAttribut(metsMdWrap, "OTHERMDTYPE")) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                    "Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", metsMdWrap);
        }
        String hodnotaAtr = ValuesGetter.getValueOfAttribut(metsMdWrap, "OTHERMDTYPE");
        if (StringUtils.isBlank(hodnotaAtr)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
                    "Atribut OTHERMDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", metsMdWrap);
        }
        if (!("NSESSS").equals(hodnotaAtr)) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                    "Atribut OTHERMDTYPE elementu <mets:mdWrap> neobsahuje hodnotu NSESSS.", metsMdWrap);
        }
    }

}
