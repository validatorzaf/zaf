package cz.zaf.validator;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;
import cz.zaf.sipvalidator.formats.VystupniFormat;
import cz.zaf.validator.profiles.ValidatorType;

/**
 * Parametry validátoru
 */
public class Params {
    private String inputPath = System.getProperty("user.dir");

    private String workDir = null;

    /**
     * Priznak davkoveho rezimu
     * 
     * V davkovem rezimu se kontroluje sada SIPu
     */
    private boolean batchMode = false;

    /**
     * Priznak testu pameti
     * 
     * V pripade testu pameti dojde k opakovani testu
     */
    private boolean memTest = false;

    /**
     * Aktivni profil validace
     */
    private cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace nsesssProfile = cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace.SKARTACE_METADATA;
    
    private cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace nsesss2024Profile = cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace.SKARTACE_METADATA;

    /**
     * Profile pro AP2023
     */
    private AP2023Profile ap2023Profile = AP2023Profile.ARCH_DESC;
    
    /**
     * Profile pro DAAIP2024
     */
    private DAAIP2024Profile da2024Profile = null;

    /**
     * Výstupní formát
     */
    private VystupniFormat vystupniFormat = VystupniFormat.VALIDACE_V2;
    
    /**
     * Validation profile
     */
    private ValidatorType validationProfile = null;
    
    /**
     * Popis hrozby
     */
    private String hrozba;

    /**
     * Cesta k vystupu
     */
    private String output;

    /**
     * Identifikátor prováděné kontroly
     */
    private String idKontroly;

    /**
     * Příznak pro zachování rozbalených SIPů
     */
    private boolean keepFiles = false;

    /**
     * Seznam kontrol, ktere se nemaji provadet
     */
    final private List<String> excludeChecks = new ArrayList<>();

    public String getIdKontroly() {
        return idKontroly;
    }

	public void setIdKontroly(String arg) {
		this.idKontroly = arg;		
	}

	public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getWorkDir() {
        return workDir;
    }

	public void setWorkDir(String workDir) {
		this.workDir = workDir;		
	}
    public boolean isBatchMode() {
        return batchMode;
    }

	public void setBatchMode(boolean batchMode) {
		this.batchMode = batchMode;
		
	}
	
	public boolean isMemTest() {
        return memTest;
    }

    public void setMemTest(boolean memTest) {
        this.memTest = memTest;
    }

    public boolean isKeepFiles() {
        return keepFiles;
    }

    public void setKeepFiles(boolean keepFiles) {
        this.keepFiles = keepFiles;
    }
    

	public void setValidationProfile(final ValidatorType validationProfile) {
		this.validationProfile = validationProfile;		
	}
	
	public ValidatorType getValidationProfile() {
		return validationProfile;
	}

    public cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace getNsesss2017Profile() {
        return nsesssProfile;
    }

	public void setNsesss2017Profile(cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace arg) {
		this.nsesssProfile = arg;		
	}

	public cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace getNsesss2024Profile() {
        return nsesss2024Profile;
    }

    public void setNsesss2024Profile(cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace arg) {
		this.nsesss2024Profile = arg;		
	}

    public VystupniFormat getVystupniFormat() {
		return vystupniFormat;
	}

	public DAAIP2024Profile getDa2024Profile() {
		return da2024Profile;
	}

	public List<String> getExcludeChecks() {
        return excludeChecks;
    }

    public String getHrozba() {
        return hrozba;
    }

	public void setHrozba(String arg) {
		this.hrozba = arg;		
	}

    public String getOutput() {
        return output;
    }
    
    public void setOutputPath(String output) {
		this.output = output;
	}


	public void addExcludeCheck(String e) {
		excludeChecks.add(e);		
	}


    public AP2023Profile getAp2023Profile() {
        return ap2023Profile;
    }

	public void setAp2023Profile(AP2023Profile arg) {
		 this.ap2023Profile = arg;		
	}


	public void setDa2024Profile(DAAIP2024Profile arg) {
		this.da2024Profile = arg;		
	}


	public void setVystupniFormat(VystupniFormat arg) {
		 vystupniFormat = arg;		
	}

}
