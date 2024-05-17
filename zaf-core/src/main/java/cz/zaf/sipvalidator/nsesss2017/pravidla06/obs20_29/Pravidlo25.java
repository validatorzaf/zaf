package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.25 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
public class Pravidlo25 extends K06PravidloBase {

    static final public String OBS25 = "obs25";

    public Pravidlo25() {
        super(OBS25,
                "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
                "Uveden je chybně popis schématu XML.",
                "Bod 2.7. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Node metsMdWrap = metsParser.getMetsMdWrap();
        if (metsMdWrap == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element <mets:mdWrap>.");
        }
        if (!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPE")) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                    "Element <mets:mdWrap> neobsahuje atribut MDTYPE.", metsMdWrap);
        }
        String hodnotaAtr = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPE");
        if (StringUtils.isBlank(hodnotaAtr)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
                    "Atribut MDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", metsMdWrap);
        }
        if (!"OTHER".equals(hodnotaAtr)) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                    "Atribut MDTYPE elementu <mets:mdWrap> neobsahuje hodnotu OTHER.", metsMdWrap);
        }
    }

}
