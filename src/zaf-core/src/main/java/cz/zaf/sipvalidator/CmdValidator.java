package cz.zaf.sipvalidator;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import cz.zaf.sipvalidator.nsesss2017.RozparsovaniSipSouboru;
import cz.zaf.sipvalidator.nsesss2017.SipValidator;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilyValidace;
import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.XmlReportBuilder;

/**
 * Command line validator
 *
 */
public class CmdValidator {

    String inputPath = null;

    protected static void printUsage() {
        PrintStream output = System.out;
        output.println("CmdValidator [přepínače] [<path>]");
        output.println("");
        output.println("Přepínače:");
        output.println(" -b|--batch Dávkový režim, vstupem je adresář obsahující SIPy");
        output.println(" -w|--workdir= Umístění pracovního adresáře, zde budou SIPy rozbaleny");
        output.println(" -d|--druh= Druh kontroly:");
        output.println("        1 = pro provedení skartačního řízení (jen metadata bez přiložených komponent)");
        output.println("        2 = pro provedení skartačního řízení (s přiloženými komponentami)");
        output.println("        3 = pro předávání dokumentů a jejich metadat do archivu");
        output.println(" -z|--hrozba= podrobnosti v případě nalezení hrozby (pro předání z antivirového programu)");
        output.println(" -o|--output Jméno souboru nebo adresáře pro uložení výsledků");
    }

    public static void main(String[] args) {
        CmdValidator cmdValidator = new CmdValidator();
        try {
            cmdValidator.validate(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validate(String[] args) throws Exception {
        if (!readParams(args)) {
            return;
        }
        validate();
    }

    private void validate() throws Exception {
        SipLoader sipLoader = new SipLoader(inputPath);
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
    }

    private boolean readParams(String[] args) {
        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if (inputPath == null) {
                inputPath = arg;
            } else {
                System.err.println("Unrecognized parameter (" + i + "): " + arg);
                return false;
            }
            i++;
        }
        if (inputPath == null) {
            inputPath = System.getProperty("user.dir");
        }
        return true;
    }

}
