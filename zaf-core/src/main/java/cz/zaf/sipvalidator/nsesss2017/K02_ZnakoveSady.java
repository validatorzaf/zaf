/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.input.BOMInputStream; // lib commons-io-2.4

import com.ibm.icu.text.CharsetDetector; // lib ucu4j-56.jar
import com.ibm.icu.text.CharsetMatch; // lib ucu4j-56.jar

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekPravidla;

/**
 * Kontroluje kódování SIP souboru.
 * Potřebuje dvě knihovny a to: ucu4j-56.jar
 * lib commons-io-2.4
 * 
 */
public class K02_ZnakoveSady
        extends KontrolaBase {
    static final public String NAME = "znakové sady";

    static final public String KOD1 = "kod1";

    static final public String BOM_PREFIX = "bom+";

    String kodovaniSipSouboru;
    String chybaKodovani;

    boolean jeKodovaniVPoradku = false;
    File sip_na_file;

    public K02_ZnakoveSady() {
    }

    // zjistí jaké má soubor .xml kódování
    private String getKodovani() throws IOException {
        String ret;
        // lib ucu4j-56.jar
        CharsetDetector det = new CharsetDetector();

        try (InputStream is = Files.newInputStream(ctx.getSip().getCestaMets())) {

            BufferedInputStream buf = new BufferedInputStream(is);
            CharsetDetector setText = det.setText(buf);

            // lib ucu4j-56.jar
            CharsetMatch detect = setText.detect();

            ret = detect.getName();
            ret = ret.toLowerCase();
            // kvůli upřesnění
            ret = getSkutecneKodovani(ret);
            return ret;
        }
    }

    private String getKodovaniVDeklaraci() throws IOException, XMLStreamException, FactoryConfigurationError {
        String encodingFromXMLDeclaration;
        try (InputStream is = Files.newInputStream(ctx.getSip().getCestaMets())) {
            // lib commons-io-2.4
            BOMInputStream bomIn = new BOMInputStream(is, false);

            final XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(bomIn);

            encodingFromXMLDeclaration = xmlStreamReader.getCharacterEncodingScheme();

            // Bom neni podporovan
            if (bomIn.hasBOM()) {
                return BOM_PREFIX + encodingFromXMLDeclaration;
            }

            if (encodingFromXMLDeclaration != null) {
                if (encodingFromXMLDeclaration.toLowerCase().equals("utf-8")) {
                    kodovaniSipSouboru = "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).";
                } else {
                    kodovaniSipSouboru = "Znakovou sadou souboru je " + encodingFromXMLDeclaration + ".";
                }

            }
        }

        return encodingFromXMLDeclaration;
    }

    static String getSkutecneKodovani(String skutecneKodovani) {
        if (skutecneKodovani.startsWith("iso"))
            skutecneKodovani = "iso-8859-2";
        if (skutecneKodovani.startsWith("windows"))
            skutecneKodovani = "windows-1250";
        return skutecneKodovani;
    }

    private boolean jsouSiKodovaniRovna() throws IOException, XMLStreamException, FactoryConfigurationError {
        String vDeklaraci = getKodovaniVDeklaraci();

        if (vDeklaraci == null)
            return false;
        
        if (vDeklaraci.startsWith(BOM_PREFIX)) {
            chybaKodovani = "Soubor obsahuje chybně BOM prefix. Deklarované kódování: " + vDeklaraci + ".";

            return false;
        }

        vDeklaraci = vDeklaraci.toLowerCase();
        String skutecneKodovani = getKodovani();
        if (!skutecneKodovani.toLowerCase().equals("utf-8")) {
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: " + vDeklaraci + ".";

            return false;
        }
        boolean ret = vDeklaraci.equals(skutecneKodovani);
        //        řádek s bom boolean ret = vDeklaraci.equals(skutecneKodovani) || (vDeklaraci.equals("utf-8 bom") && skutecneKodovani.equals("utf-8"));        
        if (!ret)
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: " + vDeklaraci + ".";
        return ret;

    }

    @Override
    public void provedKontrolu() {
        SipInfo sip = ctx.getSip();

        kodovaniSipSouboru = "Nezjištěno.";
        jeKodovaniVPoradku = false;
        chybaKodovani = null;

        if (ctx.maMetsXml()) {
            try {
                jeKodovaniVPoradku = jsouSiKodovaniRovna();
                if (chybaKodovani == null) {
                    chybaKodovani = "Chyba kódování SIP souboru.";
                }
            } catch (Exception e) {
                // chyba pri zpracovani
                chybaKodovani = "Chyba při detekci kódování: " + e.toString();
            }
        }

        sip.setKodovani(kodovaniSipSouboru);

        if (!jeKodovaniVPoradku) {
            String obecnyPopisChyby = "Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).";
            VysledekPravidla p = new VysledekPravidla(KOD1,
                    "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).", // text
                    chybaKodovani,
                    obecnyPopisChyby, null,
                    "Požadavek 11.2.7 NSESSS.",
                    BaseCode.ERROR);
            vysledekKontroly.add(p);
        }

    }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.ZNAKOVE_SADY;
    }

}
