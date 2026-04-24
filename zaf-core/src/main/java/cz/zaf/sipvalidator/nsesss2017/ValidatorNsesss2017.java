package cz.zaf.sipvalidator.nsesss2017;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.Validator;
import cz.zaf.common.validation.ValidatorInfo;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.validator.profiles.ValidatorType;

public class ValidatorNsesss2017 implements Validator, ValidationProfileInfo {
	
    private final String hrozba;
    private final String workDir;
    private final boolean isKeepFiles;
    private final ProfilValidace profilValidace;
    private final List<String> excludeChecks;
	
	public ValidatorNsesss2017(String hrozba, String workDir, boolean isKeepFiles,
		ProfilValidace profilValidace, List<String> excludeChecks) {
		this.hrozba = hrozba;
		this.workDir = workDir;
		this.isKeepFiles = isKeepFiles;
		this.profilValidace = profilValidace;
		this.excludeChecks = excludeChecks;
	}

	@Override
    public ValidationResult validate(Path path) throws Exception
	{	
		// nahrani sipu
        try (SipLoader sipLoader = new SipLoader(path, workDir, isKeepFiles);) {
        	
            SipValidator sipValidator = new SipValidator(profilValidace, excludeChecks);
            sipValidator.setHrozba(hrozba);
            sipValidator.validate(sipLoader);
        
            return sipLoader.getSip();
        }
	}

    @Override
    public ValidationProfileInfo getProfileInfo() {
        return this;
    }

    @Override
    public String getValidationType() {
    	return ValidatorType.NSESSS2017.toString();
    }

    @Override
    public String getProfileName() {
    	return profilValidace.getName();
    }

    @Override
    public String getRuleVersion() {
        return NsesssV3.ZAF_RULE_VERSION;
    }
    
    public static ValidatorInfo getValidatorInfo() {
    	return new ValidatorInfo() {
    		@Override
    		public List<? extends ValidationLayerType> getValidationLayers() {
    			return List.of(TypUrovenKontroly.values());
    		}

    		@Override
    		public List<? extends ValidationSubprofile> getValidationSubprofiles() {
    			return List.of(ZakladniProfilValidace.values());
    		}
    		
    		@Override
    		public List<Rule<?>> getRules(ValidationLayerType layerType,
    				ValidationSubprofile subProfile) {
    			if(!(layerType instanceof TypUrovenKontroly)) {
        			throw new IllegalStateException("Unexpected layer type: "+layerType);
    			}
    			if(!(subProfile instanceof ProfilValidace)) {
    				throw new IllegalStateException("Unexpected subprofile type: "+subProfile);
    			}
    			TypUrovenKontroly typUrovne = (TypUrovenKontroly) layerType;
    			ProfilValidace profilValidace = (ProfilValidace) subProfile;
				List<Rule<?>> result = new ArrayList<>();
				switch (typUrovne) {
				case SKODLIVY_KOD:
					result.addAll(KontrolaBase.instantiateRules(K00_SkodlivehoKodu.getRuleClasses()));
					break;
				case DATOVE_STRUKTURY:
					result.addAll(new K01_DatoveStruktury().createRules());
					break;
				case ZNAKOVE_SADY:
					result.addAll(new K02_ZnakoveSady().createRules());
					break;
				case SPRAVNOSTI:
					result.addAll(new K03_Spravnosti().createRules());
					break;
				case JMENNE_PROSTORY_XML:
					result.addAll(new K04_JmennychProstoruXML().createRules());
					break;
				case PROTI_SCHEMATU:
					result.addAll(new K05_ProtiSchematu().createRules());
					break;
				case OBSAHOVA:
					result.addAll(new K06_Obsahova(profilValidace).createRules());
					break;
				case KOMPONENT:
					result.addAll(new K07_Komponent(profilValidace).createRules());
					break;
				default:
					throw new IllegalStateException("Unexpected layer type: " + layerType);
				}
				return result;
			}
    	};
    }


}
