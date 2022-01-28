package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;

public class Pravidlo57 extends K06PravidloBaseOld {

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
        List<Node> identifikatory = metsParser.getIdentifikatory();
        if (CollectionUtils.isEmpty(identifikatory)) {
            return nastavChybu("Nenalezen žádný element <nsesss:Identifikator>.");
        }
        for (Node identifikator: identifikatory) {
            String str = identifikator.getTextContent();
            if (!HelperString.hasContent(str)) {
                return nastavChybu("Element <nsesss:Identifikator> obsahuje prázdnou hodnotu.", identifikator);
            }
        }
        return true;
    }

}
