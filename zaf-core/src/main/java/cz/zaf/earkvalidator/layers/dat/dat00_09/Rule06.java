package cz.zaf.earkvalidator.layers.dat.dat00_09;

import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import cz.zaf.common.FileOps;
import cz.zaf.common.Properties;
import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule06 extends AipRule {
	public static final String CODE = "dat06";
	public static final String RULE_TEXT = "Balíček MUSÍ mít technicky zpracovatelnou velikost. Výchozí limit pro maximální velikost balíčku je stanoven na 10 GB.";
	public static final String RULE_ERROR = "Balíček je příliš velký.";
	public static final String RULE_SOURCE = "CZDAX-PPR0303";

	public Rule06() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		Path aipPath = this.ctx.getLoader().getAipPath();
		
		if(aipPath == null) {
			throw new ZafException(BaseCode.CHYBA, "Neplatná cesta ke složce");
		}
		
		// Count recursively size of directory aipPath
		long size = FileUtils.sizeOfDirectory(aipPath.toFile());
		
		// read limit from properties ZAF_AIP_MAX_SIZE
		String maxSizeStr = System.getProperty(Properties.ZAF_AIP_MAX_SIZE, "10GB");
		// parse max size to bytes
		long sizeLimit = FileOps.parseSize(maxSizeStr);
		
		if(sizeLimit > 0 && size > sizeLimit) {
			// format size to user friendly format (KB, MB, GB, TB)
			String sizeStr = FileOps.formatSize(size);
			
			throw new ZafException(BaseCode.CHYBA, "Balíček je příliš velký. Zjištěná velikost: "+sizeStr);
		}
	}
}
