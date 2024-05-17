package cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07PravidloEachFile;

//OBSAHOVÁ č.47 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
public class Pravidlo7_02 extends K07PravidloEachFile {

    static final public String KOD = "kom2";

    public Pravidlo7_02() {
        super(KOD,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
                "Celistvost komponenty (počítačového souboru) je narušena nebo chybí možnost jejího ověření.",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrolaSouboru(Element metsFile, Element elFlocat, String xlinkHref, Path filePath) {
        String hodnotaAtrChecksumType = metsFile.getAttribute("CHECKSUMTYPE");
        if (StringUtils.isBlank(hodnotaAtrChecksumType)) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:file> nemá atribut CHECKSUMTYPE.", metsFile);
        }

        String hodnotaAtrCheckSum = metsFile.getAttribute("CHECKSUM");
        if (StringUtils.isBlank(hodnotaAtrCheckSum)) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:file> nemá atribut CHECKSUM.", metsFile);
        }

        String spoctenyCheckSum = getSpoctenyCheckSum(hodnotaAtrChecksumType, xlinkHref, filePath,
                                                      metsFile);

        hodnotaAtrCheckSum = hodnotaAtrCheckSum.toLowerCase();
        spoctenyCheckSum = spoctenyCheckSum.toLowerCase();

        if (!spoctenyCheckSum.equals(hodnotaAtrCheckSum)) {
            if (xlinkHref.contains(File.separator)) {
                int s = xlinkHref.lastIndexOf(File.separator);
                String g = xlinkHref.substring(s + 1);
                xlinkHref = g;
            }
            nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                               "CHECKSUM neodpovídá CHECKSUMTYPE.",
                               metsFile,
                               xlinkHref);
        }
    }

    private String getSpoctenyCheckSum(String hodnotaAtrChecksumType, String hodnotaAtrXlinkHref,
                                       Path filePath, Element metsFile) {
        String spoctenyCheckSum = "";
        try {
            if (hodnotaAtrChecksumType.equals("SHA-512") || hodnotaAtrChecksumType.equals("SHA-256")) {
                if (hodnotaAtrChecksumType.equals("SHA-512")) {
                    try (InputStream is = Files.newInputStream(filePath);) {
                        spoctenyCheckSum = DigestUtils.sha512Hex(is);
                    }
                }

                if (hodnotaAtrChecksumType.equals("SHA-256")) {
                    try (InputStream is = Files.newInputStream(filePath);) {
                        spoctenyCheckSum = DigestUtils.sha256Hex(is);
                    }
                }
            } else {
                if (hodnotaAtrXlinkHref.contains(File.separator)) {
                    int s = hodnotaAtrXlinkHref.lastIndexOf(File.separator);
                    String g = hodnotaAtrXlinkHref.substring(s + 1);
                    hodnotaAtrXlinkHref = g;
                }
                nastavChybuSouboru(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                                   "Nepovolený algoritmus v atributu CHECKSUMTYPE.",
                                   metsFile,
                                   hodnotaAtrXlinkHref);
            }

        } catch (IOException ex) {
            if (hodnotaAtrXlinkHref.contains(File.separator)) {
                int s = hodnotaAtrXlinkHref.lastIndexOf(File.separator);
                String g = hodnotaAtrXlinkHref.substring(s + 1);
                hodnotaAtrXlinkHref = g;
            }
            nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                               "Nepodařilo se spočítat checksum souboru: " + hodnotaAtrXlinkHref + ".",
                               metsFile,
                               hodnotaAtrXlinkHref, ex);
        }
        return spoctenyCheckSum;
    }

}
