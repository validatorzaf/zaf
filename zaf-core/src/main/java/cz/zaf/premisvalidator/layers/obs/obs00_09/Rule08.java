package cz.zaf.premisvalidator.layers.obs.obs00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.ObjectComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schemas.premis.PremisNS;

public class Rule08 extends PremisRule {

	public static final String CODE = "obs08";
	public static final String RULE_TEXT = "Každá intelektuální entita má uveden svůj lokální identifikátor dle specifikace.";
	public static final String RULE_ERROR = "Chybný identifikátor intelektuální entity.";
	public static final String RULE_SOURCE = "CZDAX-PMS0201";


	public Rule08() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		for(ObjectComplexType obj: premis.getObject()) {
			if(obj instanceof IntellectualEntity) {
				IntellectualEntity intEnt = (IntellectualEntity) obj;
				checkIntEnt(intEnt);
			}
		}
	}

	private void checkIntEnt(IntellectualEntity intEnt) {
		ObjectIdentifierComplexType identFound = null;
		for(ObjectIdentifierComplexType objIdent: intEnt.getObjectIdentifier()) {
			if(PremisNS.IDENT_TYPE_LOCAL.equals(objIdent.getObjectIdentifierType().getValue())) {
				if(identFound!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovaně uvedený lokální identifikátor.", ctx.formatPosition(objIdent));
				}
				identFound = objIdent;
			} else {
				continue;
			}
			String identValue = objIdent.getObjectIdentifierValue();
			if(EarkCz.OBJECT_IDENTIFIER_ITSELF.equals(identValue)) {
				continue;
			}			
			if(!ValidatorId.checkFormatId(identValue)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybný odkaz na reprezentaci.", ctx.formatPosition(intEnt));
			}
		}
		if(identFound==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybí lokální identifikátor.", ctx.formatPosition(intEnt));
		}		
	}
}
