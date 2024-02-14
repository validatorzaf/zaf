package cz.zaf.sipvalidator.nsesss2017.pravidla07.kom00_09;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.formats.MimeTypeResult;
import cz.zaf.sipvalidator.formats.MimeTypeResult.DetectionStatus;
import cz.zaf.sipvalidator.formats.MimetypeDetectorFactory;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07PravidloEachFile;

/*
 * Návrh nových parametrů (z minula)
 * -p Rozsah portů pro vnitřní procesy (standardně 10000-32000)
 * Příklad: -p 20000-20005 či --ports=20000-20005
 * -D definice hodnot
 * SIEGFIRIED_PATH=
 * DROID_PATH=
 * Jak s parametrizacemi pravidel? OBS45 – jak přepínat různé varianty?
 * TIKA vs SIEGFRIED?
 * -D FORMAT_IDENT_TOOL=TIKA | SIEGFRIED
 */

//
// OBSAHOVÁ č.45 Pokud existuje jakýkoli element <mets:file>,
// každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového
// formátu příslušné komponenty číselníku IANA na URL:
// http://www.iana.org/assignments/media-types/media-types.xhtml.
//
public class Pravidlo7_03 extends K07PravidloEachFile {

    static final public String KOD = "kom3";

    public Pravidlo7_03() {
        super(KOD,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového formátu příslušné komponenty.",
                "Komponenta (počítačový soubor) má uvedený chybný datový formát.",
                "Bod 2.15. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrolaSouboru(Element metsFile, Element elFlocat, String xlinkHref, Path filePath) {
        // application/pdf, text/plain
        String mimeType = metsFile.getAttribute(JmenaElementu.METS_MIMETYPE);
        if (StringUtils.isBlank(mimeType)) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:file> nemá atribut MIMETYPE.", metsFile);
        }

        MimeTypeResult detectedType = MimetypeDetectorFactory.getMimeType(filePath);
        if (detectedType.getDetectionStatus() == DetectionStatus.FAILED) {
            nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                               "U komponenty s deklarovaným typem: " + mimeType + " došlo k selhání detekce.",
                               metsFile, xlinkHref, detectedType.getException());
        }
        String detectedMimeType = detectedType.getDetectedMimetype();
        if (StringUtils.isBlank(detectedMimeType)) {
            nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                               "U komponenty s deklarovaným typem: " + mimeType
                                       + " se nepodařilo typ správně detekovat.",
                               metsFile, xlinkHref);
        }
        if (!detectedType.isMimetype(mimeType)) {
            // vyjimky sem
            if (detectedMimeType.equals("application/x-zip-compressed")
                    || detectedMimeType.equals("application/x-dbf")
                    || detectedMimeType.equals("application/pkcs7-signature")) {
                // datová schránka
                // application/vnd.software602.filler.form-xml-zip
                if (detectedMimeType.equals("application/pkcs7-signature")) {
                    if (!mimeType.equals("application/vnd.software602.filler.form-xml-zip")) {
                        if (!kontrolaDatoveZpravy(filePath)) {
                            nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                                    "Komponenta je soubor typu: " + detectedMimeType
                                    + ", ale její deklarovaný MIMETYPE je: " + mimeType
                                    + ". Nejedná se o soubor datové schránky",
                                               metsFile, xlinkHref);
                        }
                    }
                }
                if (detectedMimeType.equals("application/x-zip-compressed")
                        && !mimeType.equals("application/zip")) {
                    nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                            "Komponenta je soubor typu: " + detectedMimeType
                            + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".",
                                       metsFile, xlinkHref);
                }

                if (detectedMimeType.equals("application/x-dbf")
                        && !mimeType.equals("application/vnd.software602.filler.form-xml-zip")) {
                    nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                            "Komponenta je soubor typu: " + detectedMimeType
                            + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".",
                                       metsFile, xlinkHref);
                }
            } else {
                nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                        "Komponenta je soubor typu: " + detectedMimeType
                        + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".",
                                   metsFile, xlinkHref);
            }
        }
    }

    private boolean kontrolaDatoveZpravy(Path file) {
        // TODO: zlepseni cteni, pridani kontroly schematu apod.    	
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            while (reader.readLine() != null) {
                String line = reader.readLine();
                if (line.contains("</q:dmHash><q:dmQTimestamp>")) {
                    return true;
                }
            }
        } catch (IOException ioe) {
            // TODO: improve error reporting
            // now we can silently ignore this error
        }
        return false;
    }
}
