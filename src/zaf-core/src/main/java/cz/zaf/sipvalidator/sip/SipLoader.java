package cz.zaf.sipvalidator.sip;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.Validate;

import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

public class SipLoader
	implements AutoCloseable
{
	LoadType loadType = LoadType.LT_UNKNOWN;
	SipInfo sip;
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
		
		// TODO: osetreni unzip
		long l = SIP_MAIN_helper.get_sip_lenght(sipPath);
		sip = new SipInfo(sipName, sipZilFileName, loadType, l, sipPath);
		//sipName, sipZilFileName, loadType, lenght, cesta)
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

	public SipInfo getSip() {
		// can be called only on loaded state
		Validate.notNull(sip);
		return sip;
	}

}
