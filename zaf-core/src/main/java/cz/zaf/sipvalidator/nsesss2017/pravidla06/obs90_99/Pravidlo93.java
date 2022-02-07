package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;

public class Pravidlo93 extends K06PravidloBaseOld {

    static final public String OBS93 = "obs93";

    public Pravidlo93() {
        super(OBS93,
                "Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
                "Není uveden název.",
                "Příloha č. 2 NSESSS, ř. 167.");
    }

    //OBSAHOVÁ č.93 Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Element> nazvy = metsParser.getNazvy();

        for (Element nazev : nazvy) {
            String str = nazev.getTextContent();
            if (!HelperString.hasContent(str)) {
                return nastavChybu("Element <nsesss:Nazev> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                nazev),
                                   nazev);
            }
        }
        return true;
    }

}
