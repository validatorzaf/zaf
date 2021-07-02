package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;

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
    protected boolean kontrolaPravidla() {
        if (metsParser.identifikatory == null) {
            return nastavChybu("Nenalezen žádný element <nsesss:Identifikator>.");
        }
        for (int i = 0; i < metsParser.identifikatory.size(); i++) {
            Node identifikator = metsParser.identifikatory.get(i);
            String str = identifikator.getTextContent();
            if (!HelperString.hasContent(str)) {
                return nastavChybu("Element <nsesss:Identifikator> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                        identifikator),
                                   identifikator);
            }
        }
        return true;
    }

}
