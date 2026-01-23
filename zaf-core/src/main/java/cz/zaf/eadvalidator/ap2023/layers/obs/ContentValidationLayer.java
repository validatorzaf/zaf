package cz.zaf.eadvalidator.ap2023.layers.obs;

import java.util.List;

import cz.zaf.common.validation.BaseRule;
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
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule101;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule103;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule104;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule104a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109.Rule105;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule11;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule12;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule13;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule14;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule15;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule16;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule17;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule18;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19.Rule19;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule20;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule21;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule22;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule23;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule24;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29.Rule25;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule31;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule35;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule36b;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule37;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39.Rule37a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule42;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule43;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule44;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49.Rule49;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule50;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule52;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule53;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule54;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule55;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59.Rule56;

import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule60;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule61;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule62;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule63;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule64;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule65;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule66;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule67;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule68;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69.Rule69;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule70;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule71;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule72;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule73;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74a;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74b;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74c;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule74d;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule75;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule77;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule78;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs70_79.Rule79;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule80;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule81;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule82;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule83;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule84;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs80_89.Rule89;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule93;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule94;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule95;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule96;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule97;
import cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99.Rule99;

import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;

public class ContentValidationLayer extends BaseValidationLayer<EadValidationContext, EadValidationContext> {

	private ValidationSubprofile profilValidace;
	
        //pomůcka
        //archivní popis (dva archivy si něco předají a nemusí to mít podobu pomůcky nemusí to být úplné
	List<Class<? extends BaseRule<EadValidationContext>>> archDescRules = List.of(
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
                        Rule14.class,
                        Rule16.class,
                        Rule18.class,
			Rule19.class,
            Rule20.class,
            Rule21.class,
            Rule22.class,
            Rule23.class,
            Rule24.class,
            Rule25.class,
                        Rule31.class,
                        Rule35.class,
			Rule36.class,
                        Rule37.class,
            Rule42.class,
            Rule43.class, 
            Rule44.class,
            Rule49.class,
                        Rule50.class,
                        Rule52.class,
                        Rule53.class,
                        Rule54.class,
                        Rule55.class,
                        Rule56.class,
            Rule60.class, 
            Rule61.class,
            Rule62.class,
            Rule63.class,
            Rule64.class,
            Rule65.class,
            Rule66.class,
            Rule67.class,
            Rule68.class,
            Rule69.class,
                        Rule70.class,
                        Rule71.class,
                        Rule72.class,
                        Rule73.class,
                        Rule74.class,
                        Rule74a.class,
                        Rule74b.class,
                        Rule74c.class,
                        Rule75.class,
                        Rule77.class,
                        Rule78.class,
                        Rule79.class,
            Rule80.class,
            Rule83.class,
            Rule84.class,
            Rule89.class,
                        Rule93.class,
                        Rule94.class,
                        Rule95.class,
                        Rule96.class,
                        Rule97.class,
                        Rule99.class,
            Rule101.class,
            Rule103.class,
            Rule104.class,
            Rule105.class
);
	//finální pomůcka
        //pomůcka = archivní pomůcka (vyhláška má náležitosti odpovídající záhlaví atd)
	List<Class<? extends BaseRule<EadValidationContext>>> findingAidRules = List.of(
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
                        Rule13.class,
                        Rule14.class,
                        Rule15.class,
                        Rule16.class,
                        Rule17.class,
                        Rule18.class,
                        Rule19.class,
            Rule20.class,
            Rule21.class,
            Rule22.class,
            Rule23.class,
            Rule24.class,
            Rule25.class,
                        Rule31.class,
                        Rule35.class,
			Rule36.class,
                        Rule36a.class, //pro test pravidla jinak sem nepatri
                        Rule36b.class, //pro test pravidla jinak sem nepatri
                        Rule37.class,
                        Rule37a.class, //pro test pravidla jinak sem nepatri
            Rule42.class,
            Rule43.class, 
            Rule44.class,
            Rule49.class,
                        Rule50.class,
                        Rule52.class,
                        Rule53.class,
                        Rule54.class,
                        Rule55.class,
                        Rule56.class,
            Rule60.class, 
            Rule61.class,
            Rule62.class,
            Rule63.class,
            Rule64.class,
            Rule65.class,
            Rule66.class,
            Rule67.class,
            Rule68.class,
            Rule69.class,
                        Rule70.class,
                        Rule71.class,
                        Rule72.class,
                        Rule73.class,
                        Rule74.class,
                        Rule74a.class,
                        Rule74b.class,
                        Rule74c.class,
                        Rule74d.class, //pro test pravidla jinak sem nepatri
                        Rule75.class,
                        Rule77.class,
                        Rule78.class,
                        Rule79.class,
            Rule80.class,
            Rule81.class,  
            Rule82.class,
            Rule83.class,
            Rule84.class,
            Rule89.class,
                        Rule93.class,
                        Rule94.class,
                        Rule95.class,
                        Rule96.class,
                        Rule97.class,
                        Rule99.class,
            Rule101.class,
            Rule103.class,
            Rule104.class,
            Rule104a.class, //pro test pravidla jinak sem nepatri
            Rule105.class 
);
        
