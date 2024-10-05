package cz.zaf.earkvalidator.layers.dat.dat00_09;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipLoader;
import cz.zaf.earkvalidator.AipLoader.AipSrcType;
import cz.zaf.earkvalidator.AipRule;

public class Rule01 extends AipRule {
	private static final String CODE = "dat01";
	static final public String RULE_TEXT = "Balíček SIP může mít podobu složky.";
	static final public String RULE_ERROR = "Obsah složky není dostupný.";
	static final public String RULE_SOURCE = "CZDAX-PSP0101"; 

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		AipLoader loader = ctx.getLoader();
		if(loader.getAipSrcType()!=AipSrcType.DIRECTORY) {
			return;
		}
		// Kontrola zda slozka existuje
		Path srcPath = loader.getAipSrcPath();
		if(!Files.exists(srcPath)) {
			return;
		}
		// Kontrola zda slozka obsahuje datové soubory
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(srcPath);
			// Kontrola, zda složka není prázdná
			Iterator<Path> it = stream.iterator();
			if(!it.hasNext()) {
				throw new ZafException(BaseCode.CHYBA, "Složka je prázdná: "+srcPath.toString());
			}
			it.next();
		} catch (IOException e) {
			throw new ZafException(BaseCode.CHYBA, "Složku nelze otevřít: "+srcPath.toString(), e);
		}
	}
}
