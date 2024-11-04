package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule10 extends PremisRule {

	public static final String CODE = "obs10";
	public static final String RULE_TEXT = "Každá intelektuální entita je buď odkaz na archivní fond, sám balíček nebo jiný archivní balíček.";
	public static final String RULE_ERROR = "Nerozpoznaný druh intelektuální entity.";
	public static final String RULE_SOURCE = "CZDAX-PKG0101, CZDAX-PKG0301";


	public Rule10() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		premis.getObject().forEach(obj -> {
			if(obj instanceof IntellectualEntity) {
				IntellectualEntity intEnt = (IntellectualEntity)obj;
				checkIntEnt(intEnt);
			}
			
		});
	}

	private void checkIntEnt(IntellectualEntity intEnt) {
		// check if link to this package
		ObjectIdentifierComplexType localIdent = null;
		ObjectIdentifierComplexType fondId = null;
		ObjectIdentifierComplexType aipId = null;
		for(ObjectIdentifierComplexType objIdent: intEnt.getObjectIdentifier()) {
			StringPlusAuthority identType = objIdent.getObjectIdentifierType();
			String type = identType.getValue();
			// Rule08 is checking format, here we check only object type
			if(PremisConstants.IDENT_TYPE_LOCAL.equals(type)) {
				if(localIdent!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovaně uvedený lokální identifikátor.", ctx.formatPosition(objIdent));
				}
				localIdent = objIdent;
			} else 
			if(EarkCz.OBJECT_IDENTIFIER_AIP_ID.equals(type)) {
				if(aipId!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovaně uvedený identifikátor AIP_ID.", ctx.formatPosition(objIdent));					
				}
				aipId = objIdent;
			} else
			if(EarkCz.OBJECT_IDENTIFIER_FONDS_ID.equals(type)) {
				if(fondId!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovaně uvedený identifikátor FONDS_ID.", ctx.formatPosition(objIdent));					
				}
				fondId = objIdent;
			} else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Uveden neznámý typ identifikátoru: "+objIdent.getObjectIdentifierType().getValue()+".", ctx.formatPosition(objIdent));
			}
		}
		
		if(localIdent==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen identifikátor typu local.", ctx.formatPosition(intEnt));
		}
		
		if(aipId!=null&&fondId!=null) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neplatná kobinace identifikátorů (AIP_ID, FONDS_ID).", ctx.formatPosition(aipId));
		}

		if(aipId!=null) {
			// AIP ref -> localIdent has to be uuid-...
			if(EarkCz.OBJECT_IDENTIFIER_ITSELF.equals(localIdent.getObjectIdentifierValue())) {
				return;
			}
			if(!ValidatorId.checkFormatId(localIdent.getObjectIdentifierValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná podoba lokálního identifikátoru.", ctx.formatPosition(localIdent));
			}
			// OK
			return;
		}
		if(fondId!=null) {
			// FOND ref -> localIdent has to be uuid-...
			if(!ValidatorId.checkFormatId(localIdent.getObjectIdentifierValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná podoba lokálního identifikátoru.", ctx.formatPosition(localIdent));
			}
			// OK
			return;
		}
		
		// not AIP or FOND_ID -> has to be this package
		if(EarkCz.OBJECT_IDENTIFIER_ITSELF.equals(localIdent.getObjectIdentifierValue())) {
			return;
		}
		
		throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nedostatečně identifikovaná intelektuální entita.", ctx.formatPosition(intEnt));
	}
}
