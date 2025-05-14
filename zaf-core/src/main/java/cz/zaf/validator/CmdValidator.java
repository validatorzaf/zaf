package cz.zaf.validator;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.result.ProtokolWriter;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.XmlProtokolWriter;
import cz.zaf.common.result.XmlProtokolWriterOld;
import cz.zaf.common.validation.Validator;
import cz.zaf.eadvalidator.ap2023.ValidatorAp2023;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.earkvalidator.ValidatorDAAIP2024;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.sipvalidator.formats.MimetypeDetectorFactory;
import cz.zaf.sipvalidator.formats.VystupniFormat;
import cz.zaf.sipvalidator.nsesss2017.ValidatorNsesss2017;
import cz.zaf.sipvalidator.nsesss2024.ValidatorNsesss2024;
import cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
import cz.zaf.validator.profiles.ValidationProfile;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;

/**
 * Command line validator
 *
 */
public class CmdValidator {

    public static final int ERR_WRONG_PARAMS = 1;
    public static final int ERR_FAILED = 2;

    final static Logger log = LoggerFactory.getLogger(CmdValidator.class);

    // Předané parametry
    private final Params params;
    
    // Skutečné parametry validátoru
    private ValidationProfile validationProfile;
	private DAAIP2024Profile da2024Profile;
    private cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace nsesss2017Profile;    
    private cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace nsesss2024Profile;
    
    private Validator validator = null;
	private AP2023Profile ap2023Profile;

    public CmdValidator(final Params params) {
        this.params = params;
        this.validator = createValidator();
    }

    public static void main(String[] args) {
        CmdParamsReader cmdParamsReader = new CmdParamsReader();
        if (!cmdParamsReader.read(args)) {
            CmdParamsReader.printUsage();
            System.exit(ERR_WRONG_PARAMS);
            return;
        }

        CmdValidator cmdValidator = new CmdValidator(cmdParamsReader.getParams());
        try {
            cmdValidator.validate();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to run: " + e.toString());
            System.exit(ERR_FAILED);
            return;
        } finally {
            // cleanup Vera
            VeraValidatorProxy.destroy();
            MimetypeDetectorFactory.destroy();
        }
    }
    
    private ProtokolWriter createWriter() throws Exception {
        ProtokolWriter protokolWriter = null;
        if (params.getVystupniFormat() == VystupniFormat.VALIDACE_V1) {

    		protokolWriter = new XmlProtokolWriter(params.getOutput(), 
                    params.getIdKontroly(), validator.getProfileInfo());
    	}
    	else {
    		protokolWriter = new XmlProtokolWriterOld(params.getOutput(), 
                    params.getIdKontroly(), validator.getProfileInfo());
    	}
    	return protokolWriter;
    }

    public void validate() throws Exception {
        try(ProtokolWriter protokolWriter = createWriter();) {
            int repeatCnt = 1;
            if (params.isMemTest()) {
                repeatCnt = 10000;
            } 

            for (int i = 0; i < repeatCnt; i++) {
                if (i > 0) {
                    gc();
                    log.debug("Run count: " + i);
                }
                if (params.isBatchMode()) {
                    validateDavka(protokolWriter);
                } else {
                    validatePackage(protokolWriter);
                }
            }
        }
    }

    /**
     * Helper method to check if GC was run
     */
    public static void gc() {
        Object obj = new Object();
        WeakReference ref = new WeakReference<Object>(obj);
        obj = null;
        while (ref.get() != null) {
            System.gc();
        }
    }

    public void validatePackage(ProtokolWriter protokolWriter) throws Exception {

        Path path = Paths.get(params.getInputPath());

        ValidationResult result = validator.validate(path);
        
        protokolWriter.writeVysledek(result);
    }
          
    private DAAIP2024Profile detectSubProfileDAAIP2024(String metsData) {    	
		if(metsData==null) {
			Path inputPath = Paths.get(params.getInputPath());
			try {
				metsData = Files.readString(inputPath.resolve("METS.xml"), StandardCharsets.UTF_8);
			} catch (IOException e) {
				// ignore error
				return DAAIP2024Profile.AIP;
			}
		}
		// Detect subprofile
		if(metsData.contains("csip:OTHERCONTENTINFORMATIONTYPE=\"change_request_v1_0\"")) {
			return DAAIP2024Profile.SIP_CHANGE;
		}
		
		// TODO: improve detection according existence of representations
		return DAAIP2024Profile.AIP;
	}
    
    private void identifikujTypBalicku() {
    	Path inputPath = Paths.get(params.getInputPath());
    	if(Files.isRegularFile(inputPath)) {    		
    		// pokud je soubor, tak musí být pomůcka

    		String inputFileName = inputPath.getFileName().toString().toLowerCase();
    		if(inputFileName.endsWith(".zip")) {
    			detectPkgTypeZip(inputPath);
    		} else {
    			validationProfile = ValidationProfile.AP2023;
    		}
    	} else
    	// we have to detect file type
    	// check if input path is a directory
    	if (Files.isDirectory(inputPath)) {
    		// Default choice is:
    		validationProfile = ValidationProfile.NSESSS2017;
	    	// read fist 100 characters from METS.xml if exists
	    	try {
	    		String metsData = Files.readString(inputPath.resolve("METS.xml"), StandardCharsets.UTF_8);
	    		if(metsData.contains("PROFILE=\"https://stands.nacr.cz/da/2023/aip.xml\"")) {
	    			validationProfile = ValidationProfile.DAAIP2024;
	    			// Detect subprofile
	    			da2024Profile = detectSubProfileDAAIP2024(metsData);
	    		} else
	    		if(metsData.contains("=\"http://www.mvcr.cz/nsesss/v4\"")) {
	    			validationProfile = ValidationProfile.NSESSS2024;
	    		}
	    		
    		} catch (IOException e) {
	    		// ignore
	    	}	    	
    	}
    }
    
