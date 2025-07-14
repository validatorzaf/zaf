package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs50_59;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo51 extends K06PravidloBase {

    static final public String OBS51 = "obs51";

    public Pravidlo51() {
        super(OBS51,
                "Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:type s hodnotou simple.",
                "Uveden je chybně popis odkazu na komponentu (počítačový soubor).",
                "Bod 2.16. přílohy č. 3 NSESSS.");
    }

    //OBSAHOVÁ č.51 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:type s hodnotou simple.",
    @Override
    protected void kontrola() {
        List<Element> nodeListFlocat = metsParser.getNodes(MetsElements.FLOCAT);
        for (Element n : nodeListFlocat) {
            if (!ValuesGetter.hasAttribut(n, "xlink:type")) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:FLocat> neobsahuje atribut xlink:type.", n);
            }
            if (!ValuesGetter.hasAttributValue(n, "xlink:type", "simple")) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut xlink:type neobsahuje hodnotu simple.", n);
            }
        }
    }

}
