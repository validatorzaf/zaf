package cz.zaf.premisvalidator.layers.obs.obs00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.File;
import cz.zaf.schema.premis3.ObjectComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schemas.premis.PremisNS;

public class Rule07 extends PremisRule {

	public static final String CODE = "obs07";
	public static final String RULE_TEXT = "Každý objekt typu soubor má uveden svůj lokální identifikátor dle specifikace.";
	public static final String RULE_ERROR = "Chybný odkaz na soubor či balíček.";
	public static final String RULE_SOURCE = "CZDAX-PMS0201, CZDAX-PMS0202";


	public Rule07() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		for(ObjectComplexType obj: premis.getObject()) {
			if(obj instanceof File) {
				File file = (File) obj;
				checkFile(file);
			}
		}
	}

	private void checkFile(File file) {
		ObjectIdentifierComplexType identFound = null;
		for(ObjectIdentifierComplexType objIdent: file.getObjectIdentifier()) {
			if(PremisNS.IDENT_TYPE_LOCAL.equals(objIdent.getObjectIdentifierType().getValue())) {
				if(identFound!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovaně uvedený lokální identifikátor.", ctx.formatPosition(objIdent));
				}
				identFound = objIdent;
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybný odkaz na soubor, typ: "+objIdent.getObjectIdentifierType().getValue()+".", ctx.formatPosition(file));
			}
			String identValue = objIdent.getObjectIdentifierValue();
			if(!ValidatorId.checkFormatId(identValue)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybný odkaz na reprezentaci.", ctx.formatPosition(file));
			}
		}
		if(identFound==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybí lokální identifikátor.", ctx.formatPosition(file));
		}
	}
}
