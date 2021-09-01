package cz.zaf.sipvalidator;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            if (cmdParams.isDavkovyRezim()) {
                validateDavka(protokolWriter);
            } else {
                validateSip(protokolWriter);
            }
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
