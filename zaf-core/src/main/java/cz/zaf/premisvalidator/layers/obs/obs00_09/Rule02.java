package cz.zaf.premisvalidator.layers.obs.obs00_09;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule02 extends PremisRule {

	public static final String CODE = "obs02";
	public static final String RULE_TEXT = "Každá událost je popsána typem dle řízeného slovníku. Jiné typy událostí se nesmí používat.";
	public static final String RULE_ERROR = "Událost je nepovoleného typu.";
	public static final String RULE_SOURCE = "CZDAX-PMS0103, CZDAX-PMS0502";
	
	// Set of allowed event types
	public static final Set<String> eventTypes = Set.of(
			// CZDAX-PMP0203 - vznik / digitalizace
			// CZDAX-PMP0303 - vznik
			PremisConstants.EVENT_TYPE_CRE,
			// CZDAX-PMP0205 - smazání
			PremisConstants.EVENT_TYPE_DEL,
			// PMP0207 - migrace
			PremisConstants.EVENT_TYPE_MIG,
			//  PMP0212 - zabalení
			PremisConstants.EVENT_TYPE_PAC,
			// PMP0216 - rozbalení
			PremisConstants.EVENT_TYPE_UNP,
			// CZDAX-PMP0305 - Kontrola neporušenosti obsahu
			PremisConstants.EVENT_TYPE_FIX,
			// CZDAX-PMP0307 - Antivirová kontrola
			PremisConstants.EVENT_TYPE_VIR,
			// CZDAX-PMP0311 - Identifikace formátů
			PremisConstants.EVENT_TYPE_FOR,
			// CZDAX-PMP0314 - Validace
			PremisConstants.EVENT_TYPE_VAL
			);

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		
		List<EventComplexType> events = premis.getEvent();
		for(EventComplexType evnt: events) {
			StringPlusAuthority evntType = evnt.getEventType();
			String type = evntType.getValue();
			if(StringUtils.isBlank(type)) {
				throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdný typ události", 
						ctx.formatPosition(evnt));
			}
			if(!eventTypes.contains(type)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Neplatný typ události: "+type, 
						ctx.formatPosition(evnt));				
			}
		}
	}
}
