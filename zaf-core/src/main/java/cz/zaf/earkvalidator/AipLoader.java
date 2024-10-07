package cz.zaf.earkvalidator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import cz.zaf.common.FileOps;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationResultImpl;
import cz.zaf.common.xml.PositionalXMLReader2;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.schema.mets_1_12_1.Mets;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller.Listener;

/**
 * Load AIP from filesystem
 */
public class AipLoader implements AutoCloseable {
	
	static private final Logger log = LoggerFactory.getLogger(AipLoader.class);
	
    static private JAXBContext metsContext;
    {
    	try {
    		metsContext = JAXBContext.newInstance(Mets.class);
    	} catch(JAXBException e) {
    		throw new RuntimeException("Failed to initialize JAXB", e);
    	}
    }
    static XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();

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

	/**
	 * Loaded METS document
	 */
	private Document document;
	
	private Exception metsParserError;

	private Mets mets;

	private Map<Object, Location> metsToLocationMap;

	public Exception getMetsParserError() {
		return metsParserError;
	}

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

	public Document loadMets() {
    	log.debug("Parsing METS.xml, file: {}", getMetsPath());
    	
    	// reset previous document (if any)
    	document = null;
    	
        try (InputStream is = Files.newInputStream(getMetsPath())) {
            PositionalXMLReader2 xmlReader = new PositionalXMLReader2();
            document = xmlReader.readXML(is);
        } catch (SAXException e) {
            metsParserError = e;
            log.error("Parsing failed, file: {}", getMetsPath(), e);
        } catch (IOException e) {        	
        	metsParserError = e;
            log.error("Reading failed, file: {}", getMetsPath(), e);
        }
        return document;
	}

	public Document getMetsDocument() {
		return document;
	}

	public void loadMetsJaxb() throws JAXBException, XMLStreamException, IOException {
		Objects.requireNonNull(document);
		
		final Map<Object, Location> jaxbToLocationMap = new HashMap<>();
		
		var unmarshaller = metsContext.createUnmarshaller();
		
		// Create XMLEvent stream which can be monitored during unmarshalling
		try(InputStream fis = Files.newInputStream(getMetsPath())) {
			XMLStreamReader xsr = xmlInputFactory.createXMLStreamReader(fis);
			
			unmarshaller.setListener(new Listener() {
				public void beforeUnmarshal(Object target, Object parent) {
					Location location = xsr.getLocation();
					if(location!=null) {
						jaxbToLocationMap.put(target, location);
					}
					
				}
			});
			
			JAXBElement<Mets> metsObject = unmarshaller.unmarshal(xsr, Mets.class);
			this.mets = metsObject.getValue();
			this.metsToLocationMap = jaxbToLocationMap;
		}				
		
	}

	public Mets getMets() {
		return mets;
	}

}
