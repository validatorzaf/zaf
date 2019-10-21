package cz.zaf.sipvalid;

import java.io.PrintStream;

import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SipLoader;

/**
 * Command line validator
 *
 */
public class CmdValidator {
	
	String inputPath = null;
	
	protected static void printUsage() {
		PrintStream output = System.out;
		output.println("CmdValidator [<path>]");
	}

	public static void main(String[] args) {
		CmdValidator cmdValidator = new CmdValidator();
		cmdValidator.validate(args);
	}

	private void validate(String[] args) {
		if(!readParams(args)) {
			return;
		}
		validate();
	}

	private void validate() {
		SipLoader sipLoader = new SipLoader(inputPath);
		SIP_MAIN sip = sipLoader.getSip();
		// print result
	}

	private boolean readParams(String[] args) {		
		int i = 0;
		while(i<args.length) {
			String arg = args[i];
			if(inputPath==null) {
				inputPath = arg; 
			} else {
				System.err.println("Unrecognized parameter ("+i+"): "+arg);
				return false;
			}
			i++;
		}
		if(inputPath==null) {
			inputPath = System.getProperty("user.dir");
		}
		return true;
	}

}
