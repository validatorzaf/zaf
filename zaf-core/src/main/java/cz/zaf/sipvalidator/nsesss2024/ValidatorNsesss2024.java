package cz.zaf.sipvalidator.nsesss2024;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.result.ValidationProfileInfo;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.Validator;
import cz.zaf.common.validation.ValidatorInfo;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06KontrolaContext;
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
    		public List<Rule<? extends RuleEvaluationContext>> getRules(ValidationLayerType layerType,
    				ValidationSubprofile subProfile) {
    			if(!(layerType instanceof TypUrovenKontroly)) {
        			throw new IllegalStateException("Unexpected layer type: "+layerType);
    			}
    			if(!(subProfile instanceof ProfilValidace)) {
    				throw new IllegalStateException("Unexpected subprofile type: "+subProfile);
    			}
    			TypUrovenKontroly typUrovne = (TypUrovenKontroly) layerType;
    			ProfilValidace profilValidace = (ProfilValidace) subProfile;
				List<Rule<? extends RuleEvaluationContext>> result = new ArrayList<>();
				List<? extends PravidloBase> pravidla = null;
				switch (typUrovne) {
				case SKODLIVY_KOD:
					pravidla = new K00_SkodlivehoKodu().getRules();
					break;
				case DATOVE_STRUKTURY:
					pravidla = K01_DatoveStruktury.getRules();
					break;
				case ZNAKOVE_SADY:
					pravidla = K02_ZnakoveSady.getRules();
					break;
				case SPRAVNOSTI:
					pravidla = K03_Spravnosti.getRules();
					break;
				case JMENNE_PROSTORY_XML:
					pravidla = K04_JmennychProstoruXML.getRules();
					break;
				case PROTI_SCHEMATU:
					pravidla = K05_ProtiSchematu.getRules();
					break;
				case OBSAHOVA: {
					Rule<K06KontrolaContext>[] rules = profilValidace.createObsahovaPravidla();
					for (Rule<K06KontrolaContext> rule : rules) {
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

				if (pravidla != null) {
					for (PravidloBase p : pravidla) {
						result.add(p);
					}
				}
				return result;
			}
    	};
    }


}
