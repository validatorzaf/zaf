package cz.zaf.validator;

import java.io.PrintStream;

import cz.zaf.common.MessageProvider;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.sipvalidator.formats.VystupniFormat;
import cz.zaf.validator.profiles.ValidatorType;

/**
 * Třída pro načtení vstupních parametrů
 * 
 *
 */
public class CmdParamsReader {
	
	final PrintStream output;
	final PrintStream error;
	final MessageProvider messages;

    /**
     * Pozice pri cteni vstupnich parametru
     */
    int pos = 0;

    String args[];
    
    Params params = new Params();
    
    public CmdParamsReader(final PrintStream output, 
    		final PrintStream error, 
    		final MessageProvider messages) {
    	this.output = output;
    	this.error = error;
    	this.messages = messages;
    }

    /**
     * Nacte vstupni parametry
     * 
     * @param args
     *            pole argumentů
     * @return true při úspěšném načtení, false při chybě
     */
    public boolean read(String args[]) {
        this.pos = 0;
        this.args = args;

        while (pos < args.length) {
            String arg = args[pos];
            if (arg.equals("-b") || arg.equals("--batch")) {
            	params.setBatchMode(true);
            } else if (arg.equals("-k") || arg.equals("--keep")) {
            	params.setKeepFiles(true);
            } else if (arg.equals("-w")) {
                if (!readW()) {
                    return false;
                }
            } else if (arg.startsWith("--workdir=")) {
                if (!readWorkDir(arg.substring(10))) {
                    return false;
                }
            } else if (arg.equals("-p")) {
                if (!readProfileP()) {
                    return false;
                }
            } else if (arg.startsWith("--profile=")) {
                if (!readProfile(arg.substring("--profile=".length()))) {
                    return false;
                }
            } else if (arg.equals("-i")) {
                if (!readIdKontroly()) {
                    return false;
                }
            } else if (arg.startsWith("--id=")) {
                if (!readIdKontroly(arg.substring(5))) {
                    return false;
                }
                
            } else
            // for compatibility we keep undocumented parameter -z (replaced by -T)
            // will be dropped in later release
            if (arg.equals("-z")||arg.equals("-T")) {
                if (!readThreatT()) {
                    return false;
                }
            } else
            // for compatibility we keep undocumented parameter --hrozba= (replaced by --threat)
            // will be dropped in later release            	
            if (arg.startsWith("--hrozba=")) {
                if (!readThreat(arg.substring(9))) {
                    return false;
                }
            } else if (arg.startsWith("--threat=")) {
                if (!readThreat(arg.substring(9))) {
                	return false;
                }
            } else if (arg.equals("-o")) {
                if (!readO()) {
                    return false;
                }
            } else if (arg.startsWith("--output=")) {
                if (!readOutput(arg.substring(9))) {
                    return false;
                }
            } else if (arg.equals("-e")) {
                if (!readE()) {
                    return false;
                }
            } else if (arg.startsWith("--exclude=")) {
                if (!readExclude(arg.substring(10))) {
                    return false;
                }
            } else if (arg.equals("-t")) {
                if (!readTypeT()) {
                    return false;
                }
            } else if (arg.startsWith("--type=")) {
                if (!readType(arg.substring("--type=".length()))) {
                    return false;
                }
            } else if (arg.equals("-f")) {
                if (!readF()) {
                    return false;
                }
            } else if (arg.startsWith("--format=")) {
                if (!readOutputFormat(arg.substring("--format=".length()))) {
                    return false;
                }
            } else if (arg.equals("--memTest")) {
                params.setMemTest(true);
            } else if (arg.startsWith("-")) {
            	error.println(messages.getOrDefault("cmd.error.unknown_param", "Unrecognized parameter: {0}", arg));
            	return false;
            } else {
                params.setInputPath(arg);
            }
            pos++;
        }
        return true;
    }

    private boolean readIdKontroly(String arg) {
        if (arg.length() == 0) {
            error.println(messages.getOrDefault("cmd.error.param_id", "Missing id value."));
            return false;
        }
        params.setIdKontroly(arg);
        return true;
    }

