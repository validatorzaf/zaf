package cz.zaf.sipvalidator.nsesss2017.pravidla02.kod00_09;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.input.BOMInputStream; // lib commons-io-2.4

import com.ibm.icu.text.CharsetDetector; // lib ucu4j-56.jar
import com.ibm.icu.text.CharsetMatch; // lib ucu4j-56.jar

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla02.KodCheckRuleBase;

// Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).
public class Pravidlo1 extends KodCheckRuleBase {

    static final public String KOD = "kod1";
    
    static final public String BOM_PREFIX = "bom+";

    public Pravidlo1() {
        super(KOD,
                "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).",
                "Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).",
                "Požadavek 11.2.7 NSESSS.");
    }

    // zjistí jaké má soubor .xml kódování
    private String getKodovani() throws ZafException {
        // lib ucu4j-56.jar
        CharsetDetector det = new CharsetDetector();

        try (InputStream is = Files.newInputStream(ctx.getContext().getSip().getCestaMets())) {

            BufferedInputStream buf = new BufferedInputStream(is);
            CharsetDetector setText = det.setText(buf);

            // lib ucu4j-56.jar
            CharsetMatch detect = setText.detect();

            String ret = detect.getName();
            ret = ret.toLowerCase();
            // kvůli upřesnění
            if (ret.startsWith("iso"))
                ret = "iso-8859-2";
            if (ret.startsWith("windows"))
                ret = "windows-1250";
            return ret;
        }
        catch (Exception e) {
        	String chybaKodovani = "Chyba při detekci kódování: " + e.toString();
        	throw new ZafException(BaseCode.CHYBA, chybaKodovani);
        }
    }

    private String getKodovaniVDeklaraci() throws ZafException {
        try (InputStream is = Files.newInputStream(ctx.getContext().getSip().getCestaMets())) {
            // lib commons-io-2.4
            BOMInputStream bomIn = new BOMInputStream(is, false);
            if (bomIn.hasBOM()) {
                String chybaKodovani = "Soubor obsahuje chybně BOM prefix.";
                throw new ZafException(BaseCode.CHYBA, chybaKodovani);
            }

            final XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(bomIn);

            return xmlStreamReader.getCharacterEncodingScheme();
        }
        catch (Exception e) {
            ZafException ze;
            if (e instanceof ZafException) {
                // pass ZafException
                ze = (ZafException) e;
            } else {
                String chybaKodovani = "Chyba při detekci kódování: " + e.toString();
                ze = new ZafException(BaseCode.CHYBA, chybaKodovani, e);
            }
            throw ze;
        }

    }

    @Override
    protected void kontrola() {
        
        String chybaKodovani = null;

        if (ctx.getContext().maMetsXml() == false) {
        	chybaKodovani = "Soubor mets.xml neexistuje.";
        	throw new ZafException(BaseCode.CHYBA, chybaKodovani);
        }
        
        String vDeklaraci = getKodovaniVDeklaraci();
        String skutecneKodovani = getKodovani();
        if (!skutecneKodovani.toLowerCase().equals("utf-8")) {
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: " + vDeklaraci + ".";
            throw new ZafException(BaseCode.CHYBA, chybaKodovani);
        }    

        if (!vDeklaraci.toLowerCase().equals(skutecneKodovani)) {
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: " + vDeklaraci + ".";
            throw new ZafException(BaseCode.CHYBA, chybaKodovani);
        }
    }

}