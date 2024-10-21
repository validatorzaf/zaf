package cz.zaf.premisvalidator.layers.obs.obs00_09;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule01 extends PremisRule {

	public static final String CODE = "obs01";
	public static final String RULE_TEXT = "Každá událost je popsána typem dle řízeného slovníku. Jiné typy událostí se nesmí používat.";
	public static final String RULE_ERROR = "Událost je nepovoleného typu.";
	public static final String RULE_SOURCE = "CZDAX-PMS0103, CZDAX-PMS0502";
	
	// Set of allowed event types
	public static final Set<String> eventTypes = Set.of(
			// CZDAX-PKG0501 - vznik
			"cre",
			// CZDAX-PKG0601 - Vložení do digitálního archivu
			"ing",
			// CZDAX-PKG0701 - přesun
			"tra",
			// CZDAX-PKG0801 - export
			"exp",
			// CZDAX-PKG0901 - vznik balíčku
			"ipc"
			);

	public Rule01() {
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
