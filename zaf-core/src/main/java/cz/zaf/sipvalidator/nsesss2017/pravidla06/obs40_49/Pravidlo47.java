package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;

//OBSAHOVÁ č.47 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
public class Pravidlo47 extends K06PravidloBaseOld {
	
	static final public String OBS47 = "obs47";

	public Pravidlo47() {
		super(OBS47, 
				"Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.", 
				"Celistvost komponenty (počítačového souboru) je narušena nebo chybí možnost jejího ověření.", 
				"Bod 2.15. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Element metsFile : nodeListMetsFile) {
            if(!kontrolaSouboru(metsFile)) {
            	return false;
            }
        }
        
        return true;
	}

    private boolean kontrolaSouboru(Element metsFile) {
        if(!ValuesGetter.hasAttribut(metsFile, "CHECKSUMTYPE")){
            return nastavChybu("Element <mets:file> nemá atribut CHECKSUMTYPE.", metsFile);
        }
        String atributChecksumType = ValuesGetter.getValueOfAttribut(metsFile, "CHECKSUMTYPE");
        Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
        if(flocat == null){
            return nastavChybu("Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", metsFile);
        }
        if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
            return nastavChybu("Element <mets:FLocat> nemá atribut xlink:href.", flocat);
        }
        String xlinkHref = ValuesGetter.getValueOfAttribut(flocat, "xlink:href"); // komponenty/jmenosouboru
        xlinkHref = HelperString.replaceSeparators(xlinkHref);
        String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(context.getSip());
        File komponenta = new File(cestaKeKomponente);
        komponenta = new File(komponenta.getParentFile().getAbsolutePath() + File.separator + xlinkHref);
        if(!komponenta.exists()){
//            komponenta = get_komponenta(komponenta.getName());
//        }
//        if(komponenta != null){
//            cestaKeKomponente = komponenta.getAbsolutePath();
                if(xlinkHref.contains(File.separator)){
                    int s = xlinkHref.lastIndexOf(File.separator);
                    String g = xlinkHref.substring(s+1);
                    xlinkHref = g;
                }
                return nastavChybu("Nenalezena příslušná komponenta ve složce komponenty.", "Chybí soubor: " + xlinkHref + ".");
        }
        String spoctenyCheckSum = "";
        try{
            if(atributChecksumType.equals("SHA-512") || atributChecksumType.equals("SHA-256")){
                if(atributChecksumType.equals("SHA-512"))
                {
                    try(InputStream is = new FileInputStream(komponenta);) {                
                    	spoctenyCheckSum = DigestUtils.sha512Hex(is);
                    }
                }

                if(atributChecksumType.equals("SHA-256"))
                {
                    try (InputStream is = new FileInputStream(komponenta);) {                
                    	spoctenyCheckSum = DigestUtils.sha256Hex(is);
                    }
                }                    
            }
            else{
                if(xlinkHref.contains(File.separator)){
                    int s = xlinkHref.lastIndexOf(File.separator);
                    String g = xlinkHref.substring(s+1);
                    xlinkHref = g;
                }
                return nastavChybu("Nepovolený algoritmus v atributu CHECKSUMTYPE.", getMistoChyby(metsFile) + " Komponenta: " + xlinkHref + ".");
            }

        } 
        catch (IOException ex){
//                sipSoubor.get_SIP_Validation().add_rule_obsahova(47, false, seznam_pravidla[47], ex.getLocalizedMessage(), "chyba v kódování SIP souboru");
            if(xlinkHref.contains(File.separator)){
                int s = xlinkHref.lastIndexOf(File.separator);
                String g = xlinkHref.substring(s+1);
                xlinkHref = g;
            }
            return nastavChybu("Nepodařilo se spočítat checksum souboru: " + xlinkHref + ".", "Nenalezen soubor " + xlinkHref + ".");
        }
        
        if(!ValuesGetter.hasAttribut(metsFile, "CHECKSUM")){
            return nastavChybu("Element <mets:file> nemá atribut CHECKSUM.", metsFile);
        }
        
        String checkSum = ValuesGetter.getValueOfAttribut(metsFile, "CHECKSUM");
        checkSum = checkSum.toLowerCase();        
        spoctenyCheckSum = spoctenyCheckSum.toLowerCase();

        if(!spoctenyCheckSum.equals(checkSum)){
            if(xlinkHref.contains(File.separator)){
                int s = xlinkHref.lastIndexOf(File.separator);
                String g = xlinkHref.substring(s+1);
                xlinkHref = g;
            }
            return nastavChybu("CHECKSUM neodpovídá CHECKSUMTYPE.", getMistoChyby(metsFile) + " Soubor: " + xlinkHref + ".");
        }
        return true;
	}
	
	//,
}