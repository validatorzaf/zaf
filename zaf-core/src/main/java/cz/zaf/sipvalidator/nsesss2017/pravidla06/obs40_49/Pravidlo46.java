package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import org.apache.commons.lang.StringUtils;

//OBSAHOVÁ č.46 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUMTYPE hodnotu SHA-256 nebo SHA-512.",
public class Pravidlo46 extends K06PravidloBase {

    static final public String OBS46 = "obs46";

    public Pravidlo46() {
        super(OBS46,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUMTYPE hodnotu SHA-256 nebo SHA-512.",
                "Chybí popis pro ověření celistvosti komponenty (počítačového souboru) nebo je chybně uveden.",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Element metsFile : nodeListMetsFile) {
            String hodnotaAtrChecksum = metsFile.getAttribute("CHECKSUMTYPE");

            if (StringUtils.isBlank(hodnotaAtrChecksum)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:file> nemá atribut CHECKSUMTYPE.", metsFile);
            }
            if (!hodnotaAtrChecksum.equals("SHA-256") && !hodnotaAtrChecksum.equals("SHA-512")) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut CHECKSUMTYPE obsahuje nepovolenou hodnotu: " + hodnotaAtrChecksum + ".", metsFile);
            }
        }
    }

}
