package cz.zaf.validator;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.result.ProtokolWriter;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.XmlProtokolWriter;
import cz.zaf.common.result.XmlProtokolWriterOld;
import cz.zaf.common.validation.Validator;
import cz.zaf.eadvalidator.ap2023.ValidatorAp2023;
import cz.zaf.earkvalidator.ValidatorDAAIP2024;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.sipvalidator.formats.MimetypeDetectorFactory;
import cz.zaf.sipvalidator.formats.VystupniFormat;
import cz.zaf.sipvalidator.nsesss2017.ValidatorNsesss2017;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
import cz.zaf.validator.profiles.ValidationProfile;

/**
 * Command line validator
 *
 */
public class CmdValidator {

    public static final int ERR_WRONG_PARAMS = 1;
    public static final int ERR_FAILED = 2;

    final static Logger log = LoggerFactory.getLogger(CmdValidator.class);

    private CmdParams cmdParams;
    private Validator validator = null;

    public CmdValidator(final CmdParams cmdParams) {
        this.cmdParams = cmdParams;
        this.validator = createValidator();
    }

    public static void main(String[] args) {
        CmdParams cmdParams = new CmdParams();
        if (!cmdParams.read(args)) {
            CmdParams.printUsage();
            System.exit(ERR_WRONG_PARAMS);
            return;
        }

        CmdValidator cmdValidator = new CmdValidator(cmdParams);
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
        if (cmdParams.vystupniFormat == VystupniFormat.VALIDACE_V1) {

    		protokolWriter = new XmlProtokolWriter(cmdParams.getOutput(), 
                    cmdParams.getIdKontroly(), validator.getProfileInfo());
    	}
    	else {
    		protokolWriter = new XmlProtokolWriterOld(cmdParams.getOutput(), 
                    cmdParams.getIdKontroly(), validator.getProfileInfo());
    	}
    	return protokolWriter;
    }

    public void validate() throws Exception {
        try(ProtokolWriter protokolWriter = createWriter();) {
            int repeatCnt = 1;
            if (cmdParams.isMemTest()) {
                repeatCnt = 10000;
            } 

            for (int i = 0; i < repeatCnt; i++) {
                if (i > 0) {
                    gc();
                    log.debug("Run count: " + i);
                }
                if (cmdParams.isBatchMode()) {
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

        Path path = Paths.get(cmdParams.getInputPath());

        ValidationResult result = validator.validate(path);
        
        protokolWriter.writeVysledek(result);
    }
    
    private DAAIP2024Profile detectSubProfileDAAIP2024(String metsData) {
		if(metsData==null) {
			Path inputPath = Paths.get(cmdParams.getInputPath());
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
    
    private ValidationProfile identifikujTypBalicku() {
    	ValidationProfile result = null;
    	Path inputPath = Paths.get(cmdParams.getInputPath());
    	if(Files.isRegularFile(inputPath)) {
    		// pokud je soubor, tak musí být pomůcka
    		result = ValidationProfile.AP2023; 
    	} else
    	// we have to detect file type
    	// check if input path is a directory
    	if (Files.isDirectory(inputPath)) {
    		// Default choice is:
    		result = ValidationProfile.NSESSS2017;
	    	// read fist 100 characters from METS.xml if exists
	    	try {
	    		String metsData = Files.readString(inputPath.resolve("METS.xml"), StandardCharsets.UTF_8);
	    		if(metsData.contains("PROFILE=\"https://stands.nacr.cz/da/2023/aip.xml\"")) {
	    			result = ValidationProfile.DAAIP2024;
	    			// Detect subprofile
	    			cmdParams.da2024Profile = detectSubProfileDAAIP2024(metsData);
	    		}
	    		
    		} catch (IOException e) {
	    		// ignore
	    	}	    	
    	}
    	return result;
    }
    
    private Validator createValidator() {
    	ValidationProfile skutecnyTyp = cmdParams.getValidationProfile();
    	if (skutecnyTyp == null) {
        	skutecnyTyp = identifikujTypBalicku();
        }
    	if(skutecnyTyp==null) {
    		throw new IllegalArgumentException("Unknown validation profile");
    	}
    	switch (skutecnyTyp) {
        case AP2023:
            return new ValidatorAp2023(cmdParams.getAp2023Profile(), cmdParams.getExcludeChecks());
        case DAAIP2024:
        	if(cmdParams.getDa2024Profile()==null) {
        		cmdParams.da2024Profile = detectSubProfileDAAIP2024(null);
        	}
        	return new ValidatorDAAIP2024(cmdParams.getDa2024Profile(), cmdParams.getExcludeChecks(),
        			cmdParams.getWorkDir(), cmdParams.isKeepFiles());
        case NSESSS2017:
        default:
            return new ValidatorNsesss2017(cmdParams.getHrozba(),
                    cmdParams.getWorkDir(), cmdParams.isKeepFiles(),
                    cmdParams.getProfilValidace(), cmdParams.getExcludeChecks());
    	}
    }

    public void validateDavka(ProtokolWriter protokolWriter) throws Exception {
        Path inputDir = Paths.get(cmdParams.getInputPath());
        if (!Files.isDirectory(inputDir)) {
            throw new IllegalArgumentException("Input path is not directory: " + cmdParams.getInputPath());
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
