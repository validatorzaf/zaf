package cz.zaf.premisvalidator.layers.obs.obs00_09;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.schema.premis3.ObjectComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.OriginalNameComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.Representation;

public class Rule06 extends PremisRule {

	public static final String CODE = "obs06";
	public static final String RULE_TEXT = "Odkazovaná reprezentace má platný identifikátor a název.";
	public static final String RULE_ERROR = "Chybný odkaz na reprezentaci.";
	public static final String RULE_SOURCE = "CZDAX-PMS0201, CZDAX-PMS0401, CZDAX-PMS0402, CZDAX-PMS0403";


	public Rule06() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		for(ObjectComplexType obj: premis.getObject()) {
			if(obj instanceof Representation) {
				Representation rep = (Representation) obj;
				List<ObjectIdentifierComplexType> objIdents = rep.getObjectIdentifier();
				if(objIdents==null || objIdents.size()==0) {
					throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybný odkaz na reprezentaci.", ctx.formatPosition(rep));
				}
				boolean localIdentFound = false;
				for(ObjectIdentifierComplexType objIdent: objIdents) {
					if(PremisConstants.IDENT_TYPE_LOCAL.equals(objIdent.getObjectIdentifierType().getValue())) {
						localIdentFound = true;
					} else {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybný odkaz na reprezentaci, typ: "+objIdent.getObjectIdentifierType().getValue()+".", ctx.formatPosition(rep));
					}
					String identValue = objIdent.getObjectIdentifierValue();
					if(!ValidatorId.checkFormatId(identValue)) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybný odkaz na reprezentaci.", ctx.formatPosition(rep));
					}
					// get if id exists
					RepresentationInfo representationInfo = ctx.getRepresentation(identValue);
					if(representationInfo==null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybný odkaz na reprezentaci ("+identValue+").", ctx.formatPosition(rep));
					}
					OriginalNameComplexType origName = rep.getOriginalName();
					if(origName==null || StringUtils.isBlank(origName.getValue())) {
						throw new ZafException(BaseCode.CHYBI_ELEMENT, "Reprezentace nemá uveden název.", ctx.formatPosition(rep));
					}
					if(!Objects.equals(origName.getValue(), representationInfo.getName())) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Neočekávaný název reprezentace, očekávaná hodnota: "+representationInfo.getName()+", aktualní hodnota: "+origName.getValue()+".", ctx.formatPosition(rep));
					}
				}
				if(!localIdentFound) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybí lokální identifikátor.", ctx.formatPosition(rep));
				}
			}
		}
	}
}