    private boolean detectPkgTypeZip(Path inputPath) {
    	try(ZipFile zipFile = new ZipFile(inputPath.toFile())) {
            // zipFile.setCharset(Charset.forName(zipEncoding)); // extrakce českých znaků
            boolean isvalidZipFile = zipFile.isValidZipFile();
            if (!isvalidZipFile) {
             	return false;
            }
            
            List<FileHeader> fileHeaders = zipFile.getFileHeaders();
            if(fileHeaders==null || fileHeaders.isEmpty()) {
	            return false;
            }
            // vyhledani korenove slozky a nebo mets.xml
            for(FileHeader fileHeader: fileHeaders) {
            	if(fileHeader.isDirectory()) {
	            	continue;
            	}
            	// TODO: consider case sensitive detection
            	String fileName = fileHeader.getFileName().toLowerCase();
            	if(fileName.endsWith("/mets.xml")) {
            		// mets found
            		// extract mets.xml to the memory
					try ( ZipInputStream is = zipFile.getInputStream(fileHeader); ) {
						// read from InputStream at most 65536 bytes
						byte[] buffer = new byte[65536];
						int bytesRead;
						StringBuilder sb = new StringBuilder();
						while ((bytesRead = is.read(buffer)) != -1) {
							sb.append(new String(buffer, 0, bytesRead));
						}
						String metsData = sb.toString();
						if(metsData.contains("PROFILE=\"https://stands.nacr.cz/da/2023/aip.xml\"")) {
							validationProfile = ValidationProfile.DAAIP2024;
							// Detect subprofile
							da2024Profile = detectSubProfileDAAIP2024(metsData);
							return true;
						}
						if(metsData.contains("=\"http://www.mvcr.cz/nsesss/v4\"")) {
							validationProfile = ValidationProfile.NSESSS2024;
							nsesss2024Profile = detectSubProfileNSESSS2024(metsData);
							return true;
						}
						if(metsData.contains("=\"http://www.mvcr.cz/nsesss/v3\"")) {
							validationProfile = ValidationProfile.NSESSS2017;
							nsesss2017Profile = detectSubProfileNSESSS2017(metsData);
							return true;
						}
					}
            		
            	}
            }
    	} catch (ZipException e) {
        	log.error("Failed to extract zip file", e);
			return false;
		} catch (IOException e) {
        	log.error("Failed to extract zip file", e);
			return false;
		}

    	return true;
    }

	private cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace detectSubProfileNSESSS2017(String metsData) {
		// Detect subprofile
		if(metsData.contains("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) {
			return cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.PREJIMKA;
		}
		
		// TODO: improve detection according existence of representations
		if(metsData.contains("fileSec>")) {
			return cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.SKARTACE_UPLNY;
		}
		
		return cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.SKARTACE_METADATA;
	}

	private ProfilValidace detectSubProfileNSESSS2024(String metsData) {
		// Detect subprofile
		if(metsData.contains("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) {
			return ZakladniProfilValidace.PREJIMKA;
		}
		
		// TODO: improve detection according existence of representations
		if(metsData.contains("fileSec>")) {
			return ZakladniProfilValidace.SKARTACE_UPLNY;
		}
		
		return ZakladniProfilValidace.SKARTACE_METADATA;
	}

	private Validator createValidator() {
		validationProfile = params.getValidationProfile();
		da2024Profile = params.getDa2024Profile();
		nsesss2017Profile = params.getNsesss2017Profile();
		nsesss2024Profile = params.getNsesss2024Profile();
		ap2023Profile = params.getAp2023Profile();
    	if (validationProfile == null) {
        	identifikujTypBalicku();
        }
    	if(validationProfile==null) {
    		throw new IllegalArgumentException("Unknown validation profile");
    	}
    	switch (validationProfile) {
        case AP2023:
            return new ValidatorAp2023(ap2023Profile, params.getExcludeChecks());
        case DAAIP2024:
        	if(da2024Profile==null) {
        		da2024Profile = detectSubProfileDAAIP2024(null);
        	}
        	return new ValidatorDAAIP2024(params.getDa2024Profile(), params.getExcludeChecks(),
        			params.getWorkDir(), params.isKeepFiles());
        case NSESSS2024:
			return new ValidatorNsesss2024(params.getHrozba(), 
					params.getWorkDir(), params.isKeepFiles(),
					nsesss2024Profile, params.getExcludeChecks());
        case NSESSS2017:
        default:
            return new ValidatorNsesss2017(params.getHrozba(),
                    params.getWorkDir(), params.isKeepFiles(),
                    nsesss2017Profile, params.getExcludeChecks());
    	}
    }

    public void validateDavka(ProtokolWriter protokolWriter) throws Exception {
        Path inputDir = Paths.get(params.getInputPath());
        if (!Files.isDirectory(inputDir)) {
            throw new IllegalArgumentException("Input path is not directory: " + params.getInputPath());
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDir)) {
            for (final Path path : stream) {
                final Path absDirPath = path.toAbsolutePath();
                final ValidationResult sipInfo = validator.validate(absDirPath);
                
                protokolWriter.writeVysledek(sipInfo);
            }
        }
    }
}
