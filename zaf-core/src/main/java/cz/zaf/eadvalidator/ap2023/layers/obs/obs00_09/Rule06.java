package cz.zaf.eadvalidator.ap2023.layers.obs.obs00_09;


import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Ead;
import cz.zaf.schema.ead3.Filedesc;
import cz.zaf.schema.ead3.Publicationstmt;

public class Rule06 extends EadRule {
	
	static final public String CODE = "obs6";
	static final public String RULE_TEXT = "Element <ead:filedesc> obsahuje element <ead:publicationstmt>.";
	static final public String RULE_ERROR = "Element <ead:filedesc> neobsahuje element <ead:publicationstmt>.";
	static final public String RULE_SOURCE = "Část 4.1 profilu EAD3 MV ČR"; 
	
	public Rule06() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Ead ead = ctx.getEad();
		// must exist / ze schematu
		Filedesc fileDesc = ead.getControl().getFiledesc();
		Publicationstmt publStmt = fileDesc.getPublicationstmt();		
		if(publStmt==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybi element.", ctx.formatEadPosition(fileDesc));
		}
	}
}
