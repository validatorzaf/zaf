package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.1 Element <mets:mets> obsahuje atribut OBJID s neprázdnou
// hodnotou.
public class Pravidlo1 extends K06PravidloBase {

    static final public String OBS1 = "obs1";

    public Pravidlo1() {
        super(OBS1,
                "Element <mets:mets> obsahuje atribut OBJID s neprázdnou hodnotou.",
                "Chybí identifikátor datového balíčku SIP.",
                "Bod 2.1. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        Node metsMets = metsParser.getMetsRootNode();

        String objId = ValuesGetter.getValueOfAttribut(metsMets, "OBJID");
        if (objId == null) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut OBJID kořenového elementu <mets:mets>.", metsMets);
        }
        if (StringUtils.isBlank(objId)) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut OBJID kořenového elementu <mets:mets> není vyplněn.", metsMets);
        }
    }
}
