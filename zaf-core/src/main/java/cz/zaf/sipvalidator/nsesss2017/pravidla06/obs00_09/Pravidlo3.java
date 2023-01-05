package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// OBSAHOVÁ č.3 Element <mets:mets> obsahuje atribut LABEL s hodnotou
// Datový balíček pro předávání dokumentů a jejich metadat do archivu.
public class Pravidlo3 extends K06PravidloBase {

    static final public String OBS3 = "obs3";

    public Pravidlo3() {
        super(OBS3,
                "Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
                "Uveden je chybně popis datového balíčku SIP.",
                "Bod 2.1. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        Node metsMets = metsParser.getMetsRootNode();

        String hodLab = ValuesGetter.getValueOfAttribut(metsMets, "LABEL");
        if (hodLab == null) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut LABEL kořenového elementu <mets:mets>.",
                        metsMets);
        }

        if (StringUtils.isBlank(hodLab)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
                        "Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je prázdná.",
                        metsMets);
        }
        if (!hodLab.equals("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                                + hodLab + ".",
                        metsMets);
        }
    }

}