        //inherentní popis
	//inherentní arch. popis v aipu popis od puvodce(obecne) -  např co se vyteží ze SIP podle NS
	List<Class<? extends BaseRule<EadValidationContext>>> inherentDescRules = List.of(
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
			Rule14.class,
//                        Rule16.class, NEPROJDE
//                        Rule18.class NEPROJDE
			Rule19.class,
	    Rule20.class,
            Rule21.class, 
            Rule22.class,
                        Rule31.class,
//                        Rule35.class NEPROJDE
                        Rule36a.class,
//                        Rule36b.class NEPROJDE
                        Rule37a.class,
            Rule42.class,    
            Rule43.class, 
            Rule44.class,
            Rule49.class,
//                        Rule50.class, NEPROJDE
//                        Rule52.class, NEPROJDE
                        Rule53.class,
                        Rule54.class,
//                        Rule55.class, NEPROJDE
                        Rule56.class,
            Rule60.class, 
            Rule61.class,
            Rule62.class,
            Rule63.class,
            Rule64.class,
            Rule65.class,
            Rule66.class,
            Rule67.class,
            Rule68.class,
            Rule69.class,
                        Rule70.class,
                        Rule71.class,
                        Rule72.class,
                        Rule73.class,
                        Rule74.class,
                        Rule74a.class,
//                        Rule74b.class, NEPROJDE
                        Rule74c.class,
                        Rule74d.class,
                        Rule75.class,
                        Rule77.class,
                        Rule78.class,
//                        Rule79.class, NEPROJDE
//                        Rule80.class, NEPROJDE
//                        Rule83.class, NEPROJDE
            Rule84.class,
            Rule89.class,
                        Rule93.class,
                        Rule94.class,
                        Rule95.class,
                        Rule97.class,
            Rule101.class,
//            Rule102.class,
            Rule103.class,
            Rule104a.class 
);
        
        //kontextuální popis
        //contextování tím popíše arrchiválii a je to uloženo v AIP
	List<Class<? extends BaseRule<EadValidationContext>>> contextDescRules = List.of(
			Rule01.class,
			Rule03.class,
			Rule04.class,
			Rule04a.class,
                        Rule14.class,
                        Rule16.class,
                        Rule18.class,
			Rule19.class,
            Rule20.class,
            Rule21.class,
            Rule22.class,
                        Rule31.class,
                        Rule35.class,
                        Rule36.class,
                        Rule37a.class,
            Rule42.class,    
            Rule43.class,
            Rule44.class,
            Rule49.class,
                        Rule50.class,
                        Rule52.class,
                        Rule53.class,
                        Rule54.class,
                        Rule55.class,
                        Rule56.class,
            Rule60.class, 
            Rule61.class,
            Rule62.class,
            Rule63.class,
            Rule64.class,
            Rule65.class,
            Rule66.class,
            Rule67.class,
            Rule68.class,
            Rule69.class,
                        Rule70.class,
                        Rule71.class,
                        Rule72.class,
                        Rule73.class,
                        Rule74.class,
                        Rule74a.class,
                        Rule74b.class,
                        Rule74c.class,
                        Rule75.class,
                        Rule77.class,
                        Rule78.class,
                        Rule79.class,
                        Rule80.class,
                        Rule83.class,
                        Rule84.class,
            Rule89.class,
                        Rule93.class,
                        Rule94.class,
                        Rule95.class,
                        Rule97.class,
                        Rule99.class,
            Rule101.class,
////            Rule102.class,
            Rule103.class, 
            Rule104.class,
            Rule105.class
                        );

	public ContentValidationLayer(ValidationSubprofile profilValidace, String innerFileName) {
		super(ValidationLayers.OBSAH, innerFileName);
		this.profilValidace = profilValidace;
	}

	@Override
	protected void validateImpl() {

		this.provedKontrolu(ctx, createRules());
		
		
	}

	public List<? extends BaseRule<EadValidationContext>> createRules() {
		
		List<Class<? extends BaseRule<EadValidationContext>>> ruleClasses;
		if(profilValidace==AP2023Profile.ARCH_DESC) {
			ruleClasses = archDescRules;
		} else if(profilValidace==AP2023Profile.FINDING_AID) {
			ruleClasses = findingAidRules;
		} else if(profilValidace==AP2023Profile.EARK_INHERENT_DESC) {
			ruleClasses = inherentDescRules; 
		} else if(profilValidace==AP2023Profile.EARK_CONTEXTUAL_DESC) {
			ruleClasses = contextDescRules;
		} else {
			throw new IllegalStateException("Neznámý profil: " + profilValidace);
		}
		
		return createRules(ruleClasses);
	}
}
