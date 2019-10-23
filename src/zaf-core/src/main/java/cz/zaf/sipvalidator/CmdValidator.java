package cz.zaf.sipvalidator;

import org.apache.commons.lang.NotImplementedException;

import cz.zaf.sipvalidator.sip.SipLoader;

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

    private void validateSip() {
        // nahrani sipu
        SipLoader sipLoader = new SipLoader(cmdParams.getInputPath(),
                cmdParams.getWorkDir());

        /*
        SipInfo sip = sipLoader.getSip();
        
        ProfilValidace profilValidace = ProfilyValidace.SKARTACE_UPLNY;
        
        List<UrovenKontroly> kontroly = SipValidator.pripravKontroly(true, null, profilValidace.getObsahoveKontroly());
        RozparsovaniSipSouboru rss = new RozparsovaniSipSouboru(sip);
        
        // provedeni kontrol
        KontrolaContext ctx = new KontrolaContext(sip);
        for (UrovenKontroly kontrola : kontroly) {
            kontrola.provedKontrolu(ctx);
        }
        
        
        // print result
        XmlReportBuilder xmlBuilder = new XmlReportBuilder(Collections.singletonList(sip),
                null, sip.getName(), profilValidace, "",
                null, null);
                */
    }

    private void validateDavka() {
        throw new NotImplementedException();
    }

}
