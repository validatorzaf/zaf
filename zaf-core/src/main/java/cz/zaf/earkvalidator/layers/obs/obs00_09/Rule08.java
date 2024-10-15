package cz.zaf.earkvalidator.layers.obs.obs00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MetsType.MetsHdr;

public class Rule08 extends AipRule {
	public static final String CODE = "obs08";
	public static final String RULE_TEXT = "Neexistuje atribut: mets/metsHdr/@LASTMODDATE.";
	public static final String RULE_ERROR = "Uveden neplatný atribut mets/metsHdr/@LASTMODDATE.";
	public static final String RULE_SOURCE = "CZDAX-PMT0203";

	public Rule08() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		MetsHdr metsHdr = ctx.getMets().getMetsHdr();
		if (metsHdr == null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element MetsHdr.", 
					ctx.formatMetsPosition(ctx.getMets()));
		}
		
		if (metsHdr.getLASTMODDATE() != null) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezen neočekaný atribut.", 
					ctx.formatMetsPosition(metsHdr));
		}
		
	}


}
