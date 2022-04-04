package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import org.apache.commons.lang.StringUtils;

//OBSAHOVÁ č.47 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
public class Pravidlo47 extends K06PravidloBase {

    static final public String OBS47 = "obs47";

    public Pravidlo47() {
        super(OBS47,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
                "Celistvost komponenty (počítačového souboru) je narušena nebo chybí možnost jejího ověření.",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Element metsFile : nodeListMetsFile) {
            String hodnotaAtrChecksumType = metsFile.getAttribute("CHECKSUMTYPE");
            if (StringUtils.isBlank(hodnotaAtrChecksumType)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:file> nemá atribut CHECKSUMTYPE.", metsFile);
            }

            Element elFlocat = ValuesGetter.getXChild(metsFile, MetsElements.FLOCAT);
            if (elFlocat == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", metsFile);
            }
            String hodnotaAtrXlinkHref = elFlocat.getAttribute("xlink:href");
            if (StringUtils.isBlank(hodnotaAtrXlinkHref)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:FLocat> nemá atribut xlink:href.", elFlocat);
            }

            hodnotaAtrXlinkHref = HelperString.replaceSeparators(hodnotaAtrXlinkHref);
            String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(context.getSip());
            File komponenta = getKomponenta(cestaKeKomponente, hodnotaAtrXlinkHref);
            String spoctenyCheckSum = getSpoctenyCheckSum(hodnotaAtrChecksumType, hodnotaAtrXlinkHref, komponenta, metsFile);
            String hodnotaAtrCheckSum = metsFile.getAttribute("CHECKSUM");

            if (StringUtils.isBlank(hodnotaAtrCheckSum)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:file> nemá atribut CHECKSUM.", metsFile);
            }

            hodnotaAtrCheckSum = hodnotaAtrCheckSum.toLowerCase();
            spoctenyCheckSum = spoctenyCheckSum.toLowerCase();

            if (!spoctenyCheckSum.equals(hodnotaAtrCheckSum)) {
                if (hodnotaAtrXlinkHref.contains(File.separator)) {
                    int s = hodnotaAtrXlinkHref.lastIndexOf(File.separator);
                    String g = hodnotaAtrXlinkHref.substring(s + 1);
                    hodnotaAtrXlinkHref = g;
                }
                nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                        "CHECKSUM neodpovídá CHECKSUMTYPE.",
                        getMistoChyby(metsFile) + " Soubor: " + hodnotaAtrXlinkHref + ".");
            }
        }
    }

    private File getKomponenta(String cestaKeKomponente, String hodnotaAtrXlinkHref) {
        File komponenta = new File(cestaKeKomponente);
        komponenta = new File(komponenta.getParentFile().getAbsolutePath() + File.separator + hodnotaAtrXlinkHref);
        if (!komponenta.exists()) {
            if (hodnotaAtrXlinkHref.contains(File.separator)) {
                int s = hodnotaAtrXlinkHref.lastIndexOf(File.separator);
                String g = hodnotaAtrXlinkHref.substring(s + 1);
                hodnotaAtrXlinkHref = g;
            }
            nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                    "Nenalezena příslušná komponenta ve složce komponenty.",
                    "Chybí soubor: " + hodnotaAtrXlinkHref + ".");
        }
        return komponenta;
    }

    private String getSpoctenyCheckSum(String hodnotaAtrChecksumType, String hodnotaAtrXlinkHref, File komponenta, Element metsFile) {
        String spoctenyCheckSum = "";
        try {
            if (hodnotaAtrChecksumType.equals("SHA-512") || hodnotaAtrChecksumType.equals("SHA-256")) {
                if (hodnotaAtrChecksumType.equals("SHA-512")) {
                    try (InputStream is = new FileInputStream(komponenta);) {
                        spoctenyCheckSum = DigestUtils.sha512Hex(is);
                    }
                }

                if (hodnotaAtrChecksumType.equals("SHA-256")) {
                    try (InputStream is = new FileInputStream(komponenta);) {
                        spoctenyCheckSum = DigestUtils.sha256Hex(is);
                    }
                }
            } else {
                if (hodnotaAtrXlinkHref.contains(File.separator)) {
                    int s = hodnotaAtrXlinkHref.lastIndexOf(File.separator);
                    String g = hodnotaAtrXlinkHref.substring(s + 1);
                    hodnotaAtrXlinkHref = g;
                }
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Nepovolený algoritmus v atributu CHECKSUMTYPE.",
                        getMistoChyby(metsFile) + " Komponenta: " + hodnotaAtrXlinkHref + ".");
            }

        } catch (IOException ex) {
//                sipSoubor.get_SIP_Validation().add_rule_obsahova(47, false, seznam_pravidla[47], ex.getLocalizedMessage(), "chyba v kódování SIP souboru");
            if (hodnotaAtrXlinkHref.contains(File.separator)) {
                int s = hodnotaAtrXlinkHref.lastIndexOf(File.separator);
                String g = hodnotaAtrXlinkHref.substring(s + 1);
                hodnotaAtrXlinkHref = g;
            }
            nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                    "Nepodařilo se spočítat checksum souboru: " + hodnotaAtrXlinkHref + ".",
                    "Nenalezen soubor " + hodnotaAtrXlinkHref + ".");
        }
        return spoctenyCheckSum;
    }

}
