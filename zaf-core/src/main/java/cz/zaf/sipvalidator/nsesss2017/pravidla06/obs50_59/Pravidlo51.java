package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo51 extends K06PravidloBaseOld {

    static final public String OBS51 = "obs51";

    public Pravidlo51() {
        super(OBS51,
                "Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:type s hodnotou simple.",
                "Uveden je chybně popis odkazu na komponentu (počítačový soubor).",
                "Bod 2.16. přílohy č. 3 NSESSS.");
    }

    //OBSAHOVÁ č.51 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:type s hodnotou simple.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> nodeListFlocat = metsParser.getNodes(MetsElements.FLOCAT);
        for (Node n: nodeListFlocat) {
            if (!ValuesGetter.hasAttribut(n, "xlink:type")) {
                return nastavChybu("Element <mets:FLocat> neobsahuje atribut xlink:type.", n);
            }
            if (!ValuesGetter.hasAttributValue(n, "xlink:type", "simple")) {
                return nastavChybu("Atribut xlink:type neobsahuje hodnotu simple.", n);
            }
        }
        return true;
    }

}