    private boolean readIdKontroly() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_id", "Missing id value."));
            return false;
        }
        String arg = args[pos];
        return readIdKontroly(arg);
    }

    private boolean readOutput(String arg) {
        if (arg.length() == 0) {
        	error.println(messages.getOrDefault("cmd.error.param_output", "Missing output path."));
            return false;
        }
        params.setOutputPath(arg);
        return true;
    }

    private boolean readO() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_output", "Missing output path."));
            return false;
        }
        String arg = args[pos];
        return readOutput(arg);
    }

    private boolean readExclude(String arg) {
        if (arg.length() == 0) {
        	error.println(messages.getOrDefault("cmd.error.param_exclude", "Missing list of excluded checks."));
            return false;
        }
        String[] excluded = arg.split(",");
        for (String e : excluded) {
            e = e.trim();
            if (e.length() > 0) {
                params.addExcludeCheck(e);
            }
        }
        return true;
    }

    private boolean readE() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_exclude", "Missing list of excluded checks."));
            return false;
        }
        String arg = args[pos];
        return readExclude(arg);
    }

    private boolean readThreatT() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_threat", "Missing threat description."));
            return false;
        }
        String arg = args[pos];
        return readThreat(arg);
    }

    private boolean readThreat(String arg) {
        if (arg.length() == 0) {
        	error.println(messages.getOrDefault("cmd.error.param_threat", "Missing threat description."));
            return false;
        }
        params.setHrozba(arg);
        return true;
    }

    private boolean readProfileP() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_profile", "Missing validation profile."));
            return false;
        }
        String arg = args[pos];
        return readProfile(arg);
    }

    private boolean readProfile(String arg) {
		switch (arg) {
		case "AUTO":
			return true;
		case "0":
			params.setNsesss2017Profile(cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.DEVEL);
			params.setNsesss2024Profile(cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace.DEVEL);
			break;
		case "1":
		case "METADATA":
		case "SIP_METADATA":
			params.setNsesss2017Profile(
					cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.SKARTACE_METADATA);
			params.setNsesss2024Profile(
					cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace.SKARTACE_METADATA);
			break;
		case "2":
		case "KOMPLET":
		case "SIP_PREVIEW":
			params.setNsesss2017Profile(cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.SKARTACE_UPLNY);
			params.setNsesss2024Profile(cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace.SKARTACE_UPLNY);
			break;
		case "3":
		case "PREJIMKA":
		case "SIP":
			params.setNsesss2017Profile(cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.PREJIMKA);
			params.setNsesss2024Profile(cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace.PREJIMKA);
			break;
		case "FA":
			params.setAp2023Profile(AP2023Profile.FINDING_AID);
			return true;
		case "AD":
			params.setAp2023Profile(AP2023Profile.ARCH_DESC);
			return true;
		case "AIP":
			params.setDa2024Profile(DAAIP2024Profile.AIP);
			return true;
		case "DIP_METADATA":
			params.setDa2024Profile(DAAIP2024Profile.DIP_METADATA);
			return true;
		case "DIP_CONTENT":
			params.setDa2024Profile(DAAIP2024Profile.DIP_CONTENT);
			return true;
		case "SIP_CHANGE":
			params.setDa2024Profile(DAAIP2024Profile.SIP_CHANGE);
			return true;
		default:
			error.println(messages.getOrDefault("cmd.error.param_profile_unrecog", "Unknown validation profile: {0}", arg));
			return false;
		}
        return true;
    }

    private boolean readWorkDir(String arg) {
        if (arg.length() == 0) {
        	error.println(messages.getOrDefault("cmd.error.param_workdir", "Missing working directory parameter."));
            return false;
        }
        params.setWorkDir(arg);
        return true;
    }

    private boolean readW() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_workdir", "Missing working directory parameter."));
            return false;
        }
        params.setWorkDir(args[pos]);
        return true;
    }
    
    private boolean readTypeT() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_type", "Missing validation type."));
            return false;
        }
        String arg = args[pos];
        return readType(arg);
    }

    private boolean readType(String arg) {
		switch (arg) {
		case "AUTO":
			params.setValidationProfile(null);
			break;
		case "NSESSS2017":
			params.setValidationProfile(ValidatorType.NSESSS2017);
			break;
		case "NSESSS2024":
			params.setValidationProfile(ValidatorType.NSESSS2024);
			break;
		case "AP2023":
			params.setValidationProfile(ValidatorType.AP2023);
			break;
		case "DAAIP2024":
			params.setValidationProfile(ValidatorType.DAAIP2024);
			break;
		default:
			error.println(messages.getOrDefault("cmd.error.param_type_unknown", "Unknown validation type: {0}", arg));
			return false;
		}
        return true;
    }
    
    private boolean readF() {
        pos++;
        if (pos == args.length) {
        	error.println(messages.getOrDefault("cmd.error.param_format", "Missing parameter output format."));
            return false;
        }
        String arg = args[pos];
        return readOutputFormat(arg);
    }

    private boolean readOutputFormat(String arg) {
		switch (arg) {
		case "XML_V2":
			params.setVystupniFormat(VystupniFormat.VALIDACE_V2);
			break;
		case "XML_V1":
			params.setVystupniFormat(VystupniFormat.VALIDACE_V1);
			break;
		case "XML_OLD":
			params.setVystupniFormat(VystupniFormat.VALIDACE_SIP);
			break;
		default:
			error.println(messages.getOrDefault("cmd.error.param_format_unknown", "Unknown output format: {0}", arg));
			return false;
		}
        return true;
    }

	public Params getParams() {
		return params;
	}

	/**
	 * Static method to print help
	 * 
	 * @param output Output stream to print help.
	 */
    protected void printUsage() {    	
        output.println(messages.getOrDefault("cmd.help.command", "CmdValidator [switches] <path>"));
        output.println("");
        output.println(messages.getOrDefault("cmd.help.path", "path - path to the SIP/AIP/XML, in case of batch mode to the batch"));
        output.println("");
        output.println(messages.getOrDefault("cmd.help.switches", "Switches:"));
        output.println(" -b|--batch "+messages.getOrDefault("cmd.help.params.batch", "batch mode, path has to be directory with packages."));
        output.println(" -w|--workdir= "+messages.getOrDefault("cmd.help.params.workdir", "Batch mode, path has to be directory with packages."));
        output.println(" -k|--keep "+messages.getOrDefault("cmd.help.params.keep", "keep extracted data on disk for debugging."));
        output.println(" -t|--type="+messages.getOrDefault("cmd.help.params.type", "validation type (AUTO - default)"));
        output.println("      AUTO - "+messages.getOrDefault("cmd.help.params.type_auto", "automatic validation type detection"));
        output.println("      NSESSS2017");
        output.println("      NSESSS2024");
        output.println("      AP2023");
        output.println("      DAAIP2024");
        output.println(" -p|--profile= "+messages.getOrDefault("cmd.help.params.profile", "validation profile (AUTO - default):"));
        output.println("      AUTO - "+messages.getOrDefault("cmd.help.params.profile_auto", "select base checks for given validation type"));
        output.println("      AD - "+messages.getOrDefault("cmd.help.params.profile_ad", "archival description (for AP2023)"));
        output.println("      AIP - "+messages.getOrDefault("cmd.help.params.profile_aip", "interchange AIP format (for DAAIP2024)"));
        output.println("      DIP_CONTENT - "+messages.getOrDefault("cmd.help.params.profile_dip_content", "complete DIP (for DAAIP2024)"));
        output.println("      DIP_METADATA - "+messages.getOrDefault("cmd.help.params.profile_dip_metadata", "DIP with metadata only (for DAAIP2024)"));
        output.println("      FA - "+messages.getOrDefault("cmd.help.params.profile_fa", "finding aid (for AP2023)"));
        output.println("      SIP - "+messages.getOrDefault("cmd.help.params.profile_sip", "complete SIP for ingest (for NSESSS2017 and NSESSS2024)"));
        output.println("      SIP_CHANGE - "+messages.getOrDefault("cmd.help.params.profile_sip_change", "modifying SIP (for DAAIP2024)"));
        output.println("      SIP_METADATA - "+messages.getOrDefault("cmd.help.params.profile_sip_metadata", "SIP with metadata only used for selection of data (for NSESSS2017 and NSESSS2024)"));
        output.println("      SIP_PREVIEW - "+messages.getOrDefault("cmd.help.params.profile_sip_preview", "complete SIP used for selection only (for NSESSS2017 and NSESSS2024)"));
        output.println(" -e|--exclude= "+messages.getOrDefault("cmd.help.params.exclude", "comma separated list of disabled checks"));
        output.println(" -i|--id= "+messages.getOrDefault("cmd.help.params.id", "ID of the running validation"));
        output.println(" -T|--threat= "+messages.getOrDefault("cmd.help.params.threat", "optional description of detected threat (result of antivirus, malware scanner)"));
        output.println(" -o|--output= "+messages.getOrDefault("cmd.help.params.output","name of file or directory where to store validation result"));
        output.println(" -f|--format= "+messages.getOrDefault("cmd.help.params.output_format","output format (default: 1)"));
        output.println("      XML_V2 = "+messages.getOrDefault("cmd.help.params.output_format_v2","XML with generic structure (validation_v2.xsd)"));
        output.println("      XML_V1 = "+messages.getOrDefault("cmd.help.params.output_format_v1","XML with generic structure (validace_v1.xsd)"));
        output.println("      XML_OLD = "+messages.getOrDefault("cmd.help.params.output_format_old","older XML schema usable only for NSESSS2017, NSESSS2024)"));
    }
}
