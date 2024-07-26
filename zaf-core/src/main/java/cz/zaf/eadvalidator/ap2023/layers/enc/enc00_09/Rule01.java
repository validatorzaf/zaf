package cz.zaf.eadvalidator.ap2023.layers.enc.enc00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.EncodingDetector;
import cz.zaf.common.xml.EncodingDetector.Result;
import cz.zaf.eadvalidator.ap2023.EadRule;

public class Rule01 extends EadRule {
    static final public String CODE = "kod1";

    public Rule01() {
        super(CODE,
                "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte Order Mark).",
                "Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).",
                "Část 1.1.5 profilu EAD3 MV ČR");
    }

    @Override
    protected void evalImpl() {
        Result detected;
        try {
        	detected = EncodingDetector.detect(ctx.getLoader().getFilePath(), false);
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
