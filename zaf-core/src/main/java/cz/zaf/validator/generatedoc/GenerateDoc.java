package cz.zaf.validator.generatedoc;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerType;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.ValidatorInfo;
import cz.zaf.validator.profiles.ValidationProfile;

public class GenerateDoc {
    
	private final ValidatorInfo validatorInfo;
	private final ValidationProfile validationProfile;

    public GenerateDoc(final ValidationProfile validationProfile) {
    	this.validationProfile = validationProfile;
    	this.validatorInfo = validationProfile.getValidatorInfo();
    }

    private void write(PrintStream out) {
    	// write all layers
    	for(ValidationLayerType layer: validatorInfo.getValidationLayers()) {
    		out.append("= ").append(layer.getDescription()).append("\n\n");
    		
    		write(layer, out);
    	}
    }

    private void write(ValidationLayerType layer, PrintStream out) {
    	// collect rules for all subprofiles
    	Map<Integer, List<ValidationSubprofile> > activeProfiles = new HashMap<>();
    	
    	Map<Integer, Rule<? extends RuleEvaluationContext> > ruleMap = new TreeMap<>();
		for(ValidationSubprofile subProfile: this.validatorInfo.getValidationSubprofiles()) {
			List<Rule<? extends RuleEvaluationContext>> rules = this.validatorInfo.getRules(layer, subProfile);
			for(Rule<? extends RuleEvaluationContext> rule: rules) {
				int code = 0;
				for(int i = 0; i<rule.getCode().length(); i++) {
					char c = rule.getCode().charAt(i);
					if(c>='0'&&c<='9') {
						code = code*10+(c-'0');
					}
				}
				
				ruleMap.put(code, rule);
				
				List<ValidationSubprofile> activationList = activeProfiles.computeIfAbsent(code, r -> new ArrayList<>());
				activationList.add(subProfile);
			}
		}
		
		for(Integer ruleCode: ruleMap.keySet()) {
			Rule<? extends RuleEvaluationContext> rule = ruleMap.get(ruleCode);
			write(out, rule, activeProfiles.get(ruleCode));
		}
		
	}

	private void write(PrintStream out, 
			Rule<? extends RuleEvaluationContext> rule, 
			List<ValidationSubprofile> subprofiles) {
		
        String aktivniText;
        if (subprofiles!=null && subprofiles.size() > 0) {
            List<String> nazvy = subprofiles.stream().map(p -> p.getName()).collect(Collectors.toList());
            aktivniText = String.join(", ", nazvy);
        } else {
            aktivniText = "neaktivní";
        }

        String textPravidla = rule.getDescription();
        textPravidla = textPravidla.replace("\r", "");
        textPravidla = textPravidla.replace("\n- ", "\n* ");

        out.println("[[" + validationProfile + "_" + rule.getCode() + "]]");
        out.println("== " + rule.getCode());
        out.println();
        out.println("[horizontal]");
        out.println();
        out.println("Pravidlo:: " + textPravidla);
        out.println("Kód:: " + rule.getCode());
        out.println("Zdroj:: " + rule.getRuleSource());
        out.println("Popis chyby:: " + rule.getGenericError());
        out.println("Aktivní:: " + aktivniText);
        out.println();		
	}

    public static void main(String[] args) {
    	ValidationProfile validationProfile = ValidationProfile.NSESSS2017;
    	
    	for(int pos = 0; pos < args.length; pos++) {
    		String arg = args[pos];
    		switch(arg) {
    		case "NSESSS2017":
    			validationProfile = ValidationProfile.NSESSS2017;
    			break;
    		case "NSESSS2023":
    			validationProfile = ValidationProfile.NSESSS2023;
    			break;
    		case "AP2023":
    			validationProfile = ValidationProfile.AP2023;
    			break;
    		case "DAAIP2024":
    			validationProfile = ValidationProfile.DAAIP2024;
    			break;
    		}
    	}
    	
        GenerateDoc gd = new GenerateDoc(validationProfile);
        gd.write(System.out);
    }
}
