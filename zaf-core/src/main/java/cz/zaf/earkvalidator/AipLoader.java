package cz.zaf.earkvalidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.FileOps;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationResultImpl;
import cz.zaf.earkvalidator.eark.EarkConstants;

/**
 * Load AIP from filesystem
 */
public class AipLoader implements AutoCloseable {
	
	static private final Logger log = LoggerFactory.getLogger(AipLoader.class);
	
	public enum AipSrcType {
		DIRECTORY,
		FILE
	}
	
	public enum LoadStatus {
        OK,
        ERR_UNZIP_FAILED,
        ERR_ZIP_INCORRECT_STRUCTURE,
        ERR_UNKNOWN
	}
    LoadStatus loadStatus = null;
    
    Exception loadException = null;
	
	ValidationResult validationResult;
	
	private AipSrcType aipSrcType;
	
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
	 * Original path to the AIP
	 */
	private Path aipSrcPath;
	
	private Path aipPath;

	/**
	 * Short name of AIP
	 */
	String aipName;

	/**
     * Příznak umožňující zachování rozbalených souborů
     */
	private boolean keepExtactedFiles; 

	public AipLoader(Path aipSrcPath, final String workDir, final boolean keepExtactedFiles) {
		Objects.requireNonNull(aipSrcPath);
		this.workDir = workDir;
		this.keepExtactedFiles = keepExtactedFiles;
		
		this.aipSrcPath = aipSrcPath;
		validationResult = new ValidationResultImpl(null, aipSrcPath.getFileName().toString());
	}
	
	/**
	 * Load AIP
	 */
	public void init() {
		Validate.isTrue(loadStatus == null, "init can be called only once");
		
		if (aipSrcPath.toFile().isDirectory()) {
			aipSrcType = AipSrcType.DIRECTORY;
			aipPath = aipSrcPath;
			loadStatus = LoadStatus.OK; 
		} else {
			aipSrcType = AipSrcType.FILE;
			initFile();
		}
		
		// default error if not loaded
		if(loadStatus == null) {
			loadStatus = LoadStatus.ERR_UNKNOWN;
		}
	}
	
	private void initFile() {
        Path workdirPath;
        if (workDir == null) {
            workdirPath = Paths.get("rozbaleno");
        } else {
            workdirPath = Paths.get(workDir);
        }
        try {
            Files.createDirectories(workdirPath);
        } catch (IOException e) {
        	loadException = e;
            throw new RuntimeException("Failed to prepare working dir: " + workdirPath.normalize().toString());
        }
		
		// detect file type by file extension
		Path absPath = aipSrcPath.toAbsolutePath();
		String s = absPath.toString().toLowerCase();
		if (s.endsWith(".zip")) {
			this.aipPath = unzip(absPath, workdirPath);			
		} else
		if (s.endsWith(".tar")) {
			// untarFile();
		}		
        if (aipPath == null) {
            // AIP nebyl rozbalen -> chyba
            return;
        }	        
        // AIP byl rozbalen
        this.pathForDelete = aipPath;
        // aktualizace jmena (bez .zip)
        aipName = aipPath.getName(aipPath.getNameCount() - 1).toString();
        loadStatus = LoadStatus.OK;
	}

	private Path unzip(Path srcZipFile, Path pathForUnzip) {
        Validate.isTrue(loadStatus == null, "unzipSip can be called only once");

        log.debug("Unzipping file: {}, workDir: {}", srcZipFile, pathForUnzip);
        // ocekavane jmeno je bez koncovky .ZIP
        final String jmenozip = srcZipFile.toFile().getName();
        final String ocekavanejmeno = jmenozip.substring(0, jmenozip.length() - 4);		
		
		boolean unzipped = FileOps.unzip(srcZipFile, pathForUnzip, zipFile -> {
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
                loadException = e;
                // invalid ZIP file
                log.error("Failed to delete existing item", e);
                return false;
            }
	        return true;			
		});
		
		if(!unzipped) {
			loadStatus = LoadStatus.ERR_UNZIP_FAILED;
			return null;
		}

        Path rozbaleny = pathForUnzip.resolve(ocekavanejmeno);
        return rozbaleny;
	}

	public AipSrcType getAipSrcType() {
		return aipSrcType;
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

	public ValidationResult getResult() {
		return validationResult;
	}

	public Path getAipSrcPath() {
		return aipSrcPath;
	}

	public Path getAipPath() {
		return aipPath;
	}

	/**
	 * Return path to METS file
	 * @return Return null if AIP is not loaded
	 */
	public Path getMetsPath() {
		if(aipPath == null) {
			return null;
		}
		if(loadStatus != LoadStatus.OK) {
			return null;
		}
		return aipPath.resolve(EarkConstants.METS_FILE_NAME);
	}

	public Path getMetadataPath() {
		if(aipPath == null) {
			return null;
		}
		if(loadStatus != LoadStatus.OK) {
			return null;
		}
		return aipPath.resolve(EarkConstants.METADATA_DIR_NAME);
	}

}
