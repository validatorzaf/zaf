package cz.zaf.sipvalidator;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cz.zaf.sipvalidator.nsesss2017.RozparsovaniSipSouboru;
import cz.zaf.sipvalidator.sip.Create_xml;
import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.SipValidator;
import cz.zaf.sipvalidator.sip.UrovenKontroly;

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
		try {
			cmdValidator.validate(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validate(String[] args) throws Exception {
		if(!readParams(args)) {
			return;
		}
		validate();
	}

	private void validate() throws Exception {
		SipLoader sipLoader = new SipLoader(inputPath);
		SipInfo sip = sipLoader.getSip();
		
		List<UrovenKontroly> kontroly = SipValidator.pripravKontroly(true, null, SipValidator.seznam_Plny);
		RozparsovaniSipSouboru rss = new RozparsovaniSipSouboru(sip);
        
        // provedeni kontrol
        KontrolaContext ctx = new KontrolaContext(sip);
        for(UrovenKontroly kontrola: kontroly) {
        	kontrola.provedKontrolu(ctx);
        }
		
		// print result
        Create_xml xmlBuilder = new Create_xml(Collections.singletonList(sip),
        		null, sip.getName(), "plna", "", SipValidator.seznam_Plny, 
        		null, null);
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
