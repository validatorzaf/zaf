package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo57 extends K06PravidloBase {

    static final public String OBS57 = "obs57";

    public Pravidlo57() {
        super(OBS57,
                "Jakýkoli element <nsesss:Identifikator> obsahuje neprázdnou hodnotu.",
                "Není uveden identifikátor.",
                "Příloha č. 2 NSESSS, ř. 123.");
    }

    //OBSAHOVÁ č.57 Jakýkoli element <nsesss:Identifikator> obsahuje neprázdnou hodnotu.",
    @Override
    protected void kontrola() {
        List<Element> identifikatory = metsParser.getIdentifikatory();
        if (CollectionUtils.isEmpty(identifikatory)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Identifikator>.");
        }
        for (Element identifikator : identifikatory) {
            String str = identifikator.getTextContent();
            if (!HelperString.hasContent(str)) {
                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:Identifikator> obsahuje prázdnou hodnotu.", identifikator);
            }
        }
    }

}
