package cz.zaf.sipvalidator.nsesss2024.pravidla02.kod00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.EncodingDetector;
import cz.zaf.common.xml.EncodingDetector.Result;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;

// Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).
public class Pravidlo1 extends PravidloBase {

    static final public String KOD = "kod1";
    
    public Pravidlo1() {
        super(KOD,
                "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).",
                "Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).",
                "Požadavek 9.2.9 NSESSS.");
    }

    @Override
    protected void kontrola() {
        
        if (!ctx.getContext().maMetsXml()) {
            throw new ZafException(BaseCode.CHYBA, "Soubor mets.xml neexistuje.");
        }
        
        Result detected;
        try {
        	detected = EncodingDetector.detect(ctx.getContext().getSip().getCestaMets(), false);
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
