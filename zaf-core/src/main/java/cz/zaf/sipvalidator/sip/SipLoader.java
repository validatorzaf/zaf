package cz.zaf.sipvalidator.sip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.FileOps;
import cz.zaf.sipvalidator.sip.SipInfo.LoadStatus;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

public class SipLoader
        implements AutoCloseable
{
    private static Logger log = LoggerFactory.getLogger(SipLoader.class);

    SipInfo.LoadStatus loadStatus = null;

	LoadType loadType = LoadType.LT_UNKNOWN;
	SipInfo sip;
	Path sipPath;
	
	/**
	 * Short name of SIP
	 */
	String sipName;
	
	/**
	 * Jméno ZIP souboru se SIPem
	 */
    String sipZipFileName;

    /**
     * Cesta ke zdrojovemu ZIPu
     */
    private Path sipZipPath;

    /**
     * Pracovni adresar pro rozbaleni
     */
    String workDir;

    /**
     * Adresar, kde je rozbalen ZIP
     * 
     * SIPLoader by mel po sobe adresar vymazat
     */
    private Path pathForDelete;

    /**
     * Příznak umožňující zachování rozbalených souborů
     */
    private boolean keepExtactedFiles;

    public SipLoader(final Path path, final String workDir, final boolean keepExtactedFiles) {
        sipPath = path;
        this.workDir = workDir;
        this.keepExtactedFiles = keepExtactedFiles;
        loadSip();
	}

    public static long getSipLenght(Path sipPath) {
        if (sipPath == null) {
            return -1;
        }
        long g = 0;
        try {
            if (Files.exists(sipPath)) {
                if (Files.isDirectory(sipPath)) {
                    g = FileUtils.sizeOfDirectory(sipPath.toFile());
                } else {
                    g = Files.size(sipPath);
                }
            } else {
                g = -20;
            }
            return g;
        } catch (IllegalArgumentException e) {
            return -1;
        } catch (IOException e) {
            return -1;
        }
    }

    private void loadSip() {
        detectLoadType();

        // osetreni unzip
        if (loadType == LoadType.LT_ZIP) {
            unzipSip();
        } else {
            loadStatus = LoadStatus.OK;
        }
        sip = new SipInfo(sipName, sipZipFileName, loadType, sipPath, loadStatus);
	}

    private void unzipSip() {
        Validate.isTrue(loadStatus == null, "unzipSip can be called only once");

        Path workdirPath;
        if (workDir == null) {
            workdirPath = Paths.get("rozbaleno");
        } else {
            workdirPath = Paths.get(workDir);
        }
        try {
            Files.createDirectories(workdirPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare working dir: " + workdirPath.normalize().toString());
        }

        Path newSipPath = unzipSip(sipZipPath, workdirPath);
        if (newSipPath == null) {
            // SIP nebyl rozbalen -> chyba
            return;
        }
        
        // SIP byl rozbalen
        this.pathForDelete = newSipPath;
        sipPath = newSipPath;
        // aktualizace jmena (bez .zip)
        sipName = sipPath.getName(sipPath.getNameCount() - 1).toString();

        loadStatus = LoadStatus.OK;

    }

    /**
     * Rozbali SIP a vraci cestu do adresare s rozbalenym
     * 
     * @param zip
     * @param path_for_unzip
     * @return
     */
    private Path unzipSip(final Path zipPath, Path pathForUnzip) {
        Validate.isTrue(loadStatus == null, "unzipSip can be called only once");

        log.debug("Unzipping file: {}, workDir: {}", zipPath, pathForUnzip);
        // ocekavane jmeno je bez koncovky .ZIP
        final String jmenozip = zipPath.toFile().getName();
        final String ocekavanejmeno = jmenozip.substring(0, jmenozip.length() - 4);
        
        if (!FileOps.unzip(zipPath, pathForUnzip, (zipFile) -> {
        	if(!FileOps.containsSingleDirectory(zipFile, ocekavanejmeno)) {
                loadStatus = LoadStatus.ERR_ZIP_INCORRECT_STRUCTURE;
                return false;
        	}
            // drop any existing item on disk
            try {
                Path pathForDelete = pathForUnzip.resolve(ocekavanejmeno);
                FileOps.recursiveDelete(pathForDelete);
            } catch (IOException e) {
                loadStatus = LoadStatus.ERR_UNZIP_FAILED;
                // invalid ZIP file
                log.error("Failed to delete existing item", e);
                return false;
            }
	        return true;
        })) {
			if(loadStatus == null) {
				loadStatus = LoadStatus.ERR_UNZIP_FAILED;
				// invalid ZIP file
				log.info("File is not valid ZIP, fileName: {}", zipPath);			}
			return null;
        }

        Path rozbaleny = pathForUnzip.resolve(ocekavanejmeno);
        return rozbaleny;
    }


    private void detectLoadType() {
        sipName = sipPath.getName(sipPath.getNameCount() - 1).toString();
        if (Files.isDirectory(sipPath)) {
			loadType = LoadType.LT_DIR;
		} else {
            String inputPath = this.sipPath.getFileName().toString();
			String lowerCasePath = inputPath.toLowerCase();
			if(lowerCasePath.endsWith(".zip")) {
				loadType = LoadType.LT_ZIP;
                sipZipFileName = sipPath.getName(sipPath.getNameCount() - 1).toString();
                // zip musi byt nejprve odzipovan
                sipZipPath = sipPath;
                sipPath = null;
			} else
			if(lowerCasePath.endsWith(".xml")) {
				loadType = LoadType.LT_XML;
			}
		}
	}

	@Override
	public void close() throws Exception {
        if (pathForDelete != null) {
            if (!keepExtactedFiles) {
                FileOps.recursiveDelete(pathForDelete);
            }
            pathForDelete = null;
        }        
	}

	public SipInfo getSip() {
		// can be called only on loaded state
        Objects.requireNonNull(sip);
		return sip;
	}

}
