package cz.zaf.earkvalidator.layers.dat.dat00_09;

import java.nio.file.Files;
import java.nio.file.Path;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule04 extends AipRule {
	public static final String CODE = "dat04";
	public static final String RULE_TEXT = "Balíček MUSÍ obsahovat podsložku metadata.";
	public static final String RULE_ERROR = "V balíčku chybí složka metadata.";
	public static final String RULE_SOURCE = "CZDAX-PSP0105";
	
	public Rule04() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		Path metadataPath = this.ctx.getLoader().getMetadataPath();
		if(metadataPath == null) {
			throw new ZafException(BaseCode.CHYBA, "Neplatná cesta ke složce metadata");
		}
		if(!Files.isDirectory(metadataPath)) {
			throw new ZafException(BaseCode.CHYBA, "Složka metadata není adresař, cesta: "+metadataPath.toString());
		}		
	}

}
