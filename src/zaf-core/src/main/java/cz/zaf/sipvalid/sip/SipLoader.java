package cz.zaf.sipvalid.sip;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.Validate;

import cz.zaf.sipvalid.sip.SIP_MAIN.LoadType;

public class SipLoader
	implements AutoCloseable
{
	LoadType loadType = LoadType.LT_UNKNOWN;
	SIP_MAIN sip;
	Path sipPath;
	
	/**
	 * Short name of SIP
	 */
	String sipName;
	
	/**
	 * Jméno ZIP souboru se SIPem
	 */
	String sipZilFileName;

	public SipLoader(String inputPath) {
		loadSip(inputPath);
	}

	private void loadSip(String inputPath) {		
		detectLoadType(inputPath);
		
		//sip = new SIP_MAIN(sipName, sipZilFileName, loadType, lenght, cesta)
	}

	private void detectLoadType(String inputPath) {
		sipPath = Paths.get(inputPath);
		if(Files.isDirectory(sipPath)) {
			sipName = sipPath.getName(sipPath.getNameCount()-1).toString();
			loadType = LoadType.LT_DIR;
		} else {
			String lowerCasePath = inputPath.toLowerCase();
			if(lowerCasePath.endsWith(".zip")) {
				loadType = LoadType.LT_ZIP;
				sipZilFileName = inputPath;
			} else
			if(lowerCasePath.endsWith(".xml")) {
				loadType = LoadType.LT_XML;
			}
		}
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public SIP_MAIN getSip() {
		// can be called only on loaded state
		Validate.notNull(sip);
		return sip;
	}

}
