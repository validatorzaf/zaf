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

import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Kontroluje kódování SIP souboru.
 * Potřebuje dvě knihovny a to: ucu4j-56.jar
 * lib commons-io-2.4
 * 
 * @author m000xz006159
 */
public class K02_ZnakoveSady
        extends KontrolaBase {
    static final public String NAME = "kontrola znakové sady";

    static final public String KOD1 = "kod1";

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
            final XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(is);
            encodingFromXMLDeclaration = xmlStreamReader.getCharacterEncodingScheme();
            if (encodingFromXMLDeclaration != null) {
                if (encodingFromXMLDeclaration.toLowerCase().equals("utf-8")) {
                    kodovaniSipSouboru = "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).";
                } else {
                    kodovaniSipSouboru = "Znakovou sadou souboru je " + encodingFromXMLDeclaration + ".";
                }

            }
        }

        // BLOK PŘIDÁN, PROTOŽE XMLSTREAMREADER NEUMÍ ČÍST UTF-8 S BOM
        if (encodingFromXMLDeclaration == null) {
            try (InputStream is = Files.newInputStream(ctx.getSip().getCestaMets())) {
                // lib commons-io-2.4
                BOMInputStream bomIn = new BOMInputStream(is, false); // FALSE ZNAMENÁ: PŘESKOČ ZNAKY BOM A DEJ MI JEN ZBYTEK - TÍM SE DOSTANE NA UTF-8
                XMLStreamReader xmlS = XMLInputFactory.newInstance().createXMLStreamReader(bomIn);

                encodingFromXMLDeclaration = xmlS.getCharacterEncodingScheme();
                if (encodingFromXMLDeclaration != null && encodingFromXMLDeclaration.contains("utf-8")) {
                    kodovaniSipSouboru = "UTF-8 BOM";
                    return "UTF-8 BOM";
                } else {
                    kodovaniSipSouboru = encodingFromXMLDeclaration;
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
        //VYRAZENÍ VŠECH

        vDeklaraci = vDeklaraci.toLowerCase();
        String skutecneKodovani = getKodovani();
        if (!skutecneKodovani.toLowerCase().equals("utf-8")) {
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: " + vDeklaraci + ".";

            return false;
        }
        if (skutecneKodovani.toLowerCase().equals("utf-8") && vDeklaraci.toLowerCase().equals("utf-8 bom")) {
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: UTF-8 BOM.";
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

        String obecnyPopisChyby = null;
        if (!jeKodovaniVPoradku) {
            obecnyPopisChyby = "Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).";
        }

        PravidloKontroly p = new PravidloKontroly(KOD1, jeKodovaniVPoradku,
                "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).", // text
                chybaKodovani,
                obecnyPopisChyby, null,
                "Požadavek 11.2.7 NSESSS.");
        vysledekKontroly.add(p);
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
