package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
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
    protected void kontrola() {
        List<Element> nodeListFlocat = metsParser.getNodes(MetsElements.FLOCAT);
        for (Element node : nodeListFlocat) {
            if (!ValuesGetter.hasAttribut(node, "LOCTYPE")) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:FLocat> neobsahuje atribut LOCTYPE.", node);
            }
            if (!ValuesGetter.hasAttributValue(node, "LOCTYPE", "URL")) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut LOCTYPE nemá hodnotu URL.", node);
            }
        }
    }
}
