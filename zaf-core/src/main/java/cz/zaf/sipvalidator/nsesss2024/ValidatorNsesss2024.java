package cz.zaf.sipvalidator.nsesss2024;

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
import cz.zaf.sipvalidator.nsesss2024.pravidla07.K07PravidloBase;
import cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2024.profily.ZakladniProfilValidace;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.validator.profiles.ValidatorType;

public class ValidatorNsesss2024 implements Validator, ValidationProfileInfo {
	
    private final String hrozba;
    private final String workDir;
    private final boolean isKeepFiles;
    private final ProfilValidace profilValidace;
    private final List<String> excludeChecks;
	
	public ValidatorNsesss2024(String hrozba, String workDir, boolean isKeepFiles,
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
    	return ValidatorType.NSESSS2024.toString();        
    }

    @Override
    public String getProfileName() {
    	return profilValidace.getName();
    }

    @Override
    public String getRuleVersion() {
        return NsesssV4.ZAF_RULE_VERSION;
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
					result.addAll(KontrolaBase.instantiateRules(K01_DatoveStruktury.getRuleClasses()));
					break;
				case ZNAKOVE_SADY:
					result.addAll(KontrolaBase.instantiateRules(K02_ZnakoveSady.getRuleClasses()));
					break;
				case SPRAVNOSTI:
					result.addAll(KontrolaBase.instantiateRules(K03_Spravnosti.getRuleClasses()));
					break;
				case JMENNE_PROSTORY_XML:
					result.addAll(KontrolaBase.instantiateRules(K04_JmennychProstoruXML.getRuleClasses()));
					break;
				case PROTI_SCHEMATU:
					result.addAll(KontrolaBase.instantiateRules(K05_ProtiSchematu.getRuleClasses()));
					break;
				case OBSAHOVA: {
					Rule<KontrolaNsessContext>[] rules = profilValidace.createObsahovaPravidla();
					for (Rule<KontrolaNsessContext> rule : rules) {
						result.add(rule);
					}
				}
					break;
				case KOMPONENT: {
					K07PravidloBase[] rules = profilValidace.createFormatovaPravidla();
					for (K07PravidloBase rule : rules) {
						result.add(rule);
					}
				}
					break;
				default:
					throw new IllegalStateException("Unexpected layer type: " + layerType);
				}
				return result;
			}
    	};
    }


}
