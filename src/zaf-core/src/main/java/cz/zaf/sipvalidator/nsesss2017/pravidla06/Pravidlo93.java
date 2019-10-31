package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;

public class Pravidlo93 extends K06PravidloBase {

    public Pravidlo93(K06_Obsahova kontrola) {
        super(kontrola,
                K06_Obsahova.OBS93,
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
                if (StringUtils.isBlank(str)) {
                    return nastavChybu("Element <nsesss:Nazev> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                   nazev),
                                       getMistoChyby(nazev));
                }
            }
        }
        return true;
    }

}
