package cz.zaf.validator;

import java.lang.ref.WeakReference;
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
import cz.zaf.sipvalidator.formats.MimetypeDetectorFactory;
import cz.zaf.sipvalidator.formats.VystupniFormat;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValidatorNsesss2017;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
import cz.zaf.validator.profiles.ValidationProfiles;

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
                    cmdParams.getIdKontroly(), 
                    cmdParams.getProfilValidace().getNazev(),
                    NsesssV3.ZAF_RULE_PROFILE, 
                    NsesssV3.ZAF_RULE_VERSION);
    	}
    	else {
    		protokolWriter = new XmlProtokolWriterOld(cmdParams.getOutput(), 
                    cmdParams.getIdKontroly(), 
                    cmdParams.getProfilValidace());
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
                if (cmdParams.isDavkovyRezim()) {
                    validateDavka(protokolWriter);
                } else {
                    validateSip(protokolWriter);
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

    public void validateSip(ProtokolWriter protokolWriter) throws Exception {

        Path sipPath = Paths.get(cmdParams.getInputPath());

        ValidationResult sipInfo = validator.validate(sipPath);
        
        protokolWriter.writeVysledek(sipInfo);
    }
    
    private ValidationProfiles identifikujTypBalicku() {
    	return ValidationProfiles.NSESSS2017;
    }
    
    private Validator createValidator() {
    	ValidationProfiles skutecnyTyp = cmdParams.typBalicku;
    	if (cmdParams.typBalicku == null) {
        	skutecnyTyp = identifikujTypBalicku();
        }
    	switch (skutecnyTyp) {
        case AP2023:
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
