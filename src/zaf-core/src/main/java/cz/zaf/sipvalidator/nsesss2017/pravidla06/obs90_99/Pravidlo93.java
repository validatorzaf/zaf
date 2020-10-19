package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;

public class Pravidlo93 extends K06PravidloBase {

    static final public String OBS93 = "obs93";

    public Pravidlo93(K06_Obsahova kontrola) {
        super(kontrola,
                Pravidlo93.OBS93,
                "Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
                "Není uveden název.",
                "Příloha č. 2 NSESSS, ř. 167.");
    }

    //OBSAHOVÁ č.93 Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> nazvy = metsParser.getNazvy();

        if (nazvy != null) {
            for (int i = 0; i < nazvy.size(); i++) {
                Node nazev = nazvy.get(i);
                String str = nazev.getTextContent();
                if (!HelperString.hasContent(str)) {
                    return nastavChybu("Element <nsesss:Nazev> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                   nazev),
                                       getMistoChyby(nazev));
                }
            }
        }
        return true;
    }

}
