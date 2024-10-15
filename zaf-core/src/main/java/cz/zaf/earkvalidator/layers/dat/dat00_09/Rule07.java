package cz.zaf.earkvalidator.layers.dat.dat00_09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import cz.zaf.common.Properties;
import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule07 extends AipRule {
	public static final String CODE = "dat07";
	public static final String RULE_TEXT = "Balíček MUSÍ obsahovat nanejvýš definované množství souborů.";
	public static final String RULE_ERROR = "Balíček obsahuje příliš mnoho souborů.";
	public static final String RULE_SOURCE = "CZDAX-PPR0304";

	public Rule07() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	protected void evalImpl() {
		Path aipPath = ctx.getLoader().getAipPath();
		if(aipPath == null) {
			throw new ZafException(BaseCode.CHYBA, "Neplatná cesta ke složce");
		}
		
		// read limit from properties ZAF_AIP_MAX_SIZE
		String maxFileCountString = System.getProperty(Properties.ZAF_AIP_MAX_FILECOUNT, "50000");
		// parse max size to bytes
		long maxFileCount = Long.parseLong(maxFileCountString);
		
		// count recursively number of files in aipPath
		if(maxFileCount>0) {
			try {
				long fileCount = Files.walk(aipPath)
						.filter(Files::isRegularFile)
						.count();
				if(fileCount > maxFileCount) {
					throw new ZafException(BaseCode.CHYBA, "Balíček obsahuje příliš mnoho souborů. Počet: "+fileCount);
				}
			} catch(IOException e) {
				throw new ZafException(BaseCode.CHYBA, "Nelze urcit počet souborů.", e);
			}
		}
	}
}
