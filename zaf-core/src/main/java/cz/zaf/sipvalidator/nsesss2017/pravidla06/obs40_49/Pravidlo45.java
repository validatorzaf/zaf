package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.MimetypeDetector;
import cz.zaf.sipvalidator.nsesss2017.MimetypeDetector.MimeTypeResult;
import cz.zaf.sipvalidator.nsesss2017.MimetypeDetector.MimeTypeResult.DetectionStatus;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;

// OBSAHOVÁ č.45 Pokud existuje jakýkoli element <mets:file>,
// každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového
// formátu příslušné komponenty číselníku IANA na URL:
// http://www.iana.org/assignments/media-types/media-types.xhtml.
public class Pravidlo45 extends K06PravidloBaseOld {
	
	static final public String OBS45 = "obs45";

	public Pravidlo45() {
		super(OBS45,
				"Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového formátu příslušné komponenty.",
				"Komponenta (počítačový soubor) má uvedený chybný datový formát.",
				"Bod 2.15. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Node> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Node metsFile: nodeListMetsFile) {

            String mimeType = ValuesGetter.getValueOfAttribut(metsFile, JmenaElementu.METS_MIMETYPE); // application/pdf, text/plain
            if (!HelperString.hasContent(mimeType)) {
                return nastavChybu("Element <mets:file> nemá atribut MIMETYPE.", metsFile);
            }
            Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
            if(flocat == null){
                return nastavChybu("Element <mets:file> nemá dětský element <mets:FLocat>.", metsFile);
            }
            
            String xlinkHref = ValuesGetter.getValueOfAttribut(flocat, "xlink:href"); // komponenty/jmenosouboru
            if (!HelperString.hasContent(xlinkHref)) {
                return nastavChybu("Element <mets:FLocat> nemá atribut xlink:href.", flocat);
            }
            xlinkHref = HelperString.replaceSeparators(xlinkHref);
            //kvůli komponenty/
            int sep = xlinkHref.lastIndexOf(File.separator);
            if (sep == -1) {
                return nastavChybu("Element <mets:FLocat> má ve svém atributu xlink:href špatně uvedenou cestu ke komponentě: "
                        + xlinkHref + ".", flocat);
            }
            String ko_over = xlinkHref.substring(0, sep);
            if(!ko_over.equals("komponenty")){
                return nastavChybu("Element <mets:FLocat> má ve svém atributu xlink:href špatně uvedenou cestu ke komponentě: " + xlinkHref + ".", getMistoChyby(flocat));
            }
            
            String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(context.getSip());
            File file = new File(cestaKeKomponente);
            File parentFile = file.getParentFile();
            if (parentFile == null) {
                throw new RuntimeException("Chyba při získání cesty k rodiči, cesta: " + cestaKeKomponente);
            }

            file = new File(parentFile.getAbsolutePath() + File.separator + xlinkHref);
            if(!file.exists()){
                file = getKomponenta(file.getName());
                if(file == null || !file.exists()){
//                if(file == null){
//                    if(!final_path.contains(".")){
//                        return add_popisy("Nenalezena příslušná komponenta ve složce komponenty.", "SIP soubor byl špatně vygenerován, chybí komponenty.", false, get_misto_chyby(flocat) + " " + "V atributu xlink:href uveden chybný odkaz: " + name + ".");
//                    }
                    if(xlinkHref.contains(File.separator)){
                        int s = xlinkHref.lastIndexOf(File.separator);
                        String g = xlinkHref.substring(s+1);
                        xlinkHref = g;
                    }
                    return nastavChybu("Nenalezena příslušná komponenta ve složce komponenty.",
                                       "Chybí soubor: " + xlinkHref + ".");
                }
            }  
            MimeTypeResult detectedType = MimetypeDetector.getMimeType(file);
            if (detectedType.getDetectionStatus() == DetectionStatus.FAILED) {
                return nastavChybu("U komponenty s deklarovaným typem: " + mimeType + " došlo k selhání detekce typu: "
                        + detectedType.getException(), "Soubor: " + file.getName() + ".");
            }
            String detectedMimeType = detectedType.getTikaMimetype();
            if (StringUtils.isBlank(detectedMimeType)) {
                return nastavChybu("U komponenty s deklarovaným typem: " + mimeType
                        + " se nepodařilo typ správně detekovat.", "Soubor: " + file.getName() + ".");
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
                        	if(!kontrolaDatoveZpravy(file)) {
                                return nastavChybu(
                                        "Komponenta je soubor typu: " + detectedMimeType
                                                + ", ale její deklarovaný MIMETYPE je: " + mimeType
                                                + ". Nejedná se o soubor datové schránky",
                                        "Soubor: " + file.getName() + ".");                        		
                        	}
                        }
                    }
                    if (detectedMimeType.equals("application/x-zip-compressed") && !mimeType.equals(
                                                                                                    "application/zip")) {
                        return nastavChybu("Komponenta je soubor typu: " + detectedMimeType
                                + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".",
                                           "Soubor: " + file.getName() + ".");
                    }

                    if (detectedMimeType.equals("application/x-dbf") &&
                            !mimeType.equals("application/vnd.software602.filler.form-xml-zip")) {
                        return nastavChybu("Komponenta je soubor typu: " + detectedMimeType
                                + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".", "Soubor: " + file.getName()
                                        + ".");
                    }
                } else {
                    return nastavChybu("Komponenta je soubor typu: " + detectedMimeType
                            + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".", "Soubor: " + file.getName()
                                    + ".");
                }
            }
        }

        return true;
	}

    
    private boolean kontrolaDatoveZpravy(File file) {
    	// TODO: zlepseni cteni, pridani kontroly schematu apod.    	
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
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

	private File getKomponenta(String jmeno_souboru){
        File komponenty = new File(SIP_MAIN_helper.getCesta_komponenty(context.getSip()));
        if(!komponenty.exists()){
        	return null;
        }
        File[] seznam = komponenty.listFiles();
        for(File komponenta : seznam){
        	String name = komponenta.getName();
            String nameWithOutExt = FilenameUtils.removeExtension(komponenta.getName());
            if(jmeno_souboru.equals(name) || jmeno_souboru.equals(nameWithOutExt)){
            	return komponenta;
            }
        }
        return null;
    }

}
