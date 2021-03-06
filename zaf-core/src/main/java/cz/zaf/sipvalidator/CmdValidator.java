package cz.zaf.sipvalidator;

import java.lang.ref.WeakReference;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.sipvalidator.nsesss2017.SipValidator;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
import cz.zaf.sipvalidator.sip.ProtokolWriter;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.XmlProtokolWriter;

/**
 * Command line validator
 *
 */
public class CmdValidator {

    public static final int ERR_WRONG_PARAMS = 1;
    public static final int ERR_FAILED = 2;

    final static Logger log = LoggerFactory.getLogger(CmdValidator.class);

    private CmdParams cmdParams;

    public CmdValidator(final CmdParams cmdParams) {
        this.cmdParams = cmdParams;
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
        }
    }

    private void validate() throws Exception {
        try(XmlProtokolWriter protokolWriter = new XmlProtokolWriter(cmdParams.getOutput(), 
                                                                     cmdParams.getIdKontroly(), 
                                                                     cmdParams.getProfilValidace());) {
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

    private void validateSip(ProtokolWriter protokolWriter) throws Exception {

        SipInfo sipInfo = validateSip(cmdParams.getInputPath());
        
        protokolWriter.writeVysledek(sipInfo);
    }

    private SipInfo validateSip(String sipPath) throws Exception {
        // nahrani sipu
        try(SipLoader sipLoader = new SipLoader(sipPath,
                cmdParams.getWorkDir()); ) { 

            SipValidator sipValidator = new SipValidator(cmdParams.getProfilValidace(), cmdParams.getExcludeChecks());
            sipValidator.setHrozba(cmdParams.getHrozba());
            sipValidator.validate(sipLoader);
        
            return sipLoader.getSip();
        }

    }

    private void validateDavka(ProtokolWriter protokolWriter) throws Exception {
        Path inputDir = Paths.get(cmdParams.getInputPath());
        if (!Files.isDirectory(inputDir)) {
            throw new IllegalArgumentException("Input path is not directory: " + cmdParams.getInputPath());
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDir)) {
            for (final Path path : stream) {
                final String dirPath = path.toAbsolutePath().toString();
                final SipInfo sipInfo = validateSip(dirPath);
                
                protokolWriter.writeVysledek(sipInfo);
            }
        }
    }
}
