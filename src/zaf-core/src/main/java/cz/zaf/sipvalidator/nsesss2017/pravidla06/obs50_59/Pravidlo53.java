package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo53 extends K06PravidloBase {

    static final public String OBS53 = "obs53";

    public Pravidlo53() {
        super(OBS53,
                "Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut LOCTYPE s hodnotou URL.",
                "Uveden je chybně popis odkazu na komponentu (počítačový soubor).",
                "Bod 2.16. přílohy č. 3 NSESSS.");
    }

    //OBSAHOVÁ č.53 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut LOCTYPE s hodnotou URL.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", metsParser.getDocument());
        if (nodeListFlocat == null)
            return true;
        for (int i = 0; i < nodeListFlocat.getLength(); i++) {
            Node node = nodeListFlocat.item(i);
            if (!ValuesGetter.hasAttribut(node, "LOCTYPE")) {
                return nastavChybu("Element <mets:FLocat> neobsahuje atribut LOCTYPE.", node);
            }
            if (!ValuesGetter.hasAttributValue(node, "LOCTYPE", "URL")) {
                return nastavChybu("Atribut LOCTYPE nemá hodnotu URL.", node);
            }
        }
        return true;
    }
}
