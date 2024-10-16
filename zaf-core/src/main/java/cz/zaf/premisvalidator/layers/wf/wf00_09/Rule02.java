package cz.zaf.premisvalidator.layers.wf.wf00_09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import cz.zaf.common.FileOps;
import cz.zaf.common.Properties;
import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.premisvalidator.PremisRule;

public class Rule02 extends PremisRule  {
	public static final String CODE = "komp_wf02";
	public static final String RULE_TEXT = "Soubor má předpokládanou velikost.";
	public static final String RULE_ERROR = "Metadatový soubor je příliš velký.";
	public static final String RULE_SOURCE = "CZDAX-PPR0303"; 

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Path filePath = ctx.getActiveFile();
		if(filePath==null) {
			throw new ZafException(BaseCode.CHYBA, "Neplatná cesta k souboru: "+filePath);
		}
		// check size of mets file
		try {
			long fileSize = Files.size(filePath);
			
			// read limit from properties ZAF_METS_MAX_SIZE
			String maxSizeStr = System.getProperty(Properties.ZAF_METS_MAX_SIZE, "10MB");
			// parse max size to bytes
			long sizeLimit = FileOps.parseSize(maxSizeStr);
			
			if(sizeLimit > 0 && fileSize > sizeLimit) {
				// format size to user friendly format (KB, MB, GB, TB)
				String sizeStr = FileOps.formatSize(fileSize);
				
				throw new ZafException(BaseCode.CHYBA, "Soubor "+filePath.toString()+" je příliš velký. Zjištěná velikost: "+sizeStr);
			}
		} catch (IOException e) {
        	throw new ZafException(BaseCode.CHYBA, 
        			"Nepodařilo se zjistit velikost souboru. Cesta: " + filePath);        	
		}
	}
}
