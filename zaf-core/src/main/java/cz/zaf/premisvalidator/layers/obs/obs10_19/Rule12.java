package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.OriginalNameComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.Representation;
import cz.zaf.schemas.premis.PremisNS;

public class Rule12 extends PremisRule {

	public static final String CODE = "obs12";
	public static final String RULE_TEXT = "Souhrnné informace odkazují na data z původní přejímky.";
	public static final String RULE_ERROR = "Informace o přejímce neobsahují samotný objekt dat přejímky.";
	public static final String RULE_SOURCE = "CZDAX-PKG0201";


	public Rule12() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		
		Representation repSubmission = null;
				
		for(Object obj: premis.getObject()) {
			if(obj instanceof Representation) {
				Representation rep = (Representation)obj;
				// rep.ge
				OriginalNameComplexType origName = rep.getOriginalName();
				if(origName==null) {
					return;
				}
				if(EarkCz.REPRESENTATION_SUBMISSION.equals(origName.getValue())) {
					if(repSubmission!=null) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Vice reprezentaci submission", ctx.formatPosition(rep));
					}
					repSubmission = rep;
				}
			}
		}
		if(repSubmission==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybí reprezentace submission", ctx.formatPosition(premis));
		}
		ObjectIdentifierComplexType localIdent = null;
		for(ObjectIdentifierComplexType objIdent: repSubmission.getObjectIdentifier()) {
			if(objIdent.getObjectIdentifierType()!=null && PremisNS.IDENT_TYPE_LOCAL.equals(objIdent.getObjectIdentifierType().getValue())) {
				localIdent = objIdent; 
			}
		}
		if(localIdent==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Reprezentace submission nemá platný lokální identifikátor.", ctx.formatPosition(repSubmission));
		}
		RepresentationInfo repInfo = ctx.getRepresentation(localIdent.getObjectIdentifierValue());
		if(repInfo==null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena reprezentace submission s identifikátorem: "+localIdent.getObjectIdentifierValue()+".", ctx.formatPosition(repSubmission));
		}
		if(!EarkCz.REPRESENTATION_SUBMISSION.equals(repInfo.getName())) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Reprezentace s identifikátorem: "+localIdent.getObjectIdentifierValue()+" není submission.", ctx.formatPosition(repSubmission));
		}
	}
}
