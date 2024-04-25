package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.Validator;

public class ValidatorNsesss2017 implements Validator {
	
	private String hrozba = "";
	private String workDir = "";
	private boolean isKeepFiles = false;
	private ProfilValidace profilValidace = null;
	private List<String> excludeChecks = null;
	
	public ValidatorNsesss2017(String hrozba, String workDir, boolean isKeepFiles,
		ProfilValidace profilValidace, List<String> excludeChecks) {
		this.hrozba = hrozba;
		this.workDir = workDir;
		this.isKeepFiles = isKeepFiles;
		this.profilValidace = profilValidace;
		this.excludeChecks = excludeChecks;
	}

	@Override
	public SipInfo validateBalicek(String balicekPath) throws Exception 
	{	
		// nahrani sipu
        try(SipLoader sipLoader = new SipLoader(balicekPath,
                workDir, isKeepFiles);) {
        	
            SipValidator sipValidator = new SipValidator(profilValidace, excludeChecks);
            sipValidator.setHrozba(hrozba);
            sipValidator.validate(sipLoader);
        
            return sipLoader.getSip();
        }
	}

}
