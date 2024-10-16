package cz.zaf.earkvalidator.layers.comp_enc.comp_enc00_09;

import java.nio.file.Path;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.EncodingDetector;
import cz.zaf.common.xml.EncodingDetector.Result;
import cz.zaf.earkvalidator.AipRule;

public class Rule01  extends AipRule  {
	public static final String CODE = "komp_kod01";
	public static final String RULE_TEXT = "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte Order Mark).";
	public static final String RULE_ERROR = "Znaková sada souboru není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).";
	public static final String RULE_SOURCE = "CZDAX-PSP020"; 

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		Path activeFile = ctx.getActiveFile();
		
        Result detected;
        try {
        	detected = EncodingDetector.detect(activeFile, false);
        } catch(Exception e) {
            String chybaKodovani = "Chyba při detekci kódování: " + e.toString();
            throw new ZafException(BaseCode.CHYBA, chybaKodovani, e);        	
        }
        if(detected.hasBom()) {
            String chybaKodovani = "Soubor obsahuje chybně BOM prefix.";
            throw new ZafException(BaseCode.CHYBA, chybaKodovani);        	
        }
        
        if(!detected.getDetectedEncoding().equals("utf-8")) {
            String chybaKodovani = "Kódování souboru: " + detected.getDetectedEncoding() + ". Deklarované kódování: " + 
            		detected.getSchemaEncoding() + ".";
            throw new ZafException(BaseCode.CHYBA, chybaKodovani);        	
        }

        if (!detected.getSchemaEncoding().toLowerCase().equals(detected.getDetectedEncoding())) {
            String chybaKodovani = "Kódování souboru: " + detected.getDetectedEncoding() + ". Deklarované kódování: " + 
            		detected.getSchemaEncoding() + ".";
            throw new ZafException(BaseCode.CHYBA, chybaKodovani);
        }
		
	}
}

