package cz.zaf.earkvalidator.layers.dat.dat00_09;

import java.nio.file.Files;
import java.nio.file.Path;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule03 extends AipRule {
	public static final String CODE = "dat03";
	public static final String RULE_TEXT = "Balíček MUSÍ obsahovat kořenový METS.xml.";
	public static final String RULE_ERROR = "V balíčku chybí soubor METS.xml.";
	public static final String RULE_SOURCE = "CZDAX-PSP0104";
	
	public Rule03() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		Path metsPath = this.ctx.getLoader().getMetsPath();
		if(metsPath == null) {
			throw new ZafException(BaseCode.CHYBA, "Neplatná cesta k souboru METS.xml");
		}
		if(!Files.isRegularFile(metsPath)) {
			throw new ZafException(BaseCode.CHYBA, "Soubor METS.xml nenalezen: "+metsPath.toString());
		}		
	}

}
