/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.input.BOMInputStream; // lib commons-io-2.4
import org.xml.sax.SAXException;

import com.ibm.icu.text.CharsetDetector; // lib ucu4j-56.jar
import com.ibm.icu.text.CharsetMatch; // lib ucu4j-56.jar

import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;

/**
 * Kontroluje kódování SIP souboru. 
 * Potřebuje dvě knihovny a to: ucu4j-56.jar
 *                              lib commons-io-2.4                                                               
 * @author m000xz006159
 */
public class K02_ZnakoveSady
	implements UrovenKontroly
{
	static final public String NAME = "kontrola znakové sady"; 
	
	String kodovaniSipSouboru = "Nezjištěno.", chybaKodovani = "Chyba kódování SIP souboru.";
    boolean jeKodovaniVPoradku = false;
    File sip_na_file;
    
    public K02_ZnakoveSady() {
    }
    
    // zjistí jaké má soubor .xml kódování
    private String getKodovani(File file) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, XMLStreamException{
        String ret;
        // lib ucu4j-56.jar
        CharsetDetector det = new CharsetDetector(); 
        FileInputStream is = new FileInputStream(file);
        
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
    
    private String GetKodovaniVDeklaraci(File file) throws IOException, ParserConfigurationException, SAXException, XMLStreamException{
        final XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader( new FileReader(file) );
        String encodingFromXMLDeclaration = xmlStreamReader.getCharacterEncodingScheme(); 
        if(encodingFromXMLDeclaration != null){
            if(encodingFromXMLDeclaration.toLowerCase().equals("utf-8")){
                kodovaniSipSouboru = "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).";
            }
            else{
                kodovaniSipSouboru = "Znakovou sadou souboru je " + encodingFromXMLDeclaration + ".";
            }
            
        }
        
        // BLOK PŘIDÁN, PROTOŽE XMLSTREAMREADER NEUMÍ ČÍST UTF-8 S BOM
        if (encodingFromXMLDeclaration == null){
            FileInputStream fileInpStream = new FileInputStream(file);
            // lib commons-io-2.4
            BOMInputStream bomIn = new BOMInputStream(fileInpStream, false); // FALSE ZNAMENÁ: PŘESKOČ ZNAKY BOM A DEJ MI JEN ZBYTEK - TÍM SE DOSTANE NA UTF-8
            XMLStreamReader xmlS = XMLInputFactory.newInstance().createXMLStreamReader(bomIn);
            try{
                encodingFromXMLDeclaration = xmlS.getCharacterEncodingScheme();
                if(encodingFromXMLDeclaration.contains("utf-8")){
                kodovaniSipSouboru = "UTF-8 BOM";
                return "UTF-8 BOM";
            }
            else kodovaniSipSouboru = encodingFromXMLDeclaration;
            }
            catch(NullPointerException e){
                kodovaniSipSouboru = "Neuvedeno kódování souboru: <?xml version=\"1.0\" encoding=\"utf-8\"?>.";
            }
        }

        return encodingFromXMLDeclaration;   
    }
    
    static String getSkutecneKodovani(String skutecneKodovani){
        if(skutecneKodovani.startsWith("iso"))
            skutecneKodovani = "iso-8859-2";   
        if(skutecneKodovani.startsWith("windows"))
            skutecneKodovani = "windows-1250";
        return skutecneKodovani;
    }
    
    private boolean JsouSiKodovaniRovna(File file) throws IOException, ParserConfigurationException, SAXException, XMLStreamException{
        String vDeklaraci = GetKodovaniVDeklaraci(file);
        
        if(vDeklaraci == null)
            return false;
        //VYRAZENÍ VŠECH
        
        
        vDeklaraci = vDeklaraci.toLowerCase();        
        String skutecneKodovani = getKodovani(file);        
        if(!skutecneKodovani.toLowerCase().equals("utf-8")){
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: " + vDeklaraci + ".";
            
            return false;
        }
        if(skutecneKodovani.toLowerCase().equals("utf-8") && vDeklaraci.toLowerCase().equals("utf-8 bom")){
            chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: UTF-8 BOM.";
            return false;
        }
        boolean ret = vDeklaraci.equals(skutecneKodovani); 
//        řádek s bom boolean ret = vDeklaraci.equals(skutecneKodovani) || (vDeklaraci.equals("utf-8 bom") && skutecneKodovani.equals("utf-8"));        
        if(!ret) chybaKodovani = "Kódování souboru: " + skutecneKodovani + ". Deklarované kódování: " + vDeklaraci + ".";
        return ret;
        
    }

	@Override
	public void provedKontrolu(KontrolaContext ctx)
			throws IOException, FileNotFoundException, ParserConfigurationException, SAXException, XMLStreamException
	{
		boolean isFailed = ctx.isFailed();
		
        // nastavuje obě hodnoty - pak rozepsat a uložit do SIP souboru
        VysledekKontroly k = new VysledekKontroly(TypUrovenKontroly.ZNAKOVE_SADY,
        		NAME);
        ctx.pridejKontrolu(k);
        if(isFailed) {
        	return;
        }
        
        SipInfo sip = ctx.getSip();
		if (!SIP_MAIN_helper.ma_metsxml(sip)) {
			jeKodovaniVPoradku = false;
		} else {
			sip_na_file = sip.getCesta_mets().toFile();
			jeKodovaniVPoradku = JsouSiKodovaniRovna(sip_na_file);
		}

		if (jeKodovaniVPoradku) {
			PravidloKontroly p = new PravidloKontroly(0, "kod1", true, "", 0, "");
			k.add(p);
			sip.setKodovani(kodovaniSipSouboru);
		} else {
			PravidloKontroly p1 = new PravidloKontroly(0, "kod1", false, chybaKodovani, 0, "");
			k.add(p1);
			sip.setKodovani(kodovaniSipSouboru);
		}
	}

	@Override
	public String getNazev() {
		return NAME;
	}
   
}
