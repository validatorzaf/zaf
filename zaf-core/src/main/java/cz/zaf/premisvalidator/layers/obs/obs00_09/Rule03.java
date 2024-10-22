package cz.zaf.premisvalidator.layers.obs.obs00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.EventIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;
import cz.zaf.schemas.premis.PremisNS;

public class Rule03 extends PremisRule {

	public static final String CODE = "obs03";
	public static final String RULE_TEXT = "Každá událost má uveden platný identifikátor dle specifikace.";
	public static final String RULE_ERROR = "Událost má uveden chybný identifikátor.";
	public static final String RULE_SOURCE = "CZDAX-PMS0201, CZDAX-PMS0501";


	public Rule03() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		
		for(EventComplexType event: premis.getEvent()) {
			EventIdentifierComplexType eventIdent = event.getEventIdentifier();
			if(eventIdent==null) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event/eventIdentifier.", ctx.formatPosition(event));
			}
			StringPlusAuthority identType = eventIdent.getEventIdentifierType();
			if(!PremisNS.IDENT_TYPE_LOCAL.equals(identType.getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu event/eventIdentifier/eventIdentifierType, value: "+identType.getValue(), ctx.formatPosition(event));
			}
			
			String identValue = eventIdent.getEventIdentifierValue();
			if(!ValidatorId.checkFormatId(identValue)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu event/eventIdentifier/eventIdentifierValue.", ctx.formatPosition(event));
			}
		}
	}
}
