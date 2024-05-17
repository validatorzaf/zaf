package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.26 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
public class Pravidlo26 extends K06PravidloBase {

    static final public String OBS26 = "obs26";

    public Pravidlo26() {
        super(OBS26,
                "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
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
        if (!ValuesGetter.hasAttribut(metsMdWrap, "MIMETYPE")) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                    "Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", metsMdWrap);
        }
        String hodnotaAtr = ValuesGetter.getValueOfAttribut(metsMdWrap, "MIMETYPE");
        if (StringUtils.isBlank(hodnotaAtr)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
                    "Atribut MIMETYPE elementu <mets:mdWrap> má prázdnou hodnotu.", metsMdWrap);
        }
        if (!("text/xml").equals(hodnotaAtr)) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                    "Atribut MIMETYPE elementu <mets:mdWrap> neobsahuje hodnotu text/xml.", metsMdWrap);
        }
    }

}
