package cz.zaf.earkvalidator.layers.obs.obs00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MetsType.MetsHdr;

public class Rule07 extends AipRule {
	public static final String CODE = "obs07";
	public static final String RULE_TEXT = "V atributu mets/metsHdr/@CREATEDATE musí být zapsán čas vzniku balíčku.";
	public static final String RULE_ERROR = "Chybí atribut mets/metsHdr/@CREATEDATE.";
	public static final String RULE_SOURCE = "CZDAX-PMT0202";

	public Rule07() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		MetsHdr metsHdr = ctx.getMets().getMetsHdr();
		if (metsHdr == null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element MetsHdr.", 
					ctx.formatMetsPosition(ctx.getMets()));
		}
		
		if (metsHdr.getCREATEDATE() == null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/metsHdr/@CREATEDATE.", 
					ctx.formatMetsPosition(ctx.getMets()));
		}
	}


}
