package cz.zaf.sipvalidator;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.sipvalidator.nsesss2017.SipValidator;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
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
        } finally {
            // cleanup Vera
            VeraValidatorProxy.destroy();
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

        SipInfo sipInfo = validateSip(cmdParams.getInputPath());

        writeResult(cmdParams.getOutput(), Collections.singletonList(sipInfo));
    }

    private SipInfo validateSip(String sipPath) throws Exception {
        // nahrani sipu
        SipLoader sipLoader = new SipLoader(sipPath,
                cmdParams.getWorkDir());

        SipValidator sipValidator = new SipValidator(cmdParams.getProfilValidace(), cmdParams.getExcludeChecks());
        sipValidator.setHrozba(cmdParams.getHrozba());
        sipValidator.validate(sipLoader);
        
        return sipLoader.getSip();

    }

    private void validateDavka() throws Exception {
        Path inputDir = Paths.get(cmdParams.getInputPath());
        if (!Files.isDirectory(inputDir)) {
            throw new IllegalArgumentException("Input path is not directory: " + cmdParams.getInputPath());
        }
        final List<SipInfo> sips = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDir)) {
            for (final Path path : stream) {
                final String dirPath = path.toAbsolutePath().toString();
                final SipInfo sipInfo = validateSip(dirPath);
                sips.add(sipInfo);
            }
        }
        writeResult(cmdParams.getOutput(), sips);
    }

    /**
     * Zapsani vysledku do protokolu
     * 
     * @param output
     *            Jmeno vystupniho souboru
     * @param sips
     *            Seznam SIPu
     * @throws Exception
     *             Chyba pri zapisu protokolu
     */
    private void writeResult(String output, List<SipInfo> sips) throws Exception {
        String kontrolaId = cmdParams.getIdKontroly();
        if (!StringUtils.isEmpty(kontrolaId)) {
            vyslednyProtokol.getKontrolaSIP().setKontrolaID(kontrolaId);
        }
        vyslednyProtokol.setProfilValidace(cmdParams.getProfilValidace());

        for (SipInfo sipInfo : sips) {
            vyslednyProtokol.addSipInfo(sipInfo);
        }

        if (StringUtils.isNoneBlank(cmdParams.getOutput())) {
            Path outputPath = Paths.get(cmdParams.getOutput());
            if (Files.isDirectory(outputPath)) {
                // create file name
                outputPath = outputPath.resolve("protokol.xml");
                vyslednyProtokol.save(outputPath);;
            } else {
                vyslednyProtokol.save(outputPath);
            }
        } else {
            vyslednyProtokol.save(System.out);
        }

    }

}
