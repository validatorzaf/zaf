package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.SignificantPropertiesComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule11 extends PremisRule {

	public static final String CODE = "obs11";
	public static final String RULE_TEXT = "Archivní soubor má uvedenu příslušnost k archivu.";
	public static final String RULE_ERROR = "Archivní soubor nemá správně uveden pečující archiv.";
	public static final String RULE_SOURCE = "CZDAX-PKG0104";


	public Rule11() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		premis.getObject().forEach(obj -> {
			if(obj instanceof IntellectualEntity) {
				IntellectualEntity intEnt = (IntellectualEntity)obj;
				checkFond(intEnt);
			}
			
		});
	}

	private void checkFond(IntellectualEntity intEnt) {
		// check if link to this package
		ObjectIdentifierComplexType fondId = null;
		for(ObjectIdentifierComplexType objIdent: intEnt.getObjectIdentifier()) {
			StringPlusAuthority identType = objIdent.getObjectIdentifierType();
			String type = identType.getValue();
			if(EarkCz.OBJECT_IDENTIFIER_FONDS_ID.equals(type)) {
				if(fondId!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovaně uvedený identifikátor FONDS_ID.", ctx.formatPosition(objIdent));					
				}
				fondId = objIdent;
			}
		}
		if(fondId==null) {
			return;
		}
		
		// check significant props
		SignificantPropertiesComplexType instIdObj = null;
		for(SignificantPropertiesComplexType signProp: intEnt.getSignificantProperties()) {
			if(signProp.getSignificantPropertiesType1()==null) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybí typ signifikátních vlastnosti.", ctx.formatPosition(signProp));
			}
			if(EarkCz.SIGNIFICANT_PROPS_INSTITUTION_ID.equals(signProp.getSignificantPropertiesType1().getValue())) {
				if(instIdObj!=null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Opakovaně uvedená instituce.", ctx.formatPosition(signProp));
				}
				instIdObj = signProp;
				continue;
			}
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Uveden neznámý typ signifikátních vlastnosti: "+signProp.getSignificantPropertiesType1().getValue()+".", ctx.formatPosition(signProp));
		}
		if(instIdObj==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí instituce.", ctx.formatPosition(intEnt));
		}
		
	}
}
