package cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsAttributes;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07PravidloEachFile;

// Komponent č.1 Pokud existuje jakýkoli element <mets:file>, každý obsahuje
// atribut SIZE
// s hodnotou velikosti příslušné komponenty v bytech
public class Pravidlo7_01 extends K07PravidloEachFile {

    static final public String KOD = "kom1";

    public Pravidlo7_01() {
        super(KOD,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
                "Chybí velikost komponenty (počítačového souboru) nebo je uvedena chybně.",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrolaSouboru(Element metsFile, Element elFlocat, String xlinkHref, Path filePath) {
        String hodnotaAtrSize = metsFile.getAttribute(MetsAttributes.SIZE);
        if (StringUtils.isBlank(hodnotaAtrSize)) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:file> neobsahuje atribut SIZE.", metsFile);
        }

        try {
            long fileSize = Files.size(filePath);
            String velikost = String.valueOf(fileSize);
            if (!velikost.equals(hodnotaAtrSize)) {
                nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                                   "Velikost komponenty není totožná s metadaty.",
                                   metsFile, xlinkHref);
            }
        } catch (IOException ex) {
            nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                               "Velikost komponenty nelze zjistit.",
                               metsFile,
                               xlinkHref, ex);
        }

    }

}
