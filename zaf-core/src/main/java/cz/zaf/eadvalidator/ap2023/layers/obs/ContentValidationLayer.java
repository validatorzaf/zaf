package cz.zaf.eadvalidator.ap2023.layers.obs;

import java.util.List;

import cz.zaf.common.validation.BaseValidationLayer;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidationLayers;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule01;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule02;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule03;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule04;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule04a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule05;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule06;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule07;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule08;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09.Rule09;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule11;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule12;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule15;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule19;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule20;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule22;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule23;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule25;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule27; 

import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule31;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36;

import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class ContentValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	private ValidationSubprofile profilValidace;
	
        //archivní popis (dva archivy si něco předají a nemusí to mít podobu pomůcky nemusí to být úplné
	Class<?> archDescRules[] = {
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
			Rule19.class,
            Rule20.class,
            Rule22.class,
            Rule25.class,
            Rule27.class,
                        Rule31.class,
			Rule36.class,
	};
	
        //finální pomůcka = archivní pomůcka (vyhláška má náležitosti odpovídající záhlaví atd)
	Class<?> findingAidRules[] = {
			Rule01.class,
			Rule02.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
			Rule05.class,
			Rule06.class,
			Rule07.class,
			Rule08.class,
			Rule09.class,
			Rule11.class,
			Rule12.class,
            Rule15.class,
            Rule19.class,
            Rule20.class,
            Rule22.class,
            Rule23.class,
            Rule25.class,
            Rule27.class,
                        Rule31.class,
			Rule36.class,
	};

        //inherentní arch. popis v aipu popis od puvodce(obecne) -  např co se vyteží ze SIP podle NS
	Class<?> inherentDescRules[] = {
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
			Rule19.class,
			Rule20.class,
            Rule22.class,
            Rule25.class,
            Rule27.class,
                        Rule31.class,
	};
        
        //contextování tím popíše arrchiválii a je to uloženo v AIP
	Class<?> contextDescRules[] = {
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
			Rule19.class,
			Rule20.class,
            Rule22.class,
            Rule25.class,
            Rule27.class,
                        Rule31.class,
	};

	public ContentValidationLayer(ValidationSubprofile profilValidace, String innerFileName) {
		super(ValidationLayers.OBSAH, innerFileName);
		this.profilValidace = profilValidace;
	}

	@Override
	protected void validateImpl() {
		
		Class<?> ruleClasses[];
		if(profilValidace==AP2023Profile.ARCH_DESC) {
			ruleClasses = archDescRules;
		} else if(profilValidace==AP2023Profile.FINDING_AID) {
			ruleClasses = findingAidRules;
		} else {
			throw new IllegalStateException("Neznámý profil: " + profilValidace);
		}
		
		List<Rule<EadValidationContext>> rules = createRules(ruleClasses);

		this.provedKontrolu(ctx, rules);
		
		
	}
}
