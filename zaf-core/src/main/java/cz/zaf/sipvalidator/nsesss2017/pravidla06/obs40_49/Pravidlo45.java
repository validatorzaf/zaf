package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.MimetypeDetector;
import cz.zaf.sipvalidator.nsesss2017.MimetypeDetector.MimeTypeResult;
import cz.zaf.sipvalidator.nsesss2017.MimetypeDetector.MimeTypeResult.DetectionStatus;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// OBSAHOVÁ č.45 Pokud existuje jakýkoli element <mets:file>,
// každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového
// formátu příslušné komponenty číselníku IANA na URL:
// http://www.iana.org/assignments/media-types/media-types.xhtml.
public class Pravidlo45 extends K06PravidloBase {
	
	static final public String OBS45 = "obs45";

	public Pravidlo45() {
		super(OBS45,
				"Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového formátu příslušné komponenty.",
				"Komponenta (počítačový soubor) má uvedený chybný datový formát.",
				"Bod 2.15. přílohy č. 3 NSESSS."
				);
	}

	@Override
    protected void kontrola() {
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Element metsFile : nodeListMetsFile) {
            kontrolaSouboru(metsFile);
        }
    }

    private void kontrolaSouboru(Element metsFile) {
        // application/pdf, text/plain
        String mimeType = metsFile.getAttribute(JmenaElementu.METS_MIMETYPE);
        if (StringUtils.isBlank(mimeType)) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:file> nemá atribut MIMETYPE.", metsFile);
        }
        Element flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
        if (flocat == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <mets:file> nemá dětský element <mets:FLocat>.", metsFile);
        }

        String xlinkHref = flocat.getAttribute("xlink:href"); // komponenty/jmenosouboru
        if (StringUtils.isBlank(xlinkHref)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:FLocat> nemá atribut xlink:href.", flocat);
        }
        xlinkHref = HelperString.replaceSeparators(xlinkHref);

        // kontrola, ze kazda cesta zacina: komponenty/
        if (!xlinkHref.startsWith("komponenty" + File.separator)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
                        "Element <mets:FLocat> má ve svém atributu xlink:href špatně uvedenou cestu ke komponentě: "
                                + xlinkHref + ".",
                        flocat);
        }

        Path sipPath = context.getSip().getCesta();
        Path filePath = sipPath.resolve(xlinkHref);

        if (!Files.isRegularFile(filePath)) {
            nastavChybu(BaseCode.CHYBI_KOMPONENTA,
                        "Nenalezena příslušná komponenta ve složce komponenty.",
                        "Chybí soubor: " + xlinkHref + ".");
        }

        MimeTypeResult detectedType = MimetypeDetector.getMimeType(filePath);
        if (detectedType.getDetectionStatus() == DetectionStatus.FAILED) {
            nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                        "U komponenty s deklarovaným typem: " + mimeType + " došlo k selhání detekce typu: "
                                + detectedType.getException(),
                        "Soubor: " + xlinkHref + ".");
        }
        String detectedMimeType = detectedType.getTikaMimetype();
        if (StringUtils.isBlank(detectedMimeType)) {
            nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                        "U komponenty s deklarovaným typem: " + mimeType
                                + " se nepodařilo typ správně detekovat.", "Soubor: " + xlinkHref + ".");
        }
        if (!detectedType.isMimetype(mimeType)) {
            // vyjimky sem
            if (detectedMimeType.equals("application/x-zip-compressed") ||
                    detectedMimeType.equals("application/x-dbf") ||
                    detectedMimeType.equals("application/pkcs7-signature")) {
                // datová schránka
                // application/vnd.software602.filler.form-xml-zip
                if (detectedMimeType.equals("application/pkcs7-signature")) {
                    if (!mimeType.equals("application/vnd.software602.filler.form-xml-zip")) {
                        if (!kontrolaDatoveZpravy(filePath)) {
                            nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                                        "Komponenta je soubor typu: " + detectedMimeType
                                                + ", ale její deklarovaný MIMETYPE je: " + mimeType
                                                + ". Nejedná se o soubor datové schránky",
                                        "Soubor: " + xlinkHref + ".");
                        }
                    }
                }
                if (detectedMimeType.equals("application/x-zip-compressed") &&
                        !mimeType.equals("application/zip")) {
                    nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                                "Komponenta je soubor typu: " + detectedMimeType
                                        + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".",
                                "Soubor: " + xlinkHref + ".");
                }

                if (detectedMimeType.equals("application/x-dbf") &&
                        !mimeType.equals("application/vnd.software602.filler.form-xml-zip")) {
                    nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                                "Komponenta je soubor typu: " + detectedMimeType
                                        + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".",
                                "Soubor: " + xlinkHref + ".");
                }
            } else {
                nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                            "Komponenta je soubor typu: " + detectedMimeType
                                    + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".",
                            "Soubor: " + xlinkHref + ".");
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
        } catch(IOException ioe) {
        	// TODO: improve error reporting
        	// now we can silently ignore this error
        }
        return false;
	}
}
