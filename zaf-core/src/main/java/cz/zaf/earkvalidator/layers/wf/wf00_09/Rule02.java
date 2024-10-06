package cz.zaf.earkvalidator.layers.wf.wf00_09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import cz.zaf.common.FileOps;
import cz.zaf.common.Properties;
import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule02 extends AipRule  {
	public static final String CODE = "wf02";
	public static final String RULE_TEXT = "Soubor má předpokládanou velikost.";
	public static final String RULE_ERROR = "Soubor METS.xml je příliš velký.";
	public static final String RULE_SOURCE = "CZDAX-PPR0303"; 

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Path metsPath = ctx.getLoader().getMetsPath();
		if(metsPath==null) {
			throw new ZafException(BaseCode.CHYBA, "Neplatná cesta k souboru METS.xml");
		}
		// check size of mets file
		try {
			long fileSize = Files.size(metsPath);
			
			// read limit from properties ZAF_METS_MAX_SIZE
			String maxSizeStr = System.getProperty(Properties.ZAF_METS_MAX_SIZE, "10MB");
			// parse max size to bytes
			long sizeLimit = FileOps.parseSize(maxSizeStr);
			
			if(sizeLimit > 0 && fileSize > sizeLimit) {
				// format size to user friendly format (KB, MB, GB, TB)
				String sizeStr = FileOps.formatSize(fileSize);
				
				throw new ZafException(BaseCode.CHYBA, "Soubor METS.xml je příliš velký. Zjištěná velikost: "+sizeStr);
			}
		} catch (IOException e) {
        	throw new ZafException(BaseCode.CHYBA, 
        			"Nepodařilo se zjistit velikost souboru METS.xml. Cesta: " + metsPath,
        			ctx.getLoader().getMetsParserError());        	
		}
	}
}
