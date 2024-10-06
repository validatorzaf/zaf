package cz.zaf.earkvalidator.layers.dat.dat00_09;

import java.nio.file.Path;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule05 extends AipRule {
	public static final String CODE = "dat05";
	public static final String RULE_TEXT = "Složka s balíčkem má podobu UUID.";
	public static final String RULE_ERROR = "Označení složky s balíčkem neodpovídá formátu UUID.";
	public static final String RULE_SOURCE = "CZDAX-PMT0102";
	
	public Rule05() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		Path aipPath = this.ctx.getLoader().getAipPath();
		
		if(aipPath == null) {
			throw new ZafException(BaseCode.CHYBA, "Neplatná cesta ke složce");
		}
		
		String dirName = aipPath.getFileName().toString();
		// check if dirName is UUID
		if (!dirName.matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")) {
			throw new ZafException(BaseCode.CHYBA, "Označení složky s balíčkem neodpovímá formátu UUID.");
		}
	}

}
