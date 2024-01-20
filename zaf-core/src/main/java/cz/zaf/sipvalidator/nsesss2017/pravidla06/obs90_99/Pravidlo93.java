package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import java.util.List;

import org.w3c.dom.Element;
import org.apache.commons.lang3.StringUtils;

public class Pravidlo93 extends K06PravidloBase {

    static final public String OBS93 = "obs93";

    public Pravidlo93() {
        super(OBS93,
                "Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
                "Není uveden název.",
                "Příloha č. 2 NSESSS, ř. 167.");
    }

    //OBSAHOVÁ č.93 Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
    @Override
    protected void kontrola() {
        List<Element> nazvy = metsParser.getNazvy();

        for (Element nazev : nazvy) {
            String str = nazev.getTextContent();
            if (!HelperString.hasContent(str)) {
                Element entita = kontrola.getEntity(nazev);
                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:Nazev> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(nazev),
                        nazev, kontrola.getEntityId(entita));
            }
        }
    }

}
