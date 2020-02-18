package cz.zaf.sipvalidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.sipvalidator.nsesss2017.SipValidator;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.VyslednyProtokol;

/**
 * Command line validator
 *
 */
public class CmdValidator {

    public static final int ERR_WRONG_PARAMS = 1;
    public static final int ERR_FAILED = 2;
    private CmdParams cmdParams;

    VyslednyProtokol vyslednyProtokol = new VyslednyProtokol();

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
        }
    }

    private void validate() throws Exception {
        if (cmdParams.isDavkovyRezim()) {
            validateDavka();
        } else {
            validateSip();
        }
    }

    private void validateSip() throws Exception {
        // nahrani sipu
        SipLoader sipLoader = new SipLoader(cmdParams.getInputPath(),
                cmdParams.getWorkDir());

        SipValidator sipValidator = new SipValidator(cmdParams.getProfilValidace());
        sipValidator.setHrozba(cmdParams.getHrozba());
        sipValidator.validate(sipLoader);
        
        writeResult(cmdParams.getOutput(), sipLoader.getSip());
    }

    private void writeResult(String output, SipInfo sipInfo) throws Exception {
        String kontrolaId = cmdParams.getIdKontroly();
        if (!StringUtils.isEmpty(kontrolaId)) {
            vyslednyProtokol.getKontrolaSIP().setKontrolaID(kontrolaId);
        }
        vyslednyProtokol.setProfilValidace(cmdParams.getProfilValidace());

        vyslednyProtokol.addSipInfo(sipInfo);

        if (StringUtils.isNoneBlank(cmdParams.getOutput())) {
            Path outputPath = Paths.get(cmdParams.getOutput());
            if (Files.isDirectory(outputPath)) {
                // create file name
            } else {
                vyslednyProtokol.save(outputPath);
            }
        } else {
            vyslednyProtokol.save(System.out);
        }

    }

    private void validateDavka() {
    }

}
