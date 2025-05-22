package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs40_49;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.49 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
public class Pravidlo49 extends K06PravidloBase {

    static final public String OBS49 = "obs49";

    public Pravidlo49() {
        super(OBS49,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
                "Chybí datum vytvoření komponenty (počítačového souboru).",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Element metsFile : nodeListMetsFile) {
            String hodnotaAtrCreated = metsFile.getAttribute("CREATED");
            if (StringUtils.isBlank(hodnotaAtrCreated)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Elenemt <mets:file> neobsahuje atribut CREATED.", metsFile);
            }
        }
    }

}
